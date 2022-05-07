package server.services;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import server.model.Estabelecimento;
import server.repository.EstabelecimentoRepository;

@Service
@Transactional
public class EstabelecimentoService {

	@Autowired
    private EstabelecimentoRepository repo;
     
	 public List<Estabelecimento> listAll() {
	        return repo.findAll();
	    }
	     
	 
	    public void save(Estabelecimento estabelecimento) {
	        repo.save(estabelecimento);
	    }
	     
	    public Estabelecimento get(Integer id) {
	        return repo.findById(id).get();
	    }
	    
	    public List<Estabelecimento> getByIdcliente(Integer idcliente) {
	        return repo.findByIdcliente(idcliente);
	    }
	    
	    public List <Estabelecimento> listOpen()
	    {
	    	return repo.findByAberto(true);
	    }


		public void delete(Integer id) {
			repo.deleteById(id);
			
		}
	    
		 public List <Estabelecimento> search(String search)
		    {
		    	return repo.findByNomeContainingIgnoreCase(search);
		    }
	    
}
