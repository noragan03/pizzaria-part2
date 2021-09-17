package br.ueg.modelo.application.model;

import br.ueg.modelo.application.configuration.Constante;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.enums.converter.StatusSimNaoConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TBL_FUNCIONARIO", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_FUNCIONARIO", sequenceName = "TBL_S_FUNCIONARIO", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_FUNCIONARIO")
    @Column(name = "ID_FUNCIONARIO", nullable = false)
    private Long id;

    @Column(name = "NOME_FUNCIONARIO", length = 100, nullable = false)
    private String nome;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_FUNCIONARIO", referencedColumnName = "ID_TIPO_FUNCIONARIO", nullable = false)
    private TipoFuncionario tipo;

}
