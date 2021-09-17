package br.ueg.modelo.application.mapper;


import br.ueg.modelo.application.dto.CategoriaDTO;
import br.ueg.modelo.application.model.Modulo;
import br.ueg.modelo.application.model.Categoria;
import org.mapstruct.Mapper;

/**
 * Classe adapter referente a entidade {@link Modulo}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    /**
     * Converte a entidade {@link Categoria} em DTO {@link CategoriaDTO}
     *
     * @param categoria
     * @return
     */

    public CategoriaDTO toDTO(Categoria categoria);

    /**
     * Converte o DTO {@link CategoriaDTO} para entidade {@link Categoria}
     *
     * @param categoriaDTO
     * @return
     */
    public Categoria toEntity(CategoriaDTO categoriaDTO);
}
