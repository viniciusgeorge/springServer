package server.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import server.model.Imagem;


public interface ImagemRepository extends JpaRepository <Imagem, Integer>{

	Imagem findByIdproduto(Integer idProduto);
}
