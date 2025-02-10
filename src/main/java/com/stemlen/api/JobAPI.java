package com.stemlen.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stemlen.dto.ApplicantDTO;
import com.stemlen.dto.Application;
import com.stemlen.dto.JobDTO;
import com.stemlen.dto.ResponseDTO;
import com.stemlen.exception.PortalException;
import com.stemlen.service.JobService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/jobs")
public class JobAPI {
    
	@Autowired
	private JobService jobService;
	
	@PostMapping("/post")
	public ResponseEntity<JobDTO>postJob(@RequestBody @Valid JobDTO jobDTO) throws PortalException{
		 return new ResponseEntity<>(jobService.postJob(jobDTO),HttpStatus.CREATED);
	}
	@GetMapping("/getAll")
	public ResponseEntity<List<JobDTO>>getAllJobs() throws PortalException{
		 return new ResponseEntity<>(jobService.getAllJobs(),HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<JobDTO>getJob(@PathVariable Long id) throws PortalException{
		 return new ResponseEntity<>(jobService.getJob(id),HttpStatus.OK);
	}
	
	@PostMapping("/apply/{id}")
	public ResponseEntity<ResponseDTO>applyJob(@PathVariable Long id,@RequestBody ApplicantDTO applicantDTO)
			throws PortalException{
		jobService.applyJob(id, applicantDTO);
		 return new ResponseEntity<>(new ResponseDTO("Applied Successfully"),HttpStatus.OK);
	}
	@GetMapping("/postedBy/{id}")
	public ResponseEntity<List<JobDTO>>getJobsPostedBy(@PathVariable Long id) throws PortalException{
		 return new ResponseEntity<>(jobService.getJobsPostedBy(id),HttpStatus.OK);
	}
	
	@PostMapping("/changeAppStatus")
	public ResponseEntity<ResponseDTO>changeAppStatus(@RequestBody Application application)
			throws PortalException{
		jobService.changeAppStatus(application);
		 return new ResponseEntity<>(new ResponseDTO("Application Status Changed Successfully"),HttpStatus.OK);
	}
	
	
}
