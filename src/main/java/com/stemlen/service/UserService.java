package com.stemlen.service;

import com.stemlen.dto.LoginDTO;
import com.stemlen.dto.ResponseDTO;
import com.stemlen.dto.UserDTO;
import com.stemlen.exception.PortalException;


public interface UserService {
  public UserDTO registerUser(UserDTO userDTO) throws PortalException;
  
  public UserDTO getUserByEmail(String email) throws PortalException;

public UserDTO loginUser(LoginDTO loginDTO) throws PortalException;

public Boolean sendOtp(String email) throws Exception;

public Boolean verifyOtp(String email, String otp) throws PortalException;

public ResponseDTO changePassword( LoginDTO loginDTO) throws PortalException;

}
