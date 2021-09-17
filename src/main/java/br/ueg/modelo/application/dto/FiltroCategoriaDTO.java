package br.ueg.modelo.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Dados do filtro de pesquisa de Categoria")
public @Data class FiltroCategoriaDTO implements Serializable {
    
	private static final long serialVersionUID = 8054924987270635856L;
	
	@ApiModelProperty(value = "Nome da Categoria")
    private String categoria;

}
