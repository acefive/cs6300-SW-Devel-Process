package edu.gatech;

import java.util.List;

import junit.framework.TestCase;
import edu.gatech.Student;

public class StudentTest extends TestCase{
	Student student1 = null;

	Team t1 = new Team(1,5);
	Team t2 = new Team(1,2);
	
	protected void setUp() throws Exception {
		student1 = new Student();
		student1.setAttendance(5);
		student1.setCAttendance(3);
		student1.setCppAttendance(2);
		student1.setCsJobEx(true);
		student1.setEmail("ted@google.com");
		student1.setGtid("1234567");
		student1.setJavaAttendance(0);
		student1.setName("Ted Smith");
		
		student1.addTeam(t1);
		student1.addTeam(t2);
		super.setUp();
	}
	
	public void testGetTeams() {
		try {
			List<Team> teams = student1.getTeams();
			assertEquals(2,teams.size());
			assertTrue(teams.contains(t1));
			assertTrue(teams.contains(t2));
		} catch (Exception e) {
			fail("Exception");
		}
	}

	
}
