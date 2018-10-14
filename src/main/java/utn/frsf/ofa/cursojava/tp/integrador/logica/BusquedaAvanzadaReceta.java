package utn.frsf.ofa.cursojava.tp.integrador.logica;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import utn.frsf.ofa.cursojava.tp.integrador.modelo.*;

@ApplicationScoped
@Transactional
public interface BusquedaAvanzadaReceta {
	
	public List<Receta> precioMinimo(List<Receta> lista, Double precio);
	public List<Receta> precioMaximo(List<Receta> lista, Double precio);
	public List<Receta> fechaInicial(List<Receta> lista, Date fecha);
	public List<Receta> fechaFinal(List<Receta> lista, Date fecha);
	public List<Receta> autor(List<Receta> lista, Autor autor);
	public List<Receta> ingrediente(List<Receta> lista, List<Ingrediente> ingrediente);
	
	
}
