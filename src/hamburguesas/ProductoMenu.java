package hamburguesas;

public class ProductoMenu implements Producto {
    private String nombre;
    private int precioBase;

    public ProductoMenu(String nombre, int precioBase) {
        this.nombre = nombre;
        this.precioBase = precioBase;
    }

    
    public int getPrecio() {
        return precioBase;
    }

    
    public String getNombre() {
        return this.nombre;
    }

    
    public String generarTextoFactura() {
    	String recibo = " \n" +nombre + "  ".repeat(10)+String.valueOf(precioBase);
		return recibo;
    }
}
