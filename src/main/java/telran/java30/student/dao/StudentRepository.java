package telran.java30.student.dao;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import telran.java30.student.model.Student;

public interface StudentRepository extends CrudRepository<Student, Integer> {
	Stream<Student> findByName(String name);
	
	Stream<Student> findByNameRegex(String regex);
	
	long countByName(String name);
	
	@Query("{'?0':{'$gte':?1}}")
	Stream<Student> findByExamScore(String exam, int score);
}
