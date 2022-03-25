package persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="hotel")
public class Hotel implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="codHotel")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int codHotel;
	@Column(name="nombreHotel")
	private String nombreHotel;
	
	@OneToMany(mappedBy = "hotel",cascade = CascadeType.REMOVE)
	private List<Habitacion> listaHabitaciones;
	
	@OneToMany(cascade=CascadeType.REMOVE,orphanRemoval = true)
	@JoinColumn(name="codHotel")
	private List<TrabajaEmpleadoHotel> listaEmpleadosDelHotel;
	
	//Constructors
	public Hotel(String nombreHotel) {
		super();
		this.nombreHotel = nombreHotel;
		listaEmpleadosDelHotel=new ArrayList<TrabajaEmpleadoHotel>();
		listaHabitaciones=new ArrayList<Habitacion>();
	}

	public Hotel() {
		super();
	}
	
	//Getters, Setters, toString
	public int getCodHotel() {
		return codHotel;
	}

	public void setCodHotel(int codHotel) {
		this.codHotel = codHotel;
	}

	public String getNombreHotel() {
		return nombreHotel;
	}

	public void setNombreHotel(String nombreHotel) {
		this.nombreHotel = nombreHotel;
	}

	@Override
	public String toString() {
		return "Hotel with id: " + codHotel + " and name: " + nombreHotel + ".";
	}
	
	
	
	
}
