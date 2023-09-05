package hamburguesas;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Restaurante {
    private ArrayList<Pedido> pedidos;
    private Pedido pedidoEnCurso;
    private ArrayList<Combo> combos;
    private ArrayList<Ingrediente> ingredientes;
    private ArrayList<ProductoMenu> menuBase;
    private ArrayList<String> factura;

    
    public Restaurante() {
        pedidos = new ArrayList<>();
        combos = new ArrayList<>();
        ingredientes = new ArrayList<>();
        menuBase = new ArrayList<>();
        factura = new ArrayList<String>();
        
        cargarInformacionRestaurante(new File("data/ingredientes.txt"),
                                     new File("data/menu.txt"),
                                     new File("data/combos.txt"));
    }

    public void iniciarPedido(String nombreCliente, String direccionCliente) {
        pedidoEnCurso = new Pedido(nombreCliente, direccionCliente);
        String NuevoID = String.valueOf(1);
        pedidoEnCurso.setIdPedido(NuevoID);
    }

    public void cerrarYGuardarPedido() {
        if (pedidoEnCurso != null) {
            pedidoEnCurso.generarTextoFactura();
            pedidos.add(pedidoEnCurso);
            pedidoEnCurso = null;
        }
    }

    public Pedido getPedidoEnCurso() {
        return pedidoEnCurso;
    }

    public ArrayList<ProductoMenu> getMenuBase() {
        return menuBase;
    }

    public ArrayList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

	public ArrayList<String> getFacturas()
	{
		return factura;
	}
	
	public ArrayList<Combo> getCombos()
	{
		return combos;
	}
	
	public ArrayList<Pedido> getPedidos()
	{
		return pedidos;
	}
	
    
    public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos) {
    	cargarIngredientes(new File("data/ingredientes.txt"));
    	guardarFacturas();
    	cargarMenu(new File("data/menu.txt"));
    	cargarCombos(new File("data/combos.txt"));
    }

    private void cargarIngredientes(File archivoIngredientes) {

        try (BufferedReader br = new BufferedReader(new FileReader(archivoIngredientes))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 2) {
                    String nombre = partes[0].trim();
                    String precioStr = partes[1].trim();
                    try {
                        int precio = Integer.parseInt(precioStr);
                        System.out.println("Nombre: " + nombre + ", Precio: " + precio);
                        ingredientes.add(new Ingrediente(nombre, precio));
                    } catch (NumberFormatException e) {
                        System.out.println("Error al convertir precio a entero: " + precioStr);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void cargarMenu(File archivoMenu) {
    	try (BufferedReader br = new BufferedReader(new FileReader(archivoMenu))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 2) {
                    String nombre = partes[0].trim();
                    int precio = Integer.parseInt(partes[1].trim());
                    menuBase.add(new ProductoMenu(nombre, precio));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarCombos(File archivoCombos) {
        System.out.println("Cargando combos desde: " + archivoCombos.getAbsolutePath());
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCombos))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 5) {
                    String nombre = partes[0].trim();
                    String descuentoStr = partes[1].trim();
                    double descuento = obtenerNumeroDeDescuento(descuentoStr);
                    combos.add(new Combo(nombre, descuento)); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private ArrayList<String> guardarFacturas(){
		File carpetaFacturas = new File(System.getProperty("user.dir")+ "/data/" + "Recibos/");
		ArrayList<String> facturas = new ArrayList<String>(Arrays.asList(carpetaFacturas.list()));
		
		return facturas;
	}
    
    private double obtenerNumeroDeDescuento(String descuentoStr) {
        String numeroStr = descuentoStr.replaceAll("[^0-9.]", "");
        return Double.parseDouble(numeroStr) / 100.0;
    }
   
}
