package es.uv.prnr.p2;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

// JPQL de Ejercicio3 employeeInProject 
@NamedQuery(name = "Project.findEmployee", query = "SELECT COUNT(p) FROM Project p JOIN p.team e " +
		"WHERE e.firstName = :firstName AND e.last_name = :lastName " +
		"AND p.id = :projectId")

// JPQL de Ejercicio3 getTopHoursMonth
@NamedQuery(name = "Project.getTopMonths", query = "SELECT ph.month, SUM(ph.hours) AS total_hours " +
		"FROM ProjectHours ph " +
		"JOIN ph.project p " +
		"WHERE p.id = :projectId AND ph.year = :year " +
		"GROUP BY ph.month " +
        "ORDER BY total_hours DESC")

// Consulta SQL para getMonthly Budget.
@NamedNativeQuery(name = "Project.getMonthlyBudget", query = "WITH EmployeeCost AS ( " +
		"SELECT s.emp_no, (s.salary / 1650) AS coste_hora " +
		"FROM salaries s) " +
		"SELECT ph.month, ph.year, SUM(ec.coste_hora * ph.hours) AS presupuesto_total " +
		"FROM monthly_hours ph " +
		"JOIN EmployeeCost ec ON ph.fk_employee = ec.emp_no " +
		"WHERE ph.fk_project = :projectId " +
		"GROUP BY ph.year, ph.month " +
		"ORDER BY ph.year, ph.month ", resultSetMapping = "MonthBudgetMapping")

// Mapeo del ResultSet para la consulta anterior
@SqlResultSetMapping(name = "MonthBudgetMapping", classes = @ConstructorResult(targetClass = MonthlyBudget.class, columns = {
		@ColumnResult(name = "month", type = Integer.class),
		@ColumnResult(name = "year", type = Integer.class),
		@ColumnResult(name = "presupuesto_total", type = Float.class)
}))

@Entity
@Table(name = "project")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(nullable = false, unique = true)
	private String name;

	@ManyToOne
	@JoinColumn(name = "fk_department", nullable = false)
	private Department department;

	@Column(nullable = false)
	private BigDecimal budget;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@Column(nullable = false)
	private String area;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_manager", nullable = false)
	private Manager manager;

	@ManyToMany
	@JoinTable(name = "project_team", joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "emp_no"))
	private List<Employee> team = new ArrayList<>();

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProjectHours> hours = new ArrayList<>();

	public Project() {
	}

	public Project(String name, Department department, Manager manager, BigDecimal budget, LocalDate startDate,
			LocalDate endDate, String area) {
		this.name = name;
		this.department = department;
		this.manager = manager;
		this.budget = budget;
		this.startDate = startDate;
		this.endDate = endDate;
		this.area = area;
	}

	/**
	 * Relaciona el proyecto con el empleado e
	 * 
	 * @param e
	 */
	public void addEmployee(Employee e) {
		if (!team.contains(e)) {
			team.add(e);
		}
	}

	/**
	 * Añade un numero de horas al empleado e para un mes-año concreto
	 * 
	 * @param e
	 * @param month
	 * @param year
	 * @param hours
	 */
	public void addHours(Employee e, int month, int year, int hours) {
		ProjectHours ph = new ProjectHours(month, year, hours, e, this);
		this.hours.add(ph);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department Department) {
		this.department = Department;
	}

	public BigDecimal getBudget() {
		return this.budget;
	}

	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Manager getManager() {
		return manager;
	}

	public List<Employee> getEmployees() {
		return this.team;
	}

	public List<ProjectHours> getHours() {
		return this.hours;
	}

	public void print() {
		System.out.println("Project " + this.name + " from department " + this.department.getDeptName());
		System.out.print("Managed by ");
		this.manager.print();
		System.out.println("Project Team");
	}

}
