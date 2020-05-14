package com.example.hello.safe_working;

public class ChatRoom {
    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public ChatRoom(String message, String person, String uid) {
        this.message = message;
        this.person = person;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ChatRoom(String message, String person) {
        this.message = message;
        this.person = person;
    }

    public ChatRoom() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    String message,person,uid,image_url;
}
