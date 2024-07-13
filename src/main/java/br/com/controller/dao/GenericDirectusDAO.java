package br.com.controller.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.controller.factory.ConexaoHibernate;


public class GenericDirectusDAO<T>{
	
	protected static EntityManager emfdirectus = ConexaoHibernate.getEntityManager();
	

	public static <T> void save(T entity) {
		emfdirectus.getTransaction().begin();
		emfdirectus.persist(entity);
		emfdirectus.flush();
		emfdirectus.getTransaction().commit();

	}

	public static <T> void update(T entity) {
		emfdirectus.getTransaction().begin();
		emfdirectus.merge(entity);
		emfdirectus.flush();
		emfdirectus.getTransaction().commit();
	}

	public static <T> void delete(T entity) {
		emfdirectus.getTransaction().begin();
		emfdirectus.remove(emfdirectus.contains(entity) ? entity : emfdirectus.merge(entity));
		emfdirectus.flush();
		emfdirectus.getTransaction().commit();
	}

	public static <T> List<T> findAll(Class<T> persistedClass) {
		emfdirectus.clear();
		CriteriaBuilder builder = emfdirectus.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(persistedClass);
		query.from(persistedClass);
		return emfdirectus.createQuery(query).getResultList();
	}

	public static <T> T findById(Class<T> persistedClass, String fieldName, int fieldValue) {
		List<T> entities = findByField(persistedClass, "id", Integer.toString(fieldValue));
		return entities != null ? entities.get(0) : null;
	}

	public static <T> List<T> findByField(Class<T> persistedClass, String fieldName, String fieldValue) {
		emfdirectus.clear();
		CriteriaBuilder builder = emfdirectus.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(persistedClass);
		Root<T> root = criteria.from(persistedClass);
		criteria.distinct(true);
		criteria.where(builder.like(root.<String>get(fieldName), "%" + fieldValue + "%"));
		return emfdirectus.createQuery(criteria).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Class<T> findByFieldEquals(Class<T> persistedClass, String fieldName, String fieldValue) {
		emfdirectus.clear();
		CriteriaBuilder builder = emfdirectus.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(persistedClass);
		Root<T> root = criteria.from(persistedClass);
		criteria.distinct(true);
		criteria.where(builder.equal(root.<String>get(fieldName), "%" + fieldValue + "%"));
		return (Class<T>) emfdirectus.createQuery(criteria).getClass();
	}

	public List<T> findListByQueryDinamica(Class<T> persistedClass, String s) throws Exception {
		emfdirectus.clear();
		CriteriaBuilder builder = emfdirectus.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(persistedClass);
		Root<T> root = criteria.from(persistedClass);
		criteria.distinct(true);
		criteria.where(builder.like(root.<String>get(s), "%"));
		return emfdirectus.createQuery(criteria).getResultList();
	}
}
