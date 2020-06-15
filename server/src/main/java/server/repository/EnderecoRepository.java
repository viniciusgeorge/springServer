package server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import server.model.Endereco;

public interface EnderecoRepository extends JpaRepository <Endereco, Integer>{

	List<Endereco> findByIdcliente(Integer id);
	

}
