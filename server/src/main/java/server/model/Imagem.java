package server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Imagem {

	private Integer id;
	private Integer idproduto;
    private String caminho;
    
    public Imagem()
    {
    	
    }
    public Imagem(Integer id, Integer idproduto, String caminho) {
    	setId(id);
        setIdproduto(idproduto);
        setCaminho(caminho);
        
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdproduto() {
		return idproduto;
	}
	public void setIdproduto(Integer idproduto) {
		this.idproduto = idproduto;
	}
	public String getCaminho() {
		return caminho;
	}
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
    
    
	
}
