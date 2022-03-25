package modelo;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;

import dao.EmpleadoDAO;
import dao.HabitacionDAO;
import dao.HotelDAO;
import dao.TrabajaEmpleadoHotelDAO;
import persistencia.Empleado;
import persistencia.Habitacion;
import persistencia.HibernateUtil;
import persistencia.Hotel;
import persistencia.TipoHabitacion;
import persistencia.TrabajaEmpleadoHotel;

public class Principal {

	private static Scanner teclado = new Scanner(System.in);
	private static Session session;
	private static EmpleadoDAO daoEmpleado = new EmpleadoDAO();
	private static HabitacionDAO daoHabitacion = new HabitacionDAO();
	private static HotelDAO daoHotel = new HotelDAO();
	private static TrabajaEmpleadoHotelDAO daoTEH = new TrabajaEmpleadoHotelDAO();

	public static void main(String[] args) {

		try {
			int opcion=3;
			configurarSesion();
			do {
				
				try {
					menu();
					opcion = Integer.parseInt(teclado.nextLine());
					tratarMenu(opcion);
				}catch(NumberFormatException e) {
					System.out.println("Enter an integer.");
				}
				

			} while (opcion != 0);
		} catch (NumberFormatException ex) {
			System.out.println("Error: " + ex.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cerrarSesion();

	}

	public static void menu() {

		System.out.println("EMPLOYEE-HOTEL MANAGEMENT SYSTEM");
		System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
		System.out.println("0. Leave");
		System.out.println("1. Employee Menu");
		System.out.println("2. Hotel Menu");

	}

	private static void tratarMenu(int opcion) throws SQLException {
		switch (opcion) {
		case 0:
			// SALIR

			break;
		case 1:
			submenuEmpleado();
			tratarSubmenuEmpleado(opcion);
			break;
		case 2:
			submenuHotel();
			tratarSubmenuHotel(opcion);
			break;
		}

	}

	public static void submenuEmpleado() {
		System.out.println("EMPLOYEES");
		System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
		System.out.println("0. Leave.");
		System.out.println("1. List of employees.");
		System.out.println("2. List of hotels where an emplooyee works.");
		System.out.println("3. Insert employee.");
		System.out.println("4. Delete employee.");
		System.out.println("5. Add hotel where an employee works.");
		System.out.println("6. Delete hotel where an employee works.");
	}

	private static void tratarSubmenuEmpleado(int opcion) throws SQLException {
		do {
			opcion = solicitarEntero("Enter the number of the desired option: ");
			switch (opcion) {
			case 1:
				mostrarEmpleados();
				submenuEmpleado();
				break;
			case 2:
				listarHotelesDeEmpleado();
				submenuEmpleado();
				break;
			case 3:
				insertarEmpleado();
				submenuEmpleado();
				break;
			case 4:
				borrarEmpleado();
				submenuEmpleado();
				break;
			case 5:
				addHotelesDeEmpleado();
				submenuEmpleado();
				break;
			case 6:
				borrarHotelesDeEmpleado();
				submenuEmpleado();
				break;
			default:
				submenuEmpleado();
			}
		} while (opcion != 0);
	}

	/**
	 * Method that deletes a hotel in which an employee works
	 * 
	 */
	private static void borrarHotelesDeEmpleado() {
		int codHotel;
		Hotel h;
		String dni;
		Empleado e;
		dni = solicitarCadena("Enter the employee's ID:");
		e = daoEmpleado.findByPk(dni);
		if (e == null) {
			System.out.println("The entered employee does not exist.");
		} else {
			codHotel = solicitarEntero("Enter the hotel ID:");
			h = daoHotel.findByPk(codHotel);
			if (h == null) {
				System.out.println("The entered hotel does not exist.");
			} else {
				TrabajaEmpleadoHotel tra = null;
				tra = daoTEH.empleadoEnHotel(codHotel, dni);
				if (tra == null) {
					System.out.println("That worker does not work in that hotel.");
				} else {
					daoTEH.borrar(tra);
					System.out.println("Deleted successfully.");
				}
			}
		}

	}
/**
 * Method that adds a hotel where an Employee works
 */
	private static void addHotelesDeEmpleado() {
		int codHotel;
		Hotel h;
		String dni;
		Empleado e;
		dni = solicitarCadena("Enter the employee's ID:");
		e = daoEmpleado.findByPk(dni);
		if (e == null) {
			System.out.println("El empleado introducido no existe.");
		} else {
			codHotel = solicitarEntero("The entered employee does not exist:");
			h = daoHotel.findByPk(codHotel);
			if (h == null) {
				System.out.println("The entered hotel does not exist.");
			} else {
				TrabajaEmpleadoHotel tra = null;
				tra = daoTEH.empleadoEnHotel(codHotel, dni);
				if (tra == null) {
					TrabajaEmpleadoHotel teh = new TrabajaEmpleadoHotel(e, h);
					daoTEH.guardar(teh);
					System.out.println("Added successfully.");
				} else {
					System.out.println("That employee already works in that hotel.");
				}

			}
		}

	}
/**
 * Method showing the hotels where an employee works
 */
	private static void listarHotelesDeEmpleado() {
		String dni;
		Empleado e;
		dni = solicitarCadena("Enter the employee's ID:");
		e = daoEmpleado.findByPk(dni);
		if (e == null) {
			System.out.println("The entered ID does not match any of the database.");
		} else {
			List<TrabajaEmpleadoHotel> hoteles = daoTEH.hotelesTrabajaUnEmpleado(dni);
			if (hoteles.isEmpty()) {
				System.out.println("This employee has no hotel assigned.");
			}
			for (TrabajaEmpleadoHotel trabajaEmpleadoHotel : hoteles) {
				Hotel h;
				h = trabajaEmpleadoHotel.getHotel();
				System.out.println(h.toString());
			}
		}
	}
/**
 * Method that deletes an employee from the DB
 */
	private static void borrarEmpleado() {
		String dni;
		Empleado e;
		dni = solicitarCadena("Enter the employee's ID:");
		e = daoEmpleado.findByPk(dni);
		if (e == null) {
			System.out.println("The employee you are trying to delete does not exist.");
		} else {
			daoEmpleado.borrar(e);
			System.out.println("Employee deleted successfully.");
		}

	}
/**
 * Method that adds an employee to the DB
 */
	private static void insertarEmpleado() {
		String dni, nombreEmpleado;
		Empleado e;
		dni = solicitarCadena("Enter the employee's ID:");
		e = daoEmpleado.findByPk(dni);
		if (e == null) {
			do {
				nombreEmpleado = solicitarCadena("Enter the name of the employee:");
			} while (nombreEmpleado.trim() == null);
			Empleado empleado = new Empleado(dni, nombreEmpleado);
			daoEmpleado.guardar(empleado);
			System.out.println("Employee created successfully.");
		} else {
			System.out.println("The entered ID already exists in the Database.");
		}

	}

	/**
	 * Method showing all registered employees
	 */
	private static void mostrarEmpleados() {
		List<Empleado> empleados = daoEmpleado.mostrarEmpleados();
		for (Empleado empleado : empleados) {
			System.out.println(empleado.toString());
		}
	}

	private static void tratarSubmenuHotel(int opcion) throws SQLException {
		do {
			opcion = solicitarEntero("Enter the number of the desired option: ");
			switch (opcion) {
			case 1:
				listarHoteles();
				submenuHotel();
				break;
			case 2:
				listarHabitacionesDeUnHotel();
				submenuHotel();
				break;
			case 3:
				listarTipoHabDeHotel();
				submenuHotel();
				break;
			case 4:
				insertarHotel();
				submenuHotel();
				break;
			case 5:
				insertarHabitacion();
				submenuHotel();
				break;
			case 6:
				borrarHotelesDeEmpleado();
				submenuHotel();
				break;
			case 7:
				borrarHotel();
				submenuHotel();
				break;
			case 8:
				borrarHabitacion();
				submenuHotel();
				break;
			case 9:
				modificarTipoHabitacion();
				submenuHotel();
				break;
			case 10:
				listarEmpleadosDeHotel();
				submenuHotel();
				break;
			}
		} while (opcion != 0);
	}
/**
 * Method that shows all the employees that a hotel has
 */
	private static void listarEmpleadosDeHotel() {
		int codHotel;
		Hotel h;
		codHotel = solicitarEntero("Enter the hotel ID:");
		h = daoHotel.findByPk(codHotel);
		if (h == null) {
			System.out.println("The entered hotel does not exist.");
		} else {

			List<TrabajaEmpleadoHotel> empleados = daoTEH.empleadosDeHotel(codHotel);
			if (empleados.isEmpty()) {
				System.out.println("This hotel has no registered employees.");
			} else {
				for (TrabajaEmpleadoHotel trabajaEmpleadoHotel : empleados) {
					Empleado e;
					e = trabajaEmpleadoHotel.getEmpleado();
					System.out.println(e.toString());
				}
			}
		}

	}
/**
 * Method that deletes a hotel room
 */
	private static void borrarHabitacion() {
		int codHotel, codHabitacion;
		Habitacion h;
		codHotel = solicitarEntero("Enter the hotel ID:");
		codHabitacion = solicitarEntero("Enter the room ID:");
		h = daoHabitacion.findByPkYcodHotel(codHotel, codHabitacion);
		if (h == null) {
			System.out.println("There is no room with ID " + codHabitacion
					+ " in the hotel with ID " + codHotel + ".");
		} else {
			daoHabitacion.borrar(h);
			System.out.println("Room deleted successfully.");
		}

	}
/**
 * Method that deletes a hotel
 */
	private static void borrarHotel() {
		int codHotel;
		Hotel h;
		codHotel = solicitarEntero("Enter the hotel ID:");
		h = daoHotel.findByPk(codHotel);
		if (h == null) {
			System.out.println("The hotel you are trying to delete does not exist.");
		} else {
			daoHotel.borrar(h);
			System.out.println("Hotel deleted successfully.");
		}

	}
/**
 * Method that inserts a room in the DB
 */
	private static void insertarHabitacion() {
		int codHotel;
		String cadenaTipoHab;
		TipoHabitacion tipoHab = null;
		boolean hayError;
		Hotel h;

		codHotel = solicitarEntero("Enter the ID of the hotel where the room belongs:");
		h = daoHotel.findByPk(codHotel);
		if (h == null) {
			System.out.println("The entered hotel does not exist.");
		} else {
			do {
				System.out.println("What type of room is it? " + Arrays.toString(TipoHabitacion.values()));
				cadenaTipoHab = teclado.nextLine().toUpperCase();
				try {
					tipoHab = TipoHabitacion.valueOf(cadenaTipoHab);
					hayError = false;
				} catch (IllegalArgumentException e) {
					hayError = true;
				}
			} while (hayError);
			Habitacion habitacion = new Habitacion(h, tipoHab);
			daoHabitacion.guardar(habitacion);
			System.out.println("New room created successfully.");
		}

	}
/**
 * Method that inserts a hotel in the DB
 */
	private static void insertarHotel() {
		String nombreHotel;
		do {
			nombreHotel = solicitarCadena("Enter the name of the new hotel:");
		} while (nombreHotel.trim() == null);
		Hotel h = new Hotel(nombreHotel);
		daoHotel.guardar(h);
		System.out.println("New Hotel " + h.getNombreHotel() + " created successfully.");
	}
/**
 * Method that displays the types of rooms in a hotel
 */
	private static void listarTipoHabDeHotel() {
		int codHotel;
		Hotel h = null;
		codHotel = solicitarEntero("Enter the hotel ID: ");
		h = daoHotel.findByPk(codHotel);
		if (h == null) {
			System.out.println("The entered hotel does not exist.");
		} else {
			List<Habitacion> habitaciones = daoHabitacion.habitacionesDeHotel(codHotel);
			if (habitaciones.isEmpty()) {
				System.out.println("This hotel has no rooms registered.");
			} else {
				List<TipoHabitacion> tipos = daoHabitacion.habitacionesTipoDeHotel(codHotel);
				for (TipoHabitacion th : tipos) {
					System.out.println(th);
				}
			}
		}

	}
/**
 * Method that shows all the rooms that a hotel has
 */
	private static void listarHabitacionesDeUnHotel() {
		int codHotel;
		Hotel h;
		List<Habitacion> habitaciones;
		codHotel = solicitarEntero("Enter the hotel ID:");
		h = daoHotel.findByPk(codHotel);
		if (h == null) {
			System.out.println("The entered hotel does not exist.");
		} else {
			habitaciones = daoHabitacion.habitacionesDeHotel(codHotel);
			if (habitaciones.isEmpty()) {
				System.out.println("This hotel has no rooms registered.");
			} else {
				for (Habitacion habitacion : habitaciones) {
					System.out.println(habitacion.toString());
				}
			}
		}

	}
/**
 * Method showing all hotels
 */
	private static void listarHoteles() {
		List<Hotel> hoteles = daoHotel.mostrarHoteles();
		for (Hotel hotel : hoteles) {
			System.out.println(hotel.toString());
		}

	}
/**
 * Method that modifies the room type of a room
 */
	private static void modificarTipoHabitacion() {
		int codHotel, codHabitacion;
		String cadenaTipoHab;
		TipoHabitacion tipoHab = null;
		boolean hayError;
		Hotel hotel;
		Habitacion habitacion;
		codHotel = solicitarEntero("Enter the hotel ID:");
		hotel = daoHotel.findByPk(codHotel);
		if (hotel == null) {
			System.out.println("There is no hotel with the ID: " + codHotel);
		} else {
			codHabitacion = solicitarEntero("Enter the room ID:");
			habitacion = daoHabitacion.findByPkYcodHotel(codHotel, codHabitacion);
			if (habitacion == null) {
				System.out.println("There is no room with ID " + codHabitacion + " in the hotel with ID " + codHotel+".");
			} else {
				do {
					System.out.println("What type of room is it?" + Arrays.toString(TipoHabitacion.values()));
					cadenaTipoHab = teclado.nextLine().toUpperCase();
					try {
						tipoHab = TipoHabitacion.valueOf(cadenaTipoHab);
						hayError = false;
					} catch (IllegalArgumentException e) {
						hayError = true;
					}
				} while (hayError);
				habitacion.setTipoHabitacion(tipoHab);
				daoHabitacion.actualizar(habitacion);
				System.out.println("Updated room.");
			}

		}
	}

	public static void submenuHotel() {
		System.out.println("HOTEL");
		System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
		System.out.println("00. Leave.");
		System.out.println("01. List of hotels.");
		System.out.println("02. List of rooms of a hotel.");
		System.out.println("03. List types of rooms of a hotel.");
		System.out.println("04. Insert hotel.");
		System.out.println("05. Insert room.");
		System.out.println("06. Delete employee of a hotel.");
		System.out.println("07. Delete hotel.");
		System.out.println("08. Delete a hotel room.");
		System.out.println("09. Modify the type of room.");
		System.out.println("10. List hotel employees.");
	}

	private static void configurarSesion() {
		HibernateUtil.buildSessionFactory();
		HibernateUtil.openSessionAndBindToThread();
		session = HibernateUtil.getSessionFactory().getCurrentSession();

	}

	private static void cerrarSesion() {
		HibernateUtil.closeSessionFactory();
	}
/**
 * Method that requests an integer
 */
	private static int solicitarEntero(String msg) {
		int num;
		do {
			System.out.println(msg);
			num = Integer.parseInt(teclado.nextLine());
			return num;
		} while (num < 0);

	}
/**
 * Method that requests a string
 */
	private static String solicitarCadena(String msg) {
		String cadena;
		do {
			System.out.println(msg);
			cadena = teclado.nextLine();
		} while (cadena.trim().isEmpty());

		return cadena;
	}

}
