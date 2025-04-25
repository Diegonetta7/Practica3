package es.uv.prnr.p3.spring.p3_spring.repository;

import java.util.Date;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import es.uv.prnr.p3.spring.p3_spring.model.Employee;
import es.uv.prnr.p3.spring.p3_spring.model.NamesOnly;
import es.uv.prnr.p3.spring.p3_spring.model.Project;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	// TODO Métodos adicionales para el repositorio empleados

	// Búsquedas con condición con distintos operadores
	List<Employee> findByLastNameOrFirstName(String firstName, String lastName);

	List<Employee> getByIdLessThan(Integer id);

	List<Employee> readByHireDateBetween(Date d1, Date d2);

	// Búsqueda por apellido ordenada por nombre
	List<Employee> findByLastNameOrderByFirstNameAsc(String lastName);

	// Uso del operador like
	List<Employee> findByFirstNameNotLike(String likeExpression);

	// Búsqueda por apellido sin tener en cuenta mayúsculas
	List<Employee> findByLastNameIgnoreCase(String lastName);

	// Uso del operador distinct
	List<Employee> findEmployeeDistinctByFirstName(String firstName);

	// Búsqueda a partir de una colección
	List<Employee> findByFirstNameIn(List<String> names);

	// Empleado más jovén (puede usarse tambien First)
	Employee findTopByOrderByBirthDateDesc();

	// 10 primeros empleados de cada pagina
	List<Employee> findFirst10ByFirstName(String lastName, Pageable pageable);

	// Punto 3
	@Query("SELECT e.firstName FROM Employee e JOIN e.assignedTo p WHERE p.id = :projectId ORDER BY e.firstName DESC")
	List<String> getEmployeeNamesByProjectOrderedDesc(@Param("projectId") int projectId);

	// Punto 4
	Optional<NamesOnly> findByAssignedTo_IdAndId(int projectId, int employeeId);

	// Punto 5
	Page<Employee> findByFirstNameStartingWithAndLastNameStartingWith(String firstInitial, String lastInitial,
			Pageable pageable);

	// Punto 9
	@Query("SELECT e FROM Employee e JOIN e.assignedTo p GROUP BY e.id HAVING COUNT(p) > 1")
    List<Employee> findEmployeesInMoreThanOneProject();
}
