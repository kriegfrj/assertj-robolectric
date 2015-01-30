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
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.lang.model.util.SimpleAnnotationValueVisitor6;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

import org.assertj.robolectric.api.pkg2.ShadowAClass2Assert;

import com.google.common.base.Strings;

import java.io.IOException;
import java.io.PrintWriter;
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
  
  
  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    List<TypeElement> shadows = typesIn(elements.getPackageElement("org.robolectric.shadows").getEnclosedElements());
    Map<TypeMirror,TypeElement> solids = new HashMap<>();
    for (TypeElement shadow: shadows) {
      TypeMirror implemented = getImplementedClass(getImplementsMirror(shadow));
      if (implemented != null) {
        solids.put(implemented, shadow);
        PrintWriter writer = null;
        try {
          Element impType = types.asElement(implemented);
          String solidClass = impType.getSimpleName().toString();
          String androidPkg = impType.getEnclosingElement().toString();
          String assertPkg = androidPkg.replace("android", "org.assertj.robolectric.api");
          String androidAssertPkg = assertPkg.replace("robolectric", "android");
          String assertClass = solidClass + "Assert";
          String assertClassFQ = assertPkg + '.' + assertClass;
          String androidAssertClassFQ = androidAssertPkg + '.' + assertClass;
          String shadowAssertClass = "Shadow" + assertClass;
          String shadowAssertClassFQ = assertPkg + '.' + shadowAssertClass;
          
          Element shadowAssert = elements.getTypeElement(shadowAssertClassFQ);
          if (shadowAssert == null) {
            continue;
          }
          String abstractAssertClass = "Abstract" + assertClass;
          String abstractAssertClassFQ = assertPkg + '.' + abstractAssertClass;

          JavaFileObject jfo = filer.createSourceFile(abstractAssertClassFQ);
          writer = new PrintWriter(jfo.openWriter());
          writer.println("package " + assertPkg + ';');
          writer.println("import " + impType + ';');
          writer.println("import org.assertj.core.api.AbstractAssert;");
          writer.println("import " + shadowAssertClassFQ + ';');
          writer.println("public abstract class " + abstractAssertClass + '<');
          writer.println("   M extends " + abstractAssertClass + "<M,A,AA,SA>,");
          writer.println("   A extends " + solidClass + ',');
          writer.println("   AA extends " + androidAssertClassFQ + ',');
          writer.println("   SA extends " + shadowAssertClass + "> extends AbstractAssert<M,A> {");
          writer.println();
          writer.println("    protected AA actualAssert;");
          writer.println("    protected SA shadowAssert;");
          writer.println();
          String padding = Strings.repeat(" ", abstractAssertClass.length());
          writer.println("  public " + abstractAssertClass + "(A actual,");
          writer.println("         " + padding + " AA actualAssert,");
          writer.println("         " + padding + " SA shadowAssert,");
          writer.println("         " + padding + " Class<M> selfType) {");
          writer.println("    super(actual, selfType);");
          writer.println("    this.actualAssert = actualAssert;");
          writer.println("    this.shadowAssert = shadowAssert;");
          writer.println("  }");
          writer.println("}");
          writer.close();
          writer = null;
          
          jfo = filer.createSourceFile(assertClassFQ);
          writer = new PrintWriter(jfo.openWriter());
          writer.println("package " + assertPkg + ';');
          writer.println("import static org.robolectric.Shadows.shadowOf;");
          writer.println("import " + impType + ';');
          writer.println("import " + shadowAssertClassFQ + ';');
          writer.println("public class " + assertClass + " extends " + abstractAssertClass + "<");
          writer.println(assertClass + ',' + solidClass + ',' + androidAssertClassFQ + ',' + shadowAssertClass + "> {");
          writer.println("  public " + assertClass + '(' + impType.getSimpleName() + " actual) {");
          writer.println("    super(actual,");
          writer.println("          new " + androidAssertClassFQ + "(actual),");
          writer.println("          new " + shadowAssertClass + "(shadowOf(actual)),");
          writer.println("          " + assertClass + ".class);");
          writer.println("  }");
          writer.println("}");
        } catch (IOException e) {
          throw new RuntimeException(e);
        } finally {
          if (writer != null) {
            writer.close();
          }
        }
      }
    }
    
    String assertionsClassPackage = "org.assertj.robolectric.api";
    String assertionsClassName = assertionsClassPackage + ".Assertions";
    PrintWriter writer = null;
    try {
      JavaFileObject jfo = filer.createSourceFile(assertionsClassName);
      writer = new PrintWriter(jfo.openWriter());
      writer.println("package " + assertionsClassPackage + ";");
      writer.println("public final class Assertions {");

      TypeElement assertionsElement = elements.getTypeElement("org.assertj.android.api.Assertions");
      for (ExecutableElement m: methodsIn(assertionsElement.getEnclosedElements())) {
        TypeMirror actual = m.getParameters().get(0).asType();
        TypeElement shadow = solids.get(actual);
        String returnType = m.getReturnType().toString();
        if (shadow != null) {
          String roboReturn = returnType.replace("assertj.android", "assertj.robolectric");
          String shadowReturn = roboReturn.replaceFirst("(api[.].*[.])([^.]*$)", "$1Shadow$2");
          TypeElement shadowAssert = elements.getTypeElement(shadowReturn);
          System.err.println("Looking for type: " + shadowAssert + ", " + shadowReturn);
          if (shadowAssert != null) {
            returnType = roboReturn;
            writer.println("  public static " + shadowReturn + " assertThat(" + shadow + " actual) {");
            writer.println("    return new " + shadowReturn + "(actual);");
            writer.println("  }");
            writer.println();
          }
        }

        writer.println("  public static " + returnType + " assertThat(" + actual + " actual) {");
        writer.println("    return new " + returnType + "(actual);");
        writer.println("  }");
        writer.println();
      }
      
      writer.println("  private Assertions() {");
      writer.println("    throw new AssertionError(\"No instances.\");");
      writer.println("  }");
      writer.println("}");
    } catch (IOException e) {
      
    } finally {
      if (writer != null) {
        writer.close();
      }
    }
    return false;
  }

}
