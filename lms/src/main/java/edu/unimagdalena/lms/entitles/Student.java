package edu.unimagdalena.lms.entitles;
import jakarta.persistence.*;
import lombok.*;

import javax.annotation.processing.Generated;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "students")

public class Student{

@Id
@GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

@Column(name="full_name", nullable = false)
    private String fullName;

@Column(nullable = false)
    private String email;

@Column(name = "created_at")
    private Instant createdAt;

@Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "student")
    private Set<Enrollment> enrollments;

    @OneToMany(mappedBy = "student")
    private Set<Assessment> assessments;
}