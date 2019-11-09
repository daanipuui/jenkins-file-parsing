package com.danielpuiu.jenkins;

import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTPipelineDef;
import org.jenkinsci.plugins.pipeline.modeldefinition.parser.Converter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JenkinsFileParser {

    public static void main(String[] args) throws IOException {
        JenkinsInstance.prepare();

        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/Jenkinsfile"));
        ModelASTPipelineDef pipeline = Converter.scriptToPipelineDef(String.join(System.lineSeparator(), lines));
        System.out.println(pipeline.getLibraries());
        System.out.println(pipeline.getOptions());
        System.out.println(pipeline.getParameters());
        System.out.println(pipeline.getStages());

        JenkinsInstance.shutdown();
    }
}
