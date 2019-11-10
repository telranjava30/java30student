package telran.java30.student.service;

import java.util.List;

import telran.java30.student.dto.ScoreDto;
import telran.java30.student.dto.StudentDto;
import telran.java30.student.dto.StudentResponseDto;
import telran.java30.student.dto.StudentUpdateDto;

public interface StudentService {
	boolean addStudent(StudentDto studentDto);

	StudentResponseDto findStudent(Integer id);

	StudentResponseDto deleteStudent(Integer id);

	StudentDto updateStudent(Integer id, StudentUpdateDto studentUpdateDto);

	boolean addScore(Integer id, ScoreDto scoreDto);
	
	List<StudentResponseDto> findStudentsByName(String name);
	
	long countStudentsByName(String name);
	
	List<StudentResponseDto> findStudentsByExamScore(String examName, int minScore);

}
