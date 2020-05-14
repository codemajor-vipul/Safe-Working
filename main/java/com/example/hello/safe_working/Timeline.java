package com.example.hello.safe_working;

public class Timeline {
    public Timeline(){

    }
    public Timeline(String process, String processdetail) {
        this.process = process;
        this.processdetail = processdetail;
    }
    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getProcessdetail() {
        return processdetail;
    }

    public void setProcessdetail(String processdetail) {
        this.processdetail = processdetail;
    }
    String process,processdetail;
}
