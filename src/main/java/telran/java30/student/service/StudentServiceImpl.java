package telran.java30.student.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.java30.student.dao.StudentRepository;
import telran.java30.student.dto.ScoreDto;
import telran.java30.student.dto.StudentDto;
import telran.java30.student.dto.StudentNotFountException;
import telran.java30.student.dto.StudentResponseDto;
import telran.java30.student.dto.StudentUpdateDto;
import telran.java30.student.model.Student;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	StudentRepository studentRepository;
	
	@Override
	public boolean addStudent(StudentDto studentDto) {
		if(studentRepository.existsById(studentDto.getId())) {
			return false;
		}
		Student student = new Student(studentDto.getId(),
				studentDto.getName(), studentDto.getPassword());
		studentRepository.save(student);
		return true;
	}

	@Override
	public StudentResponseDto findStudent(Integer id) {
		Student student = studentRepository.findById(id)
				.orElseThrow(()-> new StudentNotFountException(id));
		return studentToStudentResponseDto(student);
	}
	
	private StudentResponseDto studentToStudentResponseDto(Student student) {
		return StudentResponseDto.builder()
				.id(student.getId())
				.name(student.getName())
				.scores(student.getScores())
				.build();
	}

	@Override
	public StudentResponseDto deleteStudent(Integer id) {
		Student student = studentRepository.findById(id).orElseThrow(()-> new StudentNotFountException(id));
		studentRepository.delete(student);
		return studentToStudentResponseDto(student);
	}

	@Override
	public StudentDto updateStudent(Integer id, StudentUpdateDto studentUpdateDto) {
		Student student = studentRepository.findById(id).orElseThrow(()-> new StudentNotFountException(id));
		if (studentUpdateDto.getName() != null) {
			student.setName(studentUpdateDto.getName());
		}
		if (studentUpdateDto.getPassword() != null) {
			student.setPassword(studentUpdateDto.getPassword());
		}
		studentRepository.save(student);
		return studentToStudentDto(student);
	}

	private StudentDto studentToStudentDto(Student student) {
		return StudentDto.builder()
				.id(student.getId())
				.name(student.getName())
				.password(student.getPassword())
				.build();
	}

	@Override
	public boolean addScore(Integer id, ScoreDto scoreDto) {
		Student student = studentRepository.findById(id).orElseThrow(()-> new StudentNotFountException(id));
		boolean res = student.addScore(scoreDto.getExamName(), scoreDto.getScore());
		studentRepository.save(student);
		return res;
	}

	@Override
	public List<StudentResponseDto> findStudentsByName(String name) {
		return studentRepository.findByName(name)
//				.map(this::studentToStudentResponseDto)
				.map(s -> 
					StudentResponseDto.builder()
							.id(s.getId())
							.name(s.getName())
							.scores(s.getScores())
							.build()
				)
				.collect(Collectors.toList());
	}

	@Override
	public long countStudentsByName(String name) {
		return studentRepository.countByName(name);
	}

	@Override
	public List<StudentResponseDto> findStudentsByExamScore(String examName, int minScore) {
		return studentRepository.findByExamScore("scores."+examName, minScore)
				.map(this::studentToStudentResponseDto)
				.collect(Collectors.toList());
	}

}
