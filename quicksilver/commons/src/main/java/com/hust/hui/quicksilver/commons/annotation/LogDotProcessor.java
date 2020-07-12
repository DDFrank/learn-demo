package com.hust.hui.quicksilver.commons.annotation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * Created by yihui on 2017/3/8.
 */
@SupportedAnnotationTypes({"com.hust.hui.quicksilver.commons.annotation.LogDot"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class LogDotProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("tttt");
        return true;
    }
}
