package server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


 
@Entity

public class Cliente {
	private Integer id;
    private String nome;
    private String email;
    private String numero;
    private boolean vendedor;
    
    
    public Cliente() {
    }
 
    public Cliente(Integer id, String nome, String email, String numero, boolean vendedor) {
        setId(id);
        setNome(nome);
        setEmail(email);
        setNumero(numero);
        setVendedor(vendedor);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public boolean isVendedor() {
		return vendedor;
	}

	public void setVendedor(boolean vendedor) {
		this.vendedor = vendedor;
	}
    
    

}
