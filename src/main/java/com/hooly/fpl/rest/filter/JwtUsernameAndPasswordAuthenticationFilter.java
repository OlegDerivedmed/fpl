package com.hooly.fpl.rest.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hooly.fpl.model.security.UserDetailsImpl;
import com.hooly.fpl.rest.dto.LoginDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    public static final Logger logger = LoggerFactory.getLogger(JwtUsernameAndPasswordAuthenticationFilter.class);

    @Value("${security.jwt.header}")
    private String jwtHeader;
    @Value("${security.jwt.prefix}")
    private String jwtPrefix;
    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Autowired
    private ObjectMapper objectMapper;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager) {
        setAuthenticationManager(authManager);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication authentication = null;
        try {
            LoginDTO loginDTO = objectMapper.readValue(request.getInputStream(), LoginDTO.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    loginDTO.getLogin(), loginDTO.getPassword(), Collections.emptyList());
            authentication = getAuthenticationManager().authenticate(authToken);
        } catch (IOException e) {
            logger.error("Can't read login request", e);
        }
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        String jwt = Jwts.builder()
                .claim("user_id", ((UserDetailsImpl) authResult.getPrincipal()).getUser().getId())
                .signWith(secretKey)
                .compact();
        response.addHeader(jwtHeader, jwtPrefix + jwt);

    }


}
