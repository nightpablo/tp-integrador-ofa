package utn.frsf.ofa.cursojava.tp.integrador.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import utn.frsf.ofa.cursojava.tp.integrador.logica.BusquedaAvanzadaReceta;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Ingrediente;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Pedido;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Receta;
import utn.frsf.ofa.cursojava.tp.integrador.servicio.PedidoService;
import utn.frsf.ofa.cursojava.tp.integrador.servicio.RecetaService;
import utn.frsf.ofa.cursojava.tp.integrador.util.RecetasFiltler;

@SessionScoped
@Named("pedidoController")
public class PedidoController implements Serializable {

	@Inject 
    PedidoService pedidoSrv;
	
	@Inject
	RecetaService recetaSrv;
	
	private Pedido pedidoSeleccionado;
	private List<Pedido> listaPedidos;
	private String leerRecetasDesdePedido;
	
	@Inject BusquedaAvanzadaReceta busquedaAvanzada;
	private RecetasFiltler recetafiltrado;
	private DualListModel<Receta> recetasDisponibles;
	
	@PostConstruct
	public void init(){
		this.pedidoSeleccionado = new Pedido();
		this.listaPedidos = pedidoSrv.listar();
		this.setRecetasDisponibles(new DualListModel<>(recetaSrv.listar(),new ArrayList<>()));
		recetafiltrado = new RecetasFiltler();
		
	}
	
	
	public PedidoService getPedidoSrv() {
		return pedidoSrv;
	}
	
	public BusquedaAvanzadaReceta getBusquedaAvanzada() {
		return busquedaAvanzada;
	}


	public void setBusquedaAvanzada(BusquedaAvanzadaReceta busquedaAvanzada) {
		this.busquedaAvanzada = busquedaAvanzada;
	}


	public RecetasFiltler getRecetafiltrado() {
		return recetafiltrado;
	}


	public void setRecetafiltrado(RecetasFiltler recetafiltrado) {
		this.recetafiltrado = recetafiltrado;
	}


	public void setPedidoSeleccionado(Pedido pedidoSeleccionado) {
		this.pedidoSeleccionado = pedidoSeleccionado;
	}
	public Pedido getPedidoSeleccionado() {
		this.pedidoSeleccionado.setRecetasPedidas(pedidoSrv.recetasPorIdPedido(pedidoSeleccionado.getIdPedido()));
		return pedidoSeleccionado;
	}
	public List<Pedido> getListaPedidos() {
		return listaPedidos;
	}
	public void setListaPedidos(List<Pedido> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}
	
	public String guardar(){
		this.pedidoSeleccionado.setRecetasPedidas(recetasDisponibles.getTarget());
        Pedido tmp = this.pedidoSrv.guardar(pedidoSeleccionado);
        this.listaPedidos.add(tmp);
        this.pedidoSeleccionado = new Pedido();
        this.setRecetasDisponibles(new DualListModel<>(recetaSrv.listar(),new ArrayList<>()));
        return "listaPedidos";
    }


	public DualListModel<Receta> getRecetasDisponibles() {
		return recetasDisponibles;
	}


	public void setRecetasDisponibles(DualListModel<Receta> recetasDisponibles) {
		this.recetasDisponibles = recetasDisponibles;
	}
	
	public String filtrarBusquedaAvanzada() {
		List<Receta> origen = recetaSrv.listar();
		List<Receta> destino = getRecetasDisponibles().getTarget();
		origen.removeAll(destino);
		this.setRecetasDisponibles(new DualListModel<>(origen,destino));
		if(!recetafiltrado.getPrecioMinimoAFiltrar().equals(0.0))
			recetasDisponibles.setSource(busquedaAvanzada.precioMinimo(recetasDisponibles.getSource(), recetafiltrado.getPrecioMinimoAFiltrar()));
		if(!recetafiltrado.getPrecioMaximoAFiltrar().equals(0.0))
			recetasDisponibles.setSource(busquedaAvanzada.precioMaximo(recetasDisponibles.getSource(), recetafiltrado.getPrecioMaximoAFiltrar()));
		if(recetafiltrado.getFechaInicialAFiltrar() !=null)
			recetasDisponibles.setSource(busquedaAvanzada.fechaInicial(recetasDisponibles.getSource(), recetafiltrado.getFechaInicialAFiltrar()));
		if(recetafiltrado.getFechaFinalAFiltrar() !=null)
			recetasDisponibles.setSource(busquedaAvanzada.fechaFinal(recetasDisponibles.getSource(), recetafiltrado.getFechaFinalAFiltrar()));
		if(recetafiltrado.getAutorAFiltrar() != null)
			recetasDisponibles.setSource(busquedaAvanzada.autor(recetasDisponibles.getSource(), recetafiltrado.getAutorAFiltrar()));
		if(!recetafiltrado.getListaIngredientesAFiltrar().isEmpty()) {
			for(Receta i: recetasDisponibles.getSource())
				i.setIngredientes(recetaSrv.ingredientesPorIdReceta(i.getId()));
			recetasDisponibles.setSource(busquedaAvanzada.ingrediente(recetasDisponibles.getSource(), recetafiltrado.getListaIngredientesAFiltrar()));
		}
		return null;
	}


	public String getLeerRecetasDesdePedido() {
		String salida = "";
		for(Receta j : pedidoSrv.recetasPorIdPedido(pedidoSeleccionado.getIdPedido()))
			salida = j.toString()+"\n";
		leerRecetasDesdePedido = salida;
		return leerRecetasDesdePedido;
	}


	public void setLeerRecetasDesdePedido(String leerRecetasDesdePedido) {
		this.leerRecetasDesdePedido = leerRecetasDesdePedido;
	}
}
