package com.stemlen.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.stemlen.dto.AccountType;
import com.stemlen.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")

public class User {
	@Id
    private Long id;
    private String name; 
    @Indexed(unique = true) //so that duplication of email do not occur
    private String email ;
    private String password;
    private AccountType accountType;
    private Long profileId;
    
    public UserDTO toDTO() {
    	return new UserDTO(this.id, this.name , this.email, this.password, this.accountType ,this.profileId);
    }
}
