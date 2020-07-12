package processor;

import annotation.Builder;
import com.squareup.javapoet.*;
import io.vertx.core.json.JsonObject;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.Set;

import static javax.lang.model.element.Modifier.STATIC;

/**
 * Package processor
 * Description: ${todo}
 * author 016039
 * date 2019/1/6上午9:32
 */
// 支持的注解
@SupportedAnnotationTypes({
        "annotation.Builder"
})
// 支持的Java版本
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class BuilderProcessor extends AbstractProcessor {

  private Filer filer;

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    filer = processingEnv.getFiler();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    System.out.println("Processing " + annotations + roundEnv);
    // 获取标注了Builder 的元素, 比如package class method 等等
    Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Builder.class);
    elements.forEach(element -> {
      // 排除不是标注在类上的
      if (!element.getKind().equals(ElementKind.CLASS)) {
        System.out.println("Builder should to annotate a class!");
        return;
      }
      // 得到类型的包装类
      TypeMirror elementTypeMirror = element.asType();
      // 新创建的类名命名方式
      String builderClassName = element.getSimpleName() + "Builder";
      // poet 的API
      TypeSpec.Builder typeBuilder = TypeSpec
              .classBuilder(builderClassName)
              .addModifiers(Modifier.FINAL, Modifier.PUBLIC);

      MethodSpec.Builder builderMethod = MethodSpec
              .methodBuilder("build")
              .returns(TypeName.get(element.asType()))
              .addStatement("$T instance = new $T()", TypeName.get(elementTypeMirror), TypeName.get(elementTypeMirror));
      // 获取元素中的成员属性
      element.getEnclosedElements().forEach(field -> {
                if (field.getKind().equals(ElementKind.FIELD)) {
                  boolean isStatic = field.getModifiers().contains(STATIC);
                  // 排除静态字段
                  if (isStatic) {
                    return;
                  }
                  String fieldName = field.getSimpleName().toString();
                  String transformedName = upperFirstChar(fieldName);

                  MethodSpec methodSpec = MethodSpec.methodBuilder("build" + transformedName)
                          .returns(TypeName.get(element.asType()))
                          .returns(ClassName.get("com.frank.generated", builderClassName))
                          .addModifiers(Modifier.PUBLIC)
                          .addParameter(TypeName.get(field.asType()), field.getSimpleName().toString())
                          .addStatement("this.$L = $L;return this", field.getSimpleName().toString(), field.getSimpleName().toString())
                          .build();

                  FieldSpec fieldSpec = FieldSpec.builder(TypeName.get(field.asType()), fieldName, Modifier.PRIVATE).build();

                  String fieldSetName = upperFirstChar(fieldName);
                  builderMethod.addStatement("instance.set$L(this.$L)", fieldSetName, fieldName);
                  typeBuilder.addField(fieldSpec);
                  typeBuilder.addMethod(methodSpec);
                }
              }

      );

      builderMethod.addStatement("return instance");

      typeBuilder.addMethod(builderMethod.build());

      TypeSpec typeSpec = typeBuilder.build();

      try {
        JavaFile.builder("com.frank.generated", typeSpec).build().writeTo(System.out);
        JavaFile.builder("com.frank.generated", typeSpec).build().writeTo(filer);
      }catch (IOException e) {
        e.printStackTrace();
      }
    });

    return true;
  }

  /**
   * Description: 大写首字母
   * param
   * return
   * author jinjunliang
   * date 2019/1/6 上午11:59
   */
  private static String upperFirstChar(String name) {
    if (name.length() < 1) {
      return name;
    }
    String firstChar = name.substring(0, 1).toUpperCase();
    if (name.length() > 1) {
      return firstChar + name.substring(1);
    }
    return firstChar;
  }
}
