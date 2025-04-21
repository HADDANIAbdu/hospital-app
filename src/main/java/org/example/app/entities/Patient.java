package org.example.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @NotEmpty(message = "field is required")
    @Size(min = 3, max = 40, message = "length must be between 3 and 40")
    private String fullname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "field is required")
    @Past
    private Date dateOfBirth;

    @NotEmpty(message = "field is required")
    private String gender;

    private boolean sick;
    @NotNull(message = "field is required")
    @Min(value = 10, message = "score must be greater than 10")
    private int score;
}
