package persistencia;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="trabajaEmpleadoHotel")
public class TrabajaEmpleadoHotel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@ManyToOne
	@JoinColumn(name="dni")
	private Empleado empleado;
	@Id
	@ManyToOne
	@JoinColumn(name="codHotel")
	private Hotel hotel;

	public Empleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	public TrabajaEmpleadoHotel() {}
	public TrabajaEmpleadoHotel(Empleado empleado,Hotel hotel) {
		this.empleado=empleado;
		this.hotel=hotel;
	}
	
}
