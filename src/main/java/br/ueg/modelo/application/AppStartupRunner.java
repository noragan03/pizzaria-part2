package br.ueg.modelo.application;

import br.ueg.modelo.application.enums.StatusAtivoInativo;
import br.ueg.modelo.application.enums.StatusSimNao;
import br.ueg.modelo.application.model.*;
import br.ueg.modelo.application.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Classe para ser executado após o Spring Inicializar.
 * Verificado o modo de start para spring.jpa.hibernate.ddl-auto==create-drop
 */

@Component
public class AppStartupRunner implements ApplicationRunner {

    public static final String NONE="none";
    public static final String CREATE_DROP="create-drop";
    public static final String CREATE = "create";
    public static final String UPDATE = "update";

    private static final Logger LOG =
            LoggerFactory.getLogger(AppStartupRunner.class);


    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Autowired
    ModuloRepository moduloRepository;

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TipoAmigoRepository tipoAmigoRepository;

    @Autowired
    AmigoRepository amigoRepository;
    
    @Autowired
    CategoriaRepository categoriaRepository;
    
    @Autowired
    CardapioRepository cardapioRepository;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOG.info("Application started with option names : {}",
                args.getOptionNames());
        LOG.info("spring.jpa.hibernate.ddl-auto={}",ddlAuto);

        if(this.ddlAuto.trim().equals(CREATE_DROP) ||
                this.ddlAuto.trim().equals(CREATE) ||
                this.ddlAuto.trim().equals(UPDATE)
        ){
            this.initiateDemoInstance();
        }
    }

    private void initiateDemoInstance() {
        LOG.info("initiateDemoInstance");
        Modulo moduloUsuario = createModuloCrud("USUARIO", "Manter Usuário");

        Modulo moduloGrupo = createModuloCrud("GRUPO", "Manter Grupo");

        Modulo moduloTipoAmigo = createModuloTipoAmigo();

        Modulo moduloAmigo = createModuloAmigo();

        Modulo moduloCategoria = createModuloCategoria();

        Modulo moduloCardapio = createModuloCardapio();

        Grupo grupo = createGrupoAdmin(Arrays.asList(moduloUsuario, moduloGrupo,moduloTipoAmigo, moduloAmigo, moduloCategoria, moduloCardapio));

        createUsuarioAdmin(grupo);

        createTipoAmigos();
        createAmigos();
        createCategorias();
        createCardapios();

    }

    /**
     * cria dados de amigo para tese
     */
    private void createAmigos() {

        TipoAmigo tipoAmigo = tipoAmigoRepository.findById(1L).get();
        TipoAmigo tipoConhecido = tipoAmigoRepository.findById(2L).get();

        Amigo amigo = new Amigo();
        amigo.setAmigo(StatusSimNao.SIM);
        amigo.setDataAmizade(LocalDate.now());
        amigo.setNome("Primeiro Amigo");
        amigo.setTipo(tipoAmigo);

        amigoRepository.save(amigo);

        Amigo conhecido = new Amigo();
        conhecido.setAmigo(StatusSimNao.SIM);
        conhecido.setDataAmizade(LocalDate.now());
        conhecido.setNome("Primeiro Conhecido");
        conhecido.setTipo(tipoConhecido);

        amigoRepository.save(conhecido);

    }

    /**
     * Cria dados de tipos de amigos para teste
     */
    private void createTipoAmigos() {
        TipoAmigo tipoAmigo=new TipoAmigo();
        tipoAmigo.setNome("Amigo");
        tipoAmigoRepository.save(tipoAmigo);

        TipoAmigo tipoConhecido = new TipoAmigo();
        tipoConhecido.setNome("Conhecido");
        tipoAmigoRepository.save(tipoConhecido);

        TipoAmigo tipoMelhorAmigo = new TipoAmigo();
        tipoMelhorAmigo.setNome("Melhor Amigo");
        tipoAmigoRepository.save(tipoMelhorAmigo);
    }

    private void createCardapios() {

        Categoria categoria = categoriaRepository.findById(1L).get();
        Categoria tipoCategoria = categoriaRepository.findById(2L).get();

        Cardapio pizzaSal = new Cardapio();
        pizzaSal.setBordaRecheada(StatusSimNao.SIM);
        pizzaSal.setIngredientes("Molho,queijo,pepperoni");
        pizzaSal.setData(LocalDate.now());
        pizzaSal.setSabor("Pepperoni");
        pizzaSal.setPreco(10.0);
        pizzaSal.setCategoria(categoria);

        cardapioRepository.save(pizzaSal);

        Cardapio pizzaDoce = new Cardapio();
        pizzaDoce.setBordaRecheada(StatusSimNao.SIM);
        pizzaDoce.setData(LocalDate.now());
        pizzaDoce.setIngredientes("Queijo e chocolate");
        pizzaDoce.setPreco(10.0);
        pizzaDoce.setSabor("Chocolate");
        pizzaDoce.setCategoria(tipoCategoria);

        cardapioRepository.save(pizzaDoce);

    }
    private void createCategorias() {
        Categoria tipoSalgada=new Categoria();
        tipoSalgada.setCategoria("Salgada");
        categoriaRepository.save(tipoSalgada);

        Categoria tipoDoce = new Categoria();
        tipoDoce.setCategoria("Doce");
        categoriaRepository.save(tipoDoce);

//        Categoria tipoVegetariana = new Categoria();
//        tipoVegetariana.setCategoria("Vegetariana");
//        categoriaRepository.save(tipoVegetariana);
//
//        Categoria tipoVegana = new Categoria();
//        tipoVegana.setCategoria("Vegana");
//        categoriaRepository.save(tipoVegana);
    }
    /**
     * Cria o Modulo de tipo de amigo e salva.
     * @return tipo amigo salvo no banco.
     */
    private Modulo createModuloTipoAmigo() {
        Modulo moduloTipoAmigo = new Modulo();

        moduloTipoAmigo.setMnemonico("TIPOAMIGO");
        moduloTipoAmigo.setNome("Manter Tipo Amigo ");
        moduloTipoAmigo.setStatus(StatusAtivoInativo.ATIVO);
        moduloTipoAmigo = moduloRepository.save(moduloTipoAmigo);

        Set<Funcionalidade> funcionalidades = getFuncionalidadesCrud().stream()
                .filter(
                        funcionalidade -> !funcionalidade.getMnemonico().equals("ATIVAR_INATIVAR")
                ).collect(Collectors.toSet());

        Funcionalidade fManter = new Funcionalidade();
        fManter.setMnemonico("REMOVER");
        fManter.setNome("Remover");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);


        for(Funcionalidade funcionalidade: funcionalidades){
            funcionalidade.setModulo(moduloTipoAmigo);
        }

        moduloTipoAmigo.setFuncionalidades(funcionalidades);
        moduloTipoAmigo = moduloRepository.save(moduloTipoAmigo);

        return moduloTipoAmigo;
    }

    /**
     * Cria o Modulo de amigo e salva.
     * @return tipo amigo salvo no banco.
     */
    private Modulo createModuloAmigo() {
        Modulo moduloAmigo = new Modulo();

        moduloAmigo.setMnemonico("AMIGO");
        moduloAmigo.setNome("Manter Amigo ");
        moduloAmigo.setStatus(StatusAtivoInativo.ATIVO);
        moduloAmigo = moduloRepository.save(moduloAmigo);

        Set<Funcionalidade> funcionalidades = getFuncionalidadesCrud().stream()
                .filter(
                        funcionalidade -> !funcionalidade.getMnemonico().equals("ATIVAR_INATIVAR")
                ).collect(Collectors.toSet());

        Funcionalidade fManter = new Funcionalidade();
        fManter.setMnemonico("REMOVER");
        fManter.setNome("Remover");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        Funcionalidade fAmigo = new Funcionalidade();
        fAmigo.setMnemonico("STATUS");
        fAmigo.setNome("É Amigo");
        fAmigo.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fAmigo);


        for(Funcionalidade funcionalidade: funcionalidades){
            funcionalidade.setModulo(moduloAmigo);
        }

        moduloAmigo.setFuncionalidades(funcionalidades);
        moduloAmigo = moduloRepository.save(moduloAmigo);

        return moduloAmigo;
    }
    /**
     * Cria o Modulo de categoria e salva.
     * @return categoria salvo no banco.
     */
    private Modulo createModuloCategoria() {
        Modulo moduloCategoria = new Modulo();

        moduloCategoria.setMnemonico("CATEGORIA");
        moduloCategoria.setNome("Manter Categoria ");
        moduloCategoria.setStatus(StatusAtivoInativo.ATIVO);
        moduloCategoria = moduloRepository.save(moduloCategoria);

        Set<Funcionalidade> funcionalidades = getFuncionalidadesCrud().stream()
                .filter(
                        funcionalidade -> !funcionalidade.getMnemonico().equals("ATIVAR_INATIVAR")
                ).collect(Collectors.toSet());

        Funcionalidade fManter = new Funcionalidade();
        fManter.setMnemonico("REMOVER");
        fManter.setNome("Remover");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);


        for(Funcionalidade funcionalidade: funcionalidades){
            funcionalidade.setModulo(moduloCategoria);
        }

        moduloCategoria.setFuncionalidades(funcionalidades);
        moduloCategoria = moduloRepository.save(moduloCategoria);

        return moduloCategoria;
    }

    /**
     * Cria o Modulo de cardapio e salva.
     * @return cardapio salvo no banco.
     */
    private Modulo createModuloCardapio() {
        Modulo moduloCardapio = new Modulo();

        moduloCardapio.setMnemonico("CARDAPIO");
        moduloCardapio.setNome("Manter Cardapio ");
        moduloCardapio.setStatus(StatusAtivoInativo.ATIVO);
        moduloCardapio = moduloRepository.save(moduloCardapio);

        Set<Funcionalidade> funcionalidades = getFuncionalidadesCrud().stream()
                .filter(
                        funcionalidade -> !funcionalidade.getMnemonico().equals("ATIVAR_INATIVAR")
                ).collect(Collectors.toSet());

        Funcionalidade fManter = new Funcionalidade();
        fManter.setMnemonico("REMOVER");
        fManter.setNome("Remover");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        Funcionalidade fCardapio = new Funcionalidade();
        fCardapio.setMnemonico("STATUS");
        fCardapio.setNome("Possui borda recheada");
        fCardapio.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fCardapio);


        for(Funcionalidade funcionalidade: funcionalidades){
            funcionalidade.setModulo(moduloCardapio);
        }

        moduloCardapio.setFuncionalidades(funcionalidades);
        moduloCardapio = moduloRepository.save(moduloCardapio);

        return moduloCardapio;
    }
    private void createUsuarioAdmin(Grupo grupo) {
        Usuario usuario = new Usuario();
        usuario.setStatus(StatusAtivoInativo.ATIVO);
        usuario.setDataCadastrado(LocalDate.now());
        usuario.setDataAtualizado(LocalDate.now());
        usuario.setTelefones(new HashSet<>());
        usuario.setCpf("10010010017");
        usuario.setLogin("admin");
        usuario.setNome("Administrador");
        usuario.setEmail("admin@teste.com.br");
        usuario.setSenha(new BCryptPasswordEncoder().encode("admin"));

        usuario = usuarioRepository.save(usuario);

        Set<UsuarioGrupo> usuarioGrupos = new HashSet<>();
        usuarioGrupos.add(new UsuarioGrupo(null,usuario,grupo));
        usuario.setGrupos(usuarioGrupos);
        usuario = usuarioRepository.save(usuario);
    }

    private Grupo createGrupoAdmin(List<Modulo> modulos) {
        Grupo grupo = new Grupo();
        grupo.setNome("Administradores");
        grupo.setAdministrador(StatusSimNao.SIM);
        grupo.setDescricao("Grupo de Administradores");
        grupo.setStatus(StatusAtivoInativo.ATIVO);
        grupo = grupoRepository.save(grupo);
        final Grupo lGrupo = grupo;
        grupo.setGrupoFuncionalidades(new HashSet<>());

        modulos.forEach(modulo -> {
            lGrupo.getGrupoFuncionalidades().addAll(
                    modulo.getFuncionalidades().stream().map(
                            funcionalidade -> new GrupoFuncionalidade(null, lGrupo, funcionalidade)
                    ).collect(Collectors.toSet())
            );
        });

        grupoRepository.save(grupo);
        return grupo;
    }

    private Modulo createModuloCrud(String moduloMNemonico, String moduloNome) {
        Modulo moduloUsuario = new Modulo();

        moduloUsuario.setMnemonico(moduloMNemonico);
        moduloUsuario.setNome(moduloNome);
        moduloUsuario.setStatus(StatusAtivoInativo.ATIVO);
        moduloUsuario = moduloRepository.save(moduloUsuario);

        final Modulo lModuloUsuario = moduloUsuario;
        Set<Funcionalidade> funcionaldiades = getFuncionalidadesCrud();

/*        funcionaldiades.stream().map(
                funcionalidade -> {
                    funcionalidade.setModulo(lModuloUsuario);
                    return funcionalidade;
                }).collect(Collectors.toSet());
        // equivalente com for*/
        for(Funcionalidade funcionalidade: funcionaldiades){
            funcionalidade.setModulo(moduloUsuario);
        }

        moduloUsuario.setFuncionalidades(funcionaldiades);
        moduloUsuario = moduloRepository.save(moduloUsuario);
        return moduloUsuario;
    }

    /**
     * retorna Funcionalidades padrão de um CRRUD
     * @return
     */
    private Set<Funcionalidade> getFuncionalidadesCrud() {
        Set<Funcionalidade> funcionalidades = new HashSet<>();

        Funcionalidade fManter = new Funcionalidade();
        fManter.setMnemonico("INCLUIR");
        fManter.setNome("Incluir");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        fManter = new Funcionalidade();
        fManter.setMnemonico("ALTERAR");
        fManter.setNome("Alterar");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        fManter = new Funcionalidade();
        fManter.setMnemonico("ATIVAR_INATIVAR");
        fManter.setNome("Ativar/Inativar");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        fManter = new Funcionalidade();
        fManter.setMnemonico("PESQUISAR");
        fManter.setNome("Pesquisar");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        fManter = new Funcionalidade();
        fManter.setMnemonico("VISUALIZAR");
        fManter.setNome("Visualizar");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);
        return funcionalidades;
    }
}
