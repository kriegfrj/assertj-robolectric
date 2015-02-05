package org.assertj.robolectric.generator;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
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

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static javax.lang.model.util.ElementFilter.*;

/**
 * Annotation processor entry point for Robolectric assertions generator.
 */
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class RobolectricAssertionsGenerator extends AbstractProcessor {

//  private RobolectricAssertionsModel model;
  private Filer filer;
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
    ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
    ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
    ve.init();
    abstractAssertTemplate = ve.getTemplate("abstract_assert.vm");
    assertTemplate = ve.getTemplate("assert.vm");
    assertionsTemplate = ve.getTemplate("assertions.vm");
    IMPLEMENTS = elements.getTypeElement("org.robolectric.annotation.Implements");
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
  
  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (generated) {
      return false;
    }
    List<TypeElement> shadows = typesIn(elements.getPackageElement("org.robolectric.shadows").getEnclosedElements());
    Map<TypeMirror,TypeElement> solids = new HashMap<>();
    for (TypeElement shadow : shadows) {
      TypeMirror implemented = getImplementedClass(getImplementsMirror(shadow));
      if (implemented != null) {
        solids.put(implemented, shadow);
        VelocityContext c = new VelocityContext();
        Element impType = types.asElement(implemented);
        String solidClass = impType.getSimpleName().toString();
        String androidPkg = impType.getEnclosingElement().toString();
        String assertPkg = androidPkg.replace("android", "org.assertj.robolectric.api");
        String assertClass = solidClass + "Assert";
        String assertClassFQ = assertPkg + '.' + assertClass;

        String shadowAssertClass = "Shadow" + assertClass;
        String shadowAssertClassFQ = assertPkg + '.' + shadowAssertClass;
        String abstractShadowAssertClass = "Abstract" + shadowAssertClass;
        String abstractShadowAssertClassFQ = assertPkg + '.' + abstractShadowAssertClass;
        TypeElement shadowAssert = elements.getTypeElement(shadowAssertClassFQ);
        if (shadowAssert == null) {
          continue;
        }
        String androidAssertPkg = assertPkg.replace("robolectric", "android");
        String androidAssertClassFQ = androidAssertPkg + '.' + assertClass;
        String abstractAndroidAssertClassFQ = androidAssertPkg + ".Abstract" + assertClass;

        TypeElement actualAssert = elements.getTypeElement(androidAssertClassFQ);
        if (actualAssert == null) {
          throw new RuntimeException("Couldn't find actual assert class: " + androidAssertClassFQ);
        }
        

        String abstractAssertClass = "Abstract" + assertClass;
        String abstractAssertClassFQ = assertPkg + '.' + abstractAssertClass;

        c.put("shadow", shadow);
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
        boolean buildAbstract = false;
        if (actualSuper.toString().equals(abstractAndroidAssertClassFQ)) {
          Iterables.addAll(methods, Iterables.filter(methodsIn(actualSuper.getEnclosedElements()), isPublicInstance));
          buildAbstract = true;
        }
        c.put("actualMethods", methods);
        methods = new ArrayList<>();
        Iterables.addAll(methods, Iterables.filter(methodsIn(shadowAssert.getEnclosedElements()),isPublicInstance));
        if (shadowSuper.toString().equals(abstractShadowAssertClassFQ)) {
          Iterables.addAll(methods, Iterables.filter(methodsIn(shadowSuper.getEnclosedElements()), isPublicInstance));
          buildAbstract = true;
        }
        c.put("shadowMethods", methods);
        c.put("buildAbstract", buildAbstract);
        
        if (buildAbstract) {
          mergeTemplate(abstractAssertTemplate, c, abstractAssertClassFQ);
          c.put("assertSuperClass", abstractAssertClass);
        } else if (actualSuper.toString().equals("org.assertj.core.api.AbstractAssert")) {
          c.put("assertSuperClass", "org.assertj.robolectric.api.AbstractRobolectricAssert");
        } else {
          c.put("assertSuperClass", actualSuper.toString().replace("android", "robolectric"));
        }
        mergeTemplate(assertTemplate, c, assertClassFQ);
      }
    }
    
    String assertionsClassPackage = "org.assertj.robolectric.api";
    String assertionsClassName = assertionsClassPackage + ".Assertions";
    
    TypeElement assertionsElement = elements.getTypeElement("org.assertj.android.api.Assertions");
    if (assertionsElement == null) {
      throw new RuntimeException("Couldn't find org.assertj.android.api.Assertions");
    }
    
    VelocityContext c = new VelocityContext();
    c.put("solids", solids);
    c.put("methods", methodsIn(assertionsElement.getEnclosedElements()));
    c.put("elements",  elements);
    mergeTemplate(assertionsTemplate, c, assertionsClassName);
    generated = true;
    return false;
  }

}
