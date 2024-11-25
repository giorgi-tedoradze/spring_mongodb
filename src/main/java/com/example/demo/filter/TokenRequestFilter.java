package com.example.demo.filter;

import com.example.demo.database.service.UserService;
import com.example.demo.token.TokenCreator;
import com.example.demo.token.TokenDriver;
import com.example.demo.token.TokenType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenRequestFilter extends OncePerRequestFilter {
    private final TokenDriver tokenDriver;
    private final UserService userService;

    public TokenRequestFilter(TokenDriver tokenDriver, UserService userService) {
        this.tokenDriver = tokenDriver;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/user")) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (!StringUtils.hasLength(header) || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + "Authorization header is incorrect" + "\"}");
            return;
        }

        String token = header.substring(7);
        TokenCreator tokenCreator = getAccessToken();

        String username = tokenCreator.extractUsername(token);

        if (StringUtils.hasLength(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userService.loadUserByUsername(username);

            if (tokenCreator.isTokenExpired(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"" + "Token expired" + "\"}");
                return;
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);
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
}
