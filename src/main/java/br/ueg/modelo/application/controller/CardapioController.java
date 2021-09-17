package br.ueg.modelo.application.controller;

import br.ueg.modelo.api.util.Validation;
import br.ueg.modelo.application.dto.CardapioDTO;
import br.ueg.modelo.application.dto.FiltroCardapioDTO;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.mapper.CardapioMapper;
import br.ueg.modelo.application.model.Cardapio;
import br.ueg.modelo.application.service.CardapioService;
import br.ueg.modelo.comum.exception.MessageResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "Cardapio API")
@RestController
@RequestMapping(path = "${app.api.base}/cardapio")
public class CardapioController extends AbstractController {

    @Autowired
    private CardapioMapper cardapioMapper;

    @Autowired
    private CardapioService cardapioService;

    @PreAuthorize("hasRole('ROLE_CARDAPIO_INCLUIR')")
    @PostMapping
    @ApiOperation(value = "Inclusão de cardapio.",
            notes = "Incluir Cardapio.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CardapioDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    public ResponseEntity<?> incluir(@ApiParam(value = "Informações de Cardapio", required = true) @Valid @RequestBody CardapioDTO cardapioDTO) {
            Cardapio grupo = cardapioMapper.toEntity(cardapioDTO);
            return ResponseEntity.ok(cardapioMapper.toDTO(cardapioService.salvar(grupo)));
    }

    /**
     * Altera a instância de {@link CardapioDTO} na base de dados.
     *
     * @param id
     * @param cardapioDTO
     * @return
     */
    @PreAuthorize("hasRole('ROLE_AMIGO_ALTERAR')")
    @ApiOperation(value = "Altera as informações de Cardapio.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CardapioDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(
            @ApiParam(value = "Código do Cardapio", required = true) @PathVariable final BigDecimal id,
            @ApiParam(value = "Informações do Cardapio", required = true) @Valid @RequestBody CardapioDTO cardapioDTO) {
        Validation.max("id", id, 99999999L);
        Cardapio cardapio = cardapioMapper.toEntity(cardapioDTO);
        cardapio.setId(id.longValue());
        Cardapio cardapioSaved = cardapioService.salvar(cardapio);
        return ResponseEntity.ok(cardapioMapper.toDTO(cardapioSaved));
    }

    /**
     * Retorna a instância de {@link CardapioDTO} pelo id informado.
     *
     * @param id
     * s@return
     */
    @PreAuthorize("hasRole('ROLE_AMIGO_PESQUISAR')")
    @ApiOperation(value = "Retorna as informações do Cardapio pelo id informado.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CardapioDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@ApiParam(value = "Código do Cardapio", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Cardapio cardapio = cardapioService.getById(id.longValue());
        CardapioDTO cardapioDTO = cardapioMapper.toDTO(cardapio);

        return ResponseEntity.ok(cardapioDTO);
    }

    /**
     * Retorna a buscar de {@link Cardapio} por {@link FiltroCardapioDTO}
     *
     * @param filtroCardapioDTO
     * @return
     */
    @PreAuthorize("hasRole('ROLE_CARDAPIO_PESQUISAR')")
    @ApiOperation(value = "Pesquisa de Cardapio.",
            notes = "Recupera as informações de Cardapio conforme dados informados no filtro de busca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CardapioDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @GetMapping(path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroCardapioDTO filtroCardapioDTO) {
        List<CardapioDTO> cardapiosDTO = new ArrayList<>();
        List<Cardapio> cardapios = cardapioService.getCardapiosByFiltro(filtroCardapioDTO);
        if(cardapios.size() > 0){
            for (Cardapio g:
             cardapios) {
                CardapioDTO cardapioDTO = cardapioMapper.toDTO(g);
                cardapiosDTO.add(cardapioDTO);
            }
        }

        return ResponseEntity.ok(cardapiosDTO);
    }

    /**
     * Retorna uma lista de {@link CardapioDTO} cadastrados.
     *
     * @return
     */
    @PreAuthorize("hasRole('ROLE_AMIGO_PESQUISAR')")
    @ApiOperation(value = "Retorna uma lista de Cardapios cadastrados.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CardapioDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getCardapios() {
        List<Cardapio> cardapios = cardapioService.getTodos();
        List<CardapioDTO> cardapiosDTO = new ArrayList<>();
        for (Cardapio cardapio : cardapios) {
            CardapioDTO cardapioDTO = cardapioMapper.toDTO(cardapio);
            cardapiosDTO.add(cardapioDTO);
        }
        return ResponseEntity.ok(cardapiosDTO);
    }

    /**
     * Remover o {@link Cardapio} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_AMIGO_REMOVER')")
    @ApiOperation(value = "Remove um Cardapio pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CardapioDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @DeleteMapping(path = "/{id:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> remover(@ApiParam(value = "Id do Cardapio", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Cardapio cardapio = cardapioService.remover(id.longValue());
        return ResponseEntity.ok(cardapioMapper.toDTO(cardapio));
    }

    /**
     * Tornar Cardapio do {@link Cardapio} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_AMIGO_STATUS')")
    @ApiOperation(value = "Tonar Cardapio do Cardapio pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CardapioDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}/tornar-cardapio", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> tonarCardapio(@ApiParam(value = "Id do Cardapio", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Cardapio cardapio = cardapioService.getById(id.longValue());
        cardapio.setBordaRecheada(StatusSimNao.SIM);
        cardapioService.salvar(cardapio);
        return ResponseEntity.ok(cardapioMapper.toDTO(cardapio));
    }

    /**
     * Deixar de Ser cardapio Cardapio do {@link Cardapio} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_AMIGO_STATUS')")
    @ApiOperation(value = "Deixar de ser Cardapio do Cardapio pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CardapioDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}/deixar-cardapio", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> deixarCardapio(@ApiParam(value = "Id do Cardapio", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Cardapio cardapio = cardapioService.getById(id.longValue());
        cardapio.setBordaRecheada(StatusSimNao.NAO);
        cardapioService.salvar(cardapio);
        return ResponseEntity.ok(cardapioMapper.toDTO(cardapio));
    }

}
