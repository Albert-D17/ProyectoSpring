package edu.unimagdalena.lms.entitles;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "instructor_profiles")

public class InstructorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String bio;

    @OneToOne(optional = false)
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    private Instructor instructor;
}
