package es.uv.prnr.p2;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import javax.persistence.*;

public class ProjectService {
	EntityManagerFactory emf;
	EntityManager em;

	public ProjectService() {
		this.emf = Persistence.createEntityManagerFactory("employees");
		this.em = emf.createEntityManager();
	}

	/**
	 * Busca un departamento
	 * 
	 * @param id identificador del departamento
	 * @return entidad con el deparamenteo encontrado
	 */
	public Department getDepartmentById(String id) {
		return em.find(Department.class, id);
	}

	/**
	 * Asciende a un empleado a manager. Utilizar una estrategía de herencia
	 * adecuada en employee. Tened en cuenta que NO puede haber dos entidades
	 * con el mismo id por lo que habrá que eliminar el empleado original en algún
	 * momento.
	 * 
	 * @param employeeId
	 * @param bonus
	 * @return
	 */
	public Manager promoteToManager(int employeeId, long bonus) {
		// Inicia una transacción
		em.getTransaction().begin();

		// Busca el empleado
		Employee employee = em.find(Employee.class, employeeId);

		// Crea la nueva instancia de Manager con los datos del empleado
		Manager manager = new Manager(employee, bonus);

		// Elimina el empleado original (porque comparten la misma clave primaria)
		em.remove(employee);

		// Guarda el nuevo Manager
		em.persist(manager);

		// Confirma la transacción
		em.getTransaction().commit();

		return manager;
	}

	/**
	 * Crea un nuevo proyecto en el area de Big Data que comienza
	 * en la fecha actual y que finaliza en 3 años.
	 * 
	 * @param name
	 * @param d      departamento asignado al proyecto
	 * @param m      manager que asignado al proyecto
	 * @param budget
	 * @return el proyecto creado
	 */
	public Project createBigDataProject(String name, Department d, Manager m,
			BigDecimal budget) {
		// Inicia la transacción
		em.getTransaction().begin();

		// Crea un nuevo proyecto con la información recibida
		Project project = new Project(
				name,
				d,
				m,
				budget,
				LocalDate.now(),
				LocalDate.now().plusYears(3),
				"Big Data");

		// Persiste el nuevo proyecto en la base de datos
		em.persist(project);

		// Confirma la transacción
		em.getTransaction().commit();

		return project;
	}

	/**
	 * Crea un equipo de proyecto. Se deberá implementa el método addEmployee de
	 * Project para incluir los empleados
	 * 
	 * @param p       proyecto al cual asignar el equipo
	 * @param startId identificador a partir del cual se asignan empleado
	 * @param endId   identificador final de empleados. Se asume que start id <
	 *                endId
	 */
	public void assignTeam(Project p, int startId, int endId) {
		// Borra los empleados previos
		p.getEmployees().clear();

		// Inicia la transacción
		em.getTransaction().begin();

		// Recorre los empleados desde el startId al endId
		for (int id = startId; id <= endId; id++) {
			Employee e = em.find(Employee.class, id);
			if (e != null && !p.getEmployees().contains(e)) {
				// Si se encuentra el empleado y no se ha añadido ya, se añade al equipo
				p.addEmployee(e);
				em.merge(p);
			}
		}

		// Confirma la transacción
		em.getTransaction().commit();
	}

	/**
	 * Genera un conjunto de horas inicial para cada empleado. El método
	 * asigna para cada mes de duración del proyecto, un número entre
	 * 10-165 de horas a cada empleado. Se utiliza el método addHours.
	 * 
	 * @param projectId
	 * @return total de horas generadas para el proyecto
	 */
	public int assignInitialHours(int projectId) {
		// Inicia la transacción
		em.getTransaction().begin();

		// Declaración del recuento de horas totales y del Random
		int horasTotales = 0;
		Random random = new Random();

		// Se busca el proyecto y se obtiene la fecha de inicio y de fin
		Project p = em.find(Project.class, projectId);
		LocalDate inicio = p.getStartDate();
		LocalDate fin = p.getEndDate();

		// Se asignan las horas a cada empleado para cada mes de duración del proyecto
		while (inicio.isBefore(fin) || inicio.isEqual(fin)) {
			for (Employee e : p.getEmployees()) {
				// Genera horas entre 10 y 165
				int horasRandom = random.nextInt(156) + 10;

				// Registra las horas del empleado
				p.addHours(e, inicio.getMonthValue(), inicio.getYear(), horasRandom);

				// Añade estas horas a las totales del proyecto
				horasTotales += horasRandom;
			}
			// Avanza al siguiente mes para continuar la iteración
			inicio = inicio.plusMonths(1);
		}

		// Confirma la transacción
		em.merge(p);
		em.persist(p);
		em.getTransaction().commit();

		return horasTotales;
	}

	/**
	 * Busca si un empleado se encuentra asignado en el proyecto utilizando la
	 * namedQuery Project.findEmployee
	 * 
	 * @param projectId
	 * @param firstName
	 * @param lastName
	 * @return cierto si se encuentra asignado al proyecto
	 */
	public boolean employeeInProject(int projectId, String firstName, String lastName) {
		Query query = em.createNamedQuery("Project.findEmployee");

		query.setParameter("projectId", projectId);
		query.setParameter("firstName", firstName);
		query.setParameter("lastName", lastName);

		Long count = (Long) query.getSingleResult();

		return count > 0;
	}

	/**
	 * Devuelve los meses con mayor número de horas de un año determinado
	 * utilizando la namedQuery Project.getTopMonths
	 * 
	 * @param projectId
	 * @param year      año a seleccionar
	 * @param rank      número de meses a mostrar, se asume que rank <= 12
	 * @return una lista de objetos mes,hora ordenados de mayor a menor
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getTopHourMonths(int projectId, int year, int rank) {
		Query query = em.createNamedQuery("Project.getTopMonths");

		query.setParameter("projectId", projectId);
		query.setParameter("year", year);
		query.setMaxResults(rank);

		return query.getResultList();
	}

	/**
	 * Devuelve para cada par mes-año el presupuesto teniendo en cuenta el
	 * coste/hora de los empleados asociado utilizando la namedQuery
	 * Project.getMonthlyBudget que realiza una consulta nativa
	 * 
	 * @param projectId
	 * @return una colección de objetos MonthlyBudget
	 */
	@SuppressWarnings("unchecked")
	public List<MonthlyBudget> getMonthlyBudget(int projectId) {
		Query query = em.createNamedQuery("Project.getMonthlyBudget");

		query.setParameter("projectId", projectId);

		return query.getResultList();
	}
}