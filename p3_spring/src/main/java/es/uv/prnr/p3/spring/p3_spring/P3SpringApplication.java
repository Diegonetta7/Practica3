package es.uv.prnr.p3.spring.p3_spring;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import es.uv.prnr.p3.spring.p3_spring.model.Department;
import es.uv.prnr.p3.spring.p3_spring.model.Employee;
import es.uv.prnr.p3.spring.p3_spring.model.Manager;
import es.uv.prnr.p3.spring.p3_spring.model.NamesOnly;
import es.uv.prnr.p3.spring.p3_spring.model.Project;
import es.uv.prnr.p3.spring.p3_spring.model.ProjectService;
import es.uv.prnr.p3.spring.p3_spring.repository.EmployeeRepository;
import es.uv.prnr.p3.spring.p3_spring.service.ProjectAPI;

@SpringBootApplication
public class P3SpringApplication implements CommandLineRunner {

	@Autowired
	private EmployeeRepository employeesRepo;
	@Autowired
	private ProjectService service;
	@Autowired
	private ProjectAPI api;

	public static void main(String[] args) {
		SpringApplication.run(P3SpringApplication.class, args);
	}

	@SuppressWarnings("unused")
	@Override
	public void run(String... strings) throws Exception {
		// Punto 1
		List<Project> areaProjects = api.projectsByArea("Big Data");
		System.out.print("Proyectos encontrados: " + areaProjects.size());

		// Punto 2
		List<Project> top3Projects = api.top3BudgetProjects();
		System.out.print("Top 3 proyectos con más presupuesto: ");
		for (Project project : top3Projects) {
			System.out.println(project.getName());
		}

		// Punto 3
		List<String> projectTeam = api.getProjectTeam(53);
		System.out.println("Empleados en el proyecto:");
		for (String employee : projectTeam) {
			System.out.println(employee);
		}

		// Punto 4
		Optional<NamesOnly> employeeFound = api.employeeInProject(10003, 53);
		if (employeeFound.isPresent()) {
			String name = employeeFound.get().getFirstName();
			String surname = employeeFound.get().getLastName();
			System.out.println("Empleado : " + name + " " + surname);
		} else {
			System.out.println("Este empleado no trabaja en ese proyecto");
		}

		// Punto 5
		List<Employee> employeeP1Names = api.employeesLike('A', 'A', 0);
		List<Employee> employeeP2Names = api.employeesLike('A', 'A', 1);

		System.out.println("Empleados de la página 1:");
		for (Employee employee : employeeP1Names) {
			System.out.println(employee.getFirstName() + " " + employee.getLastName());
		}

		System.out.println("Empleados de la página 2:");
		for (Employee employee : employeeP2Names) {
			System.out.println(employee.getFirstName() + " " + employee.getLastName());
		}

		// Punto 6
		BigDecimal totalBudget = api.totalBudgetFromArea("Big Data");
		System.out.println("Presupuesto total de este Area: " + totalBudget);

		// Punto 7
		Project cheapestProject = api.getProjectWithLessBudget();
		if (cheapestProject != null) {
			System.out.println("Proyecto con menos presupuesto: " + cheapestProject.getName());
		} else {
			System.out.println("No hay proyectos en el sistema");
		}

		// Punto 8
		List<Project> activeProjects = api.getActiveProjects(LocalDate.now());
		System.out.println("Proyectos activos actualmente:");
		for (Project project : activeProjects) {
			System.out.println("El proyecto " + project.getName() + " que acaba el " + project.getEndDate());
		}

		// Punto 9
		List<Employee> employeesInProject = api.getEmployeesInMoreThanOneProyect();
		System.out.println("Empleados en mas de un proyecto: ");
		for (Employee empleado : employeesInProject) {
			System.out.println(empleado.getFirstName() + " " + empleado.getLastName());
		}

		// Punto 10
		String nombreArea = "Big Data";
		long count = api.countEmployeesByArea(nombreArea);
		System.out.println("Número de empleados en el área " + nombreArea + ": " + count);

		// generateProjects();
		// TestConnection();

		// System.exit(0);
	}

	/**
	 * Código auxiliar que genera un conjunto de proyectos a partir del servicio
	 * de la practica 2
	 */
	public void generateProjects() {
		System.out.println("Generando proyectos...");

		List<Integer> indexes = IntStream.range(0, 5)
				.boxed()
				.collect(Collectors.toList());

		for (Integer i : indexes) {
			Department proyDepartment = service.getDepartmentById("d00" + (i + 1));
			int firstEmployeeId = 10001 + (i * 100);
			Manager projectManager = service.promoteToManager(firstEmployeeId, 1000L);
			Project acmeProject = service.createBigDataProject("Persistence Layer " + i, proyDepartment, projectManager,
					new BigDecimal(1500000.99));
			service.assignTeam(acmeProject, firstEmployeeId, firstEmployeeId + 4);
			service.assignInitialHours(acmeProject.getId());
		}
	}

	/**
	 * Test de utilización del repositorio de empleados
	 */
	public void TestConnection() {
		Employee e = employeesRepo.findById(10007).orElse(null);

		System.out.println("*** " + e.getLastName());

		List<Employee> q1 = employeesRepo.findByLastNameOrFirstName("Erde", "Georgi");

		List<Employee> q2 = employeesRepo.getByIdLessThan(10005);

		List<Employee> q3 = employeesRepo.findFirst10ByFirstName("Georgi", PageRequest.of(10, 1));
		List<Employee> q4 = employeesRepo.findEmployeeDistinctByFirstName("Georgi");

		Employee kid = employeesRepo.findTopByOrderByBirthDateDesc();

		// Obtener los empleados alfabéticamente por apellido de la posición 20
		// a la 30
		Page<Employee> topNames = employeesRepo.findAll(
				PageRequest.of(20, 30, Sort.Direction.ASC, "lastName"));

		for (Employee eTop : topNames)
			System.out.println(eTop.getLastName());

	}

}
