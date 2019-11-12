package com.danielpuiu.jenkins;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTBuildParameter;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTBuildParameters;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTKeyValueOrMethodCallPair;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTLibraries;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTOption;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTOptions;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPipelineDef;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTStages;
import org.jenkinsci.plugins.pipeline.modeldefinition.parser.Converter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

public class JenkinsParserTest {

    @BeforeClass
    public static void setup() {
        JenkinsInstance.prepare();
    }

    @AfterClass
    public static void cleanUp() throws IOException {
        JenkinsInstance.shutdown();
    }

    @Test
    public void test() throws IOException {
        String content = readFile("src/test/resources/Jenkinsfile");
        ModelASTPipelineDef pipeline = Converter.scriptToPipelineDef(content);

        ModelASTLibraries libraries = pipeline.getLibraries();
        assertNotNull(libraries);
        assertEquals(3, libraries.getLibs().size());
        assertEquals("lib1@master", libraries.getLibs().get(0).getValue());
        assertEquals("lib2@development", libraries.getLibs().get(1).getValue());
        assertEquals("lib3@feature_branch", libraries.getLibs().get(2).getValue());

        ModelASTOptions options = pipeline.getOptions();
        assertNotNull(options);
        assertEquals(1, options.getOptions().size());

        ModelASTOption option = options.getOptions().get(0);
        assertEquals("timeout", option.getName());

        ModelASTBuildParameters parameters = pipeline.getParameters();
        assertNotNull(parameters);
        assertEquals(1, parameters.getParameters().size());

        ModelASTBuildParameter parameter = parameters.getParameters().get(0);
        assertEquals("string", parameter.getName());
        assertEquals("name", ((ModelASTKeyValueOrMethodCallPair) parameter.getArgs().get(0)).getKey().getKey());

        ModelASTStages stages = pipeline.getStages();
        assertEquals(2, stages.getStages().size());
    }

    private static String readFile(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
