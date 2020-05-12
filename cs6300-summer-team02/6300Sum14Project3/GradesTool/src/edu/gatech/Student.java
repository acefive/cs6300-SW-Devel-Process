package edu.gatech;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private String name;
    private String gtID;
    private String email;
    private int attendance;
    private int cAttendance;
    private int cppAttendance;
    private int javaAttendance;
    private Boolean csJobEx;
    private List<Assignment> assignments;
    private List<Team> teams;
    
    public Student() {
    	
    }
    
    public List<Assignment> getAssignments() {
    	if(assignments==null)
    		assignments=new ArrayList<Assignment>();
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}
    
    public String getGtid() {
        return gtID;
    }

    public void setGtid(String gtid) {
        gtID = gtid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }
    
    public int getCAttendance() {
        return cAttendance;
    }

    public void setCAttendance(int cAttendance) {
        this.cAttendance = cAttendance;
    }

    public int getCppAttendance() {
        return cppAttendance;
    }

    public void setCppAttendance(int cppAttendance) {
        this.cppAttendance = cppAttendance;
    }

    public int getJavaAttendance() {
        return javaAttendance;
    }

    public void setJavaAttendance(int javaAttendance) {
        this.javaAttendance = javaAttendance;
    }

    public Boolean getCsJobEx() {
        return csJobEx;
    }

    public void setCsJobEx(Boolean csJobEx) {
        this.csJobEx = csJobEx;
    }
    
    public List<Team> getTeams(){
    	if(teams==null)
    		teams=new ArrayList<Team>();
    	return teams;
    }
    
    public void addTeam(Team team){
    	if(teams==null)
    		teams=new ArrayList<Team>();
    	
    	teams.add(team);
    }
    
    public void removeTeam(Team team){
    	teams.remove(team);
    }
    
    public String toString(){
    	return name;
    }


}
