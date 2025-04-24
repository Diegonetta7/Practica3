package es.uv.prnr.p3.spring.p3_spring;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import es.uv.prnr.p3.spring.p3_spring.model.Department;
import es.uv.prnr.p3.spring.p3_spring.model.Employee;
import es.uv.prnr.p3.spring.p3_spring.model.Manager;
import es.uv.prnr.p3.spring.p3_spring.model.Project;
import es.uv.prnr.p3.spring.p3_spring.model.ProjectService;
import es.uv.prnr.p3.spring.p3_spring.repository.EmployeeRepository;
import es.uv.prnr.p3.spring.p3_spring.service.ProjectAPI;

@SpringBootApplication
public class P3SpringApplication implements CommandLineRunner{
	
	@Autowired
	private EmployeeRepository employeesRepo;
	@Autowired
	private ProjectAPI api;
	
	
	public static void main(String[] args) {
		SpringApplication.run(P3SpringApplication.class, args);
	}
	
	@SuppressWarnings("unused")
	@Override
	public void run(String...strings) throws Exception {
		
		/*List<Project> areaProjects = api.projectsByArea("Big Data");
		System.out.print("Proyectos encontrados: " + areaProjects.size());
		List<Project> topProjects = api.top3BudgetProjects();
		
		List<Employee> projectTeam = api.getProjectTeam(53);
		
	    NamesOnly employeeFound = api.employeeInProject(10003, 53);
	    
	    List<Employee> employeeP1Names = api.employeesLike('A','A',0);
	    List<Employee> employeeP2Names = api.employeesLike('A','A',1);*/
		TestConnection();
		
		//System.exit(0);
	}
	
	/**
	 * Código auxiliar que genera un conjunto de proyectos a partir del servicio
	 * de la practica 2
	 */
	public void generateProjects() {
		ProjectService service = new ProjectService();
		
		List<Integer> indexes = IntStream.range(0,5)
				.boxed()
                .collect(Collectors.toList());
		
		for( Integer i:indexes) {
			Department proyDepartment = service.getDepartmentById("d00" + (i+1));
			int firstEmployeeId = 10001 + (i*100);
			Manager projectManager = service.promoteToManager(firstEmployeeId, 1000L);
			Project acmeProject = 
					service.createBigDataProject("Persistence Layer " + i,proyDepartment,projectManager,new BigDecimal(1500000.99));
			service.assignTeam(acmeProject,firstEmployeeId,firstEmployeeId + 4);
			service.assignInitialHours(acmeProject.getId());
		}	
	}
	
	/**
	 * Test de utilización del repositorio de empleados
	 */
	public void TestConnection() {
		Employee e = employeesRepo.findById(10003).orElse(null);
		
		System.out.println(e.getLastName());
		
		List<Employee> q1 = employeesRepo.findByLastNameOrFirstName( "Erde","Georgi");
		
		List<Employee> q2 =employeesRepo. getByIdLessThan(10005);
		
		
		List<Employee> q3 = employeesRepo.findFirst10ByFirstName("Georgi", PageRequest.of(10,1));
		List<Employee> q4 = employeesRepo.findEmployeeDistinctByFirstName("Georgi");
		
		Employee kid = employeesRepo.findTopByOrderByBirthDateDesc();
		
		
		//Obtener los empleados alfabéticamente por apellido de la posición 20 
		//a la 30
		Page<Employee> topNames = employeesRepo.findAll(
				PageRequest.of(20,30,Sort.Direction.ASC,"lastName"));
		
		
		for(Employee eTop: topNames)
			System.out.println(eTop.getLastName());
		
	}
	


}
