package es.uv.prnr.p3.spring.p3_spring.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import es.uv.prnr.p3.spring.p3_spring.model.Employee;
import es.uv.prnr.p3.spring.p3_spring.model.NamesOnly;
import es.uv.prnr.p3.spring.p3_spring.model.Project;
import es.uv.prnr.p3.spring.p3_spring.repository.EmployeeRepository;
import es.uv.prnr.p3.spring.p3_spring.repository.ProjectRepository;

@Component
public class ProjectAPI {

	@Autowired
	private EmployeeRepository employeesRepo;

	@Autowired
	private ProjectRepository projectRepo;

	public List<Project> projectsByArea(String area) {
		List<Project> projects = new ArrayList();
		projects = projectRepo.findByArea(area);
		return projects;
	}

	public List<Project> top3BudgetProjects() {
		List<Project> top3 = new ArrayList();
		// TODO
		return top3;
	}

	public List<String> getProjectTeam(int projectId) {
		return employeesRepo.getEmployeeNamesByProjectOrderedDesc(projectId);
	}

	public Optional<NamesOnly> employeeInProject(int employeeId, int projectId) {
		return employeesRepo.findByAssignedTo_IdAndId(employeeId, projectId);
	}

	public List<Employee> employeesLike(char charName, char charSurname, int page) {
		int pageSize = 10;
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<Employee> employeePage = employeesRepo.findByFirstNameStartingWithAndLastNameStartingWith(
				String.valueOf(charName),
				String.valueOf(charSurname), pageable);
		List<Employee> employees = employeePage.getContent(); 

		return employees;

	}

	public BigDecimal totalBudgetFromArea(String area){
		return projectRepo.findTotalBudgetByArea(area);
	}

	public Project getProjectWithLessBudget(){
		return projectRepo.findFirstByOrderByBudgetAsc();
	}

	public List<Project> getActiveProjects(LocalDate date){
		return projectRepo.findByEndDateGreaterThanEqual(date);
	}

	public List<Employee> getEmployeesInMoreThanOneProyect(){
		return employeesRepo.findEmployeesInMoreThanOneProject();
	}
}
