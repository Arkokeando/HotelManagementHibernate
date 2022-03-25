package dao;

import org.hibernate.Session;

import persistencia.HibernateUtil;

public class GenericDAO<T> {

	/**
	 * insert any entity in the DB
	 * @param entidad
	 */
	public void guardar(T entidad) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(entidad);
		session.getTransaction().commit();

	}
	/**
	 * Delete any entity from the DB
	 * @param entidad
	 */
	public  void borrar(T entidad) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(entidad);
		session.getTransaction().commit();
	}
	
	/**
	 * Update any entity in the database
	 * @param entidad
	 */
	public void actualizar(T entidad) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(entidad);
		session.getTransaction().commit();
	}
}
