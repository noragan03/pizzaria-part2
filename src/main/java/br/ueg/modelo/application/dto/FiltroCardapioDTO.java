package br.ueg.modelo.application.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.ueg.modelo.application.enums.StatusSimNao;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Dados do filtro de pesquisa de Cardapio")
public @Data class FiltroCardapioDTO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4407752900864695292L;

	@ApiModelProperty(value = "Nome do Cardapio")
    private String sabor;

    @ApiModelProperty(value = "Id da Categoria")
    private Long idCategoria;

    @ApiModelProperty(value = "Indica se é borda recheada")
    private StatusSimNao bordaRecheada;
}
