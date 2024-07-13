package br.com.controller.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.controller.factory.ConexaoHibernate;

public class GenericDAO<T>{
	protected static EntityManager emf = ConexaoHibernate.getEntityManager();	

	public static <T> void save(T entity, String codigo) {
		emf.getTransaction().begin();
		emf.persist(entity);
		emf.flush();
		emf.getTransaction().commit();

	}
	
	public static <T> void saveSemCodigo(T entity) {
		emf.getTransaction().begin();
		emf.persist(entity);
		emf.flush();
		emf.getTransaction().commit();

	}

	public static <T> void update(T entity) {
		emf.getTransaction().begin();
		emf.merge(entity);
		emf.flush();
		emf.getTransaction().commit();
	}

	public static <T> void delete(T entity) {
		emf.getTransaction().begin();
		emf.remove(emf.contains(entity) ? entity : emf.merge(entity));
		emf.flush();
		emf.getTransaction().commit();
	}

	public static <T> List<T> findAll(Class<T> persistedClass) {
		emf.clear();
		CriteriaBuilder builder = emf.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(persistedClass);
		query.from(persistedClass);
		return emf.createQuery(query).getResultList();
	}

	public static <T> T findById(Class<T> persistedClass, String fieldName, int fieldValue) {
		List<T> entities = findByField(persistedClass, "id", Integer.toString(fieldValue));
		return entities != null ? entities.get(0) : null;
	}

	public static <T> List<T> findByField(Class<T> persistedClass, String fieldName, String fieldValue) {
		emf.clear();
		CriteriaBuilder builder = emf.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(persistedClass);
		Root<T> root = criteria.from(persistedClass);
		criteria.distinct(true);
		criteria.where(builder.like(root.<String>get(fieldName), "%" + fieldValue + "%"));
		return emf.createQuery(criteria).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Class<T> findByFieldEquals(Class<T> persistedClass, String fieldName, String fieldValue) {
		emf.clear();
		CriteriaBuilder builder = emf.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(persistedClass);
		Root<T> root = criteria.from(persistedClass);
		criteria.distinct(true);
		criteria.where(builder.equal(root.<String>get(fieldName), "%" + fieldValue + "%"));
		return (Class<T>) emf.createQuery(criteria).getClass();
	}

	public List<T> findListByQueryDinamica(Class<T> persistedClass, String s) throws Exception {
		emf.clear();
		CriteriaBuilder builder = emf.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(persistedClass);
		Root<T> root = criteria.from(persistedClass);
		criteria.distinct(true);
		criteria.where(builder.like(root.<String>get(s), "%"));
		return emf.createQuery(criteria).getResultList();
	}
	
	public static <T> T findByFieldSingleResult(Class<T> persistedClass, String fieldName, String fieldValue) {
	    List<T> entities = findByField(persistedClass, fieldName, fieldValue);
	    return entities != null && !entities.isEmpty() ? entities.get(0) : null;
	}
	
}
