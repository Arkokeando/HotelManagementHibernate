package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import persistencia.Empleado;
import persistencia.HibernateUtil;
import persistencia.Hotel;
import persistencia.TrabajaEmpleadoHotel;

public class TrabajaEmpleadoHotelDAO extends GenericDAO<TrabajaEmpleadoHotel> {

	/**
	 * 
	 * @param codHotel
	 * @return Returns the list of employees working in a hotel
	 */
	public List<TrabajaEmpleadoHotel> empleadosDeHotel(int codHotel){
		Session session= HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("SELECT t FROM TrabajaEmpleadoHotel t WHERE codHotel=?");
		query.setInteger(0, codHotel);
		List<TrabajaEmpleadoHotel> empleados = query.list();
		
		return empleados;
	}
	/**
	 * 
	 * @param dni
	 * @return Returns the list of hotels where an employee works
	 */
	public List<TrabajaEmpleadoHotel> hotelesTrabajaUnEmpleado(String dni){
		Session session= HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("SELECT t FROM TrabajaEmpleadoHotel t WHERE dni=?");
		query.setString(0, dni);
		List<TrabajaEmpleadoHotel> hoteles = query.list();
		
		return hoteles;
	}
	/**
	 * Method that checks if an employee works in a hotel
	 * @param codHotel
	 * @param dni
	 * @return
	 */
	public TrabajaEmpleadoHotel empleadoEnHotel(int codHotel,String dni) {
		TrabajaEmpleadoHotel union=null;
		Session session= HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("SELECT t FROM TrabajaEmpleadoHotel t WHERE dni=? AND codHotel=?");
		query.setString(0, dni);
		query.setInteger(1, codHotel);
		List<TrabajaEmpleadoHotel> trabaja=query.list();
		if (trabaja.size()>0) {
			for (TrabajaEmpleadoHotel trabajaEmpleadoHotel : trabaja) {
				union=trabajaEmpleadoHotel;
			}
		}
		return union;
	}
	
}
