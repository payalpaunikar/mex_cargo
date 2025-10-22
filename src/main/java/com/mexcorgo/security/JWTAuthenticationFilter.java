package com.mexcorgo.security;


import com.mexcorgo.exception.UnauthorizedException;
import com.mexcorgo.exception.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private JWTService jwtService;

    private MyUserDetailsService myUserDetailsService;

    @Autowired
    public JWTAuthenticationFilter(JWTService jwtService, MyUserDetailsService myUserDetailsService) {
        this.jwtService = jwtService;
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
             logger.info("the request is public");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authHeader.substring(7);
            String emailId = jwtService.extractEmailId(token);

              logger.info("The username after token extraction is : " + emailId);

            if (emailId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = myUserDetailsService.loadUserByUsername(emailId);

                 logger.info("after get userdetails from our application : " + userDetails.getUsername());

                if (jwtService.validateToken(token, userDetails)) {
                     logger.info("jwt token is valid");

                    List<String> roles = jwtService.extractRole(token);

                    logger.info("Role is : "+roles);

                    if(roles.equals("Admin")){
                        List<String> department = jwtService.extractDepartment(token);

                        logger.info("department is : "+department);

                        List<SimpleGrantedAuthority> authorities = roles
                                .stream()
                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                                .map(dept-> new SimpleGrantedAuthority("DEPT_"+dept))
                                .toList();
                    }

                    List<SimpleGrantedAuthority> authorities = roles
                            .stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .toList();


                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

                    logger.info("user is authenticated with username and password : ");

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    logger.info("user authenticated perfectly");
                    logger.info("Authenticated User: " + SecurityContextHolder.getContext().getAuthentication());

                }

            }

            System.out.println("🔥 API Hit: " + request.getMethod() + " " + request.getRequestURI());

            filterChain.doFilter(request, response);

            logger.info("request is : "+request+"response is : "+response);
        }
        catch (UnauthorizedException unauthorizedException){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Unauthorized exception\", \"message\": \"User does have permission for do this work\"}");
        }
        catch (UserNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Invalid Token\", \"message\": \"User does not exist\"}");

        }

        catch (ExpiredJwtException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"JWT Token has expired\", \"message\": \"Please Login again, JWT Token expired\"}");
        }



    }
}
