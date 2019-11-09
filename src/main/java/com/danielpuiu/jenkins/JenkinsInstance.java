package com.danielpuiu.jenkins;

import hudson.LocalPluginManager;
import hudson.model.Hudson;
import hudson.security.AuthorizationStrategy;
import jenkins.model.Jenkins;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

public class JenkinsInstance {

    private static final Jenkins INSTANCE = createJenkinsInstance();

    private JenkinsInstance() {
        // prevent instantiation
    }

    public static void prepare() {
    }

    public static void shutdown() throws IOException {
        if (INSTANCE != null) {
            INSTANCE.doSafeExit(null);
        }
    }

    private static Hudson createJenkinsInstance() {
        try {
            File root = new File("target/jenkins");
            ServletContext servletContext = new DefaultServletContext();
            Hudson hudson = new Hudson(root, servletContext, new LocalPluginManager(servletContext, root));
            hudson.setAuthorizationStrategy(new AuthorizationStrategy.Unsecured());
            return hudson;
        } catch (Exception e) {
            return null;
        }
    }
}
