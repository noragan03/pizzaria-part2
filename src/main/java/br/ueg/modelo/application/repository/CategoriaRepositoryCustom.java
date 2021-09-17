/*
 * GrupoRepositoryCustom.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.modelo.application.repository;

import java.util.List;

import br.ueg.modelo.application.dto.FiltroCategoriaDTO;
import br.ueg.modelo.application.model.Categoria;

/**
 * Classe de persistência referente a entidade {@link Categoria}.
 * 
 * @author UEG
 */
public interface CategoriaRepositoryCustom {

	/**
	 * Retorna uma lista de {@link Categoria} conforme o filtro de pesquisa informado.
	 * 
	 * @param filtroCategoriaDTO
	 * @return
	 */
	public List<Categoria> findAllByFiltro(FiltroCategoriaDTO filtroCategoriaDTO);



}
