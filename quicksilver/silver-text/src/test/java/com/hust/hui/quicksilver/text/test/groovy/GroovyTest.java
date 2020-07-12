package com.hust.hui.quicksilver.text.test.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.IOException;

/**
 * Created by yihui on 2017/3/30.
 */
public class GroovyTest {

    @Test
    public void testGroovy() {
        Binding binding = new Binding();
        binding.setVariable("x", 10);
        binding.setVariable("language", "Groovy");

        GroovyShell shell = new GroovyShell(binding);
        Object value = shell.evaluate("println \"Welcome to $language\"; y = x * 2; z = x * 3; return x ");
        System.out.println(value);
    }


    @Test
    public void testGroovyEngine() throws ResourceException, ScriptException, IOException {

        String[] roots = new String[]{"src/test/java/com/hust/hui/quicksilver/text/test/groovy"};
        GroovyScriptEngine engine = new GroovyScriptEngine(roots);
        Binding binding = new Binding();
        binding.setVariable("language", "Groovy");
        Object value = engine.run("TextFormatGvy.groovy", binding);
        System.out.println(value);
    }



    @Test
    public void testGroovyLoader() throws IOException, IllegalAccessException, InstantiationException {
        GroovyClassLoader loader = new GroovyClassLoader();
        Class fileCreator = loader.parseClass(new File("src/test/java/com/hust/hui/quicksilver/text/test/groovy/TextFormatGvy2.groovy"));
        GroovyObject object = (GroovyObject) fileCreator.newInstance();
        Object value = object.invokeMethod("format", null);
        System.out.println(value);
    }



    @Test
    public void testEnginManager() throws javax.script.ScriptException, NoSuchMethodException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("Groovy");
        String HelloLanguage = "def hello(language) {return \"Hello $language\"}";
        engine.eval(HelloLanguage);
        Invocable inv = (Invocable) engine;
        Object[] params = {"Groovy"};
        Object result = inv.invokeFunction("hello", params);
        System.err.println(result);
    }

}
