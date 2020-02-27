package com.jim.db.h2;

import com.jim.db.h2.entity.Student;
import com.jim.db.h2.service.mapper.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringbootH2ApplicationTests {

	@Autowired
	private StudentRepository studentRepository;

	@Test
	public void testFindStudent() {
		Long id = 10001L;
		Student student = studentRepository.findStudentById(id);
		Assertions.assertNotNull(student);
		Assertions.assertEquals(id, student.getId());
	}

	@Test
	public void testFindStudents() {
		List<Student> students = studentRepository.findStudents();
		Assertions.assertNotEquals(0, students.size());
		Assertions.assertEquals(2, students.size());
	}
}
