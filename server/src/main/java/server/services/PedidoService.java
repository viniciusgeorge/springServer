package server.services;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import server.controller.Controller.PedidoCompleto;
import server.model.Listaprodutos;
import server.model.Pedido;
import server.repository.ListaprodutosRepository;
import server.repository.PedidoRepository;



@Service
@Transactional
public class PedidoService {
	@Autowired
    private PedidoRepository repo;
     
	@Autowired
	ListaprodutosRepository repoLista;
	
	     
	 
	    public void save(Pedido pedido) {
	        repo.save(pedido);
	    }
	     
	    public void savePedido(PedidoCompleto pedidoCompleto)
	    {
	    	
	    	
	    	repo.save(pedidoCompleto.pedido);
	    	Pedido pedido = getLastOrderByCliente(pedidoCompleto.pedido.getIdcliente());
	    
	
	    	for (int i = 0; i < pedidoCompleto.lista.length; i++)
	    	{
	    		Listaprodutos lista = new Listaprodutos();
	    		lista.setIdpedido(pedido.getId());
	    		lista.setIdproduto(pedidoCompleto.lista[i].getIdproduto());
	    		lista.setQuantidade(pedidoCompleto.lista[i].getQuantidade());
	    		
	    		repoLista.save(lista);
	    	}
	    	
	    	
	    }
	    
	    
	    
	    public Pedido get(Integer id) {
	        return repo.findById(id).get();
	    }
	     
	    public void delete(Integer id) {
	        repo.deleteById(id);
	    }
	    
	    public List<Pedido> getByCliente(Integer id)
	    {
	    	return repo.findByIdclienteOrderByIdDesc(id);
	    }
	    
	    public Pedido getLastOrderByCliente(Integer id)
	    {
	    	return repo.findTopByIdclienteOrderByIdDesc(id);
	    }
	    
	    
}

