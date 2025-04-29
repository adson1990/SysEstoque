package com.adsonlucas.SysEstoque.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adsonlucas.SysEstoque.entitiesDTO.CompraAgrupadaDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.CompraItemResumoDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.UltimaCompraDTO;
import com.adsonlucas.SysEstoque.repositories.ComprasRepository;

@Service
public class ComprasService {
    
    @Autowired
    private ComprasRepository comprasRepository;

    public List<CompraAgrupadaDTO> listarVendasPorCliente(Long codCli) {
        List<Object[]> resultados = comprasRepository.buscarComprasPorIdCliente(codCli);

        // Mapa para agrupar por cliente + data da compra
        Map<String, CompraAgrupadaDTO> agrupado = new LinkedHashMap<>();

        for (Object[] obj : resultados) {
            String nomePrd = (String) obj[0];
            String descricao = (String) obj[1];
            String nomeCli = (String) obj[2];
            Integer qtd = ((Number) obj[3]).intValue();
            LocalDateTime dataCompra = ((Timestamp) obj[4]).toLocalDateTime();
            BigDecimal valorTotal = new BigDecimal(((Number) obj[5]).toString());
            String categorias = (String) obj[6];

            String chave = nomeCli + "_" + dataCompra.toString();

            // Cria ou busca a compra agrupada
            CompraAgrupadaDTO compra = agrupado.get(chave);
            if (compra == null) {
                compra = new CompraAgrupadaDTO();
                compra.setClientName(nomeCli);
                compra.setDataCompra(dataCompra);
                compra.setItens(new ArrayList<>());
                compra.setPriceTotal(0.0);
                agrupado.put(chave, compra); // seta a chave criada no passo anterior
            }

            // Adiciona item
            CompraItemResumoDTO item = new CompraItemResumoDTO();
            item.setProductName(nomePrd);
            item.setDescription(descricao);
            item.setQuantidade(qtd);
            item.setPrcUnitario(valorTotal.doubleValue());
            item.setCategoryNames(categorias);
            compra.getItens().add(item);

            // Soma total
            double totalParcial = valorTotal.doubleValue() * qtd;
            compra.setPriceTotal(compra.getPriceTotal() + totalParcial);
        }

        return new ArrayList<>(agrupado.values());
    }
    
    public List<UltimaCompraDTO> listarUltimasCompras(Long codCli) {
        List<Object[]> resultados = comprasRepository.buscarUltimasComprasPorCliente(codCli);

        return resultados.stream()
            .map(obj -> new UltimaCompraDTO((Date) obj[0], (BigDecimal) obj[1]))
            .collect(Collectors.toList());
    }

    public List<UltimaCompraDTO> listarUltimasComprasPorValor(Long codCli) {
        List<Object[]> resultados = comprasRepository.buscarUltimasComprasOrdenadaPorValor(codCli);

        return resultados.stream()
            .map(obj -> new UltimaCompraDTO((Date) obj[0], (BigDecimal) obj[1]))
            .collect(Collectors.toList());
    }
}
