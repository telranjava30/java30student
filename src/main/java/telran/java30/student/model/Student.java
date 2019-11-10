package telran.java30.student.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = { "id" })
@NoArgsConstructor
@Document(collection = "students")
public class Student {
	@Id
	int id;
	@Setter
	String name;
	@Setter
	String password;
	Map<String, Integer> scores;

	public Student(int id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
		scores = new HashMap<>();
	}
	
	public boolean addScore(String examName, int score) {
		return scores.put(examName, score) != null;
	}

}
