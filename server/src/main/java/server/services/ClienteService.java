package server.services;

import java.util.List;


 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import server.model.Cliente;
import server.repository.ClienteRepository;
 
@Service
@Transactional

public class ClienteService {

	@Autowired
    private ClienteRepository repo;
     
	 public List<Cliente> listAll() {
	        return repo.findAll();
	    }
	     
	 
	    public void save(Cliente cliente) {
	        repo.save(cliente);
	    }
	     
	    public Cliente get(Integer id) {
	        return repo.findById(id).get();
	    }
	     
	    public void delete(Integer id) {
	        repo.deleteById(id);
	    }
	
	    public Cliente getByEmail(String email)
	    {
	    	return repo.findByEmail(email);
	    }

	    public Integer getIdByNumero(String numero)
	    {
	    	Cliente c = repo.findByNumero(numero);
	    	return c.getId();
	    }

		
}
