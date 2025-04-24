package es.uv.prnr.p3.spring.p3_spring.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.uv.prnr.p3.spring.p3_spring.model.Employee;
import es.uv.prnr.p3.spring.p3_spring.model.NamesOnly;
import es.uv.prnr.p3.spring.p3_spring.model.Project;
import es.uv.prnr.p3.spring.p3_spring.repository.EmployeeRepository;

@Component
public class ProjectAPI {
	
	@Autowired
	private EmployeeRepository employeesRepo;

	//@Autowired
	//private ProjectRepository projectRepo;
	//TODO Añadir repositorios adicionales y descomentar ProjectRepository
	
	
	
	public List<Project> projectsByArea (String area){
		List<Project> projects = new ArrayList();
		//TODO
		return projects;
	}
	
	
	public List<Project> top3BudgetProjects(){
		List<Project> top3 = new ArrayList();
		//TODO 
	    return top3;	
	}
	
	public List<Employee> getProjectTeam (int projectId) {
		//TODO 
		return null;
	}
	
	public NamesOnly employeeInProject (int employeeId, int projectId) {
		
		//TODO 
		return null;
	}
	
	public List<Employee> employeesLike (char charName, char charSurname, int page){
		List<Employee> employees = new ArrayList();
		//TODO 
		
    	return employees;
	
	}

	//TODO añadir métodos que faltan del ejercicio 3

}
