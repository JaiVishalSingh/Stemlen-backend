package com.stemlen.service;

import java.util.List;

import com.stemlen.dto.ApplicantDTO;
import com.stemlen.dto.Application;
import com.stemlen.dto.JobDTO;
import com.stemlen.exception.PortalException;


public interface JobService {

	public JobDTO postJob(JobDTO jobDTO) throws PortalException;

	public List<JobDTO> getAllJobs();

	public JobDTO getJob(Long id) throws PortalException;

	public void applyJob(Long id, ApplicantDTO applicantDTO) throws PortalException;

	public List<JobDTO> getJobsPostedBy(Long id);

	public void changeAppStatus(Application application) throws PortalException;

}
