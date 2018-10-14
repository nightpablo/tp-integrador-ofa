package utn.frsf.ofa.cursojava.tp.integrador.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DualListModel;

import utn.frsf.ofa.cursojava.tp.integrador.logica.BusquedaAvanzadaReceta;
import utn.frsf.ofa.cursojava.tp.integrador.logica.BusquedaAvanzadaRecetaMock;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Autor;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Ingrediente;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Receta;
import utn.frsf.ofa.cursojava.tp.integrador.servicio.IngredienteService;
import utn.frsf.ofa.cursojava.tp.integrador.servicio.RecetaService;
import utn.frsf.ofa.cursojava.tp.integrador.util.RecetasFiltler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mdominguez
 */

// TODO: definir anotacion de ambito 
@SessionScoped
@Named("recetaController")
public class RecetaController implements Serializable {

    @Inject
    RecetaService recetaSrv;

    @Inject
    IngredienteService ingredienteSrv;

    private Receta recetaSeleccionada;
    private Autor autorSeleccionado;
    private List<Receta> listaRecetas;
    
    //Para busqueda avanzada
    @Inject BusquedaAvanzadaReceta busquedaAvanzada;
    private List<Receta> listaRecetasFiltradas;
    private RecetasFiltler recetafiltrado;

    private DualListModel<Ingrediente> ingredientesDisponibles;
    
    public Receta getRecetaSeleccionada() {
        return recetaSeleccionada;
    }

    public void setRecetaSeleccionada(Receta recetaSeleccionada) {
        this.recetaSeleccionada = recetaSeleccionada;
        this.recetaSeleccionada .setIngredientes(recetaSrv.ingredientesPorIdReceta(recetaSeleccionada.getId()));
        this.ingredientesDisponibles.setTarget(recetaSeleccionada.getIngredientes());     
        List<Ingrediente> origen = ingredientesDisponibles.getSource();
        origen.removeAll(recetaSeleccionada.getIngredientes());
        ingredientesDisponibles.setSource(origen);
    }

    public List<Receta> getListaRecetas() {
        return listaRecetas;
    }

    public void setListaRecetas(List<Receta> listaRecetas) {
        this.listaRecetas = listaRecetas;
    }

    @PostConstruct
    public void init() {
        this.recetaSeleccionada = null;
        this.listaRecetas = recetaSrv.listar();
        List<Ingrediente> origen = ingredienteSrv.listar();
        List<Ingrediente> destino = new ArrayList<Ingrediente>();
        this.ingredientesDisponibles = new DualListModel<>(origen, destino);  
        
        setListaRecetasFiltradas(new ArrayList<>(listaRecetas));
        recetafiltrado = new RecetasFiltler();
    }

    public DualListModel<Ingrediente> getIngredientesDisponibles() {
        return ingredientesDisponibles;
    }

    public void setIngredientesDisponibles(DualListModel<Ingrediente> ingredientesDisponibles) {
        this.ingredientesDisponibles = ingredientesDisponibles;
    }

    public String guardar() {
        recetaSeleccionada.setIngredientes(this.ingredientesDisponibles.getTarget());
        // TODO completar el metodo guardar
        // setear el autor de la receta seleccionada
        // invocar al metodo qeu guarda la receta
        recetaSeleccionada.setAutor(autorSeleccionado);
        recetaSeleccionada.setDescripcion(recetaSeleccionada.getDescripcion().replaceAll("<p>", ""));
        recetaSeleccionada.setDescripcion(recetaSeleccionada.getDescripcion().replaceAll("</p>", ""));
        Receta tmp = this.recetaSrv.guardar(recetaSeleccionada);
        this.listaRecetas.add(tmp);
        this.recetaSeleccionada = null;
        return null;
    }

    public String nuevo() {
        this.recetaSeleccionada = new Receta();
        
        this.recetaSeleccionada.setIngredientes(new ArrayList<>());
        this.ingredientesDisponibles = new DualListModel<>(ingredienteSrv.listar(), new ArrayList<Ingrediente>());
        return null;
    }

    public Autor getAutorSeleccionado() {
        return autorSeleccionado;
    }

    public void setAutorSeleccionado(Autor autorSeleccionado) {
        this.autorSeleccionado = autorSeleccionado;
    }

	public List<Receta> getListaRecetasFiltradas() {
		return listaRecetasFiltradas;
	}

	public void setListaRecetasFiltradas(List<Receta> listaRecetasFiltradas) {
		this.listaRecetasFiltradas = listaRecetasFiltradas;
	}
    
    
	public String filtrarBusquedaAvanzada() {
		listaRecetasFiltradas = recetaSrv.listar();
		if(!recetafiltrado.getPrecioMinimoAFiltrar().equals(0.0))
			listaRecetasFiltradas = busquedaAvanzada.precioMinimo(listaRecetasFiltradas, recetafiltrado.getPrecioMinimoAFiltrar());
		if(!recetafiltrado.getPrecioMaximoAFiltrar().equals(0.0))
			listaRecetasFiltradas = busquedaAvanzada.precioMaximo(listaRecetasFiltradas, recetafiltrado.getPrecioMaximoAFiltrar());
		if(recetafiltrado.getFechaInicialAFiltrar() !=null)
			listaRecetasFiltradas = busquedaAvanzada.fechaInicial(listaRecetasFiltradas, recetafiltrado.getFechaInicialAFiltrar());
		if(recetafiltrado.getFechaFinalAFiltrar() !=null)
			listaRecetasFiltradas = busquedaAvanzada.fechaFinal(listaRecetasFiltradas, recetafiltrado.getFechaFinalAFiltrar());
		if(recetafiltrado.getAutorAFiltrar() != null)
			listaRecetasFiltradas = busquedaAvanzada.autor(listaRecetasFiltradas, recetafiltrado.getAutorAFiltrar());
		if(!recetafiltrado.getListaIngredientesAFiltrar().isEmpty()) {
			for(Receta i: listaRecetasFiltradas)
				i.setIngredientes(recetaSrv.ingredientesPorIdReceta(i.getId()));
			listaRecetasFiltradas = busquedaAvanzada.ingrediente(listaRecetasFiltradas, recetafiltrado.getListaIngredientesAFiltrar());
		}
		return null;
	}

	public RecetasFiltler getRecetafiltrado() {
		return recetafiltrado;
	}

	public void setRecetafiltrado(RecetasFiltler recetafiltrado) {
		this.recetafiltrado = recetafiltrado;
	}
    
}
