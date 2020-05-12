package edu.gatech;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashSet;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamsTest extends TestCase{
	GradesDB db = null;

	protected void setUp() throws Exception {
		db = new GradesDB(Constants.GRADES_DB);
	}
	
	protected void tearDown() throws Exception {
	}
	
	@Test
	public void testTeams(){
		Teams teams = new Teams(db);
		Team team = teams.getTeam(1,1);
		assertNotNull(team);
		assertEquals("Team 1 P1",team.getTeamProjectName());
	}
	
	@Test
	public void testTeamsRefresh(){
		Teams teams = new Teams();
		teams.setDB(db);
		teams.refreshTeamList();
		Team team = teams.getTeam(1,1);
		assertNotNull(team);
		assertEquals("Team 1 P1",team.getTeamProjectName());
	}
	
	@Test
	public void testTeamsGetTeams(){
		Teams teams = new Teams(db);
		
		HashSet<Team> teamsSet = teams.getTeams();
		assertNotNull(teamsSet);
		
		for (Team team : teams.getTeams()) {
			assertNotEquals(team.getTeamNumber().intValue(), -1);
			assertEquals(team, teams.getTeam(team.getProjectNumber(), team.getTeamNumber()));
			//System.out.println(team);
		}
	}

	@Test
	public void testTeamsInitializeFromFile(){
		Teams teams = new Teams();
		teams.initialize(new File(Constants.GRADES_DB));
		
		Team team = teams.getTeam(1,1);
		assertNotNull(team);
		assertEquals("Team 1 P1",team.getTeamProjectName());
	}
}
