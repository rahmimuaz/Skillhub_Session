package com.skillhub.skillhub.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;


@Document(collection = "posts")
public class Post {

    @Id
    private String id;
    private String userId; // Foreign key reference to User
    private String postType;
    private String title;
    private String description;
    private String image; // URL or base64 image string
    private int visibilityCount;
    private LocalDateTime timestamp;

    // Constructors
    public Post() {
        this.timestamp = LocalDateTime.now(); // Set default timestamp
    }

    public Post(String userId, String postType, String title, String description, String image, int visibilityCount) {
        this.userId = userId;
        this.postType = postType;
        this.title = title;
        this.description = description;
        this.image = image;
        this.visibilityCount = visibilityCount;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getPostType() { return postType; }
    public void setPostType(String postType) { this.postType = postType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public int getVisibilityCount() { return visibilityCount; }
    public void setVisibilityCount(int visibilityCount) { this.visibilityCount = visibilityCount; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}