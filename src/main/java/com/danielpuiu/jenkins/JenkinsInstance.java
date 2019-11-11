package com.danielpuiu.jenkins;

import hudson.LocalPluginManager;
import hudson.model.Hudson;
import hudson.security.AuthorizationStrategy;
import jenkins.model.Jenkins;
import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.workflow.cps.global.WorkflowLibRepository;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class JenkinsInstance {

    private JenkinsInstance() {
        // prevent instantiation
    }

    public static Jenkins getInstance() {
        return Holder.INSTANCE;
    }

    public static void prepare() {
        Holder.INSTANCE.setAuthorizationStrategy(new AuthorizationStrategy.Unsecured());
    }

    public static void shutdown() throws IOException {
        getInstance().doSafeExit(null);

        FileUtils.deleteDirectory(Holder.ROOT);
    }

    private static class Holder {

        private static final File ROOT = createJenkinsRoot();

        private static final Jenkins INSTANCE = createJenkinsInstance();

        private static File createJenkinsRoot() {
            try {
                return Files.createTempDirectory("jenkins").toFile().getAbsoluteFile();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        private static Jenkins createJenkinsInstance() {
            try {
                ServletContext servletContext = new DefaultServletContext();
                return new Hudson(ROOT, servletContext, new LocalPluginManager(servletContext, ROOT));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
    }

}
