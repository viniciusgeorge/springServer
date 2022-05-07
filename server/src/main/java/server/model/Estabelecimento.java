package server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Estabelecimento {
	
	private Integer id;
	private Integer idcliente;
    private String nome;
    private String descricao;
    private boolean aberto;
    
    
    public Estabelecimento()
    {
    	
    }
    
    public Estabelecimento(Integer id,Integer idcliente, String nome, String descricao, boolean aberto) {
    	setId(id);
    	setIdcliente(idcliente);
    	setNome(nome);
    	setDescricao(descricao);
		setAberto(aberto);
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isAberto() {
		return aberto;
	}

	public void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	public Integer getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(Integer idcliente) {
		this.idcliente = idcliente;
	}
    
    
}
