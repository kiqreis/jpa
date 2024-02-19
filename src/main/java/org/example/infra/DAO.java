package org.example.infra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DAO<T> {

  private static EntityManagerFactory emf;
  private static EntityManager em;
  private Class<T> tClass;

  static {
    try {
      emf = Persistence.createEntityManagerFactory("jdbc_sql");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public DAO() {
    this(null);
  }

  public DAO(Class<T> tClass) {
    em = emf.createEntityManager();
    this.tClass = tClass;
  }

  public DAO<T> openTransaction() {
    em.getTransaction().begin();
    return this;
  }

  public DAO<T> closeTransaction() {
    em.getTransaction().commit();
    return this;
  }

  public DAO<T> include(T data) {
    em.persist(data);
    return this;
  }

  public DAO<T> atomicInclude(T data) {
    return this.openTransaction().include(data).closeTransaction();
  }

  public List<T> getAll() {
    return this.getAll(10, 0);
  }

  public T getById(Long id) {
    return em.find(tClass, id);
  }

  public List<T> getAll(int quantity, int offset) {

    if (tClass == null) {
      throw new UnsupportedOperationException("Nullable class");
    }

    String jpql = "select t from " + tClass.getName() + " t";

    TypedQuery<T> query = em.createQuery(jpql, tClass);
    query.setMaxResults(quantity);
    query.setFirstResult(offset);

    return query.getResultList();
  }

  public List<T> query(String queryName, Object... params) {
    TypedQuery<T> query = em.createNamedQuery(queryName, tClass);

    for (int i = 0; i < params.length; i += 2) {

      query.setParameter(params[i].toString(), params[i + 1]);
    }

    return query.getResultList();
  }

  public T singleQuery(String queryName, Object... params) {
    List<T> list = query(queryName, params);
    return list.isEmpty() ? null : list.get(0);
  }

  public void closeDAO() {
    em.close();
  }
}
