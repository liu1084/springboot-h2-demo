package com.jim.db.h2.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @project: springboot-h2-demo
 * @packageName: com.jim.db.h2.entity
 * @author: Administrator
 * @date: 2020/2/27 11:43
 * @description：TODO
 */

@Data
public class Student implements Serializable {
	private static final long serialVersionUID = -4901004952125314004L;
	private Long id;
	private String name;
	private String passportNumber;
}
