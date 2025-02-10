package com.stemlen.dto;

import com.stemlen.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private Long id;
    
    @NotBlank(message = "{user.name.absent}")
    private String name; 
    
    @NotBlank(message = "{user.email.absent}")
    @Email(message = "{user.email.invalid}")
    private String email ;
    
    @NotBlank(message = "{user.password.absent}")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", 
             message = "{user.password.invaild}")
    private String password;

    private AccountType accountType;
    private Long profileId;
    
    public User toEntity() {
        return new User(this.id, this.name , this.email, this.password, this.accountType,this.profileId);
    }
}
