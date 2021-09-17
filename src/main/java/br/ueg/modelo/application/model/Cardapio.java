package br.ueg.modelo.application.model;

import br.ueg.modelo.application.configuration.Constante;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.enums.converter.StatusSimNaoConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "TBL_CARDAPIO", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_CARDAPIO", sequenceName = "TBL_S_CARDAPIO", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class Cardapio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_CARDAPIO")
    @Column(name = "ID_CARDAPIO", nullable = false)
    private Long id;

    @Column(name = "SABOR_PIZZA", length = 100, nullable = false)
    private String sabor;
    
    @Column(name = "INGREDIENTES", nullable = false, length=500, unique=false)
	private String ingredientes;

    @Column(name = "PRECO", nullable = false)
	private Double preco;
	
    @Convert(converter = StatusSimNaoConverter.class)
    @Column(name = "BORDA_RECHEADA", length = 1, nullable = false)
    private StatusSimNao bordaRecheada;

	/*@Column(name = "BORDA_RECHEADA", nullable = false)
	private Boolean bordaRecheada;*/
    
	/*@Column(name = "DATA_ALTERACAO",nullable=false)
	 * private Data dataAlteracao*/
    
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID_CATEGORIA", nullable = false)
    private Categoria categoria;
}
