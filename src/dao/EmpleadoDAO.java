package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import persistencia.Empleado;
import persistencia.HibernateUtil;
import persistencia.Hotel;

public class EmpleadoDAO extends GenericDAO<Empleado> {
	// Show all employees
	public List<Empleado> mostrarEmpleados() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("SELECT e FROM Empleado e");
		List<Empleado> empleados = query.list();

		return empleados;
	}

	// Show employee with requested ID
	public Empleado findByPk(String dni) {
		Empleado e=null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("SELECT e FROM Empleado e WHERE dni=?");
		query.setString(0, dni);
		List<Empleado> empleados = query.list();
		if (empleados.size()>0) {
			for (Empleado empleado : empleados) {
				e=empleado;
			}
		}
		return e;
	}

	public void addEmpleado(String dni, String nombre) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("INSERT INTO Empleado (dni,nombreEmpleado) VALUES (?,?)");
		query.setString(1, nombre);
		query.setString(0, dni);
		query.executeUpdate();
	}

	public void deleteEmpleado(String dni) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("DELETE FROM Empleado WHERE dni=?");
		query.setString(0, dni);
		query.executeUpdate();
	}

}
