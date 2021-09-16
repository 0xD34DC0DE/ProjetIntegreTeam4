package com.team4.backend.mapper;


import com.team4.backend.dto.StudentDto;
import com.team4.backend.dto.UserDto;
import com.team4.backend.model.Student;
import com.team4.backend.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper extends UserMapper {

    public StudentMapper() {
        super();
        this.modelMapper.createTypeMap(StudentDto.class, Student.class).includeBase(UserDto.class, User.class);
        this.modelMapper.createTypeMap(Student.class, StudentDto.class).includeBase(User.class, UserDto.class);
    }

    @Bean("studentDtoMapper")
    StudentMapper studentMapper() {
        return new StudentMapper();
    }

    public StudentDto entityToDto(Student student) {
        return modelMapper.map(student, StudentDto.class);
    }

    public Student dtoToEntity(StudentDto studentDto) {
        return modelMapper.map(studentDto, Student.class);
    }
}
