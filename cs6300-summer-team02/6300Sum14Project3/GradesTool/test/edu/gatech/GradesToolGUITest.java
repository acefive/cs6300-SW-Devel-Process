package edu.gatech;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import junit.framework.TestCase;
import edu.gatech.GradesDB;
import edu.gatech.Student;

public class GradesToolGUITest extends TestCase {
	GradesDB db = null;
	GradesToolGUI gui = null;
	
	protected void setUp() throws Exception {
		db = new GradesDB(Constants.GRADES_DB);
		gui = new GradesToolGUI();
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testApplicationBarTitle() {
		try {
			assertEquals(gui.getApplicationName(), Constants.APP_NAME);
		} catch (Exception e) {
			fail("Exception");
		}
	}
	
	// Student info should be empty before selecting any students
	public void testStudentInfo1() {
		try {
			assertEquals(gui.getStudentInfo(), "");
		} catch (Exception e) {
			fail("Exception");
		}
	}
	
	// Student info should contain the selected students information
	// immediately after selecting the student
	public void testStudentInfo2() {
		try {
			Student student = (Student) db.getStudents().toArray()[0];
			gui.selectStudent(student);
			assertEquals(gui.getStudentInfo(), db.printStudentInfo(student));
		} catch (Exception e) {
			fail("Exception");
		}
	}
	
	// Student info should contain the selected students information
	// immediately after selecting the student. Test selecting sequential
	// students.
	public void testStudentInfo3() {
		try {
			Student student1 = (Student) db.getStudents().toArray()[0];
			Student student2 = (Student) db.getStudents().toArray()[1];
			gui.selectStudent(student1);
			gui.selectStudent(student2);
			assertEquals(gui.getStudentInfo(), db.printStudentInfo(student2));
		} catch (Exception e) {
			fail("Exception");
		}
	}
	
	// Test selecting a student in the combo box and confirming that the selection has been
	// updated properly
	public void testStudentSelection1() {
		try {
			Student student1 = (Student) db.getStudents().toArray()[0];
			gui.selectStudent(student1);
			assertEquals(gui.getSelectedStudent().getName(), student1.getName());
		} catch (Exception e) {
			fail("Exception");
		}
	}	

	// Test selecting a student in the combo box and confirming that the selection has been
	// updated properly. Test more than one sequential selections.
	public void testStudentSelection2() {
		try {
			Student student1 = (Student) db.getStudents().toArray()[0];
			Student student2 = (Student) db.getStudents().toArray()[1];
			gui.selectStudent(student1);
			gui.selectStudent(student2);
			assertEquals(gui.getSelectedStudent().getName(), student2.getName());
		} catch (Exception e) {
			fail("Exception");
		}
	}
	
	// Test save 
	public void testSaveButton() {
		try {
			Student student1 = (Student) db.getStudents().toArray()[0];
			System.out.println(student1.getName());
			gui.selectStudent(student1);
			
			File tmpFile = null;
			try {
				tmpFile = File.createTempFile("student_info_test", ".txt");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tmpFile.deleteOnExit();
			
			gui.saveButtonClick(tmpFile.getAbsolutePath());
			
		    assertTrue(tmpFile.exists());

		    FileInputStream fis = new FileInputStream(tmpFile);
		    byte[] data = new byte[(int)tmpFile.length()];
		    fis.read(data);
		    fis.close();
		    String content = new String(data, "UTF-8");
		    
			assertEquals(gui.getStudentInfo(), content);
			
		} catch (Exception e) {
			fail("Exception");
		}
	}

    
    
	public void testPrintStudentInfo() {
		try {
			Student student = new Student();
			student.setAttendance(5);
			student.setCAttendance(3);
			student.setCppAttendance(2);
			student.setCsJobEx(true);
			student.setEmail("ted@google.com");
			student.setGtid("1234567");
			student.setJavaAttendance(0);
			student.setName("Ted Smith");
			
			Team t1 = new Team(1,5);
			Team t2 = new Team(1,2);
			student.addTeam(t1);
			student.addTeam(t2);
			
			String info = db.printStudentInfo(student);
			assertEquals("Ted Smith|ted@google.com|1234567|True|5|3|2|0|2,5", info);
		} catch (Exception e) {
			fail("Exception");
		}
	}
	
}
