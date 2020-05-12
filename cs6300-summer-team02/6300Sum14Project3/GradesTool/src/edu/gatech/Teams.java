package edu.gatech;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Teams implements TeamsInterface {
	
	// Map ProjectNum,TeamNum -> team
	private Map<Integer, Map<Integer, Team>> teams;
	private GradesDB db;

	public Teams() {
	}
	
	public Teams(GradesDB db) {
		setDB(db);
		refreshTeamList();
	}
	
	public void setDB(GradesDB db) {
		this.db = db;
	}
	
	public void refreshTeamList() {
		teams = new HashMap<Integer, Map<Integer, Team>>();
		
		int i,j;
		for (i=0; i < GradesDB.NUM_PROJECTS; i++) {
			
			//Project number uses one based indexing
			Integer projNum = i+1;
			System.out.println("Setting 11111");
			// Iterate over projects from spreadsheet
			for (Map.Entry<String, String[]> entry : db.pTeamsArray[i].entrySet()) {
				
				// Parse team number from string "Team 2" -> 2
				String teamName = entry.getKey();
				Integer teamNum = -1;
				String[] parts = teamName.split(" ");
				if (parts.length == 2) {
					teamNum = Integer.parseInt(parts[1]);
				}
				
				// Create team based on team number and project number
				Team team = new Team(projNum, teamNum);
				System.out.println("Setting adfsd");
				// Iterate over team members from spreadsheet
				for (String name : entry.getValue()) {
					if (name==null)
						continue; //TODO: why is this ever null here?
					
					// Add team member to team
					team.addStudent(name);
				
					// Add team member contributions to team member
					Map<String, Map<String, Double>> contributionProj = db.projectContributions.get(projNum);
					Map<String, Double> graders = contributionProj.get(name);
					Double averageContribution = graders.get("AVERAGE");
					System.out.println("Setting average");
					team.setAverageContributionRating(name, averageContribution);
				}
				
				// Add team to Teams collection
				addTeam(team);
			}
			
			double[] teamGrades = db.pGradesArray[i].get("TOTAL:");
			
			for (j=0; j<GradesDB.NUM_TEAMS; j++) {
				// Team num is one based index
				int teamNum = j + 1;
				int teamGrade = (int) teamGrades[j+1];
				teams.get(projNum).get(teamNum).setTeamGrade(teamGrade);
			}
		}
	}
	
	private void addTeam(Team team) {
		// Helper function to add team to internal double hash based collection
		
		Integer projNum = team.getProjectNumber();
		Integer teamNum = team.getTeamNumber();
		
		if (!teams.containsKey(projNum)) {
			teams.put(projNum, new HashMap<Integer, Team>());
		}
		
		teams.get(projNum).put(teamNum, team);
	}


	public Team getTeam(Integer projNum, Integer teamNum) {
		if (teams == null) {
			return null;
		}
		
		if (!teams.containsKey(projNum)) {
			return null;
		}
		
		return teams.get(projNum).get(teamNum);
	}

	@Override
	public void initialize(File database) {
		try {
			this.db = new GradesDB(database.getAbsolutePath());
			refreshTeamList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public HashSet<Team> getTeams() {
		if (teams == null) {
			return null;
		}
		
		HashSet<Team> set = new HashSet<Team>();
		
		// Iterate over projects then teams, adding each team to the hashset
		for (Map.Entry<Integer, Map<Integer, Team>> projectEntry : teams.entrySet()) {
			for (Map.Entry<Integer, Team> teamEntry : projectEntry.getValue().entrySet()) {
				set.add(teamEntry.getValue());
			}
		}
		
		return set;
	}

	public String getAverageGradeForProject(String projectName) {
		double totalGrade=0; 
		double numberOfTeams=0;
		for(Team team: getTeams()){
			if(team.getProjectName().equals(projectName)){
				totalGrade=totalGrade+team.getTeamGrade();
				numberOfTeams++;
			}
		}
		return String.format("%.0f", totalGrade/numberOfTeams);
	}
}
