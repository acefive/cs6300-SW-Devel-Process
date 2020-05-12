package edu.gatech;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Assignment implements Comparable<Assignment>{

	private String name;
	private int number;
	private String description;
	private ConcurrentMap<Student, Double> student_grade = new ConcurrentHashMap();
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Student))
			return false;
		Student other = (Student) obj;
		if (name == null) {
			if (other.getName() != null)
				return false;
		} else if (!name.equals(other.getName()))
			return false;
		return true;
	}

	public void setAssignmentName(String key) {
		this.name = key;
	}
	
	public String getAssignmentName() {
		return name;
	}
	
	public void setAssignmentDescr(String descrip) {
		this.description = descrip;
	}
	
	public String getAssignmentDescr() {
		return description;
	}
	
	public void addStudentGradeInfo(Student student, Double grade) {
		if (!(this.student_grade.containsKey(student))) {
			student_grade.put(student, grade);
		}
	}
	
	public void removeStudentGradeInfo(String studentname) {
		
		Set<Student> keySet = student_grade.keySet();
		Iterator<Student> keySetIterator = keySet.iterator();
		while (keySetIterator.hasNext()) {
		   Student key = keySetIterator.next();
		   if(key!=null && key.getName().equalsIgnoreCase(studentname)) {
			   student_grade.remove(key);
		   }
		}
		
	}
	
	public void updateStudentGradeInfo(Student student, Double grade) {
		if (this.student_grade.containsKey(student)) {
			student_grade.put(student, grade);
		}
	}
	
	public double getStudentGradeInfo(String studentname) {
		double grade = 0;
		Set<Student> keySet = student_grade.keySet();
		Iterator<Student> keySetIterator = keySet.iterator();
		while (keySetIterator.hasNext()) {
		   Student key = keySetIterator.next();
		   if(key.getName().equalsIgnoreCase(studentname)) {
			  grade = student_grade.get(key);
		   }
		}
		return grade;
	}
	
	public Integer getNumStudents() {
		return student_grade.size();
	}

	public void setAssignmentNum(Integer anum) {
		this.number = anum;
	}

	public int getAssignmentNum() {
		return number;
	}

	@Override
	public int compareTo(Assignment assignment) {
		return this.getAssignmentName().compareTo(assignment.getAssignmentName());
	}

	public String getAverageGrade() {
		
		double totalGrade=0; 
		double numberOfStudents=0;
		
		Set<Student> keySet = student_grade.keySet();
		Iterator<Student> keySetIterator = keySet.iterator();
		while (keySetIterator.hasNext()) {
				Student key = keySetIterator.next();
				totalGrade=totalGrade+student_grade.get(key);
				numberOfStudents++;
			
		}
		return String.format("%.0f", totalGrade/numberOfStudents);
	}

	
}
