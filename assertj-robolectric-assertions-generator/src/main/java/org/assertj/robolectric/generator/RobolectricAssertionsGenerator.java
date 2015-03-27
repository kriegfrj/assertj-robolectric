package org.assertj.robolectric.generator;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.lang.model.util.SimpleAnnotationValueVisitor6;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.omg.CORBA.PRIVATE_MEMBER;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.sun.tools.internal.jxc.gen.config.Classes;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import static javax.lang.model.util.ElementFilter.*;

/**
 * Annotation processor entry point for Robolectric assertions generator.
 */
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class RobolectricAssertionsGenerator extends AbstractProcessor {

//  private RobolectricAssertionsModel model;
  private Filer filer;
  private Messager messager;
  private Elements elements;
  private Types types;
  private TypeElement IMPLEMENTS;
  private VelocityEngine ve = new VelocityEngine();
  private Template abstractAssertTemplate;
  private Template assertTemplate;
  private Template assertionsTemplate;
  
  //  boolean generated = false;

  /**
   * Default constructor.
   */
  public RobolectricAssertionsGenerator() {
  }

  @Override
  public void init(ProcessingEnvironment environment) {
    super.init(environment);

    filer = environment.getFiler();
    elements = environment.getElementUtils();
    types = environment.getTypeUtils();
    messager = environment.getMessager();
    ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
    ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
//    ve.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SystemLogChute");
    ve.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
    ve.init();
    abstractAssertTemplate = ve.getTemplate("abstract_assert.vm");
    assertTemplate = ve.getTemplate("assert.vm");
    assertionsTemplate = ve.getTemplate("assertions.vm");
    IMPLEMENTS = elements.getTypeElement("org.robolectric.annotation.Implements");
    messager.printMessage(Kind.NOTE, "Initialized RobolectricAssertionsGenerator");
  }


  public AnnotationMirror getAnnotationMirror(Element element, TypeElement annotation) {
    TypeMirror expectedType = annotation.asType();
    for (AnnotationMirror m : element.getAnnotationMirrors()) {
      if (types.isSameType(expectedType, m.getAnnotationType())) {
        return m;
      }
    }
    return null;
  }

  public static ElementVisitor<TypeElement,Void> typeVisitor = new SimpleElementVisitor6<TypeElement,Void>() {
    @Override
    public TypeElement visitType(TypeElement e, Void p) {
      return e;
    }
  };

  public static AnnotationValue getAnnotationValue(AnnotationMirror annotationMirror, String key) {
    for (Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet() ) {
      if (entry.getKey().getSimpleName().toString().equals(key)) {
        return entry.getValue();
      }
    }
    return null;
  }

  public static AnnotationValueVisitor<TypeMirror,Void> valueVisitor = new SimpleAnnotationValueVisitor6<TypeMirror,Void>() {
    @Override
    public TypeMirror visitType(TypeMirror t, Void arg) {
      return t;
    }
  };

  public static AnnotationValueVisitor<String, Void> classNameVisitor = new SimpleAnnotationValueVisitor6<String, Void>() {
    @Override
    public String visitString(String s, Void arg) {
      return s;
    }
  };
  
  public AnnotationMirror getImplementsMirror(Element elem) {
    return getAnnotationMirror(elem, IMPLEMENTS);
  }
  
  private TypeMirror getImplementedClassName(AnnotationMirror am) {
    AnnotationValue className = getAnnotationValue(am, "className");
    if (className == null) {
      return null;
    }
    String classNameString = classNameVisitor.visit(className);
    if (classNameString == null) {
      return null;
    }
    TypeElement impElement = elements.getTypeElement(classNameString.replace('$', '.'));
    if (impElement == null) {
      return null;
    }
    return impElement.asType();
  }
  
  public TypeMirror getImplementedClass(AnnotationMirror am) {
    if (am == null) {
      return null;
    }
    // RobolectricWiringTest prefers className (if provided) to value, so we do the same here.
    TypeMirror impType = getImplementedClassName(am);
    if (impType != null) {
      return impType;
    }
    AnnotationValue av = getAnnotationValue(am, "value");
    if (av == null) {
      return null;
    }
    TypeMirror type = valueVisitor.visit(av);
    if (type == null) {
      return null;
    }
    
    return type;
  }
  
  private void mergeTemplate(Template template, VelocityContext c, String clazz) {
    messager.printMessage(Kind.MANDATORY_WARNING, "Creating class: " + clazz);
    Writer writer = null;
    try {
      writer = filer.createSourceFile(clazz).openWriter();
      template.merge(c, writer);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      if (writer != null) {
        try {
          writer.close();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }
  
  boolean generated = false;

  private Map<TypeMirror,TypeMirror> buildAssertionsMap() {
    TypeElement assertionsElement = elements.getTypeElement("org.assertj.android.api.Assertions");
    if (assertionsElement == null) {
      throw new RuntimeException("Couldn't find org.assertj.android.api.Assertions");
    }
    
    Map<TypeMirror,TypeMirror> map = new HashMap<>();
    
    for (ExecutableElement m : methodsIn(assertionsElement.getEnclosedElements())) {
      if (m.getSimpleName().toString().equals("assertThat")) {
        map.put(m.getParameters().get(0).asType(), m.getReturnType());
      }
    }
    
    return map;
  }
  
  private Map<TypeMirror,TypeMirror> buildShadowAssertionsMap() {
    TypeElement shadowAssertionsElement = elements.getTypeElement("org.robolectric.shadows.Assertions");
    if (shadowAssertionsElement == null) {
      throw new RuntimeException("Couldn't find org.robolectric.shadows.Assertions");
    }
    
    Map<TypeMirror,TypeMirror> map = new HashMap<>();
    
    for (ExecutableElement m : methodsIn(shadowAssertionsElement.getEnclosedElements())) {
      if (m.getSimpleName().toString().equals("assertThat")) {
        map.put(m.getParameters().get(0).asType(), m.getReturnType());
      }
    }
    
    return map;
  }
  
  private Set<TypeMirror> buildSolidsWithShadowOf() {
    TypeElement assertionsElement = elements.getTypeElement("org.robolectric.Shadows");
    if (assertionsElement == null) {
      throw new RuntimeException("Couldn't find org.robolectric.Shadows");
    }
    Set<TypeMirror> set = new HashSet<>();
    
    for (ExecutableElement m : methodsIn(assertionsElement.getEnclosedElements())) {
      if (m.getSimpleName().toString().equals("shadowOf")) {
        set.add(m.getParameters().get(0).asType());
      }
    }
    
    return set;

  }
  
  private static class CompareTypes implements Comparator<TypeMirror> {

    @Override
    public int compare(TypeMirror o1, TypeMirror o2) {
      return o1.toString().compareTo(o2.toString());
    }
  }
  
  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (generated) {
      return false;
    }

    Map<TypeMirror,TypeMirror> assertionsMap = buildAssertionsMap();
    Map<TypeMirror,TypeMirror> shadowAssertionsMap = buildShadowAssertionsMap();
    Set<TypeMirror> solidsWithShadowOf = buildSolidsWithShadowOf();
    System.err.println("Assertions map: " + assertionsMap);
    
    List<TypeElement> shadows = typesIn(elements.getPackageElement("org.robolectric.shadows").getEnclosedElements());
    if (shadows.size() == 0) {
      messager.printMessage(Kind.ERROR, "Didn't find any shadows!");
    }
    Map<TypeMirror,TypeElement> solids = new HashMap<>();
    Map<String,TypeMirror> shadowAsMapStrings = new HashMap<>();
    Set<String> generatedAbstracts = new HashSet<>();
    Map<String,VelocityContext> classes = new HashMap<>();
    SortedSet<TypeMirror> shadowOnly = new TreeSet<>(new CompareTypes());
    Set<TypeMirror> privateSolids = new HashSet<>();
    for (TypeMirror shadow : shadowAssertionsMap.keySet()) {
      shadowAsMapStrings.put(shadow.toString(), shadowAssertionsMap.get(shadow));
      TypeElement shadowElement = (TypeElement)types.asElement(shadow);
      TypeMirror implemented = getImplementedClass(getImplementsMirror(shadowElement));
      messager.printMessage(Kind.MANDATORY_WARNING, "Processing shadow: " + shadow);
      if (implemented != null) {
        solids.put(implemented, shadowElement);
        VelocityContext c = new VelocityContext();
        Element impType = types.asElement(implemented);
        String solidClass = impType.getSimpleName().toString();
        String androidPkg = impType.getEnclosingElement().toString();
        
        TypeMirror shadowAssertionType = shadowAssertionsMap.get(shadow);
        messager.printMessage(Kind.MANDATORY_WARNING, "Processing shadowAssert: " + shadowAssertionType);
        if (shadowAssertionType == null) {
          System.err.println("\n====>Pipping class: " + shadowAssertionType + ", " + shadow);
          continue;
        }

        TypeElement shadowAssert = (TypeElement)types.asElement(shadowAssertionType);
        String shadowAssertClass = shadowElement.getSimpleName() + "Assert";
        String shadowAssertClassFQ = "org.robolectric.shadows." + shadowAssertClass;
        String abstractShadowAssertClass = "Abstract" + shadowAssertClass;
        String abstractShadowAssertClassFQ = "org.robolectric.shadows." + abstractShadowAssertClass;
        
        TypeMirror androidAssertionType = assertionsMap.get(implemented);
        
        if (androidAssertionType == null) {
          System.err.println("\n======>Dipping class: " + implemented);
          shadowOnly.add(implemented);
//          if (!impType.getModifiers().contains(Modifier.PUBLIC)) {
          if (!solidsWithShadowOf.contains(implemented)) {
            privateSolids.add(implemented);
          }
          continue;
        }
        
        Element androidAssertionTypeElement = types.asElement(androidAssertionType);
        String androidAssertPkg = androidAssertionTypeElement.getEnclosingElement().toString();
        String assertClass = androidAssertionTypeElement.getSimpleName().toString();
        String assertPkg = androidAssertPkg.replace("android", "robolectric");
        String assertClassFQ = assertPkg + '.' + assertClass;
        
        String androidAssertClassFQ = androidAssertPkg + '.' + assertClass;
        String abstractAndroidAssertClassFQ = androidAssertPkg + ".Abstract" + assertClass;

        TypeElement actualAssert = elements.getTypeElement(androidAssertClassFQ);
        if (actualAssert == null) {
          System.err.println("\n====>Skipping class: " + androidAssertClassFQ);
          // FIXME: should still create an assertion for the shadow.
          continue;
//          throw new RuntimeException("Couldn't find actual assert class: " + androidAssertClassFQ);
        }

        String abstractAssertClass = "Abstract" + assertClass;
        String abstractAssertClassFQ = assertPkg + '.' + abstractAssertClass;

        c.put("shadow", shadowElement);
        c.put("solidClass", solidClass);
        c.put("solidClassFQ", impType);
        c.put("androidPkg", androidPkg);
        c.put("assertPkg", assertPkg);
        c.put("androidAssertPkg", androidAssertPkg);
        c.put("assertClass", assertClass);
        c.put("assertClassFQ", assertClassFQ);
        c.put("androidAssertClassFQ", androidAssertClassFQ);
        c.put("abstractAndroidAssertClassFQ", abstractAndroidAssertClassFQ);
        c.put("shadowAssertClass", shadowAssertClass);
        c.put("shadowAssertClassFQ", shadowAssertClassFQ);
        c.put("abstractAssertClass", abstractAssertClass);
        c.put("abstractAssertClassFQ", abstractAssertClassFQ);
        c.put("abstractShadowAssertClass", abstractShadowAssertClass);
        c.put("abstractShadowAssertClassFQ", abstractShadowAssertClassFQ);

        TypeElement actualSuper = (TypeElement)types.asElement(actualAssert.getSuperclass());
        TypeElement shadowSuper = (TypeElement)types.asElement(shadowAssert.getSuperclass());
        
        c.put("actualSuperElement", actualSuper);
        c.put("shadowSuperElement", shadowSuper);
        
        Predicate<ExecutableElement> isPublicInstance = new Predicate<ExecutableElement>() {

          @Override
          public boolean apply(ExecutableElement input) {
            Set<Modifier> modifiers = input.getModifiers();
            return modifiers.contains(Modifier.PUBLIC) && !modifiers.contains(Modifier.STATIC);
          }
          
        };
        List<ExecutableElement> methods = new ArrayList<>();
        Iterables.addAll(methods, Iterables.filter(methodsIn(actualAssert.getEnclosedElements()), isPublicInstance));
        boolean buildAbstract = true;
        if (actualSuper.toString().equals(abstractAndroidAssertClassFQ)) {
          Iterables.addAll(methods, Iterables.filter(methodsIn(actualSuper.getEnclosedElements()), isPublicInstance));
        } else {
          buildAbstract = false;
        }
        c.put("actualMethods", methods);
        methods = new ArrayList<>();
        Iterables.addAll(methods, Iterables.filter(methodsIn(shadowAssert.getEnclosedElements()),isPublicInstance));
        if (shadowSuper.toString().equals(abstractShadowAssertClassFQ)) {
          Iterables.addAll(methods, Iterables.filter(methodsIn(shadowSuper.getEnclosedElements()), isPublicInstance));
        } else {
          buildAbstract = false;
        }
        c.put("shadowMethods", methods);
        c.put("buildAbstract", buildAbstract);
        

        System.err.println("=================================");
        TreeSet<Object> keys = new TreeSet<>(Arrays.asList(c.getKeys()));
        for (Object key : keys) {
          System.err.println(key + ": " + c.get((String)key));
        }
        if (buildAbstract) {
          mergeTemplate(abstractAssertTemplate, c, abstractAssertClassFQ);
          generatedAbstracts.add(abstractAssertClassFQ);
        }
        classes.put(assertClassFQ, c);
      }
    }
    
    for (Map.Entry<String,VelocityContext> entry : classes.entrySet()) {
      String assertClassFQ = entry.getKey();
      VelocityContext c = entry.getValue();
      boolean buildAbstract = ((Boolean)c.get("buildAbstract")).booleanValue();
      Object abstractAssertClass = c.get("abstractAssertClass");
      if (buildAbstract) {
        c.put("assertSuperClass", abstractAssertClass);
      } else {
        final String actualSuperStr = c.get("actualSuperElement").toString();
        final String abstractRoboAssert = "org.assertj.robolectric.api.AbstractRobolectricAssert";
        if (actualSuperStr.equals("org.assertj.core.api.AbstractAssert")) {
          c.put("assertSuperClass", abstractRoboAssert);
        } else {
          final String ourSuperStr = actualSuperStr.replace("android", "robolectric");
          c.put("assertSuperClass", generatedAbstracts.contains(ourSuperStr) ? ourSuperStr : abstractRoboAssert);
        }
      }
      mergeTemplate(assertTemplate, c, assertClassFQ);
    }
    
    String assertionsClassPackage = "org.assertj.robolectric.api";
    String assertionsClassName = assertionsClassPackage + ".Assertions";
    
    TypeElement assertionsElement = elements.getTypeElement("org.assertj.android.api.Assertions");
    if (assertionsElement == null) {
      throw new RuntimeException("Couldn't find org.assertj.android.api.Assertions");
    }
    
    VelocityContext c = new VelocityContext();
    System.err.println("Solids: " + solids);
    System.err.println("Shadow only: " + shadowOnly);
    System.err.println("Shadow assertions map: " + shadowAssertionsMap);
    c.put("solids", solids);
    c.put("methods", methodsIn(assertionsElement.getEnclosedElements()));
    c.put("elements",  elements);
    c.put("shadowOnly", shadowOnly);
    c.put("privateSolids", privateSolids);
    c.put("shadowAssertionsMap", shadowAssertionsMap);
    c.put("shadowAsMapStrings", shadowAsMapStrings);
    mergeTemplate(assertionsTemplate, c, assertionsClassName);
    this.generated = true;
    return false;
  }

  public static class Wrapper {
    Map<TypeMirror, TypeMirror> map;
    public Wrapper(Map<TypeMirror,TypeMirror> map) {
      this.map = map;
    }
    
    public TypeMirror get(TypeMirror key) {
      return map.get(key);
    }

    @Override
    public String toString() {
      return map.toString();
    }
  }
}
