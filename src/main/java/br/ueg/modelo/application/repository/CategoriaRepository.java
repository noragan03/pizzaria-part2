package br.ueg.modelo.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.ueg.modelo.application.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>, CategoriaRepositoryCustom {
	
	@Query("SELECT COUNT(categoria) FROM Categoria c" +
            " WHERE lower(categoria.categoria) LIKE lower(:nomeCategoria)" +
            " AND (:idCategoria IS NULL OR categoria.id != :idCategoria)")
    public Long countByNomeAndNotId(String nomeCategoria, Long idCategoria);
	
}
