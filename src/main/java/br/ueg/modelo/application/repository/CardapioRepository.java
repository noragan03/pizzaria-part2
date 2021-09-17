package br.ueg.modelo.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.ueg.modelo.application.model.Cardapio;
import br.ueg.modelo.application.model.Usuario;

/**
 * Classe de persistência referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Repository
public interface CardapioRepository extends JpaRepository<Cardapio, Long>, CardapioRepositoryCustom{

	@Query(""+
	"SELECT CASE WHEN COUNT(p) > 0 THEN "+
	"TRUE ELSE FALSE END " + 
	"FROM Cardapio p "+
	"WHERE lower(p.sabor)=lower(?1)" //com a adicao de lower ele vai encontrar palavras escritas com letras minusculas tambem
	)
	Boolean exitePizza(String sabor); //Precisa do banco de dados para confirmar
	
//	@Query("SELECT p FROM Pizzaria p WHERE p.bordaRecheada = true") //funcionando
//	List<Categoria> findByActiveTrue(Boolean borda);
//	
//	@Query("SELECT p FROM Pizzaria p WHERE p.bordaRecheada = false")//funcionando :)
//	List<Categoria> findByActiveFalse(Boolean borda);

    /**
     * Retorna o número de {@link Cardapio} pelo 'sabor' , desconsiderando o
     * 'Cardapio' com o 'id' informado.
     *
     * @param sabor
     * @param idCardapio
     * @return
     */
    @Query("SELECT COUNT(cardapio) FROM Cardapio cardapio" +
            " WHERE lower(cardapio.sabor) LIKE lower(:sabor)" +
            " AND (:idCardapio IS NULL OR cardapio.id != :idCardapio)")
    public Long countByNomeAndNotId(String sabor, Long idCardapio);

    /**
     * Listar todos os Amigos
     * @return
     */
    @Query("SELECT cardapio from Cardapio cardapio" +
            " INNER JOIN FETCH cardapio.categoria")
    public List<Cardapio> getTodos();

    /**
     * Busca uma {@link Cardapio} pelo id Informado
     *
     * @param idCardapio
     * @return
     */
    @Query("SELECT cardapio from Cardapio cardapio" +
            " INNER JOIN FETCH cardapio.categoria" +
            " WHERE cardapio.id = :idCardapio ")
    public Optional<Cardapio> findByIdFetch( @Param("idCardapio") final Long idCardapio);

}
