package server.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import server.model.Cliente;


public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	Cliente findByEmail(String email);
	Cliente findByNumero(String numero);

}
