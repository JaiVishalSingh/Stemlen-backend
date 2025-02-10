package com.stemlen.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stemlen.dto.ProfileDTO;
import com.stemlen.exception.PortalException;
import com.stemlen.service.ProfileService;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/profiles")
public class ProfileAPI {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/get/{id}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long id) throws PortalException {
        return new ResponseEntity<>(profileService.getProfile(id), HttpStatus.OK); 
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<ProfileDTO>> getAllProfiles() throws PortalException {
        return new ResponseEntity<>(profileService.getAllProfiles(), HttpStatus.OK); 
    }

    @PutMapping("/update")
    public ResponseEntity<ProfileDTO> updateProfile(@RequestBody ProfileDTO profileDTO) throws PortalException {
        return new ResponseEntity<>(profileService.updateProfile(profileDTO), HttpStatus.OK);
    }
}
