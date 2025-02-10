package com.stemlen.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stemlen.entity.OTP;
import java.time.LocalDateTime;


public interface OTPRepository extends MongoRepository<OTP, String> {
	Optional<OTP> findByEmail(String email);
	List<OTP> findByCreationTimeBefore(LocalDateTime expiry);

}
