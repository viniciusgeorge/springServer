package server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import server.model.Estabelecimento;

public interface EstabelecimentoRepository extends JpaRepository <Estabelecimento, Integer> {

	List <Estabelecimento> findByAberto(boolean aberto);
	List <Estabelecimento> findByNomeContainingIgnoreCase(String search);
	List <Estabelecimento> findByIdcliente(Integer idcliente);
}
