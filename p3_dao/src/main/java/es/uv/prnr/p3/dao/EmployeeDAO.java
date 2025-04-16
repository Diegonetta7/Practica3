package es.uv.prnr.p3.dao;

import java.util.List;
//TODO Cambiar este import por el que corresponda a vuestro proyecto/clase
import es.uv.prnr.p2.Employee;

public interface EmployeeDAO {
	   public Employee getEmployeeById(int id);
	   public List<Employee> getEmployees();
	   public void createEmployee(Employee e);
	   public void delete(Employee e);
	      
	   //TODO Añadir métodos del ejercicio 1
	  
		 
}
