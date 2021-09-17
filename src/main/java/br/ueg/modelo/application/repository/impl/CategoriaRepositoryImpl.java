package br.ueg.modelo.application.repository.impl;

import br.ueg.modelo.application.dto.FiltroCategoriaDTO;
import br.ueg.modelo.application.model.Categoria;
import br.ueg.modelo.application.repository.CategoriaRepositoryCustom;
import br.ueg.modelo.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CategoriaRepositoryImpl implements CategoriaRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Categoria> findAllByFiltro(FiltroCategoriaDTO filtroCategoriaDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT grupo FROM Categoria categoria");
        
        jpql.append(" WHERE 1=1 ");

        if (!Util.isEmpty(filtroCategoriaDTO.getCategoria())) {
            jpql.append(" AND UPPER(categoria.nomeCategoria) LIKE UPPER('%' || :nomeCategoria || '%')  ");
            parametros.put("nomeCategoria", filtroCategoriaDTO.getCategoria());
        }

        TypedQuery<Categoria> query = entityManager.createQuery(jpql.toString(), Categoria.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }

}
