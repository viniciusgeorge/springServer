package server.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import server.model.*;
import server.security.SmsCode;
import server.services.ClienteService;
import server.services.EnderecoService;
import server.services.EstabelecimentoService;
import server.services.ListaprodutosService;
import server.services.PedidoService;
import server.services.ProdutoService;
 
@RestController
public class Controller {
 
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private EnderecoService enderecoService;
    
    @Autowired
    private EstabelecimentoService estabelecimentoService;
    
    @Autowired
    private ProdutoService produtoService;
    
    @Autowired
    private PedidoService pedidoService;
    
    @Autowired
    private ListaprodutosService lpService;
    
    
    SmsCode smsCode = new SmsCode();
    
    public static class PedidoCompleto {

    	public Pedido pedido;
    	public Listaprodutos[] lista;
    }


    @GetMapping("/home")
    public String padrao()
    {
    	return "Teste";
    }
    


    //READ
    
    @GetMapping("/getPedidos")
    public ResponseEntity<List<PedidoCompleto>> getPedidos(@RequestHeader("Authorization") String jwt64) {
      
    	
    	
    	try {
    		List<PedidoCompleto> pedidosCompletos= new ArrayList<PedidoCompleto>();
            List<Pedido> pedidos = pedidoService.getByCliente((getIdByUserNumber(jwt64)));
            for(int i = 0; i < pedidos.size(); i++)
            {
            	PedidoCompleto pc = new PedidoCompleto();
            	pc.pedido = pedidos.get(i);
            	List<Listaprodutos> lp = lpService.getByIdpedido(pc.pedido.getId());
            	Listaprodutos [] novaLista = new Listaprodutos[lp.size()];
            	novaLista = lp.toArray(novaLista);
            	pc.lista = novaLista;
            	
            	pedidosCompletos.add(pc);
            }
            return new ResponseEntity<List<PedidoCompleto>>(pedidosCompletos, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<PedidoCompleto>>(HttpStatus.NOT_FOUND);
        } 
        
    }
 
    @GetMapping("/getEnderecos")
    
    public ResponseEntity<List<Endereco>> getE(@RequestHeader("Authorization") String jwt64) {
        try {
            List<Endereco> enderecos = enderecoService.getByCliente(getIdByUserNumber(jwt64));
            return new ResponseEntity<List<Endereco>>(enderecos, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<Endereco>>(HttpStatus.NOT_FOUND);
        }      
    }
    
	@GetMapping("/getCliente")
	    
	    public ResponseEntity<Cliente> getCliente(@RequestHeader("Authorization") String jwt64) {
	        try {
	            Cliente cliente = clienteService.get(getIdByUserNumber(jwt64));
	            return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	        } catch (NoSuchElementException e) {
	            return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
	        }      
	    }
    
    
	@GetMapping("/getProdutos/{idEstabelecimento}")
	
	public ResponseEntity<List<Produto>> getProdutos(@PathVariable Integer idEstabelecimento) {
	
		try {
	        List<Produto> produtos = produtoService.getByIdEstabelecimento(idEstabelecimento);
	        return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	    } catch (NoSuchElementException e) {
	        return new ResponseEntity<List<Produto>>(HttpStatus.NOT_FOUND);
	    }      
	}
	
	@GetMapping("/getEstabelecimentos")
	
	public ResponseEntity<List<Estabelecimento>> getEstab() {
	
		try {
	        List<Estabelecimento> estabelecimentos = estabelecimentoService.listOpen();
	        return new ResponseEntity<List<Estabelecimento>>(estabelecimentos, HttpStatus.OK);
	    } catch (NoSuchElementException e) {
	        return new ResponseEntity<List<Estabelecimento>>(HttpStatus.NOT_FOUND);
	    }      
	}
	  
	@GetMapping("/getProduto/{id}")
	
	public ResponseEntity <Produto> getProduto(@PathVariable Integer id) {
	
		try {
	        Produto produto = produtoService.get(id);
	        return new ResponseEntity<Produto>(produto, HttpStatus.OK);
	    } catch (NoSuchElementException e) {
	        return new ResponseEntity<Produto>(HttpStatus.NOT_FOUND);
	    }      
	}
	
	  
	@GetMapping("/getEstabelecimento/{id}")
	
	public ResponseEntity <Estabelecimento> getEstabelecimento(@PathVariable Integer id) {
	
		try {
	        Estabelecimento estabelecimento = estabelecimentoService.get(id);
	        return new ResponseEntity<Estabelecimento>(estabelecimento, HttpStatus.OK);
	    } catch (NoSuchElementException e) {
	        return new ResponseEntity<Estabelecimento>(HttpStatus.NOT_FOUND);
	    }      
	}
	    
	
	@GetMapping("/searchEstabelecimentos/{search}")
	
	public ResponseEntity<List<Estabelecimento>> searchEstab(@PathVariable String search) {
	
		try {
	        List<Estabelecimento> estabelecimentos = estabelecimentoService.search(search);
	        return new ResponseEntity<List<Estabelecimento>>(estabelecimentos, HttpStatus.OK);
	    } catch (NoSuchElementException e) {
	        return new ResponseEntity<List<Estabelecimento>>(HttpStatus.NOT_FOUND);
	    }      
	}
	
	
	//CREATE
	
	@PostMapping("/addCliente")
	public ResponseEntity<?> addCliente(@RequestHeader("Authorization") String jwt64, @RequestBody Cliente cliente) {
	    
		
		if(cliente.getNumero().equals(getUserNumber(jwt64)))
		{
			clienteService.save(cliente);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		else
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
	}
	
	@PostMapping("/addProduto")
	public ResponseEntity<?> addProduto(@RequestHeader("Authorization") String jwt64, @RequestBody Produto produto) {
	    
		Estabelecimento estabelecimento = estabelecimentoService.get(produto.getIdestabelecimento());
		
		if(estabelecimento.getIdcliente().equals(getIdByUserNumber(jwt64)))
		{
			produtoService.save(produto);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		
	}
	
	@PostMapping("/addEndereco")
	public ResponseEntity<?> addEndereco(@RequestHeader("Authorization") String jwt64, @RequestBody Endereco endereco) {
	    
		if (endereco.getIdcliente().equals(getIdByUserNumber(jwt64)))
		{
			enderecoService.save(endereco);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	
	}
	
	@PostMapping("/addEstabelecimento")
	public ResponseEntity<?> addEstabelecimento(@RequestHeader("Authorization") String jwt64, @RequestBody Estabelecimento estabelecimento) {
	    
		Cliente cliente = clienteService.get(getIdByUserNumber(jwt64));
		
		if(estabelecimento.getIdcliente().equals(cliente.getId()) && cliente.isVendedor())
		{
			estabelecimentoService.save(estabelecimento);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	
	}
	
	
	@PostMapping("/addPedido")
	public ResponseEntity <?> addPedido(@RequestHeader("Authorization") String jwt64, @RequestBody PedidoCompleto pedidoCompleto) {
		
		if(pedidoCompleto.pedido.getIdcliente().equals(getIdByUserNumber(jwt64)))
		{
			pedidoService.savePedido(pedidoCompleto);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	
		
	}
	
	@PostMapping("/getCode")
	public void getCode(@RequestBody String number) {
		
		System.out.println(smsCode.newCode(number));
		
	}
	
	    
	//UPDATE
	
	@PutMapping("/updateEndereco")
	public ResponseEntity<?> updateEndereco(@RequestHeader("Authorization") String jwt64, @RequestBody Endereco endereco) {
	    
		Endereco enderecoExistente = enderecoService.get(endereco.getId());
	    if(enderecoExistente != null) {
			if (endereco.getIdcliente().equals(getIdByUserNumber(jwt64)))
			{
				enderecoService.save(endereco);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}
	    else
	    {
	    	 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	 		
	    }
	    	
	} 
		   
		
	@PutMapping("/updateEstabelecimento")
	public ResponseEntity<?> updateEstabelecimento(@RequestHeader("Authorization") String jwt64, @RequestBody Estabelecimento estabelecimento) {
	    
		Estabelecimento estabelecimentoExistente = estabelecimentoService.get(estabelecimento.getId());
	    if(estabelecimentoExistente != null) {
			if (estabelecimento.getIdcliente().equals(getIdByUserNumber(jwt64)))
			{
				estabelecimentoService.save(estabelecimento);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}
	    else
	    {
	    	 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	 		
	    }
	    	
	} 
		
	@PutMapping("/updateProduto")
	public ResponseEntity<?> updateProduto(@RequestHeader("Authorization") String jwt64, @RequestBody Produto produto) {
	    
		Produto produtoExistente = produtoService.get(produto.getId());
	    if(produtoExistente != null) {
	    	Estabelecimento estabelecimento = estabelecimentoService.get(produtoExistente.getIdestabelecimento());
			if (estabelecimento.getIdcliente().equals(getIdByUserNumber(jwt64)))
			{
				produtoService.save(produto);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}
	    else
	    {
	    	 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	 		
	    }
	    	
	} 
	
	

	@PutMapping("/updateCliente")
	public ResponseEntity<?> updateCliente(@RequestHeader("Authorization") String jwt64, @RequestBody Cliente cliente) {
	    
		Cliente clienteExistente = clienteService.get(cliente.getId());
	    if(clienteExistente != null) {
			if (cliente.getId().equals(getIdByUserNumber(jwt64)))
			{
				cliente.setNumero(clienteExistente.getNumero());
				clienteService.save(cliente);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}
	    else
	    {
	    	 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	 		
	    }
	    	
	} 
		 
	//DELETE
	
	@DeleteMapping("/deleteCliente")
	public ResponseEntity<?> deleteCliente(@RequestHeader("Authorization") String jwt64) {
	    clienteService.delete(getIdByUserNumber(jwt64));
	    return new ResponseEntity<>(HttpStatus.OK);
	    
	}
	
	@DeleteMapping("/deleteEndereco/{id}")
	public  ResponseEntity<?> deleteEndereco(@RequestHeader("Authorization") String jwt64, @PathVariable Integer id) {
	    
		Endereco endereco = enderecoService.get(id);
		if(endereco != null)
		{
			if(endereco.getIdcliente().equals(getIdByUserNumber(jwt64))) {
					
				enderecoService.delete(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}
		else
		{
	    	 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
	@DeleteMapping("/deleteEstabelecimento/{id}")
	public  ResponseEntity<?> deleteEstabelecimento(@RequestHeader("Authorization") String jwt64, @PathVariable Integer id) {
	    
		Estabelecimento estabelecimento = estabelecimentoService.get(id);
		if(estabelecimento != null)
		{
			if(estabelecimento.getIdcliente().equals(getIdByUserNumber(jwt64))) {
					
				estabelecimentoService.delete(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}
		else
		{
	    	 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
	@DeleteMapping("/deleteProduto/{id}")
	public  ResponseEntity<?> deleteProduto(@RequestHeader("Authorization") String jwt64, @PathVariable Integer id) {
	    
		
		Produto produto = produtoService.get(id);
		if(produto != null)
		{
			Estabelecimento estabelecimento = estabelecimentoService.get(produto.getIdestabelecimento());
			if(estabelecimento.getIdcliente().equals(getIdByUserNumber(jwt64))) {
					
				produtoService.delete(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}
		else
		{
	    	 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
	@DeleteMapping("/deletePedido/{id}")
	public  ResponseEntity<?> deletePedido(@RequestHeader("Authorization") String jwt64, @PathVariable Integer id) {
	    
		Pedido pedido = pedidoService.get(id);
		if(pedido != null)
		{
			if(pedido.getIdcliente().equals(getIdByUserNumber(jwt64))) {
					
				List<Listaprodutos> lp = lpService.getByIdpedido(id);
				for (int i = 0; i < lp.size(); i++)
				{
					lpService.delete(lp.get(i).getId());
				}
				
				pedidoService.delete(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}
		else
		{
	    	 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
	private Integer getIdByUserNumber(String jwt64)
	{
		
		String [] jwt = jwt64.split("\\.");
		
		byte[] decodedBytes = Base64.getDecoder().decode(jwt[1]);
		String decodedString = new String(decodedBytes);
		
		String [] json = decodedString.split("\"");
		return clienteService.getIdByNumero(json[3]);
		
		
	}
	
	private String getUserNumber(String jwt64)
	{
		
		String [] jwt = jwt64.split("\\.");
		
		byte[] decodedBytes = Base64.getDecoder().decode(jwt[1]);
		String decodedString = new String(decodedBytes);
		
		String [] json = decodedString.split("\"");
		return json[3];
		
		
	}
	
	    // RESTful API method for Create operation
	     
	    // RESTful API method for Update operation
	     
	    // RESTful API method for Delete operation
	    
	    
}