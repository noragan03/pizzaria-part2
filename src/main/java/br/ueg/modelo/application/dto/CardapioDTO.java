package br.ueg.modelo.application.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.ueg.modelo.application.enums.StatusSimNao;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String sabor;

    @ApiModelProperty(value = "Id da Categoria")
    private Long idCategoria;

    @ApiModelProperty(value = "nome da Categoria")
    private String nomeCategoria;

    @ApiModelProperty(value = "Indica se a borda é recheada")
    private StatusSimNao bordaRecheada;

    @ApiModelProperty(value = "Preco da pizza")
    private Double preco;

    @ApiModelProperty(value = "Indica se a borda é recheada")
    private String ingredientes;

    @ApiModelProperty(value = "data de cadastro")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate data;
}
