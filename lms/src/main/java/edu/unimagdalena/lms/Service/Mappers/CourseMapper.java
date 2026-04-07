package edu.unimagdalena.lms.Service.Mappers;

import edu.unimagdalena.lms.DTO.CourseDTO;
import edu.unimagdalena.lms.entitles.Course;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(source = "instructor.id", target = "instructorId")
    CourseDTO.CourseResponse toResponse(Course entity);

    @Mapping(source = "instructorId", target = "instructor.id")
    Course toEntity(CourseDTO.CourseCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(CourseDTO.CourseUpdateRequest dto, @MappingTarget Course entity);
}