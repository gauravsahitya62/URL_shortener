package com.urlshortener.dto;

import java.time.LocalDateTime;

public class UrlResponse {
    private String shortUrl;
    private String originalUrl;
    private String shortCode;
    private LocalDateTime createdAt;
    private Long clickCount;

    public UrlResponse() {
    }

    public UrlResponse(String shortUrl, String originalUrl, String shortCode, LocalDateTime createdAt, Long clickCount) {
        this.shortUrl = shortUrl;
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.createdAt = createdAt;
        this.clickCount = clickCount;
    }

    // Getters and Setters
    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }
}

