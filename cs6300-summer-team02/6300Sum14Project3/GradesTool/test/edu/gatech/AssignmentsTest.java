package edu.gatech;

import junit.framework.TestCase;

public class AssignmentsTest extends TestCase {
	GradesDB db = null;
	Assignments allAssignments = null;
	
	protected void setUp() throws Exception {
		db = new GradesDB(Constants.GRADES_DB);
		allAssignments = new Assignments(db);		
		super.setUp();		
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	public void testAssignmentListRefresh() throws Exception {
		
		//remove one of the assignments in assignments list
		Assignment tmp = allAssignments.getAssignmentObj("Assignment 1");		
		allAssignments.removeAssignment(tmp);
		
		//test, it should equal 2
		try {
			assertEquals(2,allAssignments.getNumAssignments().intValue());
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}
		
		//refresh it and test again, it should equal 3
		allAssignments = allAssignments.assignmentsListRefresh(db);
		
		try {
			assertEquals(3,allAssignments.getNumAssignments().intValue());
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}
	}
	
	
	public void testAddAssignment() {
		Assignment tmp = new Assignment();
		tmp.setAssignmentName("test assignment");
		
		allAssignments.addAssignment(tmp);
		
		try {
			assertEquals(4,allAssignments.getNumAssignments().intValue());
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}
		
	}
	
	
	
	public void testRemoveAssignment() {
		
		Assignment tmp = allAssignments.getAssignmentObj("Assignment 1");		
		allAssignments.removeAssignment(tmp);
		Integer num_after = allAssignments.getNumAssignments();
		
		try {
			assertEquals(2, allAssignments.getNumAssignments().intValue());
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}
		
	}
	
    public void testGetNumAssignments() {

		try {
			assertEquals(3,allAssignments.getNumAssignments().intValue());
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}
		
	}
}
