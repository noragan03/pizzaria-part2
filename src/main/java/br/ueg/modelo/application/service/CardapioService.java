package br.ueg.modelo.application.service;

import br.ueg.modelo.application.dto.FiltroCardapioDTO;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.exception.SistemaMessageCode;
import br.ueg.modelo.application.model.Cardapio;
import br.ueg.modelo.application.repository.CardapioRepository;
import br.ueg.modelo.comum.exception.BusinessException;
import br.ueg.modelo.comum.util.CollectionUtil;
import br.ueg.modelo.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CardapioService {

    @Autowired
    private CardapioRepository cardapioRepository;

    /**
     * Retorna uma lista de {@link Cardapio} conforme o filtro de pesquisa informado.
     *
     * @param filtroDTO
     * @return
     */
    public List<Cardapio> getCardapiosByFiltro(final FiltroCardapioDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<Cardapio> cardapios = cardapioRepository.findAllByFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(cardapios)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return cardapios;
    }

    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Cardapio
     *
     * @param filtroDTO
     */
    private void validarCamposObrigatoriosFiltro(final FiltroCardapioDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;

        if (!Util.isEmpty(filtroDTO.getSabor())) {
            vazio = Boolean.FALSE;
        }

        if (filtroDTO.getIdCategoria()!=null) {
            vazio = Boolean.FALSE;
        }

        if (filtroDTO.getBordaRecheada()!=null) {
            vazio = Boolean.FALSE;
        }
        
        //Falta filtroDTO do preco
        
        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
    }

    /**
     * Retorna uma lista de {@link Cardapio} cadatrados .
     *
     * @return
     */
    public List<Cardapio> getTodos() {
        List<Cardapio> cardapios = cardapioRepository.getTodos() ;

        if (CollectionUtil.isEmpty(cardapios)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return cardapios;
    }

    /**
     * Retorno um a {@link Cardapio} pelo Id informado.
     * @param id - id to Cardapio
     * @return
     */
    public Cardapio getById(Long id){
        Optional<Cardapio> cardapioOptional = cardapioRepository.findByIdFetch(id);

        if(!cardapioOptional.isPresent()){
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return cardapioOptional.get();
    }

    /**
     * Salva a instânica de {@link Cardapio} na base de dados conforme os critérios
     * especificados na aplicação.
     *
     * @param cardapio
     * @return
     */
    public Cardapio salvar(Cardapio cardapio) {

        if(cardapio.getId() == null && cardapio.getBordaRecheada() == null){
            cardapio.setBordaRecheada(StatusSimNao.SIM);
        }

        validarCamposObrigatorios(cardapio);
        validarDuplicidade(cardapio);

        cardapioRepository.save(cardapio);

        Cardapio cardapioSaved = this.getById(cardapio.getId());
        return cardapioSaved;
    }

    public Cardapio remover(Long id){
        Cardapio cardapio = this.getById(id);

        cardapioRepository.delete(cardapio);

        return cardapio;
    }



    /**
     * Verifica se os Campos Obrigatórios foram preenchidos.
     *
     * @param cardapio
     */
    private void validarCamposObrigatorios(final Cardapio cardapio) {
        boolean vazio = Boolean.FALSE;

        if (Util.isEmpty(cardapio.getSabor())) {
            vazio = Boolean.TRUE;
        }

        if (cardapio.getCategoria()==null || cardapio.getCategoria().getId()== null) {
            vazio = Boolean.TRUE;
        }

        if (cardapio.getBordaRecheada()==null) {
            vazio = Boolean.TRUE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
    }

    /**
     * Verifica se o TipoCardapio a ser salvo já existe na base de dados.
     *
     * @param cardapio
     */
    private void validarDuplicidade(final Cardapio cardapio) {
        Long count = cardapioRepository.countByNomeAndNotId(cardapio.getSabor(), cardapio.getId());

        if (count > BigDecimal.ZERO.longValue()) {
            throw new BusinessException(SistemaMessageCode.ERRO_PIZZA_DUPLICADA);
        }
    }

}
