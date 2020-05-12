package edu.gatech;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// Currently this class stubs out functions that represent possible interfaces
// to GUI widgets.

public class GradesToolGUI extends JFrame{

	JPanel innerPanel= new JPanel();
	JPanel rightPanel= new JPanel();
	JButton saveButton = new JButton("Save to File"); 
	JButton gradeButton = new JButton("Calculate Grade"); 
	final JFileChooser fc = new JFileChooser();
	String saveFile ="student_info";
	JPanel gradeCalc = new JPanel();
	Assignments assignments;
	Projects projects;
	Teams teams;

	JTextField formulaTextField= new JTextField();
	JTextField gradeTextField= new JTextField();

	GradesDB db = new GradesDB(Constants.GRADES_DB);
	
	JTextArea textArea = new JTextArea();
	public static void main(String args[]){
		try {
			new GradesToolGUI();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public GradesToolGUI () throws Exception {
		projects = new Projects(db);
		assignments= new Assignments(db);
		teams = new Teams(db);
		JPanel mainPanel= new JPanel();
		mainPanel.setFont(new Font("Arial",0,40));
		mainPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		mainPanel.setLayout(new GridLayout(0,2));

		JPanel gridLeftPanel= new JPanel();
		gridLeftPanel.setFont(new Font("Arial",0,40));
		gridLeftPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		gridLeftPanel.setLayout(new GridLayout(0,1));
		
		
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setBackground(Color.LIGHT_GRAY);
		
		studentSelectorComboBox= new Vector<Student>(db.getStudents());
		Student student = new Student();
		student.setName("Please Select Student");
		studentSelectorComboBox.add(0, student);
		JComboBox<Student> comboBox = new JComboBox<Student>(studentSelectorComboBox);
		comboBox.setFont(new Font("Arial",0,18));
		comboBox.setSelectedIndex(0);
		comboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JComboBox<Student> jcmbType = (JComboBox<Student>) e.getSource();
				selectedStudentComboBox = (Student) jcmbType.getSelectedItem();
				textArea.setEditable(false);
				textArea.setFont(new Font("Arial",0,18));
				textArea.setText("");
				if(selectedStudentComboBox.getGtid()!=null){
					saveButton.setEnabled(true);
					textArea.append("Name:  "+selectedStudentComboBox.getName()+"\n");
					textArea.append("Email:  "+selectedStudentComboBox.getEmail()+"\n");
					textArea.append("GTID:  "+selectedStudentComboBox.getGtid()+"\n");
					textArea.append("Attendance:  "+selectedStudentComboBox.getAttendance()+"\n\n");
					//textArea.append("\nProject Details\n");

					HashMap<String,String> teamInfo = new HashMap<String,String>();
					for(Team team: teams.getTeams()){
						if(team.getStudentNames().contains(selectedStudentComboBox.getName())){
							StringBuilder sb = new StringBuilder(400);
							sb.append(team.getProjectName()+":\n");
							sb.append("Team Grade:  "+team.getTeamGrade()+"\n");
							sb.append("Average Team Grade: "+ teams.getAverageGradeForProject(team.getProjectName())+"\n");
							sb.append("Participation Grade:  "+team.getStudentParticipationGrade(selectedStudentComboBox)+"\n");
							teamInfo.put(team.getProjectName(), sb.toString());
						}
					}
					List<String> keys = new ArrayList<String>();
					keys.addAll(teamInfo.keySet());
					Collections.sort(keys);
					for(String key:keys){
						textArea.append(teamInfo.get(key));
					}
					textArea.append("\n");
					ArrayList<Assignment> assignmentList = (ArrayList<Assignment>) assignments.getAssignmentList();
					Collections.sort(assignmentList);
					for(Assignment assignment: assignmentList){
						textArea.append(assignment.getAssignmentName()+": \n");
						textArea.append("Student Grade:  "+String.format("%.0f", assignment.getStudentGradeInfo(selectedStudentComboBox.getName()))+"\n");
						textArea.append("Average Grade:  "+assignment.getAverageGrade()+"\n");
					}
				}else{
					saveButton.setEnabled(false);
				}
				textArea.setBackground(Color.LIGHT_GRAY);
				textArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
				rightPanel.add(textArea);
				repaint();
				revalidate();
			}
		});
		
		saveButton.setEnabled(false);
		saveButton.addActionListener(new ActionListener() {
			 
	            public void actionPerformed(ActionEvent e)
	            {
	                //Execute when button is pressed
	            	fc.setSelectedFile(new File(saveFile));
	            
	            	int returnVal = fc.showSaveDialog(null);
	            	if (returnVal == JFileChooser.APPROVE_OPTION) {
	                    File file = fc.getSelectedFile();
	                    saveButtonClick(file.getAbsolutePath());
	                    JOptionPane.showMessageDialog(null, "File has been saved","File Saved",JOptionPane.INFORMATION_MESSAGE);
	                    
	                } else {
	                    System.out.println("Open command cancelled by user.");
	                }
	            }
	        }); 
		innerPanel.setLayout(new FlowLayout());
		innerPanel.add(comboBox, BorderLayout.NORTH);
		innerPanel.add(saveButton);
		gridLeftPanel.add(innerPanel);
		gridLeftPanel.add(setupGradeCalcPanel());
		mainPanel.add(gridLeftPanel);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		mainPanel.add(rightPanel);
		add(mainPanel);
		pack();
		this.setSize(1000, 800);
		setTitle(applicationNameTitleBar);
		setVisible(true);
	}
	private JPanel setupGradeCalcPanel() {
		JPanel smallPanel = new JPanel();
		gradeTextField.setEditable(false);
		smallPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		smallPanel.setLayout(new GridLayout(0,1));
		JLabel label = new JLabel();
		label.setText("Enter Grading Formula");
		smallPanel.add(label);
		formulaTextField.setText(db.getFormula());
		smallPanel.add(formulaTextField);
		JPanel smallgrid = new JPanel();
		smallgrid.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		smallgrid.setLayout(new GridLayout(0,2));
		gradeButton.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
                db.setFormula(formulaTextField.getText());
                gradeTextField.setText(""+db.getStudentGrade(selectedStudentComboBox)+"%");
            }
        }); 
		smallgrid.add(gradeButton);
		gradeTextField.setText("0%");
		smallgrid.add(gradeTextField);
		smallPanel.add(smallgrid);
		gradeCalc.setLayout(new FlowLayout());
		gradeCalc.add(smallPanel);
		return gradeCalc;
	}
	
	String applicationNameTitleBar=Constants.APP_NAME;
	
	public void setApplicationName(String name) {
		applicationNameTitleBar = name;
	}
	
	public String getApplicationName(){
		return applicationNameTitleBar;
	}
	
	// Student Selector Combo Box interface
	// JComboBox can take a vector of objects that implement toString
	Vector<Student> studentSelectorComboBox;
	Student selectedStudentComboBox;
	
	public Student getSelectedStudent(){
		return selectedStudentComboBox;
	}
	
	
	public void selectStudent(Student student) {
		for (Student s : studentSelectorComboBox) {
			if (student.getName().equals(s.getName())) {
				selectedStudentComboBox = s;
			}
		}
	}
	
	
	// Student Info Test Box interface
	String studentInfoTextBox="";
	public String getStudentInfo() {
		if(selectedStudentComboBox==null)
			return studentInfoTextBox;
		else
			return selectedStudentComboBox.getName() + "|" + selectedStudentComboBox.getEmail() + "|" + selectedStudentComboBox.getGtid() +
    				"|" + capFirstLetter(selectedStudentComboBox.getCsJobEx()) +
    				"|" + selectedStudentComboBox.getAttendance() 
    				+  "|" + selectedStudentComboBox.getCAttendance() + "|" + selectedStudentComboBox.getCppAttendance() + 
    				"|" + selectedStudentComboBox.getJavaAttendance() + "|" + getTeamNums(selectedStudentComboBox);
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
	// SaveButton interface
	public void saveButtonClick(String path) {
		try {
			PrintWriter writer = new PrintWriter(path, "UTF-8");
			writer.print(getStudentInfo());
			writer.close();
			  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
