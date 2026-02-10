package com.urlshortener.controller;

import com.urlshortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class RedirectController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    // Handle redirect for short codes (7-character alphanumeric), e.g. /abc1234
    // This pattern avoids capturing paths like /index.html or other static resources.
    @GetMapping("/{shortCode:[a-zA-Z0-9]{7}}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable("shortCode") String shortCode) {
        Optional<String> originalUrl = urlShortenerService.getOriginalUrl(shortCode);
        
        if (originalUrl.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", originalUrl.get())
                    .build();
        }
        
        return ResponseEntity.notFound().build();
    }
}

