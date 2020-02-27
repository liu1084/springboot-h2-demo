package com.jim.db.h2;

import com.jim.db.h2.entity.Student;
import com.jim.db.h2.service.mapper.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@Slf4j
public class SpringbootH2Application implements CommandLineRunner {

	@Autowired
	private StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootH2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Long studentId = 10001L;
		Student student = studentRepository.findStudentById(studentId);
		log.debug("Student id = {}, {}", studentId, student);

		List<Student> students = studentRepository.findStudents();
		log.debug("{}",students);
	}
}
