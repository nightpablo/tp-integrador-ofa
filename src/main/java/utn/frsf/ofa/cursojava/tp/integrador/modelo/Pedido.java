package utn.frsf.ofa.cursojava.tp.integrador.modelo;


import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido;
    @Temporal(TemporalType.DATE)
    private Date fechaPedido;
    private String direccionEnvio;
    
    @ManyToMany
	@JoinTable(inverseJoinColumns = @JoinColumn(name = "receta_id", referencedColumnName = "id"))
    private List<Receta> recetas;
    
    
	public Integer getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}
	public Date getFechaPedido() {
		return fechaPedido;
	}
	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}
	public String getDireccionEnvio() {
		return direccionEnvio;
	}
	public void setDireccionEnvio(String direccionEnvio) {
		this.direccionEnvio = direccionEnvio;
	}
	public List<Receta> getRecetasPedidas() {
		return recetas;
	}
	public void setRecetasPedidas(List<Receta> recetas) {
		this.recetas = recetas;
	}
    
    @Override
    public String toString() {
    	return direccionEnvio+";"+fechaPedido;
    }
    
    
}
