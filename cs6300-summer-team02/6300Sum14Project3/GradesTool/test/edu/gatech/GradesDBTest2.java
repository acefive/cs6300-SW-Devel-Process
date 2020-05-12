package edu.gatech;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

import com.speqmath.Parser;

import junit.framework.TestCase;
import edu.gatech.GradesDB;
import edu.gatech.Student;

public class GradesDBTest2 extends TestCase {
	GradesDB db = null;
	Student student = null;
	
	protected void setUp() throws Exception {
		db = new GradesDB(Constants.GRADES_DB);
		student = new Student();
		student.setAttendance(5);
		student.setCAttendance(3);
		student.setCppAttendance(2);
		student.setCsJobEx(true);
		student.setEmail("ted@google.com");
		student.setGtid("1234567");
		
		student.setJavaAttendance(0);
		student.setName("Ted Smith");
		Team t0 = new Team(1,3);
		Team t1 = new Team(1,5);
		Team t2 = new Team(1,2);
		student.addTeam(t1);
		student.addTeam(t2);
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testPrintStudentInfo() {
		try {
			String info = db.printStudentInfo(student);
			assertEquals("Ted Smith|ted@google.com|1234567|True|5|3|2|0|2,5", info);
		} catch (Exception e) {
			fail("Exception" + e.getMessage());
		}
	}
	
	public void testAverageAssignmentGrade(){	
		Student caileigh = db.getStudentByID("901234506");
		Student shevon = db.getStudentByID("901234504");
		
		assertEquals(db.getAverageAssignmentGrade(caileigh),98.3333,.01);
		assertEquals(db.getAverageAssignmentGrade(shevon),80,.01);
	}

	public void testTeamForProj(){	
		Student grier = db.getStudentByID("901234503");
		Student shevon = db.getStudentByID("901234504");
		
		assertEquals(db.getTeamForProject(grier, 2),"Team 3");
		assertEquals(db.getTeamForProject(grier, 3),"Team 3");
		
		assertEquals(db.getTeamForProject(shevon, 1),"Team 1");
		assertEquals(db.getTeamForProject(shevon, 2),"Team 3");
		assertEquals(db.getTeamForProject(shevon, 3),"Team 2");
	}
	
	public void testGetAvgProjectContribution(){
		Student caileigh = db.getStudentByID("901234506");
		Student shevon = db.getStudentByID("901234504");
		
		assertEquals(db.getAvgProjectContribution(caileigh,1),7.67,.01);
		assertEquals(db.getAvgProjectContribution(caileigh,2),8.5,.01);
		assertEquals(db.getAvgProjectContribution(caileigh,3),9,.01);
		
		assertEquals(db.getAvgProjectContribution(shevon,1),9.25,.01);
		assertEquals(db.getAvgProjectContribution(shevon,2),7.5,.01);
		assertEquals(db.getAvgProjectContribution(shevon,3),8.25,.01);
	}
	
	public void testProjectGrade(){	
		Student caileigh = db.getStudentByID("901234506");
		Student shevon = db.getStudentByID("901234504");
	
		assertEquals(db.getGradeForProject(shevon, 1),93.0,.01);
		assertEquals(db.getGradeForProject(shevon, 2),86.0,.01);
		assertEquals(db.getGradeForProject(shevon, 3),96.0,.01);
		
		assertEquals(db.getGradeForProject(caileigh, 1),90.0,.01);
		assertEquals(db.getGradeForProject(caileigh, 2),96.0,.01);
		assertEquals(db.getGradeForProject(caileigh, 3),100.0,.01);
	}
	
    public void testGetStudentGrade(){
    	db.setFormula("AS * 0.2 + AT * 0.2 + ((PR1 + PR2 + PR3)/3) * 0.6");
    	Student caileigh = db.getStudentByID("901234506");
		Student shevon = db.getStudentByID("901234504");
    	assertEquals(db.getStudentGrade(caileigh),93.46,0.1);
    	assertEquals(db.getStudentGrade(shevon),91.0,0.1);
    	
    	db.setFormula("AS * 0.2 + AT * 0.2 + ((PR1*(IC1/10) + PR2*(IC2/10) + PR3*(IC3/10))/3) * 0.6");
    	assertEquals(db.getStudentGrade(caileigh),84.38,0.1);
    	assertEquals(db.getStudentGrade(shevon),81.94,0.1);
    }

    public void testGetAllGrades(){
    	db.setFormula("AS * 0.2 + AT * 0.2 + ((PR1 + PR2 + PR3)/3) * 0.6");
    	HashMap<Student,Double> grades = (HashMap<Student, Double>) db.getAllGrades();
    	assertEquals(grades.size(),14);
    	assertEquals(grades.get(db.getStudentByName("Sheree Gadow")),95.2,0.1);
    	assertEquals(grades.get(db.getStudentByName("Caileigh Raybould")),93.46,0.1);
    }
    
    public void testGetAverageGrade(){
    	assertEquals(93.67,db.getAverageGrade(),.01);
    }
   
    public void testGetMedianGrade(){
    	db.setFormula("AS * 0.2 + AT * 0.2 + ((PR1*(IC1/10) + PR2*(IC2/10) + PR3*(IC3/10))/3) * 0.6");
    	assertEquals(84.66,db.getMedianGrade(),.01);
    	
    	db.setFormula("AS * 0.2 + AT * 0.2 + ((PR1 + PR2 + PR3)/3) * 0.6");
    	assertEquals(93.467,db.getMedianGrade(),.01);
    }
     
}
