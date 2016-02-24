package com.brainacad.lms.fastivets.model;

import java.io.Serializable;
import java.util.Date;
import java.util.TreeMap;
import java.util.TreeSet;

public class Course implements Serializable, Comparable<Course> {	
	private static final long serialVersionUID = 1L;
		
	private final int ID;
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private int days;	//as bit mask
	private Integer trainer;
	private TreeSet<Integer> students = new TreeSet<>();
	private TreeMap <Integer, TreeMap<Integer, Integer>> gradebook = new TreeMap<>();
		
	public Course(int id) {
		ID = id;
	}
	
	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public Integer getTrainer() {
		return trainer;
	}

	public void setTrainer(Integer trainer) {
		this.trainer = trainer;
	}
	
	public TreeSet<Integer> getStudents() {
		return students;
	}
	
	public TreeMap<Integer, TreeMap<Integer, Integer>> getGradebook() {
		return gradebook;
	}

	@Override
	public int compareTo(Course course) {
		return ID - course.ID;
	}
	
	@Override
	public String toString() {
		return String.format("%d: %s", ID, name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Course other = (Course) obj;
		if (ID != other.ID)	return false;
		return true;
	}

	public static class Task implements Serializable, Comparable<Task>{
		private static final long serialVersionUID = 1L;
		
		private final int ID;
		private String title;
		private Integer course;
		
		public Task(int id) {
			ID = id;
		}		

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
		
		public int getID() {
			return ID;
		}
		
		public Integer getCourse() {
			return course;
		}

		public void setCourse(Integer course) {
			this.course = course;
		}

		@Override
		public int compareTo(Task task) {
			return ID - task.ID;
		}

		@Override
		public String toString() {
			return String.format("%d: %s", ID, title);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ID;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			Task other = (Task) obj;
			if (ID != other.ID)	return false;
			return true;
		}		
	}
}