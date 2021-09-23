package br.ueg.modelo.application.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.ueg.modelo.application.configuration.Constante;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.enums.converter.StatusSimNaoConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @Column(name = "DATA_ATUALIZACAO")
   	private LocalDate data;
    
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID_CATEGORIA", nullable = false)
    private Categoria categoria;
}
