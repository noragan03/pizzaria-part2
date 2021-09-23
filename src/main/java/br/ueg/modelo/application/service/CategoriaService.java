package br.ueg.modelo.application.service;

import br.ueg.modelo.application.configuration.Constante;
import br.ueg.modelo.application.dto.FiltroGrupoDTO;
import br.ueg.modelo.application.dto.FiltroCategoriaDTO;
import br.ueg.modelo.application.enums.StatusAtivoInativo;
import br.ueg.modelo.application.exception.SistemaMessageCode;
import br.ueg.modelo.application.model.Grupo;
import br.ueg.modelo.application.model.GrupoFuncionalidade;
import br.ueg.modelo.application.model.Categoria;
import br.ueg.modelo.application.repository.GrupoFuncionalidadeRepository;
import br.ueg.modelo.application.repository.CategoriaRepository;
import br.ueg.modelo.comum.exception.BusinessException;
import br.ueg.modelo.comum.util.CollectionUtil;
import br.ueg.modelo.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * Retorna uma lista de {@link Categoria} conforme o filtro de pesquisa informado.
     *
     * @param filtroDTO
     * @return
     */
    public List<Categoria> getCategoriasByFiltro(final FiltroCategoriaDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<Categoria> grupos = categoriaRepository.findAllByFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(grupos)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return grupos;
    }

    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Grupo, verifica se tem pelo meno 4 caracteres.
     *
     * @param filtroDTO
     */
    private void validarCamposObrigatoriosFiltro(final FiltroCategoriaDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;

        if (!Util.isEmpty(filtroDTO.getCategoria())) {
            vazio = Boolean.FALSE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
    }

    /**
     * Retorna uma lista de {@link Categoria} cadatrados .
     *
     * @return
     */
    public List<Categoria> getTodos() {
        List<Categoria> categorias = categoriaRepository.findAll();

        if (CollectionUtil.isEmpty(categorias)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return categorias;
    }

    /**
     * Retorno um a {@link Categoria} pelo Id informado.
     * @param id - id to tipo Amigo
     * @return
     */
    public Categoria getById(Long id){
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);

        if(!categoriaOptional.isPresent()){
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return categoriaOptional.get();
    }

    /**
     * Salva a instânica de {@link Categoria} na base de dados conforme os critérios
     * especificados na aplicação.
     *
     * @param categoria
     * @return
     */
    public Categoria salvar(Categoria categoria) {
        validarCamposObrigatorios(categoria);
        validarDuplicidade(categoria);

        Categoria grupoSaved = categoriaRepository.save(categoria);
        return grupoSaved;
    }

    public Categoria remover(Long id){
        Categoria categoria = this.getById(id);

        categoriaRepository.delete(categoria);

        return categoria;
    }



    /**
     * Verifica se os Campos Obrigatórios foram preenchidos.
     *
     * @param categoria
     */
    private void validarCamposObrigatorios(final Categoria categoria) {
        boolean vazio = Boolean.FALSE;

        if (Util.isEmpty(categoria.getCategoria())) {
            vazio = Boolean.TRUE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
    }

    /**
     * Verifica se o Categoria a ser salvo já existe na base de dados.
     *
     * @param categoria
     */
    private void validarDuplicidade(final Categoria categoria) {
        Long count = categoriaRepository.countByNomeAndNotId(categoria.getCategoria(), categoria.getId());

        if (count > BigDecimal.ZERO.longValue()) {
            throw new BusinessException(SistemaMessageCode.ERRO_CATEGORIA_DUPLICADA);
        }
    }

}
