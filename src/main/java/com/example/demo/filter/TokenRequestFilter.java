package com.example.demo.filter;

import com.example.demo.database.service.UserService;
import com.example.demo.token.TokenCreator;
import com.example.demo.token.TokenDriver;
import com.example.demo.token.TokenType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class TokenRequestFilter extends OncePerRequestFilter {
    private static final int BEARER_SUBSTRING_LENGTH = 7;
    private static final String IGNORABLE_PREFFIX = "/user";

    private final TokenDriver tokenDriver;
    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith(IGNORABLE_PREFFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (!StringUtils.hasLength(header) || !header.startsWith("Bearer ")) {
            setUnauthorizedResponse(response, "Authorization header is incorrect");
            return;
        }

        String token = header.substring(BEARER_SUBSTRING_LENGTH);
        TokenCreator tokenCreator = getAccessToken();

        String username = tokenCreator.extractUsername(token);

        if (StringUtils.hasLength(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userService.loadUserByUsername(username);

            if (tokenCreator.isTokenExpired(token)) {
                setUnauthorizedResponse(response, "Token expired");
                return;
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
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

    private void setUnauthorizedResponse(HttpServletResponse response, String x) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + x + "\"}");
    }

    private TokenCreator getAccessToken() {
        try {
            return tokenDriver.getTokenCreatorByType(TokenType.JWT_ACCESS_TOKEN);
        } catch (TokenDriver.TokenTypeNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
