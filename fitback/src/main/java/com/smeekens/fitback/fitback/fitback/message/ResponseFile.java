package com.smeekens.fitback.fitback.fitback.message;

import com.smeekens.fitback.fitback.fitback.models.Feedback;
import com.smeekens.fitback.fitback.fitback.models.User;

import java.util.List;

public class ResponseFile {
    private User user;
    private List<Feedback> feedback;
    private Long id;
    private String name;
    private String url;
    private String type;
    private long size;

    public ResponseFile(Long id, String name, String url, String type, long size) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
    }


    public ResponseFile(List<Feedback> feedback, User user, Long id, String name, String url, String type, long size) {
        this.feedback =  feedback;
        this.user = user;
        this.id = id;
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
    }

    public List<Feedback> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<Feedback> feedback) {
        this.feedback = feedback;
    }

    public Long getUser() {
        return user.getId();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
