package com.danielpuiu.jenkins;

import javax.servlet.SessionCookieConfig;

public class DefaultSessionCookieConfig implements SessionCookieConfig {

    @Override
    public void setName(String s) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setDomain(String s) {

    }

    @Override
    public String getDomain() {
        return null;
    }

    @Override
    public void setPath(String s) {

    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public void setComment(String s) {

    }

    @Override
    public String getComment() {
        return null;
    }

    @Override
    public void setHttpOnly(boolean b) {

    }

    @Override
    public boolean isHttpOnly() {
        return false;
    }

    @Override
    public void setSecure(boolean b) {

    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public void setMaxAge(int i) {

    }

    @Override
    public int getMaxAge() {
        return 0;
    }
}
