package edu.gatech;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import junit.framework.TestCase;
import edu.gatech.Project;
import edu.gatech.Team;


public class AssignmentTest extends TestCase {
	
	GradesDB db = null;
	Assignments allAssignments = null;
	
	public void setUp() throws Exception {
		
		db = new GradesDB(Constants.GRADES_DB);
		allAssignments = new Assignments(db);		
		super.setUp();	

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testGetAssignmentDescription() {
		
		Assignment a = allAssignments.getAssignmentObj("Assignment 1");
		try {
			assertEquals("swiki page",a.getAssignmentDescr());
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}
		
	}
	
	public void testGetAssignmentNumber() {
		Assignment a = allAssignments.getAssignmentObj("Assignment 1");
	
		try {
			assertEquals(1,a.getAssignmentNum());
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}
		
	}
	
	public void testAddStudentGradeInfo() {

		Assignment a = allAssignments.getAssignmentObj("Assignment 1");

		Student stud = new Student();
		stud.setName("Joe Fairchild");		
		a.addStudentGradeInfo(stud, 99.0);
		
		try {
			assertEquals(99.0,a.getStudentGradeInfo("Joe Fairchild"));
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}
		
	}
	
	public void testUpdateStudentGradeInfo() {

		Assignment a = allAssignments.getAssignmentObj("Assignment 1");

		Student s = new Student();
		s.setName("Gabby Fairchild");
		
		a.addStudentGradeInfo(s, 95.0);
		a.updateStudentGradeInfo(s, 110.0);
		
		try {
			assertEquals(110.0,a.getStudentGradeInfo("Gabby Fairchild"));
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}
		
	}
	
	
	public void testRemoveStudentGradeInfo() {

		Assignment a = allAssignments.getAssignmentObj("Assignment 2");
		a.removeStudentGradeInfo("Josepha Jube");
		
		try {
			assertEquals(13,a.getNumStudents().intValue());
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}
		
	}
	
}
