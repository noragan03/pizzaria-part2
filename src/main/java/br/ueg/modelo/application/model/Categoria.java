package br.ueg.modelo.application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.ueg.modelo.application.configuration.Constante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_CATEGORIA", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_CATEGORIA", sequenceName = "TBL_S_CATEGORIA", allocationSize = 1, schema = Constante.DATABASE_OWNER)

public @Data class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_CATEGORIA")
	@Column(name = "ID_CATEGORIA", nullable = false)
	private Long id;
	
	@Column(name = "NOME_CATEGORIA", length = 100, nullable = false)
    private String categoria;
	
}
