package server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import server.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	List<Produto> findByIdestabelecimento(Integer id);
	
}
