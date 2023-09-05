package hamburguesas;

import java.io.File;
import java.util.ArrayList;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Pedido {
    private  int numeroPedidos = 0;
    private String idPedido;
    private String nombreCliente;
    private String direccionCliente;
    private ArrayList<Producto> itemsPedido;

    public Pedido(String nombreCliente, String direccionCliente) {
        
        this.nombreCliente = nombreCliente;
        this.direccionCliente = direccionCliente;
        itemsPedido = new ArrayList<Producto>();
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido)
	{
		this.idPedido = idPedido;
	}
    
    public void agregarProducto(Producto nuevoItem) {
        itemsPedido.add(nuevoItem);
    }

    public int getPrecioNetoPedido() {
        int precioNeto = 0;
        for (Producto producto : itemsPedido) {
            precioNeto += producto.getPrecio();
        }
        return precioNeto;
    }

	public int getNumeroPedidos()
	{
		return this.numeroPedidos;
	}
    
	public void setNumeroPedidos(int numeroPedidos)
	{
		this.numeroPedidos = numeroPedidos;
	}
	
    public int getPrecioTotalPedido() {
         int Total = (int) (getPrecioNetoPedido() * 1.19);
         return Total;
    }

    public int getPrecioIVApedido() {
        return getPrecioTotalPedido() - getPrecioNetoPedido();
    }

    public String generarTextoFactura() {
        StringBuilder factura = new StringBuilder();
        factura.append("Factura - Pedido #" + idPedido + "\n");
        factura.append("Cliente: " + nombreCliente + "\n");
        factura.append("Dirección: " + direccionCliente + "\n");
        factura.append("Items:\n");

        for (Producto producto : itemsPedido) {
            factura.append(producto.getNombre() + " - $" + producto.getPrecio() + "\n");
        }

        factura.append("Precio Neto: $" + getPrecioNetoPedido() + "\n");
        factura.append("IVA: $" + getPrecioIVApedido() + "\n");
        factura.append("Precio Total: $" + getPrecioTotalPedido() + "\n");

        return factura.toString();
    }

    public ArrayList<Producto> getItemsProducto()
	{
		return this.itemsPedido;
	}
	
	public void setItemsProducto(ArrayList<Producto> itemsProducto)
	{
		this.itemsPedido = itemsProducto;
	}
    
	public void guardarFactura(int opcion)
	{
		try
		{
			String factura = generarTextoFactura();
			File archivo = new File(System.getProperty("user.dir")+ "/data/" + "Recibos/"+ idPedido +".txt");
			if (!archivo.exists())
				archivo.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
			bw.write(factura);
			bw.close();
			
			if (opcion == 1)
			{
				System.out.println(factura);
			}
		}
		catch (Exception err)
		{
		err.printStackTrace();
		System.out.println("Hubo un error guardando el archivo");
		}
	}
	
	public void mostarInfoPedido()
	{
		for (Producto producto : itemsPedido)
		{
			System.out.println(producto.getNombre() + "          " + String.valueOf(producto.getPrecio()));
		}
		int totalNeto = getPrecioNetoPedido();
		double precioIVA = getPrecioIVApedido();
		double precioTotal = getPrecioTotalPedido();
		
		System.out.println("\nTotal Neto: " + totalNeto);
		System.out.println("\nIVA: " + precioIVA);
		System.out.println("\nTOTAL: " + precioTotal);
		
	}
}