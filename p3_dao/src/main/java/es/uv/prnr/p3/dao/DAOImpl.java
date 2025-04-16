package es.uv.prnr.p3.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class DAOImpl<K, T> implements DAO<K, T> {

	protected EntityManager em;
	protected Class<T> entityClass;

	protected DAOImpl(EntityManager em, Class<T> entityClass) {
		this.em = em;
		this.entityClass = entityClass;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Query q = em.createQuery("from " + this.entityClass.getName());
		return q.getResultList();
	}

	public T getById(K id) {
		return em.find(entityClass, id);
	}

	public void create(T entity) {
		this.em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
	}

	public void update(T entity) {
		this.em.getTransaction().begin();
		em.merge(entity);
		em.getTransaction().commit();
	}

	public void delete(T entity) {
		this.em.getTransaction().begin();
		em.remove(entity);
		em.getTransaction().commit();
	}

	/**
	 * Método añadido en el ejercicio 1.2.
	 * 
	 * Recibe un identificador de una entidad concreta y
	 * lo borra devolviendo true o false
	 */
	@Override
	public boolean deleteById(K id) {
		boolean borrado = false;
		T entity = em.find(entityClass, id);

		if (entity != null) {
			this.em.getTransaction().begin();
			em.remove(entity);
			this.em.getTransaction().commit();

			borrado = true;
		}

		return borrado;
	}

	/**
	 * Método añadido en el ejercicio 1.2.
	 * 
	 * Recibe una condición para la cláusula WHERE con formato JPQL (criteria) y
	 * retorna las entidades que la cumplen (por ejemplo, firstName = ‘Remko’
	 * devolvería todos los empleados con dicho nombre)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriteria(String jpqlCondition) {
		String queryStr = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE " + jpqlCondition;

		Query query = em.createQuery(queryStr, entityClass);

		return query.getResultList();
	}

}
