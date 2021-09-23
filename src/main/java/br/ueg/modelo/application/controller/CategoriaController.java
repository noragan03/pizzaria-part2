package br.ueg.modelo.application.controller;

import br.ueg.modelo.api.util.Validation;
import br.ueg.modelo.application.dto.FiltroCategoriaDTO;
import br.ueg.modelo.application.dto.CategoriaDTO;
import br.ueg.modelo.application.mapper.CategoriaMapper;
import br.ueg.modelo.application.model.Grupo;
import br.ueg.modelo.application.model.Categoria;
import br.ueg.modelo.application.service.CategoriaService;
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

@Api(tags = "Categoria API")
@RestController
@RequestMapping(path = "${app.api.base}/categoria")
public class CategoriaController extends AbstractController {

    @Autowired
    private CategoriaMapper categoriaMapper;

    @Autowired
    private CategoriaService categoriaService;

    @PreAuthorize("hasRole('ROLE_CATEGORIA_INCLUIR')")
    @PostMapping
    @ApiOperation(value = "Inclusão/alteração de categoria.",
            notes = "Incluir/Alterar Categoria.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CategoriaDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    public ResponseEntity<?> incluir(@ApiParam(value = "Informações de Categoria", required = true) @Valid @RequestBody CategoriaDTO categoriaDTO) {
            Categoria grupo = categoriaMapper.toEntity(categoriaDTO);
            return ResponseEntity.ok(categoriaMapper.toDTO(categoriaService.salvar(grupo)));
    }

    /**
     * Altera a instância de {@link CategoriaDTO} na base de dados.
     *
     * @param id
     * @param categoriaDTO
     * @return
     */

    @PreAuthorize("hasRole('ROLE_CATEGORIA_ALTERAR')")
    @ApiOperation(value = "Altera as informações de Categoria.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CategoriaDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(
            @ApiParam(value = "Código do Categoria", required = true) @PathVariable final BigDecimal id,
            @ApiParam(value = "Informações do Categoria", required = true) @Valid @RequestBody CategoriaDTO categoriaDTO) {
        Validation.max("id", id, 99999999L);
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        categoria.setId(id.longValue());
        Categoria categoriaSaved = categoriaService.salvar(categoria);
        return ResponseEntity.ok(categoriaMapper.toDTO(categoriaSaved));
    }

    /**
     * Retorna a instância de {@link CategoriaDTO} pelo id informado.
     *
     * @param id
     * s@return
     */
    @PreAuthorize("hasRole('ROLE_CATEGORIA_PESQUISAR')")
    @ApiOperation(value = "Retorna as informações do Categoria pelo id informado.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CategoriaDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@ApiParam(value = "Código do Grupo", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Categoria categoria = categoriaService.getById(id.longValue());
        CategoriaDTO categoriaDTO = categoriaMapper.toDTO(categoria);

        return ResponseEntity.ok(categoriaDTO);
    }

    /**
     * Retorna a buscar de {@link Categoria} por {@link FiltroCategoriaDTO}
     *
     * @param filtroCategoriaDTO
     * @return
     */
    @PreAuthorize("hasRole('ROLE_CATEGORIA_PESQUISAR')")
    @ApiOperation(value = "Pesquisa de Categoria.",
            notes = "Recupera as informações de Categoria conforme dados informados no filtro de busca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CategoriaDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
  
    @GetMapping(path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroCategoriaDTO filtroCategoriaDTO) {
        List<CategoriaDTO> categoriasDTO = new ArrayList<>();
        List<Categoria> categorias = categoriaService.getCategoriasByFiltro(filtroCategoriaDTO);
        if(categorias.size() > 0){
            for (Categoria g:
             categorias) {
                CategoriaDTO categoriaDTO = categoriaMapper.toDTO(g);
                categoriasDTO.add(categoriaDTO);
            }
        }

        return ResponseEntity.ok(categoriasDTO);
    }

    /**
     * Retorna uma lista de {@link CategoriaDTO} cadastrados.
     *
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Retorna uma lista de Categorias cadastrados.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CategoriaDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getCategorias() {
        List<Categoria> categorias = categoriaService.getTodos();
        List<CategoriaDTO> categoriasDTO = new ArrayList<>();
        for (Categoria categoria : categorias) {
            CategoriaDTO categoriaDTO = categoriaMapper.toDTO(categoria);
            categoriasDTO.add(categoriaDTO);
        }
        return ResponseEntity.ok(categoriasDTO);
    }

    /**
     * Ativa o {@link Grupo} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_CATEGORIA_REMOVER')")
    @ApiOperation(value = "Remove um Categoria pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CategoriaDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @DeleteMapping(path = "/{id:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> remover(@ApiParam(value = "Id do Categoria", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Categoria categoria = categoriaService.remover(id.longValue());
        return ResponseEntity.ok(categoria);
    }

}
