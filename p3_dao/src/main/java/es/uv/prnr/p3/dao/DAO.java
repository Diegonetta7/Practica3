package es.uv.prnr.p3.dao;

import java.util.List;

public interface DAO<K, T> {
	public T getById(K id);

	public List<T> findAll();

	public void create(T entity);

	public void update(T entity);

	public void delete(T entity);

	// Declaración añadida en el ejercicio 1.2
	public boolean deleteById(K id);

	// Declaración añadida en el ejercicio 1.2
	public List<T> findByCriteria(String jpqlCondition);
}
