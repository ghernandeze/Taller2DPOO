package consola;

import hamburguesas.Ingrediente;
import hamburguesas.Producto;
import hamburguesas.Combo;
import hamburguesas.Pedido;
import hamburguesas.ProductoMenu;
import hamburguesas.ProductoAjustado;
import java.util.ArrayList;
import hamburguesas.Restaurante;
import java.io.File;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Aplicacion {
	public static void main(String[] args){
		Aplicacion consola = new Aplicacion();
		consola.ejecutarAplicacion();
	}
	
	public void mostrarMenu(){
		System.out.println("\nOpciones de la aplicación\n");
		System.out.println("1. Mostrar el menu ");
		System.out.println("2. Iniciar un nuevo pedido ");
		System.out.println("3. Agregar un elemento a un pedido ");
		System.out.println("4. Cerrar un pedido y guardar la factura ");
		System.out.println("5. Consultar informacion de un pedido segun el ID");
		System.out.println("6. Salir");
	}
	
	public void ejecutarAplicacion(){
		Restaurante restaurante = new Restaurante();
		cargarInformacionRestaurante(restaurante);
		boolean continuar = true;
		while (continuar)
		{
			try{
				mostrarMenu();
				int opcionSeleccionada = Integer.parseInt(input("Seleccione una opcion"));
				if (opcionSeleccionada == 1)
					mostrarMenuProductos(restaurante);
				else if (opcionSeleccionada == 2 )
					ejecutarInicarNuevoPedido(restaurante);
				else if (opcionSeleccionada == 3 )
					ejecutarAgregarProducto(restaurante);
				else if (opcionSeleccionada == 4 )
					ejecutarguardarCerrarOrden(restaurante);
				else if (opcionSeleccionada == 5 )
					ejecutarConsultarPorID(restaurante);
				else if (opcionSeleccionada == 6 )
				{
					System.out.println("Saliendo de la aplicacion ...");
					continuar = false;
				}
				else
				{
					System.out.println("Por favor seleccione una opción válida.");
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println("Debe seleccionar uno de los números de las opciones.");
			}
		}		
	}
	
	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}
	
	private HashMap<Integer, ProductoMenu> mostrarMenuProductos(Restaurante restaurante)
	{
		ArrayList<ProductoMenu> listaProductos = restaurante.getMenuBase();
		HashMap<Integer, ProductoMenu> numeroProducto = new HashMap<Integer, ProductoMenu>();
		
		int numero = 1;
		for (ProductoMenu producto : listaProductos){
			System.out.println("\n" + String.valueOf(numero) + ") " + "Producto : " + producto.getNombre());
			System.out.println("   Precio: " + producto.getPrecio());
			numeroProducto.put(numero, producto);
			numero += 1;
		}
		return numeroProducto;
	}
	
	private HashMap<Integer, Combo> mostrarMenuCombos(Restaurante restaurante){
		ArrayList<Combo> listaCombos = restaurante.getCombos();
		HashMap<Integer, Combo> numeroCombo = new HashMap<Integer, Combo>();
		int numero = 1;
		for (Combo combo : listaCombos){
			System.out.println("\n" + String.valueOf(numero) + ") " + "Producto : " + combo.getNombre());
			System.out.println("   Precio: " + combo.getPrecio());
			numeroCombo.put(numero, combo);
			numero += 1;
		}
		return numeroCombo;
	}
	
	private HashMap<Integer, Ingrediente> mostrarMenuIngredientes(Restaurante restaurante){
		ArrayList<Ingrediente> listaIngredientes = restaurante.getIngredientes();
		HashMap<Integer, Ingrediente> numeroIngrediente = new HashMap<Integer, Ingrediente>();
		int num = 1;
		for (Ingrediente producto : listaIngredientes)
		{
			System.out.println("\n" + String.valueOf(num) + ") " + "Producto: " + producto.getNombre());
			System.out.println("   Precio: " + producto.getCostoAdicional());
			numeroIngrediente.put(num, producto);
			num += 1;
		}
		return numeroIngrediente;
	}
	
	private void ejecutarAgregarProducto(Restaurante restaurante)
	{
		System.out.println("\nEscoja el menu que desea llevar: \n");
		System.out.println("1. Menu de productos");
		System.out.println("2. Menu de combos");
		int opcionMenu = Integer.parseInt(input("\nseleccione una opcion"));
		
		if (opcionMenu == 1){
			HashMap<Integer, ProductoMenu> mapaP = mostrarMenuProductos(restaurante);
			int opcion = Integer.parseInt(input("Por favor seleccione una opción"));
			for(Integer numero :  mapaP.keySet() )
			{	
				boolean variable = true;

				if (numero.equals(Integer.valueOf((opcion))))
				{
					Pedido pedidoActual = restaurante.getPedidoEnCurso();
					pedidoActual.agregarProducto(mapaP.get(numero));
					
					ArrayList<Ingrediente> ingredientesAgregados = new ArrayList<Ingrediente>();
					ArrayList<Ingrediente> ingredientesEliminados = new ArrayList<Ingrediente>();
					ProductoMenu productoBase = mapaP.get(numero);
					ProductoAjustado nuevoProductoAjustado = new ProductoAjustado(productoBase, ingredientesAgregados, ingredientesEliminados);
					
					
					while (variable){
						System.out.println("Quiere agregar otro producto a su pedido?");
						System.out.println("1. Si");
						System.out.println("2. No");
						int opcionCambio = Integer.parseInt(input("Por favor seleccione una opcion"));
						
						if(opcionCambio == 1){	
							System.out.println("1. Deseo añadirle algo a mi pedido");
							System.out.println("2. Deseo eliminar algo de mi pedido");
							int opcion2 = Integer.parseInt(input("Por favor seleccione una opción"));
							if(opcion2 == 1)
							{
								agregarEliminarProductos(restaurante, productoBase, nuevoProductoAjustado, "añadir");
								pedidoActual.agregarProducto(nuevoProductoAjustado);
							}
							else if(opcion2 == 2)
							{
								agregarEliminarProductos(restaurante, productoBase, nuevoProductoAjustado, "eliminar");
							}
							else{
								System.out.println("Seleccione una opción válida");
							}
						}
						else if(opcionCambio == 2){
							variable = false;
						}
						else{
							System.out.println("Seleccione una opción válida");
						}
					}
				}
				if (mapaP.containsKey(numero) == false){
					System.out.println("Seleccione una opción válida");
				}
			}
		}	
		else if (opcionMenu == 2){
			
			HashMap<Integer, Combo> mapaCombo = mostrarMenuCombos(restaurante);
			int opcion_2 = Integer.parseInt(input("Por favor seleccione una opción"));
			
			for(Integer valorDos :  mapaCombo.keySet())
			{
				if (mapaCombo.containsKey(valorDos) == false){
					System.out.println("Seleccione una opción válida");
				}
				if (valorDos == Integer.valueOf(opcion_2)){
					Pedido pedidoActual = restaurante.getPedidoEnCurso();
					pedidoActual.agregarProducto(mapaCombo.get(valorDos));
				}
			}
		}
		else{
			System.out.println("Seleccione una opción válida");
		}
	}
	
	private void ejecutarInicarNuevoPedido(Restaurante restaurante){
		String nombre = input("Nombre");
		String direccion = input("Direccion");
		restaurante.iniciarPedido(nombre, direccion);		
	}
		
	private void agregarEliminarProductos(Restaurante restaurante, Producto productoBase, ProductoAjustado productoAjustado, String opcion){
		HashMap<Integer, Ingrediente> mapaIngrediente = mostrarMenuIngredientes(restaurante);
		int opcionMenu = Integer.parseInt(input("Seleccione el ingrediente que desea " + opcion));
		
		for(Integer numero :  mapaIngrediente.keySet()){
			if (numero == Integer.valueOf(opcionMenu)){
				if (opcion == "Añadir"){
					productoAjustado.agregarAgregado(mapaIngrediente.get(numero));
				}
				if (opcion == "Eliminar"){
					productoAjustado.agregarEliminado(mapaIngrediente.get(numero));
				}
			}
			else if (mapaIngrediente.containsKey(numero) == false){
				System.out.println("Seleccione una opción valida");
			}
		}
	}
	
	private void ejecutarguardarCerrarOrden(Restaurante restaurante){
		System.out.println("Imprimir recibo?");
		System.out.println("1. Si");
		System.out.println("2. No");
		int opcion = Integer.parseInt(input("Seleccione una opción: "));
		
		Pedido pedidoActual = restaurante.getPedidoEnCurso();
		pedidoActual.guardarFactura(opcion);
		}
	
	private void ejecutarConsultarPorID(Restaurante restaurante){
		ArrayList<String> facturas = restaurante.getFacturas();
		System.out.println("Digite el ID que desea buscar");
		String id = input("ID de pedido: ");
		ArrayList<Pedido> pedidos = restaurante.getPedidos();
		
		if (facturas.contains(id)){
			for (Pedido pedido : pedidos)
			{
				if (pedido.getIdPedido() == id){
					pedido.mostarInfoPedido();
				}
			}
		}
		else{
			System.out.println("El ID ingresado no existe");
		}
	}
	
	public void cargarInformacionRestaurante(Restaurante restaurante){
		
		File ingredientes = new File("C:\\Users\\guill\\OneDrive\\Documentos\\3er Semestre\\dpo\\Workspace\\hamburguesas\\hamburguesas\\data\\ingredientes.txt");
		File menu = new File("C:\\Users\\guill\\OneDrive\\Documentos\\3er Semestre\\dpo\\Workspace\\hamburguesas\\hamburguesas\\data\\menu.txt");
		File combos = new File("C:\\Users\\guill\\OneDrive\\Documentos\\3er Semestre\\dpo\\Workspace\\hamburguesas\\hamburguesas\\data\\combos.txt");
		restaurante.cargarInformacionRestaurante(ingredientes, menu, combos);
	}
}