package edu.gatech;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import junit.framework.TestCase;
import edu.gatech.Project;
import edu.gatech.Team;


public class ProjectTest extends TestCase {

	GradesDB db = null;
	Projects allProjects = null;
	
	public void setUp() throws Exception {
		
		GradesDB db = new GradesDB(Constants.GRADES_DB);
		allProjects = new Projects(db);		
		super.setUp();
			
	}

	public void testGetProjDescription() {
		Project proj = allProjects.getProjectObj("P1");
		try {
			assertEquals("WordCount in Java",proj.getProjDescr());
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}
		
	}
	
	public void testGetProjName() {
		Project proj = allProjects.getProjectObj("P3");
		try {
			assertEquals("P3",proj.getProjName());
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}
		
	}
	
	
	public void testGetProjNum() {
		Project proj = allProjects.getProjectObj("P3");
		try {
			assertEquals(3,proj.getProjNum().intValue());
		} catch (Exception ex) {
			fail("Exception: "+ex);
	}
		
	}
	
	public void testGetAvgProjGrade() {
		Project proj = allProjects.getProjectObj("P1");
		try {
			assertEquals(93.0,proj.getAvgProjGrade());
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}
		
	}

	public void testGetNumTeams() {
		Project proj = allProjects.getProjectObj("P1");
		
		try {
			assertEquals(3,proj.getNumTeams().intValue());
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}
		
	}
	
	public void testGetCriteriaMaxPts() {
		Project proj = allProjects.getProjectObj("P1");
		
		try {
			assertEquals(5,proj.getCriteriaMaxPts("Man Page").intValue());
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}
		
	}

	public void testGetTeams() {
		Project proj = allProjects.getProjectObj("P1");
		
		List<Team> p1teams = proj.getTeams();	
		try {
			assertEquals(3,p1teams.size());
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}
		
		Team tt = p1teams.get(0);//this should be team 1
	    try {
	    	assertEquals("Team 1",tt.getTeamName());
	    } catch (Exception ex) {
	    	fail("Exception: "+ex);
	    }
	}
	
	public void testGetTeamGrade() {
		Project proj = allProjects.getProjectObj("P1");		
		List<Team> p1teams = proj.getTeams();	
		Team tt = p1teams.get(0);//this should be team 1
		try {
	    	assertEquals("Team 1",tt.getTeamName());
	    } catch (Exception ex) {
	    	fail("Exception: "+ex);
	    }
		
		double grade = tt.getTeamGrade();
		try {
			assertEquals(93.0,grade);
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}
	    
	}
	
}
