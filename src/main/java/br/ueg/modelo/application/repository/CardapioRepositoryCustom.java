/*
 * GrupoRepositoryCustom.java
 * Copyright (c) UEG.
 *
 */
package br.ueg.modelo.application.repository;


import br.ueg.modelo.application.dto.FiltroAmigoDTO;
import br.ueg.modelo.application.dto.FiltroCardapioDTO;
import br.ueg.modelo.application.model.Amigo;
import br.ueg.modelo.application.model.Cardapio;
import br.ueg.modelo.application.model.Grupo;

import java.util.List;

/**
 * Classe de persistência referente a entidade {@link Grupo}.
 * @author UEG
 */
public interface CardapioRepositoryCustom {

	/**
	 * Retorna uma lista de {@link Cardapio} conforme o filtro de pesquisa informado.
	 * 
	 * @param filtroCardapioDTO
	 * @return
	 */
	public List<Cardapio> findAllByFiltro(FiltroCardapioDTO filtroCardapioDTO);

}
