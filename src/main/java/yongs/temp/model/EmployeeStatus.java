package yongs.temp.model;

public class EmployeeStatus {
    String id;
    String name;
    String sex;
    double salary;
    String project;
    String skill;
    String level;
    String grade;
    
    public EmployeeStatus(String id,
    					  String name,
    					  String sex,
    					  double salary,
    					  String project,
    					  String skill,
    					  String level,
    					  String grade) {
    	this.id = id;
    	this.name = name;
    	this.sex = sex;
    	this.salary = salary;
    	this.project = project;
    	this.skill = skill;
    	this.level = level;
    	this.grade = grade;
    }
 
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
}
