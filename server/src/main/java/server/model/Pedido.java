package server.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pedido {
	
	private Integer id;
    private Integer idcliente;
    private Integer idendereco;
    private String formapagamento;
    private BigDecimal frete;
    private BigDecimal precototal;
    private Timestamp data;
    public Pedido()
    {
    	
    }

	public Pedido(Integer id, Integer idcliente, Integer idendereco, String formapagamento,
			BigDecimal frete, BigDecimal precototal, Timestamp data) {
		
	
	
		this.id = id;
		this.idcliente = idcliente;
		this.idendereco = idendereco;
		
		this.formapagamento = formapagamento;
		this.frete = frete;
		this.precototal = precototal;
		this.data = data;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(Integer idcliente) {
		this.idcliente = idcliente;
	}

	public Integer getIdendereco() {
		return idendereco;
	}

	public void setIdendereco(Integer idendereco) {
		this.idendereco = idendereco;
	}

	
	public String getFormapagamento() {
		return formapagamento;
	}

	public void setFormapagamento(String formapagamento) {
		this.formapagamento = formapagamento;
	}

	public BigDecimal getFrete() {
		return frete;
	}

	public void setFrete(BigDecimal frete) {
		this.frete = frete;
	}

	public BigDecimal getPrecototal() {
		return precototal;
	}

	public void setPrecototal(BigDecimal precototal) {
		this.precototal = precototal;
	}

	public Timestamp getData() {
		return data;
	}
	
	public void setData(Timestamp data) {
		this.data = data;
	}

    
}
