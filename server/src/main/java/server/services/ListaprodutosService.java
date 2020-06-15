package server.services;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import server.model.Listaprodutos;
import server.repository.ListaprodutosRepository;

@Service
@Transactional
public class ListaprodutosService {

	
	@Autowired
    private ListaprodutosRepository repo;
     
	 
	     
	 
	    public void save(Listaprodutos lp) {
	        repo.save(lp);
	    }
	     
	    public Listaprodutos get(Integer id) {
	        return repo.findById(id).get();
	    }
	    
	    public List<Listaprodutos> getByIdpedido(Integer idPedido)
	    {
	    	return repo.findByIdpedido(idPedido);
	    }

		public void delete(Integer id) {
			repo.deleteById(id);
			
		}
	   
}
