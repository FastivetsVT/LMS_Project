package com.brainacad.lms.fastivets.model;

public class Student extends Person {	
	private static final long serialVersionUID = 1L;
	private int age;
	
	public Student(int id) {
		super(id);
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}