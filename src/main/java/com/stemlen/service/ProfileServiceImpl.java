package com.stemlen.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stemlen.dto.ProfileDTO;
import com.stemlen.entity.Profile;
import com.stemlen.exception.PortalException;
import com.stemlen.repository.ProfileRepository;
import com.stemlen.utility.Utilities;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService {
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Override
	public Long createProfile(String email,String name) throws PortalException {
		Profile profile = new Profile();
        profile.setId(Utilities.getNextSequence("profiles"));
        profile.setEmail(email); 
        profile.setName(name);// made changes if error occur go , remove it and remove from ProfileService UserServiceImpl
        profile.setSkills(new ArrayList<>());
        profile.setExperiences(new ArrayList<>());
        profile.setCertifications(new ArrayList<>());
        profileRepository.save(profile);
        return profile.getId();
	}

	@Override
	public ProfileDTO getProfile(Long id) throws PortalException {
	    return profileRepository.findById(id)
	        .orElseThrow(() -> new PortalException("PROFILE_NOT_FOUND"))
	        .toDTO();
	}


	@Override
	public ProfileDTO updateProfile(ProfileDTO profileDTO) throws PortalException {
		profileRepository.findById(profileDTO.getId())
	            .orElseThrow(() -> new PortalException("PROFILE_NOT_FOUND"));
	    profileRepository.save(profileDTO.toEntity());
	    return profileDTO;
	}

	@Override
	public List<ProfileDTO> getAllProfiles() {
		return profileRepository.findAll().stream().map((x)->x.toDTO()).toList();
	}

	

}
