package com.crypto.CoinFolio.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.crypto.SecretKey;

public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7); // Remove 'Bearer ' prefix

            try {
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes()); // Ensure this key is at least
                                                                                       // 256-bit for HS256

                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                String email = claims.get("email", String.class);
                String authorities = claims.get("authority", String.class);

                List<GrantedAuthority> authoritiesList = AuthorityUtils
                        .commaSeparatedStringToAuthorityList(authorities);

                // Create the authentication object with principal, credentials, and authorities
                Authentication auth = new UsernamePasswordAuthenticationToken(email, null, authoritiesList);

                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                // Handle token parsing exception (specific to your needs)
                throw new ServletException("Invalid or expired token", e);
            }
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
