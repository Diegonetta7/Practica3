package es.uv.prnr.p2;

import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Clase principal para probar la ejecución de ProjectService
 * Se recomienda descomentar el código de los ejercicios conforme se vayan
 * realizando.
 * 
 * @author Paco
 *
 */
public class P2Main {
	public static void main(String[] args) {
		ProjectService service = new ProjectService();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("employees");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		/* Comprobar funcionamiento */
		Employee e = em.find(Employee.class, 222222);
		e.print();

		Employee newEmployee = new Employee(1, "Edgar", "Cood",
				LocalDate.of(1923, 8, 19), LocalDate.now(), Employee.Gender.M);
		em.persist(newEmployee);
		e = em.find(Employee.class, 1);
		e.print();
		em.remove(e);
		em.getTransaction().commit();

		/*
		 * Ejercicio 2. Definiendo un servicio de persistencia
		 */
		// getDepartmentById
		Department proyDepartment = service.getDepartmentById("d005");
		System.out.println(
				"Nombre del departamento " + proyDepartment.getDeptNo() + ": " + proyDepartment.getDeptName() + ".");

		// promoteToManager
		Manager projectManager = service.promoteToManager(10007, 1000L);
		System.out.println("Nombre del manager " + projectManager.getId() + ": " + projectManager.getFirstName()
				+ ". Salario: " + projectManager.getBonus());

		// createBigDataProject
		Project acmeProject = service.createBigDataProject("Persistence Layer",
				proyDepartment,
				projectManager, new BigDecimal(1500000.99));
		System.out.println("Nombre del proyecto: " + acmeProject.getName() + ", nombre del departamento: "
				+ acmeProject.getDepartment().getDeptName() + ", nombre del manager: "
				+ acmeProject.getManager().getFirstName() + " y presupuesto: " + acmeProject.getBudget());

		// assignTeam
		service.assignTeam(acmeProject, 10001, 10011);
		System.out.println("El tamaño del equipo es (debe ser 5): " + acmeProject.getEmployees().size());

		// assignInitialHours
		int totalHours = service.assignInitialHours(acmeProject.getId());
		System.out.println("Total project hours: " + totalHours);

		/*
		 * Ejercicio 3. Prueba de consultas
		 */
		// employeeInProject
		if (service.employeeInProject(acmeProject.getId(), "Parto", "Bamford"))
			System.out.println("Parto Bamford assigned to project");
		if (!service.employeeInProject(acmeProject.getId(), "Luke", "Johnson"))
			System.out.println("Luke Johnson is not assigned to project");

		// getTopHourMonths
		List<Object[]> results = service.getTopHourMonths(acmeProject.getId(), 2025,
				3);
		for (Object[] result : results) {
			System.out.println("Month " + result[0] + " Hours " + result[1]);
		}

		// getMonthlyBudget
		List<MonthlyBudget> monthBudgets = service.getMonthlyBudget(acmeProject.getId());
		for (MonthlyBudget budget : monthBudgets) {
			System.out.println(budget.getMonth() + "-" + budget.getYear() + " : " +
					budget.getAmount() + " euros");
		}

		/* Eliminamos la información creada */
		em.getTransaction().begin();
		Manager m = em.merge(projectManager);
		Project p = em.merge(acmeProject);
		em.remove(p);
		em.remove(m);
		em.getTransaction().commit();

		return;
	}
}
