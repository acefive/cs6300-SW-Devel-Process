package edu.gatech;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamTests extends TestCase{
	GradesDB db = null;
	Teams teams = null;

	@Before
	public void setUp() throws Exception {
		db = new GradesDB(Constants.GRADES_DB);
	    teams = new Teams(db);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testTeamNames() {
		Team team = teams.getTeam(1,1);
		assertNotNull(team);
		
		List<String> studentNames = team.getStudentNames();
		String nameString = "";
		for(String name: studentNames){
			nameString+="|"+name;
		}
		assertEquals("|Freddie Catlay|Shevon Wise|Kym Hiles|Ernesta Anderson|Sheree Gadow",nameString);
	}
	
	@Test
	public final void testTeamGrad(){
		Team team = teams.getTeam(1,1);
		
		assertNotNull(team);
		assertEquals(93,team.getTeamGrade());
	}
	
	@Test
	public final void testTeamNumber(){
		Team team = teams.getTeam(1,1);
		assertNotNull(team);
		
		assertEquals((Integer) 1,team.getTeamNumber());
	}
	
	@Test
	public final void testTeamParticipationGrades() {
		Team team = teams.getTeam(1,1);
		assertNotNull(team);
		
		List<String> studentParticipationGrades=team.getStudentParticipationGrades();
		String gradeString = "";
		for(String grade: studentParticipationGrades){
			gradeString+="|"+grade;
		}
		assertEquals("|Freddie Catlay 9.25|Shevon Wise 9.25|Kym Hiles 9.25|Ernesta Anderson 9.00|Sheree Gadow 7.00",gradeString);
	}
}
