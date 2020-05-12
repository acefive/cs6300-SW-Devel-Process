package edu.gatech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Team {

	private Integer projectNumber = -1;
	private Integer teamNumber = -1;
	private List<String> students = new ArrayList<String>();
	private Map<String, Double> averageContribution = new HashMap<String, Double>();
	private int teamGrade;
	

	Team(Integer projectNumber, Integer teamNumber) {
		this.teamNumber = teamNumber;
		this.projectNumber = projectNumber;
	}
	

	public void setTeamGrade(int teamGrade) {
		this.teamGrade = teamGrade;
	}

	public void addStudent(String student) {
		students.add(student);
	}

	public void setAverageContributionRating(String Number, Double averageContribution) {
		this.averageContribution.put(Number, averageContribution);
	}

	public String getTeamProjectName() {
		return String.format("Team %d P%d", teamNumber, projectNumber);
	}
	
	public String getTeamName() {
		return String.format("Team %d", teamNumber);
	}

	public String getProjectName() {
		return String.format("P%d", projectNumber);
	}
	
	public List<String> getStudentNames() {
		return students;
	}


	public int getTeamGrade() {
		return teamGrade;
	}

	public List<String> getStudentParticipationGrades() {
		
		List<String> grades = new ArrayList<String>();
		
		for (String name : students) {
			grades.add(String.format("%s %.2f", name, averageContribution.get(name)));
		}

		return grades;
	}
public String getStudentParticipationGrade(Student student) {
		
		for (String name : students) {
			if(name.equals(student.getName()))
				return String.format("%.2f", averageContribution.get(name));
		}

		return "0";
	}

	public Integer getTeamNumber() {
		return teamNumber;
	}
	
	public Integer getProjectNumber() {
		return projectNumber;
	}
	
	public void setTeamNumber(Integer teamNumber) {
		this.teamNumber = teamNumber;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((projectNumber == null) ? 0 : projectNumber.hashCode());
		result = prime * result
				+ ((teamNumber == null) ? 0 : teamNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Team other = (Team) obj;
		if (projectNumber == null) {
			if (other.projectNumber != null) {
				return false;
			}
		} else if (!projectNumber.equals(other.projectNumber)) {
			return false;
		}
		if (teamNumber == null) {
			if (other.teamNumber != null) {
				return false;
			}
		} else if (!teamNumber.equals(other.teamNumber)) {
			return false;
		}
		return true;
	}


	@Override
	public String toString() {
		return "Team [projectNumber=" + projectNumber + ", teamNumber="
				+ teamNumber + ", students=" + students
				+ ", averageContribution=" + averageContribution
				+ ", teamGrade=" + teamGrade + "]";
	}

}
