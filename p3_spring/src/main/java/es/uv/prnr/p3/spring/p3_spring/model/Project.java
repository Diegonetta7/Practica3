package es.uv.prnr.p3.spring.p3_spring.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

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
