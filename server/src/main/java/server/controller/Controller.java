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
import server.services.ImagemService;
import server.services.ListaprodutosService;
import server.services.PedidoService;
import server.services.ProdutoService;
 
@RestController
@CrossOrigin
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
    
    @Autowired
    private ImagemService imgService;
    SmsCode smsCode = new SmsCode();
    
    public static class Listaprodutosmaior {

    	public Listaprodutos lista;
    	public String nomeproduto;
    }
    
    public static class PedidoCompleto {

    	public Pedido pedido;
    	public Listaprodutosmaior[] lista;
    }

    public static class PedidoCompletoSimples {

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
            	List<Listaprodutosmaior> lpm = new ArrayList<Listaprodutosmaior>();
            	for(int j = 0; j < lp.size(); j++)
            	{
            		Listaprodutosmaior l = new Listaprodutosmaior();
            		
            		String nomeprod = produtoService.get(lp.get(j).getIdproduto()).getNome();
            		l.lista = lp.get(j);
            		l.nomeproduto = nomeprod;
            		lpm.add(l);
            		
            	}
            	Listaprodutosmaior [] novaLista = new Listaprodutosmaior[lpm.size()];
            	novaLista = lpm.toArray(novaLista);
            	pc.lista = novaLista;
            	
            	pedidosCompletos.add(pc);
            }
            return new ResponseEntity<List<PedidoCompleto>>(pedidosCompletos, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<PedidoCompleto>>(HttpStatus.NOT_FOUND);
        } 
        
    }
    
    
    
    @GetMapping("/getPedidosPorEstabelecimento/{idEstabelecimento}")
    public ResponseEntity<List<PedidoCompleto>> getPedidosPorEstabelecimento(@RequestHeader("Authorization") String jwt64, @PathVariable Integer idEstabelecimento) {
      
    	Estabelecimento e = estabelecimentoService.get(idEstabelecimento);
    	if (e.getIdcliente() == getIdByUserNumber(jwt64))
    	{
    		try {
        		List<PedidoCompleto> pedidosCompletos= new ArrayList<PedidoCompleto>();
        		
        		
        	
        		List <Produto> produtos = produtoService.getByIdEstabelecimento(idEstabelecimento);
        		List <Integer> li = new ArrayList<Integer>();
        		List<Pedido> pedidos = new ArrayList<Pedido>();
        		for(int z = 0; z < produtos.size(); z++)
        		{
        			
        			List <Listaprodutos> lp = lpService.getByIdproduto(produtos.get(z).getId());
        			
        			for(int y = 0; y < lp.size(); y++)
        			{
        				if(!li.contains(lp.get(y).getIdpedido()))
        				{
        					li.add(lp.get(y).getIdpedido());
        				}
        			}
        			
        			
        			
        		}
        		
        		for (int w = 0; w < li.size(); w++)
        		{
        			pedidos.add(pedidoService.get(li.get(w)));
        		}
        		//select * from pedido where id in( select distinct idpedido from listaprodutos where idproduto in (select id from produto where idestabelecimento = 1));
               
                for(int i = 0; i < pedidos.size(); i++)
                {
                	PedidoCompleto pc = new PedidoCompleto();
                	pc.pedido = pedidos.get(i);
                	List<Listaprodutos> lp = lpService.getByIdpedido(pc.pedido.getId());
                	List<Listaprodutosmaior> lpm = new ArrayList<Listaprodutosmaior>();
                	for(int j = 0; j < lp.size(); j++)
                	{
                		Listaprodutosmaior l = new Listaprodutosmaior();
                		
                		String nomeprod = produtoService.get(lp.get(j).getIdproduto()).getNome();
                		l.lista = lp.get(j);
                		l.nomeproduto = nomeprod;
                		lpm.add(l);
                		
                	}
                	Listaprodutosmaior [] novaLista = new Listaprodutosmaior[lpm.size()];
                	novaLista = lpm.toArray(novaLista);
                	pc.lista = novaLista;
                	
                	pedidosCompletos.add(pc);
                }
                return new ResponseEntity<List<PedidoCompleto>>(pedidosCompletos, HttpStatus.OK);
            } catch (NoSuchElementException ex) {
                return new ResponseEntity<List<PedidoCompleto>>(HttpStatus.NOT_FOUND);
            } 
    	}
    	
    	else
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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
	        	System.out.println(getIdByUserNumber(jwt64));
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
	
	
@GetMapping("/getProdutosDisponiveis/{idEstabelecimento}")
	
	public ResponseEntity<List<Produto>> getProdutosDisp(@PathVariable Integer idEstabelecimento) {
	
		try {
	        List<Produto> produtos = produtoService.getByIdEstabelecimentoDisp(idEstabelecimento);
	        return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	    } catch (NoSuchElementException e) {
	        return new ResponseEntity<List<Produto>>(HttpStatus.NOT_FOUND);
	    }      
	}
	
@GetMapping("/getEstabelecimentos/{idcliente}")
	
	
	public ResponseEntity<List<Estabelecimento>> getEstabByClienteId(@PathVariable Integer idcliente) {
	
		try {
	        List<Estabelecimento> estabelecimentos = estabelecimentoService.getByIdcliente(idcliente);
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
	
@GetMapping("/getImagem/{idproduto}")
	
	public ResponseEntity <Imagem> getImagem(@PathVariable Integer idproduto) {
	
		try {
	        Imagem imagem = imgService.get(idproduto);
	        return new ResponseEntity<Imagem>(imagem, HttpStatus.OK);
	    } catch (NoSuchElementException e) {
	        return new ResponseEntity<Imagem>(HttpStatus.NOT_FOUND);
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
	    
		
		
			cliente.setNumero(getUserNumber(jwt64));
			clienteService.save(cliente);
			return new ResponseEntity<>(HttpStatus.OK);
		
		
		
		
	}
	
	@PostMapping("/addImagem")
	public ResponseEntity<?> addImagem(@RequestHeader("Authorization") String jwt64, @RequestBody Imagem imagem) {
	    
		
		
			
			imgService.save(imagem);
			return new ResponseEntity<>(HttpStatus.OK);
		
		
		
		
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
	    
			endereco.setCep(endereco.getCep().replaceAll("[\\D]", ""));
			endereco.setIdcliente(getIdByUserNumber(jwt64));
			enderecoService.save(endereco);
			return new ResponseEntity<>(HttpStatus.OK);
	
	
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
	public ResponseEntity <?> addPedido(@RequestHeader("Authorization") String jwt64, @RequestBody PedidoCompletoSimples pedidoCompletoSimples) {
		
		if(pedidoCompletoSimples.pedido.getIdcliente().equals(getIdByUserNumber(jwt64)))
		{
			pedidoService.savePedido(pedidoCompletoSimples);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	
		
	}
	
	@PostMapping("/getCode")
	public ResponseEntity<String> getCode(@RequestBody String number) {
		 System.out.println(number);
		number = number.replaceAll("[\\D]", ""); 
		
		try {
	        String code = smsCode.newCode(number);
	        System.out.println(number);
	        System.out.println(number.length());
	        System.out.println(code);
	        return new ResponseEntity<String>(code, HttpStatus.OK);
	    } catch (NoSuchElementException e) {
	        return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }   
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
		   
	@PutMapping("/updateImagem")
	public ResponseEntity<?> updateImagem(@RequestHeader("Authorization") String jwt64, @RequestBody Imagem imagem) {
	    
		Imagem imagemExistente = imgService.get(imagem.getIdproduto());
	    if(imagemExistente != null) {
	    	
			if (estabelecimentoService.get(produtoService.get(imagem.getIdproduto()).getIdestabelecimento()).getIdcliente().equals(getIdByUserNumber(jwt64)))
			{
				
				imgService.save(imagem);
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
			if (estabelecimentoExistente.getIdcliente().equals(getIdByUserNumber(jwt64)))
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
	
	@DeleteMapping("/deleteImagem/{idproduto}")
	public  ResponseEntity<?> deleteImagem(@RequestHeader("Authorization") String jwt64, @PathVariable Integer idproduto) {
	    
		Imagem imagem = imgService.get(idproduto);
		if(imagem != null)
		{
			if(estabelecimentoService.get(produtoService.get(imagem.getIdproduto()).getIdestabelecimento()).getIdcliente().equals(getIdByUserNumber(jwt64))) {
					
				enderecoService.delete(idproduto);
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