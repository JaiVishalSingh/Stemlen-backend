package com.stemlen.service;

import java.util.List;

import com.stemlen.dto.ProfileDTO;
import com.stemlen.exception.PortalException;

public interface ProfileService {
	public Long createProfile(String email,String name) throws PortalException;//made changes string name
	public ProfileDTO getProfile(Long id) throws PortalException;
	public ProfileDTO updateProfile(ProfileDTO profileDTO)throws PortalException;
	public List<ProfileDTO> getAllProfiles();

}
