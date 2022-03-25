package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import persistencia.Hotel;
import persistencia.Empleado;
import persistencia.HibernateUtil;

public class HotelDAO extends GenericDAO<Hotel>{
	// Show all hotels
	public List<Hotel> mostrarHoteles(){
		Session session= HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("SELECT h FROM Hotel h");
		List<Hotel> hoteles = query.list();
		
		return hoteles;
	}
	// Returns the hotel with the requested codHotel
	public Hotel findByPk(int codHotel) {
		Hotel hot=null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("SELECT h FROM Hotel h WHERE codHotel=?");
		query.setInteger(0, codHotel);
		List<Hotel> hoteles=query.list();
		if (hoteles.size()>0) {
			for (Hotel h : hoteles) {
				hot=h;
			}
		}
		
		return hot;
	}

}
