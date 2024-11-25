package com.example.demo.filter;

import com.example.demo.token.TokenCreator;
import com.example.demo.token.TokenDriver;
import com.example.demo.token.TokenType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenRequestFilter extends OncePerRequestFilter {
    private final TokenDriver tokenDriver;

    public TokenRequestFilter(TokenDriver tokenDriver) {
        this.tokenDriver = tokenDriver;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain)
            throws ServletException, IOException {
        String token = getBearerFromRequest(request);
        TokenCreator tokenCreator = getAccessToken();
        if (token != null && tokenCreator.isTokenExpired(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expired");
            return;
        }
        chain.doFilter(request, response);
    }

    private TokenCreator getAccessToken() {
        try {
            return tokenDriver.getTokenCreatorByType(TokenType.JWT_ACCESS_TOKEN);
        } catch (TokenDriver.TokenTypeNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String getBearerFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
