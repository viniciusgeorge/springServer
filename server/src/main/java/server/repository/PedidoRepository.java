package server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import server.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

	List<Pedido> findByIdclienteOrderByIdDesc(Integer id);
	Pedido findTopByIdclienteOrderByIdDesc(Integer id);
}


