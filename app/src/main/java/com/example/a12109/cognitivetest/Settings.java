package com.example.a12109.cognitivetest;

public class Settings {
    private String remoteHost = "10.12.13.214";
    private int remotePort = 5000;

    public String getRemoteHost() {
        return this.remoteHost;
    }

    public int getRemotePort() {
        return this.remotePort;
    }

    public void setRemoteHost(String rmtHst) {
        this.remoteHost = rmtHst;
    }

    public void setRemotePort(int rmtPt) {
        this.remotePort = rmtPt;
    }
}
