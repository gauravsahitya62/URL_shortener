package com.urlshortener.service;

import com.urlshortener.model.UrlMapping;
import com.urlshortener.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    private static final int SHORT_CODE_LENGTH = 7;

    public String shortenUrl(String originalUrl) {
        // Normalize URL (add http:// if no protocol)
        String normalizedUrl = normalizeUrl(originalUrl);

        // Check if URL already exists
        Optional<UrlMapping> existing = urlMappingRepository.findAll().stream()
                .filter(mapping -> mapping.getOriginalUrl().equals(normalizedUrl))
                .findFirst();

        if (existing.isPresent()) {
            return baseUrl + "/" + existing.get().getShortCode();
        }

        // Generate unique short code
        String shortCode = generateShortCode(normalizedUrl);

        // Ensure uniqueness
        while (urlMappingRepository.existsByShortCode(shortCode)) {
            shortCode = generateShortCode(normalizedUrl + System.currentTimeMillis());
        }

        // Save to database
        UrlMapping urlMapping = new UrlMapping(normalizedUrl, shortCode);
        urlMappingRepository.save(urlMapping);

        return baseUrl + "/" + shortCode;
    }

    public Optional<String> getOriginalUrl(String shortCode) {
        Optional<UrlMapping> mapping = urlMappingRepository.findByShortCode(shortCode);
        if (mapping.isPresent()) {
            UrlMapping urlMapping = mapping.get();
            urlMapping.incrementClickCount();
            urlMappingRepository.save(urlMapping);
            return Optional.of(urlMapping.getOriginalUrl());
        }
        return Optional.empty();
    }

    public Optional<UrlMapping> getUrlStats(String shortCode) {
        return urlMappingRepository.findByShortCode(shortCode);
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    private String generateShortCode(String url) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(url.getBytes(StandardCharsets.UTF_8));
            String base64Hash = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
            return base64Hash.substring(0, Math.min(SHORT_CODE_LENGTH, base64Hash.length()));
        } catch (NoSuchAlgorithmException e) {
            // Fallback to simple hash
            String hash = String.valueOf(Math.abs(url.hashCode()));
            if (hash.length() >= SHORT_CODE_LENGTH) {
                return hash.substring(0, SHORT_CODE_LENGTH);
            } else {
                // Pad with zeros if needed
                StringBuilder sb = new StringBuilder(hash);
                while (sb.length() < SHORT_CODE_LENGTH) {
                    sb.append("0");
                }
                return sb.toString();
            }
        }
    }

    private String normalizeUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("URL cannot be empty");
        }
        url = url.trim();
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        return url;
    }
}

