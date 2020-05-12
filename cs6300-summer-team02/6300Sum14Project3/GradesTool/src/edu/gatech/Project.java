package edu.gatech;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

// Project class to hold information about a particular project, 
// including project name, project description, list of teams, and average project grade.

public class Project {
	
	private String name;
	private int number;
	private String description;
	private List<Team> teams = new ArrayList<Team>();
	private double avg_grade;
	private Hashtable<String, Integer> criteria_maxpts = new Hashtable();
	
	public void setProjName(String pname) {
		this.name = pname;
	}
	
	public String getProjName() {
		return name;
	}
	
	public void setProjNum(Integer pnum) {
		this.number = pnum;
	}
	
	public Integer getProjNum() {
		return number;
	}
	
	public void setProjDescr(String descrip) {
		this.description = descrip;
	}
	
	public String getProjDescr() {
		return description;
	}
	
	public double getAvgProjGrade() {

		double tot_team_grades = 0.0;
		int num_teams = teams.size();
		//we can get the team_grade from each Team object and calc avg
		if (num_teams > 0) {
			for (int i=0; i < num_teams; i++) {
				tot_team_grades += teams.get(i).getTeamGrade();
			}
			try {
				this.avg_grade = (double) tot_team_grades / teams.size();
			} catch (Exception ex) {
				this.avg_grade = 0.0;
			}
		} else this.avg_grade = 0.0;
		
		return avg_grade;
	}
	
    public List<Team> getTeams(){
    	return teams;
    }
    
	
    public Team getTeam(int team_num){
    	//loop through our team objects and return the team # we are looking for
    	Team t = null;
    	for (int i = 0; i < teams.size(); i++) {
    	    t = teams.get(i);
    	    if (t.getTeamNumber() == team_num) {
    	    	return t;
    	    }
    	}
    	return null;
    }
    	
    public double getGrade(Team team){
    	//loop through our team objects and find the team # we are looking for
    	Team t = null;
    	for (int i = 0; i < teams.size(); i++) {
    	    t = teams.get(i);
    	    if (t.getTeamNumber() == team.getTeamNumber()) {
    	    	return t.getTeamGrade();
    	    }
    	}
    	return 0.0;
    }
    
    public Integer getNumTeams(){
    	return teams.size();
    }
    
    
    public void addTeam(Team team) {
    	this.teams.add(team);
    }
    
    public void removeTeam(Team team) {
    	this.teams.remove(team);
    }
    
    public Set<String> getProjectCriteria() {
    	return criteria_maxpts.keySet();
    }
    
    public void setCriteriaMaxPts(String key, Integer pts) {
    	this.criteria_maxpts.put(key, pts);
    }
      
    public Integer getCriteriaMaxPts(String criteria) {
    	Integer pts = 0;
		Set<String> keySet = criteria_maxpts.keySet();
		Iterator<String> keySetIterator = keySet.iterator();
		while (keySetIterator.hasNext()) {
		   String key = keySetIterator.next();
		   if(key.equalsIgnoreCase(criteria)) {
			  pts = criteria_maxpts.get(key);
		   }
		}
		return pts;
    }
    

}
