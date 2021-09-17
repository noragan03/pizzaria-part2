package br.ueg.modelo.application.mapper;

import br.ueg.modelo.application.dto.CardapioDTO;
import br.ueg.modelo.application.model.Cardapio;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
/**
 * Classe adapter referente a entidade {@link Cardapio}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { SimNaoMapper.class})
public interface CardapioMapper {
    /**
     * Converte a entidade {@link Cardapio} em DTO {@link CardapioDTO}
     *
     * @param cardapio
     * @return
     */
    @Mapping(source = "categoria.id", target = "idCategoria")
    @Mapping(source = "categoria.categoria", target = "nomeCategoria")
    public CardapioDTO toDTO(Cardapio cardapio);

    /**
     * Converte o DTO {@link CategoriaDTO} para entidade {@link Categoria}
     *
     * @param cardapioDTO
     * @return
     */
    @Mapping(source = "cardapioDTO.idCategoria", target = "categoria.id")
    public Cardapio toEntity(CardapioDTO cardapioDTO);
}
