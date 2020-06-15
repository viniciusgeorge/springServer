package server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Listaprodutos {
	private Integer id;
	private Integer idpedido;
    private Integer idproduto;
    private Integer quantidade;
    
    
    
    public Listaprodutos()
    {
    	
    }
    
    public Listaprodutos(Integer id, Integer idpedido,Integer idproduto, Integer quantidade) {
		this.id = id;
		this.idpedido = idpedido;
		this.idproduto = idproduto;
		this.quantidade = quantidade;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
		this.id = id;
	}

    
    
	public Integer getIdpedido() {
		return idpedido;
	}

	public void setIdpedido(Integer idpedido) {
		this.idpedido = idpedido;
	}

	public Integer getIdproduto() {
		return idproduto;
	}

	public void setIdproduto(Integer idproduto) {
		this.idproduto = idproduto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	
    
}
