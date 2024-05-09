package com.adsonlucas.SysEstoque.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.tests.Factory;

@DataJpaTest
@ActiveProfiles("test")
public class ClientRepositoryTest {

	@Autowired
	private ClientRepository repoTest;
	
	private long idNaoExistente;
	private long idExistente;
	private long countTotalClientes;
	private long idClienteParaAtualizar;
	
	@BeforeEach
	void setUp() throws Exception{
		idNaoExistente = 100L;
		idExistente = 1L;
		countTotalClientes = 11L;
		idClienteParaAtualizar = 2L;
	}
	
	@Test
	public void deleteObjetoQuandoIdExistir() {
		
		
		repoTest.deleteById(idExistente);
		
		Optional<Client> result = repoTest.findById(idExistente);
		
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void saveDeveGravarComAutoIncrementQuandoIdNulo() {
		
		Client cliente = Factory.createdClient();
		
		cliente = repoTest.save(cliente);
		
		Assertions.assertNotNull(cliente.getID());
		Assertions.assertEquals(countTotalClientes + 1, cliente.getID());
	}
	
	@Test
	public void deleteNaoRetornaErroQuandoIdNaoExistir() {
		
		 Assertions.assertDoesNotThrow(() -> {
			repoTest.deleteById(idNaoExistente);
		}); 
	}
	
	@Test
	public void updateDeveSubstituirClienteQuandoIdExistir() {
		
		Client cliente = Factory.createdClient();
		
		Optional<Client> result = repoTest.findById(idClienteParaAtualizar);
		Client clientEntity = result.get();
		
		clientEntity.setName(cliente.getName());
		
		repoTest.save(clientEntity);
		
		Assertions.assertEquals(cliente.getName(), clientEntity.getName());
	}

	/** Método para deletar quando o ID não existir
	 * não é possível mais executar o teste pois nas versões mais novas
	 * do spring não é lançada mais a exceção EmptyResultDataAccessException
	 * esta exceção foi suprimida nesse caso agindo apenas silenciosamente
	 * e não alterando informação alguma no banco.
	 */
 /*	@Test
	public void deleteResultaErroQuandoIdNaoExistir() {
		
		 Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repoTest.deleteById(idNaoExistente);
		}); 
	} */
}
