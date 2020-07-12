package processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * Package processor
 * Description: ${todo}
 * author 016039
 * date 2019/1/9下午9:11
 */
// 支持的注解
@SupportedAnnotationTypes({
        "annotation.JsonConverter"
})
// 支持的Java版本
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class JsonConverterProcessor extends AbstractProcessor {
  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    return false;
  }
}
