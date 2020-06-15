package server.services;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import server.model.Produto;
import server.repository.ProdutoRepository;

@Service
@Transactional


public class ProdutoService {
	@Autowired
    private ProdutoRepository repo;
     
	 public List<Produto> listAll() {
	        return repo.findAll();
	    }
	     
	 
	    public void save(Produto produto) {
	        repo.save(produto);
	    }
	     
	    public Produto get(Integer id) {
	        return repo.findById(id).get();
	    }
	    
	 public List<Produto> getByIdEstabelecimento(Integer idEstabelecimento)
	 {
		 return repo.findByIdestabelecimento(idEstabelecimento);
	 }


	public void delete(Integer id) {
		repo.deleteById(id);
		
	}

}
