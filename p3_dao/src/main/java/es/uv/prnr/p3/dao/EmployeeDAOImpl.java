package es.uv.prnr.p3.dao;

import java.util.List;
import javax.persistence.EntityManager;
import es.uv.prnr.p2.*;

public class EmployeeDAOImpl extends DAOImpl<Integer, Employee>
		implements EmployeeDAO {

	public EmployeeDAOImpl(EntityManager em) {
		super(em, Employee.class);
	}

	public Employee getEmployeeById(int id) {
		return this.getById(id);
	}

	public List<Employee> getEmployees() {
		return this.findAll();
	}

	public void createEmployee(Employee e) {
		this.create(e);
	}

	/**
	 * Método añadido en el ejercicio 1.3.
	 * 
	 * Elimina un empleado tomando su id como argumento.
	 */
	@Override
	public boolean deleteEmployeeById(int id) {
		return this.deleteById(id);
	}

	/**
	 * Método añadido en el ejercicio 1.3.
	 * 
	 * Busca todos los empleados cuyo nombre sea el pasado como parámetro.
	 */
	@Override
	public List<Employee> getByFirstName(String firstName) {
		return this.findByCriteria("e.firstName = '" + firstName + "'");
	}

}
