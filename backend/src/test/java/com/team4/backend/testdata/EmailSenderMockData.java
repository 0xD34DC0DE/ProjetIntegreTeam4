package com.team4.backend.testdata;

public abstract class EmailSenderMockData {

    public static String getReceiver() {
        return "receiver";
    }

    public static String getSubject() {
        return "subject";
    }

    public static String getContent() {
        return "content";
    }

    public static String getPrincipalEmail() {
        return "principal@gmail.com";
    }

}
