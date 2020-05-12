package edu.gatech;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

// Projects class to hold information about all projects for a class. 
// The class contains a constructor that takes a reference to a GradesDB instance as input and 
// constructs (and stores) the Project objects based on the information in that instance. 
// The class also provides a method that refreshes the list of projects.

public class Projects implements ProjectsInterface {
	
	private GradesDB db;
	List<Project> all_projects = new ArrayList<Project>();
	
	public Projects(GradesDB db) {
		
		this.db = db;
		refreshProjectList();
		
	}

	void refreshProjectList() {

		Project p = null;
		all_projects = new ArrayList<Project>();
		
		//
		// loop through the GradesDB.projectList HashTable and create a new Project object for each one
		//
		Set<String> keySet = db.projectList.keySet();
		Iterator<String> keySetIterator = keySet.iterator();
		while (keySetIterator.hasNext()) {
		   String key = keySetIterator.next();
		   p = new Project();
		   
		   //name
		   p.setProjName(key);
		   
		   //number
		   p.setProjNum(Integer.valueOf(key.replaceAll("[A-z]+", "")));
		   int project_number = p.getProjNum();
		   int project_number_index = project_number -1;
		   
		   //description
		   p.setProjDescr(db.projectList.get(key));
		   
		   //Teams
		   int proj_num_teams = db.pTeamsArray[project_number_index].size();
		   String team_key = null;
		   for (int x=1; x<=proj_num_teams; x++) {
				   Hashtable<String, String[]> project_team_from_xls = db.getProjectTeamsForProj(project_number);
				   Team proj_team = new Team(project_number,x);
				   team_key = "Team "+x;
				   for (int y=0; y<5; y++) {
					   proj_team.addStudent(project_team_from_xls.get(team_key)[y]);
				   }
				   p.addTeam(proj_team);
				   
		   }
		
		   //criteria
	   	   Hashtable<String, double[]> project_grades_from_xls = db.getProjectGradesForProj(project_number);
		   Set<String> criteria_keySet = project_grades_from_xls.keySet(); 
		   String criteria_key = null;

		   //iterate through each criteria, getting each team value for it
		   Iterator<String> criteria_keySetIterator = criteria_keySet.iterator();
		   while (criteria_keySetIterator.hasNext()) {
				    criteria_key = criteria_keySetIterator.next();	
			        //element 0 is the maximum points that can be earned on this criteria
			    	double maxpoints = project_grades_from_xls.get(criteria_key)[0];
				   	//get each team grade for this criteria
			    	for (int y=1; y<=3; y++) {
			    		double t_criteria_grade = project_grades_from_xls.get(criteria_key)[y];
					    //if this criteria is the total then we need to set the team grade overall
					  		if (criteria_key.equalsIgnoreCase("TOTAL:")) {
					  			p.getTeam(y).setTeamGrade((int) t_criteria_grade);
					  		}
			    	}
			        p.setCriteriaMaxPts(criteria_key, (int) maxpoints);
					  
		   }
		   
		   //save Project object to list
		   addProject(p);
		   
		}
	}

	public Integer getNumProjects() {
		return all_projects.size();
	}

	public void addProject(Project p) {
		all_projects.add(p);
    }
    
    public void removeProject(Project proj) {
    	all_projects.remove(proj);
    }
    
    public List<Project> getProjectList() {
		return all_projects;
    }
    
    public Project getProjectObj(String pname) {
 
    	for (int i = 0; i < all_projects.size(); i++) {
    	    Project p = all_projects.get(i);
    	    if (p.getProjName().equalsIgnoreCase(pname)) {
    	    	return p;
    	    }
    	}
    	return null;
    }

	@Override
	public void initialize(File database) {
		try {
			this.db = new GradesDB(database.getAbsolutePath());
			refreshProjectList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public HashSet<Project> getProjects() {
		
		if (all_projects == null) {
			return null;
		} else 	
			return new HashSet(all_projects);
		
	}

}
