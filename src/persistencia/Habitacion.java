package persistencia;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="habitacion")
public class Habitacion implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="codHabitacion")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int codHabitacion;
	@Enumerated(EnumType.STRING)
	@Column(name="tipoHabitacion")
	TipoHabitacion tipoHabitacion;
	@ManyToOne
	@JoinColumn(name="codHotel")
	private Hotel hotel;

	@Override
	public String toString() {
		return "Room with id: " + codHabitacion + ", type of room: " + tipoHabitacion + " from the hotel with id: " + hotel.getCodHotel()+" and name: "+hotel.getNombreHotel()
				+ "]";
	}
	public Habitacion() {}
	public Habitacion(Hotel hotel,TipoHabitacion tipoHabitacion) {
		this.hotel=hotel;
		this.tipoHabitacion=tipoHabitacion;
	}
	public int getCodHabitacion() {
		return codHabitacion;
	}
	public void setCodHabitacion(int codHabitacion) {
		this.codHabitacion = codHabitacion;
	}
	public Enum<TipoHabitacion> getTipoHabitacion() {
		return tipoHabitacion;
	}
	public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
}
