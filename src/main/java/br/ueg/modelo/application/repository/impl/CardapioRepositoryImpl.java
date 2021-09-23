package br.ueg.modelo.application.repository.impl;

import br.ueg.modelo.application.dto.FiltroCardapioDTO;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.model.Cardapio;
import br.ueg.modelo.application.repository.CardapioRepositoryCustom;
import br.ueg.modelo.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CardapioRepositoryImpl implements CardapioRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Cardapio> findAllByFiltro(FiltroCardapioDTO filtroCardapioDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT cardapio FROM Cardapio cardapio");
        jpql.append(" INNER JOIN FETCH cardapio.categoria categoria");

        jpql.append(" WHERE 1=1 ");

        if (!Util.isEmpty(filtroCardapioDTO.getSabor())) {
            jpql.append(" AND UPPER(cardapio.sabor) LIKE UPPER('%' || :sabor || '%')  ");
            parametros.put("sabor", filtroCardapioDTO.getSabor());
        }

        if (filtroCardapioDTO.getIdCategoria()!=null) {
            jpql.append(" AND cardapio.sabor.id = :idCategoria "); //nome do parametro
            parametros.put("idCategoria", filtroCardapioDTO.getIdCategoria());
        }

        /*Verificar se necessario para o nosso caso - Modelo Cardapio deve ser enum*/
        
        if (filtroCardapioDTO.getBordaRecheada() != null) {
            jpql.append(" AND cardapio.bordaRecheada = :bordaRecheada ");
            parametros.put("bordaRecheada", filtroCardapioDTO.getBordaRecheada());
        }

        TypedQuery<Cardapio> query = entityManager.createQuery(jpql.toString(), Cardapio.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }
}
