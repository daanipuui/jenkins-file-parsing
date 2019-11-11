package com.danielpuiu.jenkins;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPipelineDef;
import org.jenkinsci.plugins.pipeline.modeldefinition.parser.Converter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.testng.Assert.assertFalse;

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

        assertFalse(pipeline.getLibraries().getLibs().isEmpty());
        assertFalse(pipeline.getOptions().getOptions().isEmpty());
        assertFalse(pipeline.getParameters().getParameters().isEmpty());
        assertFalse(pipeline.getStages().getStages().isEmpty());
    }

    private static String readFile(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
