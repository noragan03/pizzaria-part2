package br.ueg.modelo.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Categoria")
public @Data
class CategoriaDTO  implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8045874021076000348L;

	@ApiModelProperty(value = "id da Categoria")
    private Long id;

    @ApiModelProperty(value = "Nome da Categoria")
    private String categoria;

}
