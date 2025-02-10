package com.stemlen.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stemlen.jwt.AuthRequest;
import com.stemlen.jwt.AuthResponse;
import com.stemlen.jwt.JwtHelper;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/auth")
public class AuthAPI {
     @Autowired
     private UserDetailsService userDetailsService;
     
     @Autowired
     private AuthenticationManager authenticationManager;
     
     @Autowired
     private JwtHelper jwtHelper;
     
     @PostMapping("/login")
     public ResponseEntity<?>createAuthenticationToken(@RequestBody AuthRequest request){
    	 authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    	 final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
    	 final String jwt =jwtHelper.generateToken(userDetails);
    	 return ResponseEntity.ok(new AuthResponse(jwt));
     }
     
}
