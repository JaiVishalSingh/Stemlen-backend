package com.stemlen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Experience {
    private String title;
    private String company;
    private String location;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean working;
    private String description;
}
