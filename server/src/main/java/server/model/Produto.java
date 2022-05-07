package server.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Produto {
	private Integer id;
    private Integer idestabelecimento;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String caminhoimagem;
    private boolean disponivel;
    
    public Produto()
    {
    	
    }
    
    public Produto(Integer id, Integer idestabelecimento, String nome, String descricao, BigDecimal preco, String caminhoImagem, boolean disponivel) {
    	setId(id);
    	setIdestabelecimento(idestabelecimento);
    	setNome(nome);
    	setDescricao(descricao);
    	setPreco(preco);
    	setCaminhoimagem(caminhoImagem);
    	setDisponivel(disponivel);
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdestabelecimento() {
		return idestabelecimento;
	}

	public void setIdestabelecimento(Integer idestabelecimento) {
		this.idestabelecimento = idestabelecimento;
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

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getCaminhoimagem() {
		return caminhoimagem;
	}

	public void setCaminhoimagem(String caminhoImagem) {
		this.caminhoimagem = caminhoImagem;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}
    
    
    
}
