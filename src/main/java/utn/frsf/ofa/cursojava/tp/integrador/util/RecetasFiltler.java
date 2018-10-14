package utn.frsf.ofa.cursojava.tp.integrador.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import utn.frsf.ofa.cursojava.tp.integrador.modelo.Autor;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Ingrediente;

public class RecetasFiltler {
	private Double precioMinimoAFiltrar, precioMaximoAFiltrar;
    private Date fechaInicialAFiltrar, fechaFinalAFiltrar;
    private Autor autorAFiltrar;
    private List<Ingrediente> listaIngredientesAFiltrar;
    
    public RecetasFiltler() {
    	listaIngredientesAFiltrar = new ArrayList<>();
        precioMinimoAFiltrar = 0.0;
        precioMaximoAFiltrar = 0.0;
    }
    
	public Double getPrecioMinimoAFiltrar() {
		return precioMinimoAFiltrar;
	}
	public void setPrecioMinimoAFiltrar(Double precioMinimoAFiltrar) {
		this.precioMinimoAFiltrar = precioMinimoAFiltrar;
	}
	public Double getPrecioMaximoAFiltrar() {
		return precioMaximoAFiltrar;
	}
	public void setPrecioMaximoAFiltrar(Double precioMaximoAFiltrar) {
		this.precioMaximoAFiltrar = precioMaximoAFiltrar;
	}
	public Date getFechaInicialAFiltrar() {
		return fechaInicialAFiltrar;
	}
	public void setFechaInicialAFiltrar(Date fechaInicialAFiltrar) {
		this.fechaInicialAFiltrar = fechaInicialAFiltrar;
	}
	public Date getFechaFinalAFiltrar() {
		return fechaFinalAFiltrar;
	}
	public void setFechaFinalAFiltrar(Date fechaFinalAFiltrar) {
		this.fechaFinalAFiltrar = fechaFinalAFiltrar;
	}
	public Autor getAutorAFiltrar() {
		return autorAFiltrar;
	}
	public void setAutorAFiltrar(Autor autorAFiltrar) {
		this.autorAFiltrar = autorAFiltrar;
	}
	public List<Ingrediente> getListaIngredientesAFiltrar() {
		return listaIngredientesAFiltrar;
	}
	public void setListaIngredientesAFiltrar(List<Ingrediente> listaIngredientesAFiltrar) {
		this.listaIngredientesAFiltrar = listaIngredientesAFiltrar;
	}
    
    
}
