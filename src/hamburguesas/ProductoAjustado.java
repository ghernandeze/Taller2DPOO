package hamburguesas;

import java.util.ArrayList;

public class ProductoAjustado implements Producto {
    private ProductoMenu base;
    private ArrayList<Ingrediente> agregados;
    private ArrayList<Ingrediente> eliminados;

    public ProductoAjustado(ProductoMenu base, ArrayList<Ingrediente> eliminados, ArrayList<Ingrediente> agregados) {
    	this.agregados = agregados;
		this.eliminados = eliminados;
    	this.base = base;
    }

    public void agregarAgregado(Ingrediente ingredienteAgredo) {
        agregados.add(ingredienteAgredo);
    }

    public void agregarEliminado(Ingrediente ingredienteEliminado) {
        eliminados.add(ingredienteEliminado);
    }

    
    public ArrayList<Ingrediente> getAgregado() {
        return agregados;
    }

    public ArrayList<Ingrediente> getEliminado() {
        return eliminados;
    }
    
    public int getPrecio() {
        int precioTotal = base.getPrecio();
        for (Ingrediente agregado : agregados) {
            precioTotal += agregado.getCostoAdicional();
        }
        for (Ingrediente eliminado : eliminados) {
            precioTotal -= eliminado.getCostoAdicional();
        }
        return precioTotal;
    }
    
    public String getNombre() {
        return base.getNombre();
    }
    
    public String generarTextoFactura(){	{
		String recibo = base.generarTextoFactura();
		if (agregados.size() > 0)
		{
			recibo += "\nadicion de: ";
			for ( Ingrediente ingrediente : agregados){
				recibo += "\n" + ingrediente.getNombre() + "          " + String.valueOf(ingrediente.getCostoAdicional());	
			}
		}
		if (eliminados.size() > 0){
			recibo += "\nsin: ";
			for ( Ingrediente ingrediente : eliminados){
				recibo += "\n" + ingrediente.getNombre();
			}
		}
		return recibo;
		}
    }
    

    
}