package com.company.modules.SmsService.model;

/**
 * Created by Dave on 07.08.17.
 */
public class SMS {
    String sender;
    String message;


    public SMS(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
