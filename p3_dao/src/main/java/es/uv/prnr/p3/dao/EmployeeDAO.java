package es.uv.prnr.p3.dao;

import java.util.List;
import es.uv.prnr.p2.Employee;

public interface EmployeeDAO {
	public Employee getEmployeeById(int id);

	public List<Employee> getEmployees();

	public void createEmployee(Employee e);

	public void delete(Employee e);

	// Declaración añadida en el ejercicio 1.3
	public boolean deleteEmployeeById(int id);

	// Declaración añadida en el ejercicio 1.3
	public List<Employee> getByFirstName(String firstName);

}
