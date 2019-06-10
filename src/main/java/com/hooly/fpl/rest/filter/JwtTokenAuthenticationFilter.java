package com.hooly.fpl.rest.filter;

import com.hooly.fpl.model.service.UserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    @Value("${security.jwt.header}")
    private String jwtHeader;
    @Value("${security.jwt.prefix}")
    private String jwtPrefix;
    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Autowired
    private UserServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String header = httpServletRequest.getHeader(jwtHeader);
        if (header == null || !header.startsWith(jwtPrefix)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String token = header.replace(jwtPrefix, "");
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret.getBytes())
                .parseClaimsJws(token)
                .getBody();
//        if (claims.getExpiration().after(new Date())) {
//            SecurityContextHolder.clearContext();
//            return;
//        }
        Long userId = claims.get("user_id", Long.class);
        if (userId != null) {
            userService.findById(userId).ifPresent(user -> {
                List<String> authorities = Optional.ofNullable((List<String>) claims.get("authorities")).orElse(List.of());
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user, null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            });
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
