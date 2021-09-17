package br.ueg.modelo.application.model;

import br.ueg.modelo.application.configuration.Constante;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "TBL_TIPO_FUNCIONARIO", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_TIPO_FUNCIONARIO", sequenceName = "TBL_S_TIPO_FUNCIONARIO", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class TipoFuncionario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_TIPO_FUNCIONARIO")
    @Column(name = "ID_TIPO_FUNCIONARIO", nullable = false)
    private Long id;

    @Column(name = "NOME_TIPO_FUNCIONARIO", length = 100, nullable = false)
    private String nome;

}
