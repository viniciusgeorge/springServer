package server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Endereco {
	
	private Integer id;
    private Integer idcliente;
    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String referencia;
    private String numero;
    
    
    
    public Endereco() {
    }
 

	public Endereco(Integer id, Integer idcliente, String cep, String rua, String bairro, String cidade, String estado,
			String referencia, String numero) {
		setId(id);
		setIdcliente(idcliente);
		setCep(cep);
		setRua(rua);
		setBairro(bairro);
		setCidade(cidade);
		setEstado(estado);
		setReferencia(referencia);
		setNumero(numero);
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


	public String getCep() {
		return cep;
	}


	public void setCep(String cep) {
		this.cep = cep;
	}


	public String getRua() {
		return rua;
	}


	public void setRua(String rua) {
		this.rua = rua;
	}


	public String getBairro() {
		return bairro;
	}


	public void setBairro(String bairro) {
		this.bairro = bairro;
	}


	public String getCidade() {
		return cidade;
	}


	public void setCidade(String cidade) {
		this.cidade = cidade;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getReferencia() {
		return referencia;
	}


	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}
    
    
}
