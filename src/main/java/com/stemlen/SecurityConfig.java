package com.stemlen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import com.stemlen.jwt.JwtAuthEntryPoint;
import com.stemlen.jwt.JwtAuthFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthEntryPoint point;

    @Autowired
    private JwtAuthFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ Fix: Defined configuration source method
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login", "/users/register", "/users/verifyOtp/**", "/users/sendOtp/**").permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000")); // ✅ Allow frontend origin
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}



//package com.stemlen;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.stemlen.jwt.JwtAuthEntryPoint;
//import com.stemlen.jwt.JwtAuthFilter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//	
//	@Autowired
//	private JwtAuthEntryPoint point;
//	
//	@Autowired
//	private JwtAuthFilter filter;
//
//   
//
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	    http.csrf(csrf -> csrf.disable())
//	        .authorizeRequests()
//	        .requestMatchers("/auth/login", "/users/register", "/users/verifyOtp/**", "/users/sendOtp/**").permitAll() // Fix: Added "/" before "users/register"
//	        .anyRequest()
//	        .authenticated()
//	        .and()
//	        .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
//	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//	    
//	    http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
//	    return http.build();
//	}
//
//}
