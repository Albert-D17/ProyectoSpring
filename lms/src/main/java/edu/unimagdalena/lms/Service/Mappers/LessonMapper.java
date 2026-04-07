package edu.unimagdalena.lms.Service.Mappers;

import edu.unimagdalena.lms.DTO.LessonDTO;
import edu.unimagdalena.lms.entities.Lesson;
import org.mapstruct.*;

    @Mapper()
    public interface LessonMapper {

        @Mapping(source = "course.id", target = "courseId")
        LessonDTO.LessonResponse toResponse(Lesson entity);

        @Mapping(source = "courseId", target = "course.id")
        Lesson toEntity(LessonDTO.LessonCreateRequest dto);

        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
        void updateEntity(LessonDTO.LessonUpdateRequest dto, @MappingTarget Lesson entity);
    }
