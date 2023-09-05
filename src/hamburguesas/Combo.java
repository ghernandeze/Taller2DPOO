package hamburguesas;

import java.util.ArrayList;


public class Combo implements Producto {
    private Double descuento;
	private String nombre;
	private ArrayList<ProductoMenu> itemsCombo;
	
    public Combo(String nombre, Double descuento) {
        this.nombre = nombre;
        this.descuento = descuento;
        this.itemsCombo = new ArrayList<ProductoMenu>();
    }

    public void agregarItemACombo(ProductoMenu itemCombo) {
    	itemsCombo.add(itemCombo);
    }


    public int getPrecio(){
    	int precio = 0;
        for (ProductoMenu producto : itemsCombo) {
            precio += producto.getPrecio()* (1 - descuento);
        }
        return precio;
    }
    
    
    public Double getDescuento() {
        return descuento;
    }


    public String generarTextoFactura()
	{
		String factura = "\n" + nombre;
		for (Producto producto : itemsCombo)
		{
			factura += "\n" + producto.getNombre() + " ".repeat(10) + String.valueOf(producto.getPrecio()* (1 - descuento));
		}
		String precio = String.valueOf(this.getPrecio());
		factura += "\nTotal: " + " ".repeat(10) +  precio;
		
		return factura;
	}


    public String getNombre() {
        return nombre;
    }
    
    public ArrayList<ProductoMenu> getItemsCombo()
	{
		return itemsCombo;
	}
    
}




