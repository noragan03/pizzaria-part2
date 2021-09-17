package br.ueg.modelo.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Amigo")
public @Data
class CardapioDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -590511504440866005L;

	@ApiModelProperty(value = "id da Pizza")
    private Long id;

    @ApiModelProperty(value = "Sabor da Pizza")
    private String nome;

    @ApiModelProperty(value = "Id da Categoria")
    private Long idCategoria;

    @ApiModelProperty(value = "nome da Categoria")
    private String nomeCategoria;

    @ApiModelProperty(value = "Indica se a borda é recheada")
    private Boolean bordaRecheada;

}
