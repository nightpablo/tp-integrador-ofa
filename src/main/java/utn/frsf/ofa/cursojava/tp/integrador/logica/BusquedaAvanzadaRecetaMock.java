package utn.frsf.ofa.cursojava.tp.integrador.logica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import utn.frsf.ofa.cursojava.tp.integrador.modelo.Autor;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Ingrediente;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Receta;

@Stateless
public class BusquedaAvanzadaRecetaMock implements BusquedaAvanzadaReceta {

	@Override
	public List<Receta> precioMinimo(List<Receta> lista, Double precio) {
		List<Receta> salida = new ArrayList<Receta>();
		for(Receta i : lista) 
			if(i.getPrecio()>=precio)
				salida.add(i);
		return salida;
	}

	@Override
	public List<Receta> precioMaximo(List<Receta> lista, Double precio) {
		List<Receta> salida = new ArrayList<Receta>();
		for(Receta i : lista) 
			if(i.getPrecio()<=precio)
				salida.add(i);
		return salida;
	}

	@Override
	public List<Receta> fechaInicial(List<Receta> lista, Date fecha) {
		List<Receta> salida = new ArrayList<Receta>();
		for(Receta i : lista) 
			if(i.getFechaCreacion().compareTo(fecha)>=0)
				salida.add(i);
		return salida;
	}

	@Override
	public List<Receta> fechaFinal(List<Receta> lista, Date fecha) {
		List<Receta> salida = new ArrayList<Receta>();
		for(Receta i : lista) 
			if(i.getFechaCreacion().compareTo(fecha)<=0)
				salida.add(i);
		return salida;
	}

	@Override
	public List<Receta> autor(List<Receta> lista, Autor autor) {
		List<Receta> salida = new ArrayList<Receta>();
		for(Receta i : lista) 
			if(i.getAutor().getId()==autor.getId())
				salida.add(i);
		return salida;
	}

	@Override
	public List<Receta> ingrediente(List<Receta> lista, List<Ingrediente> ingrediente) {
		List<Receta> salida = new ArrayList<Receta>();
		for(Receta i : lista) 
			if(enLista(i.getIngredientes(), ingrediente))
				salida.add(i);
		return salida;
	}
	
	private boolean enLista(List<Ingrediente> deLaReceta, List<Ingrediente> delFiltro) {
		for(Ingrediente i: delFiltro)
			if(!deLaReceta.contains(i)) return false;
		
		return true;
	}

}
