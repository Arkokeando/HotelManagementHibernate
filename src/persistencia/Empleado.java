package persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="empleado")
public class Empleado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="dni")
	private String dni;
	@Column(name="nombreEmpleado")
	private String nombreEmpleado;
	
	@OneToMany(cascade = CascadeType.REMOVE,orphanRemoval = true)
	@JoinColumn(name="dni")
	List<TrabajaEmpleadoHotel> listaHotelesDeEmpleado;
	
	//Constructors
	public Empleado() {
	}
	
	public Empleado(String dni, String nombreEmpleado) {
		super();
		this.dni = dni;
		this.nombreEmpleado = nombreEmpleado;
		listaHotelesDeEmpleado=new ArrayList<TrabajaEmpleadoHotel>();
	}

	//Getters, Setters, toString
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombreEmpleado() {
		return nombreEmpleado;
	}
	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}
	@Override
	public String toString() {
		return "Empleado with ID: " + dni + " and name: " + nombreEmpleado + ".";
	}
	
	
}
