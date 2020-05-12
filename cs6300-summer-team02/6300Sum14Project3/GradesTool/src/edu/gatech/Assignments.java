package edu.gatech;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

// Assignments class holds information about all assignments for a class. 
// The class contains a constructor that takes a reference to a GradesDB instance as input and 
// constructs (and stores) the Assignment objects based on the information in that instance. 
// The class also provides a method that refreshes the list of assignments.
public class Assignments {
	
	List<Assignment> all_assignments = new ArrayList<Assignment>();
	
	public Assignments(GradesDB gdb) {
		Assignment a = null; 
		
		// loop through the GradesDB.assignmentList HashTable and create a new Assignment object for each one
		// then add each Assignment object to the all_assignments list.
		int num_assignments = gdb.getNumAssignments();
		Set<String> keySet = gdb.assignmentList.keySet();
		Iterator<String> keySetIterator = keySet.iterator();
		while (keySetIterator.hasNext()) {
		   String key = keySetIterator.next();
		   
		   a = new Assignment();
		   
		   //name
		   a.setAssignmentName(key);
		   
		   //number
		   a.setAssignmentNum(Integer.valueOf(key.replaceAll("[A-z\\s]+", "")));
		   int assignment_number = a.getAssignmentNum();
		   int assignment_number_index = assignment_number +1;
		   
		   //description
		   a.setAssignmentDescr(gdb.assignmentList.get(key));
		   
		   //students
		   Hashtable<String, double[]> assignment_student_from_xls = gdb.getAssignmentGrades();
		   Set<String> grades_keySet = assignment_student_from_xls.keySet();
		   Iterator<String> grades_keySetIterator = grades_keySet.iterator();
		   while (grades_keySetIterator.hasNext()) {
			   String grades_key = grades_keySetIterator.next();
			   double[] grade_row = assignment_student_from_xls.get(grades_key);
			   Student student = new Student();
			   student.setName(grades_key);
			   a.addStudentGradeInfo(student, grade_row[assignment_number]); 
		   }	
		   
		  addAssignment(a);

		}
		
		   		
	}
	
	public Assignments assignmentsListRefresh(GradesDB gdb) {
		
		return new Assignments(gdb);	

	}
	
	public Integer getNumAssignments() {
		return all_assignments.size();
	}

	public void addAssignment(Assignment a) {
    	this.all_assignments.add(a);
    }
    
    public void removeAssignment(Assignment assignment) {
    	all_assignments.remove(assignment);
    }
    
    public List<Assignment> getAssignmentList() {
		return all_assignments;
    }
    
    public Assignment getAssignmentObj(String aname) {
    	for (int i = 0; i < all_assignments.size(); i++) {
    	    Assignment a = all_assignments.get(i);
    	    if (a.getAssignmentName().equalsIgnoreCase(aname)) {
    	    	return a;
    	    }
    	}
    	return null;
    }

}

