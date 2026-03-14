package edu.unimagdalena.lms.Repositories;

import edu.unimagdalena.lms.entitles.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InstructorRepository extends JpaRepository<Instructor, UUID> {

}
