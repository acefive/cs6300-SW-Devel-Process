package edu.gatech;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.speqmath.Parser;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GradesDB implements OverallGradeCalculatorInterface{
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    HashSet<edu.gatech.Student> studentList;
    Hashtable<String,String> assignmentList;
    Hashtable<String,String> projectList;
    double[] gradeArray;
    String[] studentArray;
    
    private String formula;
    
    public static int NUM_PROJECTS = 3;
    public static int NUM_TEAMS = 3;
    Hashtable<String,String[]> pTeamsList;
    Hashtable<String,String[]>[] pTeamsArray = (Hashtable<String,String[]>[]) new Hashtable<?,?>[20];
    
    Hashtable<String, double[]> aGradesList;
    Hashtable<String, double[]> pGradesList;
    Hashtable<String, double[]>[] pGradesArray = (Hashtable<String, double[]>[]) new Hashtable<?,?>[50];
    
    public Map<Integer, Map<String,Map<String,Double>>> projectContributions = new HashMap<Integer, Map<String, Map<String, Double>>>();

    public GradesDB(String filePath) throws Exception{
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            workbook = new XSSFWorkbook(file);
        } catch (Exception e) {
            System.out.println("File not found!");
            System.exit(-1);
        }
        
        //default formula
        formula = "AS * 0.2 + AT * 0.2 + ((PR1 + PR2 + PR3)/3) * 0.6";
        sheet = workbook.getSheetAt(0);
        studentList  = new HashSet<Student>();
        Iterator<Row> rowIterator = sheet.iterator();
        
        //get the header row out of the way
        Row row = rowIterator.next();
        while(rowIterator.hasNext()) {
            row = rowIterator.next();

            //For each row, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator();
            Student s = new Student();
            
            while(cellIterator.hasNext()) {

                Cell cell = cellIterator.next();
               	switch(cell.getColumnIndex()){
               		case 0:
               			s.setName(cell.getStringCellValue());
               			break;
               		case 1:
               			cell.setCellType(Cell.CELL_TYPE_STRING);
               			s.setGtid(cell.getStringCellValue());
               			break;
               		case 2:
               			s.setEmail(cell.getStringCellValue());
               			break;
               		case 3:
               			s.setCAttendance((int) cell.getNumericCellValue()); 
               			break;
               		case 4:
               			s.setCppAttendance((int) cell.getNumericCellValue()); 
               			break;
               		case 5:
               			s.setJavaAttendance((int) cell.getNumericCellValue()); 
               			break;
               		case 6:
               			Boolean csEx = false;
               			if(cell.getStringCellValue() == "Y")
               				csEx = true;
               			s.setCsJobEx(csEx); 
               			break;
               	}
               
            }
            studentList.add(s);
        }
        
        //open second worksheet containing projects and assignments
        sheet = workbook.getSheetAt(1);
        assignmentList  = new Hashtable<String,String>();
        projectList = new Hashtable<String,String>();
        rowIterator = sheet.iterator();
        
        //get the header row out of the way
         row = rowIterator.next();
        while(rowIterator.hasNext()) {
            row = rowIterator.next();
            //check if row is an empty row before adding
            if(!row.getCell(0).getStringCellValue().equals("") && !row.getCell(1).getStringCellValue().equals(""))
            	projectList.put(row.getCell(0).getStringCellValue(),row.getCell(1).getStringCellValue());
            if(!row.getCell(3).getStringCellValue().equals("") && !row.getCell(4).getStringCellValue().equals(""))
            	assignmentList.put(row.getCell(3).getStringCellValue(),row.getCell(4).getStringCellValue());
            
        }
          
        //open third worksheet containing attendance
        sheet = workbook.getSheetAt(2);
        rowIterator = sheet.iterator();
        
        //get the two headers row out of the way
        rowIterator.next();
        rowIterator.next();
        
        while(rowIterator.hasNext()) {
            row = rowIterator.next();
            //check if row is an empty row before adding
            if(!row.getCell(0).getStringCellValue().equals("")) {
            	String name = row.getCell(0).getStringCellValue();
            	Student thisStudent = getStudentByName(name);
            	double grade = row.getCell(1).getNumericCellValue();
            	int gradeFloor = (int) (100.0*grade);
            	thisStudent.setAttendance(gradeFloor);
            }
        }
            
        //open 4th worksheet containing assignment grades
        sheet = workbook.getSheetAt(3);
        aGradesList  = new Hashtable<String,double[]>();
        rowIterator = sheet.iterator();
        
        //get the header row out of the way
         row = rowIterator.next();
        while(rowIterator.hasNext()) {
            row = rowIterator.next();
            //check if row is an empty row before adding
            if(!row.getCell(0).getStringCellValue().equals("")) {
            	gradeArray = new double[4];
            	for (int i=0; i<4; i++) {
            		gradeArray[i] = row.getCell(i+1).getNumericCellValue();
            	}           	
            	aGradesList.put(row.getCell(0).getStringCellValue(),gradeArray);
            }
            
        }
         
        
        //open 5th worksheet containing P1 Teams
        sheet = workbook.getSheetAt(4);       
        pTeamsList = new Hashtable<String,String[]>();
        
        rowIterator = sheet.iterator();
        
        //get the header row out of the way
        row = rowIterator.next();
        while(rowIterator.hasNext()) {
            row = rowIterator.next();
            //check if row is an empty row before adding
            if(!row.getCell(0).getStringCellValue().equals("") && !row.getCell(1).getStringCellValue().equals("")) {
                studentArray = new String[5];
            	for (int i=0; i<5; i++) {
            		if (!row.getCell(i+1).getStringCellValue().equals(""))
            			studentArray[i] = row.getCell(i+1).getStringCellValue();
            	}           	
            	pTeamsList.put(row.getCell(0).getStringCellValue(),studentArray);
            }
            
        }
        
        //1st element in the Array is P1 info for teams 1-3
        pTeamsArray[0] = pTeamsList;
      
        //open 6th worksheet containing P2 Teams
        sheet = workbook.getSheetAt(5);        
        pTeamsList = new Hashtable<String,String[]>();        
        rowIterator = sheet.iterator();
        
        //get the header row out of the way
        row = rowIterator.next();
        while(rowIterator.hasNext()) {
            row = rowIterator.next();
            //check if row is an empty row before adding
            if(!row.getCell(0).getStringCellValue().equals("") && !row.getCell(1).getStringCellValue().equals("")) {
                studentArray = new String[5];
            	for (int i=0; i<5; i++) {
            		if (!row.getCell(i+1).getStringCellValue().equals(""))
            			studentArray[i] = row.getCell(i+1).getStringCellValue();
            	}           	
            	pTeamsList.put(row.getCell(0).getStringCellValue(),studentArray);
            }
            
        }
        
        //2nd element in the Array is P2 info for teams 1-3
        pTeamsArray[1] = pTeamsList;
       
        //open 7th worksheet containing P3 Teams
        sheet = workbook.getSheetAt(6);       
        pTeamsList = new Hashtable<String,String[]>();       
        rowIterator = sheet.iterator();
        
        //get the header row out of the way
        row = rowIterator.next();
        while(rowIterator.hasNext()) {
            row = rowIterator.next();
            //check if row is an empty row before adding
            if(!row.getCell(0).getStringCellValue().equals("") && !row.getCell(1).getStringCellValue().equals("")) {
            	//populate array of student names in the worksheet
                studentArray = new String[5];
            	for (int i=0; i<5; i++) {
            		if (!row.getCell(i+1).getStringCellValue().equals(""))
            			studentArray[i] = row.getCell(i+1).getStringCellValue();
            	}           	
            	pTeamsList.put(row.getCell(0).getStringCellValue(),studentArray);
            }
            
        }
        
        //3rd element in the Array is P3 info for teams 1-3
        pTeamsArray[2] = pTeamsList;
       
        
        //open 8th worksheet containing P1 Grades
        //Criteria				Max pts		Team 1	Team 2	Team 3
        //Code - Test Cases		5			5		4		3
        sheet = workbook.getSheetAt(7);       
        pGradesList = new Hashtable<String,double[]>();
        
        rowIterator = sheet.iterator();
        
        //get the header row out of the way
        row = rowIterator.next();
        while(rowIterator.hasNext()) {
            row = rowIterator.next();
            //check if row is an empty row before adding
            if(!row.getCell(0).getStringCellValue().equals("")) {
                gradeArray = new double[4];
            	for (int i=0; i<4; i++) {
            			gradeArray[i] = row.getCell(i+1).getNumericCellValue();
            	}           	
            	pGradesList.put(row.getCell(0).getStringCellValue(),gradeArray);
            }
            
        }
        
        //1st element in the GradesList is P1 grade info for teams 1-3
        pGradesArray[0] = pGradesList;
        
        //open 9th worksheet containing P2 Teams
        sheet = workbook.getSheetAt(8);        
        pGradesList = new Hashtable<String,double[]>();        
        rowIterator = sheet.iterator();
        
        //get the header row out of the way
         row = rowIterator.next();
        while(rowIterator.hasNext()) {
            row = rowIterator.next();
            //check if row is an empty row before adding
            if(!row.getCell(0).getStringCellValue().equals("")) {
                gradeArray = new double[4];
            	for (int i=0; i<4; i++) {
            			gradeArray[i] = row.getCell(i+1).getNumericCellValue();
            	}           	
            	pGradesList.put(row.getCell(0).getStringCellValue(),gradeArray);
            }
            
        }
        
        //2nd element in the ArrayList is P2 grade info for teams 1-3
        pGradesArray[1] = pGradesList;
        
        //open 10th worksheet containing P3 Teams
        sheet = workbook.getSheetAt(9);        
        pGradesList = new Hashtable<String,double[]>();        
        rowIterator = sheet.iterator();
        
        //get the header row out of the way
        row = rowIterator.next();
        
        while(rowIterator.hasNext()) {
            row = rowIterator.next();
            //check if row is an empty row before adding
            if(!row.getCell(0).getStringCellValue().equals("")) {
                gradeArray = new double[4];
            	for (int i=0; i<4; i++) {
            			gradeArray[i] = row.getCell(i+1).getNumericCellValue();
            	}           	
            	pGradesList.put(row.getCell(0).getStringCellValue(),gradeArray);
            }
            
        }
        
        //3rd element in the ArrayList is P3 grade info for teams 1-3
        pGradesArray[2] = pGradesList;

        int i;
        for (i=0; i<NUM_PROJECTS; i++) {
        	int projNum = i+1;
        	int sheetNum = i+10;
        	
        	projectContributions.put(projNum, processProjectContributionSheet(sheetNum));
        }
    }
  
    // P1-P3 Contri worksheets
    // idea for storing the data when reading it in:
    // HashMap<String,HashMap<String,String>[]> contri;
    // "Freddie Catlay" ==> {"AVERAGE" ==> 9.25}, {"Shevon Wise" ==> 9}, {"Kim Hyles" ==> 10}, {"Ernesta Anderson" ==> 10}, {"Sheree Gadow" ==> 9}
    // "Shevon Wise" ==> {"AVERAGE" ==> 9.25}, {"Freddie Catlay" ==> 8}, {"Kim Hyles" ==> 8}, {"Ernesta Anderson" ==> 9}, {"Sheree Gadow" ==> 8}
    // etc.
    // no need to keep track of the team
    // no need to keep track of the headers (Grader 1, Grader 2, etc)	
    // yes need to keep track of which project/worksheet it is
    private Map<String,Map<String,Double>> processProjectContributionSheet(int sheetNum) {
        sheet = workbook.getSheetAt(sheetNum);        
        HashMap<String,Map<String, Double>> contributions = new HashMap<String,Map<String, Double>>();
        
        Iterator<Row> rowIterator = sheet.iterator();
        
        //get the header row out of the way
        Row row = rowIterator.next();
        
        List<String> graders;
		int i, j, col;
        while(rowIterator.hasNext()) {
        	row = rowIterator.next();
        	
    		// Parse team header
    		graders = new ArrayList<String>();
    		col = 3;
    		while (true) {
    			String grader = row.getCell(col).getStringCellValue().trim();
    			if (grader.length()==0)
    				break;
    			graders.add(grader);
    			col++;
    		}		

    		// Parse team row
    		for (i=0; i<graders.size(); i++) {
	        	row = rowIterator.next();
	    		String name = row.getCell(1).getStringCellValue().trim();	    		
	    		Double averageContribution = row.getCell(2).getNumericCellValue();
	    		
	    		contributions.put(name, new HashMap<String, Double>());
	    		contributions.get(name).put("AVERAGE", averageContribution);
	
	    		for (j=0; j<graders.size(); j++) {
	    			if (name.equals(graders.get(j)))
	    				continue;
	    			col = j+3;
	    			Double grade = row.getCell(col).getNumericCellValue();
	    			contributions.get(name).put(graders.get(j), grade);
	    		}
    		}
        }
        
        return contributions;
    }

    public int getNumStudents(){
        return studentList.size();
    }

    public int getNumAssignments(){
        return assignmentList.size();
    }
    
    public Hashtable<String,double[]> getAssignmentGrades() {
    	return aGradesList;
    }

    public int getNumProjects(){
        return projectList.size();
    }
    
    public Hashtable<String, String[]>[] getProjectTeams() {
    	return pTeamsArray;
    }
     
    public Hashtable<String, String[]> getProjectTeamsForProj(int project_num) {
    	return pTeamsArray[project_num-1];
    }
    
    public Hashtable<String, double[]>[] getProjectGrades() {
    	return pGradesArray;
    }
    
    public Hashtable<String, double[]> getProjectGradesForProj(int project_num) {
    	return pGradesArray[project_num-1];
    }

    public String getTeamForProject(Student student, int projNum){	    
    	Hashtable<String, String[]> teams = this.getProjectTeamsForProj(projNum);
		for(String teamName : teams.keySet()){
			String[] members = teams.get(teamName);
			for(String m : members){
				if(m != null){
					if(m.trim().equals(student.getName())){
						return teamName;
					}
				}
			}
		}
		return "Not Found";
    }
    
    public double getAvgProjectContribution(Student student,Integer projNum){
    	return this.projectContributions.get(projNum).get(student.getName()).get("AVERAGE");
    }
    
    public double getGradeForProject(Student student, Integer projNum){
    	String teamName = this.getTeamForProject(student, projNum);
		Integer teamNum = Integer.parseInt(teamName.substring(teamName.length()-1,teamName.length()));
		
		Hashtable<String, double[]>[] ps = this.getProjectGrades();
		return ps[projNum-1].get("TOTAL:")[teamNum];
    }
    
    public Hashtable<String, String> getProjects(){
        return projectList;
    }

    public HashSet<Student> getStudents(){
        return studentList;
    }

    public Student getStudentByName(String name){
    	
    	Iterator<Student> iterator = studentList.iterator();
    	while(iterator.hasNext()){
    		Student itrStudent = iterator.next();
            if (itrStudent.getName().equalsIgnoreCase(name)) {
                return itrStudent;
            }
        }
        return new Student();
    }

    public Student getStudentByID(String id){
    	Iterator<Student> iterator = studentList.iterator();
    	while(iterator.hasNext()){
    		Student itrStudent = iterator.next();
            if (itrStudent.getGtid().equals(id)) {
                return itrStudent;
            }
        }
        return new Student();
    }
    
    public String printStudentInfo(Student student){
    	return student.getName() + "|" + student.getEmail() + "|" + student.getGtid() +
    				"|" + capFirstLetter(student.getCsJobEx()) + "|" + student.getAttendance() 
    				+  "|" + student.getCAttendance() + "|" + student.getCppAttendance() + 
    				"|" + student.getJavaAttendance() + "|" + getTeamNums(student);
    }
    public String capFirstLetter(Boolean string){
    	if(string==null){
			return "False";
		}else if(string){
    		return "True";
    	}else{
    		return "False";
    	}
    }
	public Team getTeamByName(String string) {
		// TODO Auto-generated method stub
		Team team = new Team(1,1);
		return team;
	}
	
    public void setFormula(String formula){
    	this.formula = formula;
    }
    
    public String getFormula(){
    	return this.formula;
    }
    
    public double getAverageAssignmentGrade(Student student){
    	return this.getAssignmentGrades().get(student.getName())[0];
    }

    public double getStudentGrade(Student student){
    	Parser p = new Parser();
    	
    	//formula looks like this:
    	//
    	//("AS * 0.2 + AT * 0.2 + ((PR1 + PR2 + PR3)/3) * 0.6");
    	//replace the values with these
    	//AS = db.getAverageAssignmentGrade(student)
    	//AT = student.attendance
    	//PRn = grade for project N
    	//ICn = avg indiv contribution to project N
    	//Not too elegant
    	String subFormula = formula;
    	subFormula = subFormula.replaceAll("AS",String.valueOf(this.getAverageAssignmentGrade(student)));
    	subFormula = subFormula.replaceAll("AT",String.valueOf(student.getAttendance()));
    	subFormula = subFormula.replaceAll("PR1",String.valueOf(this.getGradeForProject(student,1)));
    	subFormula = subFormula.replaceAll("PR2",String.valueOf(this.getGradeForProject(student,2)));
    	subFormula = subFormula.replaceAll("PR3",String.valueOf(this.getGradeForProject(student,3)));
    	subFormula = subFormula.replaceAll("IC1",String.valueOf(this.getAvgProjectContribution(student, 1)));
    	subFormula = subFormula.replaceAll("IC2",String.valueOf(this.getAvgProjectContribution(student, 2)));
    	subFormula = subFormula.replaceAll("IC3",String.valueOf(this.getAvgProjectContribution(student, 3)));
    	
    	double grade = p.parse(subFormula);
    	return grade;
    }
    
    public Map<Student, Double> getAllGrades(){
    	HashMap<Student,Double> m = new HashMap<Student,Double>();
    	
    	Iterator<Student> iterator = studentList.iterator();
    	while(iterator.hasNext()){
    		Student itrStudent = iterator.next();
    		m.put(itrStudent, this.getStudentGrade(itrStudent));      
        }
    	
    	return m;
    }
    public double getAverageGrade(){
    	Map<Student,Double> grades = getAllGrades();
    	double gradeSum = 0;
    	for(Double d : grades.values()){
    		gradeSum += d;
    	}
    	
    	return gradeSum / grades.size();
    }
    
    public double getMedianGrade(){
    	Collection<Double> grades = getAllGrades().values();
    	Double[] gradesArr = grades.toArray(new Double[grades.size()]); 
    	Arrays.sort(gradesArr);
    	int middle = gradesArr.length / 2;
        if (gradesArr.length % 2 == 0)
        {
          return (gradesArr[middle - 1] + gradesArr[middle]) / 2;
        }
        else
        {
          return gradesArr[middle];
        }
    }
	
	public String getTeamNums(Student student){
		ArrayList<Integer> teamNums = new ArrayList<Integer>();
    	for(Team t : student.getTeams()){
    		teamNums.add(t.getTeamNumber());
    	}
    	
    	Collections.sort(teamNums);
    	String teamString = "";
    	int i = 0;
    	for(Integer teamNum : teamNums){
    		if(i > 0)
    			teamString += ",";
    		
    		teamString += String.valueOf(teamNum);
    		i = i + 1;
    	}
    	return teamString;
	}

    
}
