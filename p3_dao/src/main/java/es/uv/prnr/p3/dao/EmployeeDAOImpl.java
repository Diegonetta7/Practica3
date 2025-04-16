package es.uv.prnr.p3.dao;

import java.util.List;
import javax.persistence.EntityManager;
//TODO Cambiar este import por el que corresponda a vuestro proyecto/clase
import es.uv.prnr.p2.*;

public class EmployeeDAOImpl extends DAOImpl<Integer,Employee>
	implements EmployeeDAO{
	
	public EmployeeDAOImpl(EntityManager em) {
		super(em,Employee.class);
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

	// TODO Completad metodos a implementar del ejercicio 1




}
