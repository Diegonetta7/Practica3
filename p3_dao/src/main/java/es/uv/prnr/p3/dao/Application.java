package es.uv.prnr.p3.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import es.uv.prnr.p2.Employee;
import es.uv.prnr.p2.Department;
import es.uv.prnr.p2.Manager;
import es.uv.prnr.p2.Project;

public class Application {

	public static void main(String[] args) throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("acmeEmployees");
		EntityManager em = emf.createEntityManager();
		EmployeeDAOImpl repository = new EmployeeDAOImpl(em);

		// testConnection(repository);
		// testExercise1(repository);

		DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.TYPE.JPA);
		ProjectDAO projectDAO = daoFactory.getProjectDAO();
		testExercise2(projectDAO);

	}

	/*
	 * public static void testConnection(EmployeeDAOImpl repository) {
	 * int count = 0;
	 * List<Employee> myEmployees = repository.getEmployees();
	 * 
	 * for(Employee e: myEmployees){
	 * System.out.println(
	 * e.getFirstName()+" "+ e.getLastName()+" "+
	 * e.getBirthDate().toString());
	 * if(count++ > 10)
	 * break;
	 * }
	 * Employee e = repository.getEmployeeById(10007);
	 * System.out.println("Test Connection getEmployeeById " + e.getFirstName());
	 * }
	 */

	public static void testExercise1(EmployeeDAOImpl repository) {
		Employee e = new Employee(1, "Remko", "Master",
				LocalDate.of(1923, 8, 19), LocalDate.now(), Employee.Gender.M);

		repository.create(e);
		List<Employee> found = repository.getByFirstName("Remko");
		System.out.println(found.size() + " employess with Remko name found");
		repository.deleteEmployeeById(e.getId());
	}

	public static void testExercise2(ProjectDAO repository) {
		Department department = new Department("d008", "Research");
		Employee employee = new Employee(1, "Remko", "Master",
				LocalDate.of(1923, 8, 19), LocalDate.now(), Employee.Gender.M);
		Manager manager = new Manager(employee, 0L);

		Project newProject = new Project("Test amazing things", department, manager, new BigDecimal("35000.50"),
				LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 1), "Testing");

		repository.createProject(newProject);
		System.out.println("Proyecto \"" + newProject.getName() + "\" añadido con éxito");

		Project p = repository.getProjectById(newProject.getId());
		if (p != null) {
			System.out.println("Proyecto recuperado por ID:");
			p.print();
		} else {
			System.out.println("No se encontró el proyecto por ID.");
		}

		System.out.println("Buscamos el proyecto por nombre:");
		List<Project> resultsByName = repository.getByName("Test amazing things");
		if (!resultsByName.isEmpty()) {
			for (Project project : resultsByName) {
				project.print();
			}
		} else {
			System.out.println("No se encontró ningún proyecto con ese nombre.");
		}

		System.out.println("Eliminamos el proyecto creado");
		boolean deleted = repository.deleteProjectById(newProject.getId());

		if (deleted) {
			System.out.println("El proyecto fue eliminado correctamente.");
		} else {
			System.out.println("¡Error! El proyecto aún existe:");
		}
	}

}
