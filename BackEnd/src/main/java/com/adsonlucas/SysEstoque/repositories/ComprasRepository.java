package com.adsonlucas.SysEstoque.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adsonlucas.SysEstoque.entities.Compras;

@Repository
public interface ComprasRepository extends JpaRepository<Compras, Long>{

	@Query(value = """
		    SELECT 
		        p.name AS productName,
		        p.description,
		        cli.name AS clientName,
		        i.quantidade AS quantity,
		        c.data_compra AS purchaseDate,
		        i.preco AS price,
		        string_agg(cat.description, ', ') AS categoryNames
		    FROM tb_compras c
		    JOIN tb_client cli ON cli.id = c.cod_cli
		    JOIN tb_compras_itens i ON i.compra_id = c.id
		    JOIN tb_product p ON p.id = i.produto_id
		    JOIN tb_product_category pc ON pc.product_id = p.id
		    JOIN tb_category_product cat ON cat.id = pc.category_product_id
		    WHERE cli.id = :codCli
		    GROUP BY p.name, cli.name, i.quantidade, c.data_compra, i.preco
		    ORDER BY c.data_compra DESC
		    """, nativeQuery = true)
		List<Object[]> buscarComprasPorIdCliente(@Param("codCli") Long codCli);


}
