package school.sptech.zup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.domain.ZupLog;
import school.sptech.zup.dto.PesoFormulario;
import school.sptech.zup.dto.UsuarioAdminPutRequest;
import school.sptech.zup.dto.UsuarioComumPutRequestBody;
import school.sptech.zup.dto.UsuarioEmpresaPutRequestBody;
import school.sptech.zup.dto.obj.ListaObj;
import school.sptech.zup.dto.obj.UsuarioObj;
import school.sptech.zup.dto.request.CadastroDadosInfluencerRequest;
import school.sptech.zup.dto.request.FotoRequest;
import school.sptech.zup.dto.request.PerguntasPerfilRequest;
import school.sptech.zup.dto.response.AnalisePerfilResponse;
import school.sptech.zup.dto.response.FotoResponse;
import school.sptech.zup.dto.response.PerfilUsuarioResponse;
import school.sptech.zup.repository.UsuarioRepository;
import school.sptech.zup.repository.ZupLogRepository;
import school.sptech.zup.service.AutenticacaoJWT.UsuarioLoginDto;
import school.sptech.zup.service.Mappings.Mappings;
import school.sptech.zup.util.DateUtil;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository _usuarioRepository;
    private final Mappings _mappingPerfilUsuario;
    private final DateUtil _dateUtil;
    private final ZupLogRepository _zupLogRepository;
    public ListaObj<UsuarioObj> getListUsuario(){
        List<Usuario> usuarioConsulta = _usuarioRepository.findAll();
        if (usuarioConsulta.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Usuario não encontrado");
        }
        ListaObj<UsuarioObj> listaUsuario = new ListaObj(usuarioConsulta.size());
        for (int i = 0; i < usuarioConsulta.size(); i++){
            UsuarioObj usuarioObj = new UsuarioObj();

            usuarioObj.setId(usuarioConsulta.get(i).getId());
            usuarioObj.setNome(usuarioConsulta.get(i).getNome());
            usuarioObj.setEmail(usuarioConsulta.get(i).getEmail());
            usuarioObj.setUsername(usuarioConsulta.get(i).getUsername());
            usuarioObj.setSenha(usuarioConsulta.get(i).getSenha());
            usuarioObj.setInfluencer(usuarioConsulta.get(i).isInfluencer());
            usuarioObj.setAutenticado(usuarioConsulta.get(i).getAutenticado());
            usuarioObj.setCpf(usuarioConsulta.get(i).getCpf());
            usuarioObj.setCnpj(usuarioConsulta.get(i).getCnpj());

            listaUsuario.adiciona(usuarioObj);
        }
        UsuarioObj aux = new UsuarioObj();
        for (int a = 0; a < listaUsuario.getTamanho(); a++){
            for (int b = a + 1; b < listaUsuario.getTamanho(); b++){
                var comparacao = listaUsuario.getElemento(a).getNome().compareToIgnoreCase(listaUsuario.getElemento(b).getNome());
                if (comparacao > 0){
                    aux = listaUsuario.getElemento(a);
                    listaUsuario.setElemento(a, listaUsuario.getElemento(b));
                    listaUsuario.setElemento(b, aux);
                }
            }
        }
        return listaUsuario;
    }
    public Usuario getUsername(UsuarioLoginDto loginDto) {
        var consulta = buscaPorUsername(loginDto.getUsername());
        if (consulta.getSenha().equals(loginDto.getSenha())){
            return consulta;
        }
        return consulta;
    }
    public Usuario buscaPorUsername(String username){
        List<Usuario> usuarioConsulta = _usuarioRepository.findAll();
        for (int i = 0; i < usuarioConsulta.size(); i++) {
            if (usuarioConsulta.get(i).getUsername().equals(username)){
                return usuarioConsulta.get(i);
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado");
    }
    public byte[] BuscarImagemPorId(@PathVariable Long idFoto){
        var consulta = _usuarioRepository.findAll();
        for (int i = 0; i < consulta.size(); i++){
            if (consulta.get(i).getId() == idFoto){
                return consulta.get(i).getFoto();
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Imagem não encontrado");
    }
    public Usuario buscaPorId(Long id){
        Optional<Usuario> usuarioConsulta = _usuarioRepository.findById(id);
        if (usuarioConsulta.isPresent()){
            return usuarioConsulta.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado");
    }
    public Usuario atualizarUsuarioComum(UsuarioComumPutRequestBody usuarioPutRequestBody) {
        var consulta = buscaPorId(usuarioPutRequestBody.getId());
            Usuario usuario = Usuario.builder()
                    .id(consulta.getId())
                    .nome(usuarioPutRequestBody.getNome())
                    .email(usuarioPutRequestBody.getEmail())
                    .username(usuarioPutRequestBody.getUsername())
                    .senha(usuarioPutRequestBody.getSenha())
                    .autenticado(usuarioPutRequestBody.getAutenticado())
                    .influencer(usuarioPutRequestBody.isInfluencer())
                    .cpf(usuarioPutRequestBody.getCpf())
                    .cnpj(null)
                    .build();
            _usuarioRepository.save(usuario);
            return usuario;
    }
    public Usuario atualizarUsuarioEmpresa(UsuarioEmpresaPutRequestBody usuarioPutRequestBody) {
        var consulta = buscaPorId(usuarioPutRequestBody.getId());
            Usuario usuario = Usuario.builder()
                    .id(consulta.getId())
                    .nome(usuarioPutRequestBody.getNome())
                    .email(usuarioPutRequestBody.getEmail())
                    .username(usuarioPutRequestBody.getUsername())
                    .senha(usuarioPutRequestBody.getSenha())
                    .autenticado(usuarioPutRequestBody.getAutenticado())
                    .influencer(usuarioPutRequestBody.isInfluencer())
                    .cnpj(usuarioPutRequestBody.getCnpj())
                    .cpf(null)
                    .build();
            _usuarioRepository.save(usuario);
            return usuario;
    }
    public Usuario atualizarUsuarioAdmin(UsuarioAdminPutRequest usuarioPutRequestBody) {
        var consulta = buscaPorId(usuarioPutRequestBody.getId());
            Usuario usuario = Usuario.builder()
                    .id(consulta.getId())
                    .nome(usuarioPutRequestBody.getNome())
                    .email(usuarioPutRequestBody.getEmail())
                    .username(usuarioPutRequestBody.getUsername())
                    .senha(usuarioPutRequestBody.getSenha())
                    .autenticado(null)
                    .influencer(usuarioPutRequestBody.isInfluencer())
                    .cnpj(null)
                    .cpf(null)
                    .Admin(usuarioPutRequestBody.getAdmin())
                    .build();
            _usuarioRepository.save(usuario);
            return usuario;
    }
    public Usuario deleteUser(long id) {
        var retorno = buscaPorId(id);
            retorno.setAutenticado(false);
            return retorno;
    }
    public List<PerfilUsuarioResponse> getPerfis(Long IdPerfil) {
        var consultaPerfil = _usuarioRepository.BuscaUsuarioTpPerfil(IdPerfil);

        if (consultaPerfil.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil não encontrado");
        }
        var UsuarioPerfilResponse = _mappingPerfilUsuario.MappingPerfilUsuarioInfluencer(consultaPerfil);
        return UsuarioPerfilResponse;
    }
    public PerfilUsuarioResponse salvarPerfilUsuario(PerguntasPerfilRequest perguntas) {

        var medirPesoFormulario = pesoFormulario(perguntas);

        if (medirPesoFormulario == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao tentar salvar Formulario");

        var analiseFinalFormulario = analiseFormulario(medirPesoFormulario);

        if (analiseFinalFormulario != null){
            Long idUsuario = Long.parseLong(perguntas.getIdUsuario());
           var buscaUsuario =  _usuarioRepository.findById(idUsuario);

           var Mapping = _mappingPerfilUsuario.MappingAtualizacaoUsuario(buscaUsuario, analiseFinalFormulario);
           var MappingRetornoMobile =_mappingPerfilUsuario.MappingUsuarioRetorno(Mapping);

           return MappingRetornoMobile;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao tentar salvar Formulario");
    }
    private AnalisePerfilResponse analiseFormulario(PesoFormulario medirPesoFormulario) {
        Integer conta = (contaPeso(medirPesoFormulario));

        AnalisePerfilResponse analise = new AnalisePerfilResponse();

        if (conta < 4){
            analise.setIdPerfil(1L);
            analise.setPerfil("Conservador");
        }
        else if (conta > 3 && conta < 7){
            analise.setIdPerfil(2L);
            analise.setPerfil("Moderado");
        }
        else{
            analise.setIdPerfil(3L);
            analise.setPerfil("Agressivo");
        }

        ZupLog log = new ZupLog();
        log.setDescricao("Id= " + conta + ", " + analise.getPerfil());
        log.setDt_entrada(
                _dateUtil.formLocalDate(LocalDateTime.now())
        );
        _zupLogRepository.save(log);

        return analise;
    }
    private Integer contaPeso(PesoFormulario medirPesoFormulario) {
        var conta = medirPesoFormulario.getPesoTela1() + medirPesoFormulario.getPesoTela2() +
                medirPesoFormulario.getPesoTela3() + medirPesoFormulario.getPesoTela4() +
                medirPesoFormulario.getPesoTela5();
        return conta;
    }
    private PesoFormulario pesoFormulario(PerguntasPerfilRequest perguntas) {
        var pesoTela1 = 0;
        var pesoTela2 = 0;
        var pesoTela3 = 0;
        var pesoTela4 = 0;
        var pesoTela5 = 0;

        // Definição Pesos da Tela Formulario Mobile 1
        pesoTela1 = perguntas.getTela1().equals("Sim") ? 1 : 0;

        // Definição Pesos da Tela Formulario Mobile 2
        if (perguntas.getTela2().equals("R$ 0,00 - R$ 1000,00")) pesoTela2 = 0;
        else if (perguntas.getTela2().equals("R$ 1000,00 - R$ 5000,00")) pesoTela2 = 1;
        else if (perguntas.getTela2().equals("R$ 5000,00 ou mais")) pesoTela2 = 2;

        // Definição Pesos da Tela Formulario Mobile 3
        if (perguntas.getTela3().equals("Venderia na hora")) pesoTela3 = 0;
        else if (perguntas.getTela3().equals("Esperaria recuperar o valor perdido e venderia")) pesoTela3 = 1;
        else if (perguntas.getTela3().equals("Compraria mais")) pesoTela3 = 2;


        // Definição Pesos da Tela Formulario Mobile 4
        if (perguntas.getTela4().equals("Sim")) pesoTela4 = 0;
        else if (perguntas.getTela4().equals("Não acredito muito na variação de mercado")) pesoTela4 = 1;
        else if (perguntas.getTela4().equals("Não, acredito na variável do mercado")) pesoTela4 = 2;


        // Definição Pesos da Tela Formulario Mobile 5
        if (perguntas.getTela5().equals("Somente visitar, gostei das notícias daqui e é bem útil pro meu dia a dia")) pesoTela5 = 0;
        else if (perguntas.getTela5().equals("Investir a partir do que consumir daqui")) pesoTela5 = 1;
        else if (perguntas.getTela5().equals("Fazer análises minuciosas junto com os indicadores disponíveis na ZUP")) pesoTela5 = 2;

        PesoFormulario pesoFormulario = new PesoFormulario();

        pesoFormulario.setPesoTela1(pesoTela1);
        pesoFormulario.setPesoTela2(pesoTela2);
        pesoFormulario.setPesoTela3(pesoTela3);
        pesoFormulario.setPesoTela4(pesoTela4);
        pesoFormulario.setPesoTela5(pesoTela5);

        return pesoFormulario;
    }

    public List<PerfilUsuarioResponse> getTodosInfluencers() {
        var buscaTodos = _usuarioRepository.BuscaTodosUsuariosInfluencers();

        if (buscaTodos.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao tentar salvar Formulario");

        return _mappingPerfilUsuario.MappingTodosInfluencers(buscaTodos);
    }


    public Boolean salvarDadosInfleuncer(Usuario buscaUsuario, CadastroDadosInfluencerRequest cadastro){

        var mapping = _mappingPerfilUsuario.MappingDadosInfluencer(buscaUsuario, cadastro);

        if (mapping.getId() == buscaUsuario.getId()) return true;

        return false;
    }

    public FotoResponse salvarFoto(Long idUsuario, FotoRequest foto) {

        var mapping = _mappingPerfilUsuario.mappingsalvarFoto(idUsuario, foto);

        if (mapping != null){
            return mapping;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível salvar a foto");
    }
}
