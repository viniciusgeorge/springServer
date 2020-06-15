package server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import server.model.Listaprodutos;

public interface ListaprodutosRepository extends JpaRepository<Listaprodutos, Integer>{
	
	List<Listaprodutos> findByIdpedido(Integer idPedido);

}
