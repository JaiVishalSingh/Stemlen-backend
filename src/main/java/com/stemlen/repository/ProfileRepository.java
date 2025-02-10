package com.stemlen.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stemlen.entity.Profile;

public interface ProfileRepository extends MongoRepository<Profile, Long> {

}
