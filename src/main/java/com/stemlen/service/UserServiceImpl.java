package com.stemlen.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stemlen.dto.LoginDTO;
import com.stemlen.dto.ResponseDTO;
import com.stemlen.dto.UserDTO;
import com.stemlen.entity.OTP;
import com.stemlen.entity.User;
import com.stemlen.exception.PortalException;
import com.stemlen.repository.OTPRepository;
import com.stemlen.repository.UserRepository;
import com.stemlen.utility.OTPTemp;
import com.stemlen.utility.Utilities;

import jakarta.mail.internet.MimeMessage;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProfileService profileService;
    
    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public UserDTO registerUser(UserDTO userDTO) throws PortalException {
     Optional<User> optional=userRepository.findByEmail(userDTO.getEmail());
        if(optional.isPresent()) throw new PortalException("USER_FOUND");
        //made a change of userDTO.getName()
        userDTO.setProfileId( profileService.createProfile(userDTO.getEmail(), userDTO.getName()));
        userDTO.setId(Utilities.getNextSequence("users"));
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User user = userDTO.toEntity();
        user = userRepository.save(user);

        return user.toDTO();
    }
    
   
    @Override
    public UserDTO loginUser(LoginDTO loginDTO) throws PortalException{
       
        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(()->new PortalException("USER_NOT_FOUND"));
        
       if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))throw new PortalException("INVALID_CREDENTIALS");
       
        return user.toDTO();
    }
    @Override
    public Boolean sendOtp(String email) throws Exception {
        System.out.println("Sending OTP to: " + email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new PortalException("USER_NOT_FOUND"));

        MimeMessage mm = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mm, true);
        message.setTo(email);
        message.setSubject("Your OTP Code");

        String genOTP = Utilities.genrateOTP();
        System.out.println("Generated OTP: " + genOTP);

        OTP otp = new OTP(email, genOTP, LocalDateTime.now());
        otpRepository.save(otp);
        System.out.println("OTP saved to DB.");

        message.setText(OTPTemp.getMessageBody(genOTP, user.getName()), true);

        mailSender.send(mm);
        System.out.println("Email sent successfully.");
        return true;
    }


    @Override
    public Boolean verifyOtp(String email, String otp) throws PortalException {
        OTP storedOtp = otpRepository.findByEmail(email)
            .orElseThrow(() -> new PortalException("OTP_NOT_FOUND"));

        if (!storedOtp.getOtpCode().equals(otp)) {
            throw new PortalException("INVALID_OTP");
        }
        if (storedOtp.getCreationTime().plusMinutes(10).isBefore(LocalDateTime.now())) {
            throw new PortalException("OTP_EXPIRED");
        }
       
        return true;
    }

    @Override
    public ResponseDTO changePassword(LoginDTO loginDTO) throws PortalException {
        // Find user by email
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new PortalException("USER_NOT_FOUND"));
        
        // Encrypt and update the user's password
        user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
        userRepository.save(user);
        
        return new ResponseDTO("Password changed successfully.");
    }
    
    @Scheduled(fixedRate = 60000)
    public void removeExpiredOTPs() {
        LocalDateTime expiry=LocalDateTime.now().minusMinutes(10);
        List<OTP>expiredOTPs=otpRepository.findByCreationTimeBefore(expiry);
        if(!expiredOTPs.isEmpty()) {
        	otpRepository.deleteAll(expiredOTPs);
        	System.out.println("Removed "+expiredOTPs.size()+" expired OTPs");
        }
    }


	@Override
	public UserDTO getUserByEmail(String email) throws PortalException {
		return userRepository.findByEmail(email)
                .orElseThrow(() -> new PortalException("USER_NOT_FOUND")).toDTO();
	}


    
    
}
