package com.stemlen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Certification {
	private String name;
	private String issuer;
	private LocalDateTime issueDate;
	private String certificateID;
}
