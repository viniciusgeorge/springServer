package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import server.model.Imagem;
import server.repository.ImagemRepository;

@Service
@Transactional
public class ImagemService {
	@Autowired
	
	private ImagemRepository repo;
	
	public void save(Imagem imagem) {
        repo.save(imagem);
    }
     
    public Imagem get(Integer idproduto) {
    	if(idproduto != null)
    		return repo.findByIdproduto(idproduto);
    	else return null;
    }
    
    public void delete(Integer id) {
        repo.deleteById(id);
      
    }

}
