package com.stemlen.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sequence")
public class Sequence {

    @Id
    private String id;

    private Long seq = 0L; // Default value for sequence
}
