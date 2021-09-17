package br.ueg.modelo.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Tipo Amigo")
public @Data
class PizzariaDTO  implements Serializable {

	private static final long serialVersionUID = -7360509088273650750L;

	@ApiModelProperty(value = "id da Pizzaria")
    private Long id;

    @ApiModelProperty(value = "Nome da Pizzaria")
    private String nome;

}
