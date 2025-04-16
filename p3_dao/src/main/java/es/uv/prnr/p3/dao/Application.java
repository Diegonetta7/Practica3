package es.uv.prnr.p3.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import es.uv.prnr.p2.Employee;

public class Application {

	public static void main(String[] args) throws SQLException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("acmeEmployees");
		EntityManager em = emf.createEntityManager();
		EmployeeDAOImpl repository = new EmployeeDAOImpl(em);

		// testConnection(repository);
		testExercise1(repository);

		// TODO Obtener el DAO mediante la factoria
		// testExercise2(projectRepo);

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

	/*
	 * public static void testExercise2(ProjectDAO repository) {
	 * 
	 * 
	 * List<Project> currentProjects = repository.getProjects();
	 * System.out.println(currentProjects.size() + " proyectos encontrados");
	 * 
	 * // Recuperamos el proyecto que hemos creado
	 * p = repository.getProjectById(currentProjects.get(0 ).getId());
	 * p.print();
	 * 
	 * System.out.println("Asignamos el nuevo proyecto");
	 * repository.assignTeam(p, 20001, 20003);
	 * p.print();
	 * 
	 * }
	 */
}
