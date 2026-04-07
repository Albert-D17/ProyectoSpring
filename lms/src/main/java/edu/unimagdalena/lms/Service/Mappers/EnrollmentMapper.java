package edu.unimagdalena.lms.Service.Mappers;

import edu.unimagdalena.lms.DTO.EnrollmentDTO;
import edu.unimagdalena.lms.entities.Enrollment;
import org.mapstruct.*;

@Mapper()
 public interface EnrollmentMapper {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "course.id", target = "courseId")
    EnrollmentDTO.EnrollmentResponse toResponse(Enrollment entity);

    @Mapping(source = "studentId", target = "student.id")
    @Mapping(source = "CourseId", target = "course.id")
    Enrollment toEntity(EnrollmentDTO.EnrollmentCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(EnrollmentDTO.EnrollmentUpdateRequest dto, @MappingTarget Enrollment entity);
}