package com.jim.db.h2.service.mapper;

import com.jim.db.h2.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @project: springboot-h2-demo
 * @packageName: com.jim.db.h2.service.impl
 * @author: Administrator
 * @date: 2020/2/27 11:44
 * @description：TODO
 */
@Mapper
@Repository
public interface StudentRepository {
	@Select(value = "select id, name, passport_number as passportNumber from student t1 where t1.id = #{id, jdbcType=BIGINT}")
	Student findStudentById(Long id);

	List<Student> findStudents();
}
