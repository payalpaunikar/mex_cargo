package com.mexcorgo.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private MyUserDetailsService myUserDetailsService;

    private JWTAuthenticationFilter jwtAuthenticationFilter;


    @Autowired
    public SecurityConfig(MyUserDetailsService myUserDetailsService,JWTAuthenticationFilter jwtAuthenticationFilter){
        this.myUserDetailsService = myUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .cors(Customizer.withDefaults()) // Enable CORS
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(request-> request
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/add/master",
                                "/update/master/{masterId}",
                                "/delete/master/{masterId}").hasRole("Admin")
                        .requestMatchers("/emp/register","/department",
                                "/available/member","/available/leader","/created/group",
                                "/delete/group/{groupId}",
                                "/update/group/{groupId}/name",
                                "/delete/group/{groupId}/member/{memberId}",
                                "/update/group/{groupId}/leader/{leaderId}",
                                "/get/group/{groupId}/name",
                                "/group/{groupId}/add/member").hasAnyRole("Admin","Head")
                        .requestMatchers("/created/lead",
                                "/get/currentuser/lead",
                                "/get/lead/{leadId}/company/details",
                                "/get/lead/{leadId}/company/emp/details",
                                "/get/lead/{leadId}/need",
                                "/get/lead/{leadId}/note",
                                "/delete/lead/{leadId}",
                                "/get/todoentity/{toDoEntity}/currentdate/count",
                                "/add/phone/details",
                                "/add/email/details",
                                "/add/whatsApp/details",
                                "/add/physicalmeeting/details",
                                "/add/netsearching/details",
                                "/add/meeting/details",
                                "/add/report/details",
                                "/add/meeting/details",
                                "/add/miscellaneous/details",
                                "/get/lead/{leadId}",
                                "/update/lead/{leadId}",
                                "/update/lead/{leadId}/company/details",
                                "/update/lead/{leadId}/company/emp/details",
                                "/update/lead/{leadId}/need/details",
                                "/update/lead/{leadId}/note/details",
                                "/add/note/{noteId}/followups",
                                "/get/todoentity/count/list",
                                "/get/todo/{todoId}/phone/details",
                                "/get/todo/{toDoId}/email/details",
                                "/get/todo/{toDoId}/meeting/details",
                                "/get/todo/{toDoId}/miscellaneous/details",
                                "/get/todo/{toDoId}/netsearching/details",
                                "/get/todo/{toDoId}/report/details",
                                "/get/todo/{toDoId}/physicalmeeting/details",
                                "/get/todo/{toDoId}/whatsapp/details",
                                "/get/email/{emailId}/details",
                                "/get/meeting/{meetingId}/details",
                                "/get/miscellaneous/{miscellaneousId}/details",
                                "/get/report/{reportId}/details",
                                "/get/phone/{phoneId}/details",
                                "/get/whatsApp/{whatsAppId}/details",
                                "/get/physicalmeeting/{physicalMeetingId}/details",
                                "/get/netsearching/{netSearchingId}/details",
                                "/update/email/{emailId}/details",
                                "/update/meeting/{meetingId}/details",
                                "/update/whatsApp/{whatsAppId}/details",
                                "/update/report/{reportId}/details",
                                "/update/physicalmeeting/{physicalMeetingId}/details",
                                "/update/phone/{phoneId}/details",
                                "/update/netsearching/{netSearchingId}/details",
                                "/update/miscellaneous/{miscellaneousId}/details",
                                "/delete/email/{emailId}/details",
                                "/delete/meeting/{meetingId}/details",
                                "/delete/miscellaneous/{miscellaneousId}/details",
                                "/delete/netsearching/{netSearchingId}/details",
                                "/delete/phone/{phoneId}/details",
                                "/delete/physicalmeeting/{physicalMeetingId}/details",
                                "/delete/report/{reportId}/details",
                                "/delete/whasApp/{whatsAppId}/details",
                                "/purchase/**",
                                "/quatation/**",
                                "/lead/{leadId}/add/post-lead-details")
                        .hasAnyRole("Admin","Head","Leader","Member")
                        .requestMatchers("/get/groups/name",
                                "/get/group/{groupId}").hasAnyRole("Admin","Head","Leader")
                        .requestMatchers(
                                "/get/pricing/emp/list").hasAnyRole("Admin","Head","Purchase Executive")
                       // .requestMatchers("/get/master/list").hasAnyRole("Admin","Purchase Executive")
                        //.requestMatchers("/pricingexceutive/quatation/**").hasAnyRole("Admin","Head","Pricing Executive")
                        .requestMatchers("/get/quatation/{quatationId}/master",
                                "/get/quatation/{quatationId}/master/{masterId}/response").hasAnyRole("Admin","Purchase Executive","Pricing Executive")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session-> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        return daoAuthenticationProvider;
    }


//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }

    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(Collections.singletonList(daoAuthenticationProvider()));
    }


//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:3000","http://localhost:3001")); // Frontend URL
//        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH"));
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));  // Allow specific origins or use "*"
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH"));  // Allowed HTTP methods
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "x-auth-token", "Accept"));  // Allowed headers
        configuration.setExposedHeaders(Arrays.asList("Authorization", "x-auth-token"));  // Expose headers to the client
        configuration.setAllowCredentials(true);  // Allow credentials (cookies, authorization headers, etc.)
        configuration.setMaxAge(3600L);  // Cache preflight response for 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // Apply CORS configuration to all endpoints
        return source;
    }

}
