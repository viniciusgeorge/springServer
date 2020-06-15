package server.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import server.model.Endereco;
import server.repository.EnderecoRepository;

@Service
@Transactional
public class EnderecoService {

	@Autowired
    private EnderecoRepository repo;
     
	 public List<Endereco> listAll() {
	        return repo.findAll();
	    }
	     
	 
	    public void save(Endereco endereco) {
	        repo.save(endereco);
	    }
	     
	    public Endereco get(Integer id) {
	        return repo.findById(id).get();
	    }
	     
	    public void delete(Integer id) {
	        repo.deleteById(id);
	    }
	    
	    public List<Endereco> getByCliente(Integer id)
	    {
	    	return repo.findByIdcliente(id);
	    }
}
