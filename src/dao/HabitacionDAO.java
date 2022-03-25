package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import persistencia.Empleado;
import persistencia.Habitacion;
import persistencia.HibernateUtil;
import persistencia.TipoHabitacion;

public class HabitacionDAO extends GenericDAO<Habitacion> {

	/**
	 * 
	 * @return return all rooms
	 */
	public List<Habitacion> mostrarHabitaciones() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("SELECT h FROM Habitacion h");
		List<Habitacion> habitaciones = query.list();

		return habitaciones;
	}

	/**
	 *  
	 * @param codHotel
	 * @return Returns the types of rooms that a Hotel has
	 */
	public List<TipoHabitacion> habitacionesTipoDeHotel(int codHotel) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session
				.createQuery("SELECT h.tipoHabitacion FROM Habitacion h WHERE codHotel=? GROUP BY tipoHabitacion");
		query.setInteger(0, codHotel);
		List<TipoHabitacion> tHabitaciones = query.list();

		return tHabitaciones;
	}
	
	/**
	 * 
	 * @param codHotel
	 * @return Returns the rooms that a hotel has
	 */
	public List<Habitacion> habitacionesDeHotel(int codHotel){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("SELECT h FROM Habitacion h WHERE codHotel=?");
		query.setInteger(0, codHotel);
		List<Habitacion> habitaciones = query.list();
		return habitaciones;
	}

	/**
	 * Method that searches for a hotel room
	 * @param codHotel
	 * @param codHabitacion
	 * @return null if the room does not belong to the hotel
	 */
	public Habitacion findByPkYcodHotel(int codHotel, int codHabitacion) {
		Habitacion hab=null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("SELECT h FROM Habitacion h WHERE codHotel=? AND codHabitacion=?");
		query.setInteger(0, codHotel);
		query.setInteger(1, codHabitacion);
		List<Habitacion> habitaciones = query.list();
		if (habitaciones.size()>0) {
			for (Habitacion h : habitaciones) {
				hab=h;
			}
		}
		return hab;
	}


}
