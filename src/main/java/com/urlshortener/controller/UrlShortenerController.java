package com.urlshortener.controller;

import com.urlshortener.dto.UrlRequest;
import com.urlshortener.dto.UrlResponse;
import com.urlshortener.model.UrlMapping;
import com.urlshortener.service.UrlShortenerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@Valid @RequestBody UrlRequest request) {
        try {
            String shortUrl = urlShortenerService.shortenUrl(request.getUrl());
            UrlResponse response = new UrlResponse();
            response.setShortUrl(shortUrl);
            response.setOriginalUrl(request.getUrl());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Error: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error creating short URL: " + e.getMessage()));
        }
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");
        return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
    }
    
    // Inner class for error responses
    private static class ErrorResponse {
        private String error;
        
        public ErrorResponse(String error) {
            this.error = error;
        }
        
        public String getError() {
            return error;
        }
        
        public void setError(String error) {
            this.error = error;
        }
    }

    @GetMapping("/stats/{shortCode}")
    public ResponseEntity<?> getUrlStats(@PathVariable String shortCode) {
        Optional<UrlMapping> mapping = urlShortenerService.getUrlStats(shortCode);
        if (mapping.isPresent()) {
            UrlMapping urlMapping = mapping.get();
            UrlResponse response = new UrlResponse(
                    urlShortenerService.getBaseUrl() + "/" + urlMapping.getShortCode(),
                    urlMapping.getOriginalUrl(),
                    urlMapping.getShortCode(),
                    urlMapping.getCreatedAt(),
                    urlMapping.getClickCount()
            );
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }
}

