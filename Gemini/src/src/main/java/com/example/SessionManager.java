package com.example.badapp.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class SessionManager {

    private Map<String, String> sessions = new HashMap<>();

    public String createSession(String username) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, username); // Onveilige sessieopslag
        return sessionId;
    }

    public String getSessionUser(String sessionId) {
        return sessions.get(sessionId); // Geen sessievalidatie
    }
}