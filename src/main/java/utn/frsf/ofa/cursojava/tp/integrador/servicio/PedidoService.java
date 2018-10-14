package utn.frsf.ofa.cursojava.tp.integrador.servicio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import utn.frsf.ofa.cursojava.tp.integrador.modelo.Pedido;
import utn.frsf.ofa.cursojava.tp.integrador.modelo.Receta;

@Stateless
public class PedidoService {
	@PersistenceContext(unitName = "RECETAS_PU")
    private EntityManager em;
	
	public Pedido guardar(Pedido a){
        if(a.getIdPedido()!=null && a.getIdPedido()>0) {
            return em.merge(a);
        }
        em.persist(a);
        em.flush();
        em.refresh(a);
        return a;
    }
	
	public List<Pedido> listar(){
		return em.createQuery("SELECT a FROM Pedido a").getResultList();
	}
	
	public List<Receta> recetasPorIdPedido(Integer id){
        return em.createQuery("SELECT i FROM Pedido p JOIN p.recetas i WHERE p.idPedido = :P_ID_PEDIDO")
                .setParameter("P_ID_PEDIDO", id)
                .getResultList();
    }
}
