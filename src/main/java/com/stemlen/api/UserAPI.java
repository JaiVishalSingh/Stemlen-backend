package com.stemlen.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stemlen.dto.LoginDTO;
import com.stemlen.dto.ResponseDTO;
import com.stemlen.dto.UserDTO;
import com.stemlen.exception.PortalException;
import com.stemlen.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/users")
public class UserAPI {
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<UserDTO>registerUser(@RequestBody @Valid UserDTO userDTO) throws PortalException{
		userDTO=userService.registerUser(userDTO);
		 return new ResponseEntity<>(userDTO,HttpStatus.CREATED);
	}
		 
		 @PostMapping("/login")
			public ResponseEntity<UserDTO>loginUser(@RequestBody @Valid LoginDTO loginDTO) throws PortalException{
				 return new ResponseEntity<>(userService.loginUser(loginDTO),HttpStatus.OK);
		
	}
		 
		 @PostMapping("/changepassword")
			public ResponseEntity<ResponseDTO>changePassword(@RequestBody @Valid LoginDTO loginDTO) throws PortalException{
				 return new ResponseEntity<>(userService.changePassword(loginDTO),HttpStatus.OK);
		
	}
		 
		 @PostMapping("/sendOtp/{email}")
		 public ResponseEntity<ResponseDTO> sendOtp(@PathVariable @Email(message = "{user.email.invalid}") String email) {
		     try {
		         userService.sendOtp(email);
		         return new ResponseEntity<>(new ResponseDTO("OTP Sent Successfully"), HttpStatus.OK);
		     } catch (PortalException ex) {
		         return new ResponseEntity<>(new ResponseDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
		     } catch (Exception e) {
		         // Log exception to help with debugging
		         System.err.println("Error in sending OTP: " + e.getMessage());
		         return new ResponseEntity<>(new ResponseDTO("Error while sending OTP"), HttpStatus.INTERNAL_SERVER_ERROR);
		     }
		 }
		 
		 @GetMapping("/verifyOtp/{email}/{otp}")
		    public ResponseEntity<ResponseDTO> verifyOtp(@PathVariable  @Email(message = "{user.email.invalid}") String email, @PathVariable @Pattern (regexp = "^[0-9]{6}$",message = "{otp.invaild}") String otp) {
		        try {
		            boolean isValid = userService.verifyOtp(email, otp);
		            if (isValid) {
		                return new ResponseEntity<>(new ResponseDTO("OTP Verified Successfully"), HttpStatus.OK);
		            } else {
		                return new ResponseEntity<>(new ResponseDTO("Invalid OTP"), HttpStatus.BAD_REQUEST);
		            }
		        } catch (PortalException ex) {
		            return new ResponseEntity<>(new ResponseDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
		        } catch (Exception e) {
		            System.err.println("Error in verifying OTP: " + e.getMessage());
		            return new ResponseEntity<>(new ResponseDTO("Error while verifying OTP"), HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }

    

}
