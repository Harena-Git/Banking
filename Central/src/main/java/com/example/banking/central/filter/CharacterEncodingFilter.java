package com.example.banking.central.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CharacterEncodingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(CharacterEncodingFilter.class);
    private String encoding = "UTF-8";
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String encodingParam = filterConfig.getInitParameter("encoding");
        if (encodingParam != null) {
            encoding = encodingParam;
        }
        logger.info("CharacterEncodingFilter initialisé avec l'encodage: {}", encoding);
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Définir l'encodage des requêtes et réponses
        httpRequest.setCharacterEncoding(encoding);
        httpResponse.setCharacterEncoding(encoding);
        
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        logger.info("CharacterEncodingFilter détruit");
    }
}