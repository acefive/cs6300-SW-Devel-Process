package edu.gatech;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import edu.gatech.Project;
import edu.gatech.Team;


public class ProjectsTest extends TestCase {
	GradesDB db = null;
	Projects allProjects = null;
	
	protected void setUp() throws Exception {
		db = new GradesDB(Constants.GRADES_DB);
		allProjects = new Projects(db);		
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testprojListRefresh() throws Exception {
		
		//remove one of the projects in the projects list
		Project tmp = allProjects.getProjectObj("P1");		
		allProjects.removeProject(tmp);

		//test, it should equal 2
		try {
			assertEquals(2,allProjects.getNumProjects().intValue());
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}
		
		//refresh it and test again, it should equal 3
		allProjects.refreshProjectList();

		try {
			assertEquals(3, allProjects.getNumProjects().intValue());
		} catch (Exception ex) {
			fail("exception: "+ex);
		}
		
	}

	
	public void testAddProj() {
		Project tmp = new Project();
		tmp.setProjName("test proj");
		
		allProjects.addProject(tmp);
		
		try {
			assertEquals(4,allProjects.getNumProjects().intValue());
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}
		
		Project tmp2 = allProjects.getProjectObj("test proj");		
		try {
			assertEquals(tmp, tmp2);
		} catch (Exception ex) {
			fail("Exception ex");
		}
		
	}
	
	
	public void testRemoveProj() {
		
		Project tmp = allProjects.getProjectObj("P1");		
		allProjects.removeProject(tmp);
		
		try {
			assertEquals(2,allProjects.getNumProjects().intValue());
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}

		tmp = allProjects.getProjectObj("P1");		
		try {
			assertEquals(null, tmp);
		} catch (Exception ex) {
			fail("Exception ex");
		}
		
	}
	
    public void testGetNumProjects() {
		
		try {
			assertEquals(3,allProjects.getNumProjects().intValue());
		} catch (Exception ex) {
			fail("Exception: "+ex);
		}
		
	}
    
    public void testGetTeamGrade() {
    	Project p = allProjects.getProjectObj("P1");	
    	Team t = p.getTeam(2);
    	p.getGrade(t);
    	
    	try {
    		assertEquals(96.0,p.getGrade(t));
    	} catch (Exception ex) {
    		fail("Exception: "+ex);
    	}
    }
    
    public void testGetProjects() {
    	HashSet<Project> pp = allProjects.getProjects();
    	assertNotNull(pp);
    	    	
    	for(Project proj : pp){
    		assertEquals(pp.size(),3);
        }
		
    }
    
}

