package com.stemlen.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stemlen.dto.ApplicantDTO;
import com.stemlen.dto.Application;
import com.stemlen.dto.ApplicationStatus;
import com.stemlen.dto.JobDTO;
import com.stemlen.dto.JobStatus;
import com.stemlen.entity.Applicant;
import com.stemlen.entity.Job;
import com.stemlen.exception.PortalException;
import com.stemlen.repository.JobRepository;
import com.stemlen.utility.Utilities;

@Service("jobService")
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

	@Override
	public JobDTO postJob(JobDTO jobDTO) throws PortalException {
		if(jobDTO.getId()==0) {
		jobDTO.setId(Utilities.getNextSequence("jobs"));
		jobDTO.setPostTime(LocalDateTime.now());
		}
		else {
			Job job=jobRepository.findById(jobDTO.getId())
					.orElseThrow(()->new PortalException("JOB_NOT_FOUND"));
			if(job.getJobStatus().equals(JobStatus.DRAFT) ||
					jobDTO.getJobStatus().equals(JobStatus.CLOSED))
				jobDTO.setPostTime(LocalDateTime.now());
		}
		return jobRepository.save(jobDTO.toEntity()).toDTO();
	}

	@Override
	public List<JobDTO> getAllJobs() {
		    return jobRepository.findAll().stream()
		        .map((x) -> x.toDTO())
		        .toList();

	}

	@Override
	public JobDTO getJob(Long id) throws PortalException {
		
		return jobRepository.findById(id)
				.orElseThrow(()->new PortalException("JOB_NOT_FOUND"))
				.toDTO();
	}

	@Override
	public void applyJob(Long id, ApplicantDTO applicantDTO) throws PortalException {
		Job job=jobRepository.findById(id).orElseThrow(()->new PortalException("JOB_NOT_FOUND"));
		List<Applicant>applicants=job.getApplicants();
		if(applicants==null)
			applicants=new ArrayList<>();
		if(applicants.stream().filter((x)->x.getApplicantId()==applicantDTO.getApplicantId()).toList()
				.size()>0) throw new PortalException("JOB_APPLIED_ALREADY");
		applicantDTO.setApplicationStatus(ApplicationStatus.APPLIED);
		applicants.add(applicantDTO.toEntity());
		job.setApplicants(applicants);
		jobRepository.save(job);
	}

	@Override
	public List<JobDTO> getJobsPostedBy(Long id) {
		 return jobRepository.findByPostedBy(id).stream()
			        .map((x) -> x.toDTO())
			        .toList();
	}

	@Override
	public void changeAppStatus(Application application) throws PortalException {
		Job job=jobRepository.findById(application.getId()).orElseThrow(()->new PortalException("JOB_NOT_FOUND"));
		List<Applicant>applicants=job.getApplicants().stream().map((x)->{
			if(application.getApplicantId()==x.getApplicantId()) {
				x.setApplicationStatus(application.getApplicationStatus());
				if(application.getApplicationStatus().equals(ApplicationStatus.INTERVIEWING))
					x.setInterviewTime(application.getInterviewTime());
			}
			return x;
		}).toList();
		job.setApplicants(applicants);
		jobRepository.save(job);
		
	}

}
