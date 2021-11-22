package com.smeekens.fitback.fitback.fitback.message;

import com.smeekens.fitback.fitback.fitback.models.Feedback;
import com.smeekens.fitback.fitback.fitback.models.User;

import java.util.List;

public class ResponseFile {
    private List<Feedback> feedback;
    private String username;
    private Long id;
    private String name;
    private String url;
    private String type;
    private long size;

    public ResponseFile(List<Feedback> feedback, String username, Long id, String name, String url, String type, long size) {
        this.feedback =  feedback;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSize(long size) {
        this.size = size;
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
