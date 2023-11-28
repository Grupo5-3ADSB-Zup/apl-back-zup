package school.sptech.zup.service.Mappings;

import lombok.Data;
import org.springframework.stereotype.Service;
import school.sptech.zup.domain.Comentario;
import school.sptech.zup.domain.Noticia;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.domain.ZupLog;
import school.sptech.zup.dto.request.CadastroDadosInfluencerRequest;
import school.sptech.zup.dto.request.FotoRequest;
import school.sptech.zup.dto.request.PerguntasPerfilRequest;
import school.sptech.zup.dto.request.PesoComentariosRequest;
import school.sptech.zup.dto.response.*;
import school.sptech.zup.repository.ComentarioRepository;
import school.sptech.zup.repository.CurtidaRepository;
import school.sptech.zup.repository.UsuarioRepository;
import school.sptech.zup.repository.ZupLogRepository;
import school.sptech.zup.util.DateUtil;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Service
public class Mappings {
    private final DateUtil _dateUtil;
    private final ComentarioRepository _comentarioRepository;
    private final CurtidaRepository _curtidaRepository;
    private final UsuarioRepository _usuarioRepository;
    private final ZupLogRepository _zupLogRepository;
    public List<PerfilUsuarioResponse> MappingPerfilUsuarioInfluencer(List<Usuario> usuarios){

        List<PerfilUsuarioResponse> listaUsuariosPerfis = new ArrayList<>();
        for (int i = 0; i < usuarios.size(); i++){
            PerfilUsuarioResponse perfilUsuario = new PerfilUsuarioResponse();
            perfilUsuario.setNome(usuarios.get(i).getNome());
            perfilUsuario.setIdPerfil(usuarios.get(i).getIdPerfil());
            perfilUsuario.setDescPerfil(usuarios.get(i).getDescPerfil());
            perfilUsuario.setLinkInstagram(usuarios.get(i).getLinkInstagram());
            perfilUsuario.setLinkYoutube(usuarios.get(i).getLinkYoutube());
            perfilUsuario.setLinkTikTok(usuarios.get(i).getLinkTikTok());
            perfilUsuario.setFoto(usuarios.get(i).getFoto());

            listaUsuariosPerfis.add(perfilUsuario);
        }
        return listaUsuariosPerfis;
    }

    public List<NoticiaMobileResponse> MappingNoticiasMobile(List<Noticia> noticias){
        List<NoticiaMobileResponse> listaNoticiasMobile = new ArrayList<>();
        for (int i = 0; i < noticias.size(); i++){
            NoticiaMobileResponse noticia = new NoticiaMobileResponse();
            noticia.setId(noticias.get(i).getId());
            noticia.setTitulo(noticias.get(i).getTitulo());
            noticia.setDescricao(noticias.get(i).getDescricao());
            noticia.setLink(noticias.get(i).getLink());
            noticia.setEmissora(noticias.get(i).getEmissora());
            noticia.setDtNoticia(
                    _dateUtil.formDate(noticias.get(i).getDtNoticia())
            );
            noticia.setFotoNoticia(noticias.get(i).getFoto());
            listaNoticiasMobile.add(noticia);
        }
        return listaNoticiasMobile;
    }

    public List<ComentarioMobileResponse> MappingComentariosNoticiasMobile(List<Comentario> comentarios) {

        var BuscaUsuarios = _comentarioRepository.findUsuariosComment(comentarios.get(0).getNoticias().getId());
        List<ComentarioMobileResponse> comentariosMobile = new ArrayList<>();
        for (int i = 0; i < comentarios.size(); i++){
            ComentarioMobileResponse comentario = new ComentarioMobileResponse();
            comentario.setId(comentarios.get(i).getId());
            comentario.setDescricao(comentarios.get(i).getDescricao());
            if (comentarios.get(i).getUsuario().getId() == BuscaUsuarios.get(i).getId()){
                comentario.setNome(BuscaUsuarios.get(i).getNome());
                comentario.setFoto(BuscaUsuarios.get(i).getFoto());
            }
            comentariosMobile.add(comentario);
        }
        return comentariosMobile;
    }

    public List<ComentarioIAResponse> MappingComentariosIA(List<Comentario> comentarios) {
        List<ComentarioIAResponse> comentariosIA = new ArrayList<>();
        for (int i = 0; i < comentarios.size(); i++){
            ComentarioIAResponse comentario = new ComentarioIAResponse();
            comentario.setId(comentarios.get(i).getId());
            comentario.setDescricao(comentarios.get(i).getDescricao());
            comentario.setNome(comentarios.get(i).getUsuario().getNome());


            comentariosIA.add(comentario);
        }
        return comentariosIA;
    }

    public List<Comentario> AtualizarComentario(List<Comentario> comentarios, List<PesoComentariosRequest> pesoComentariosRequest){
        List<Comentario> listaComentarios = new ArrayList<>();

        for (int i = 0; i < pesoComentariosRequest.size(); i++){

            if (pesoComentariosRequest.get(i).getId() == comentarios.get(i).getId()
                    && comentarios.get(i).getPesoComentario() == null) {
                Comentario comentarioAtualizado = Comentario.builder()
                        .id(comentarios.get(i).getId())
                        .descricao(comentarios.get(i).getDescricao())
                        .dtComentario(comentarios.get(i).getDtComentario())
                        .dtComentarioFormatada(comentarios.get(i).getDtComentarioFormatada())
                        .pesoComentario(pesoComentariosRequest.get(i).getPeso())
                        .usuario(comentarios.get(i).getUsuario())
                        .noticias(comentarios.get(i).getNoticias())
                        .build();
                listaComentarios.add(comentarioAtualizado);
            }
        }
        return listaComentarios;
    }

    public CalculoPesoPorNoticiaIAResponse MappingPesoPorcentagem(Double pesoCompra,
                                                                  Double pesoPensaEmCompra,
                                                                  Double pesoNeutro,
                                                                  Double pesoPenseEmVender,
                                                                  Double pesoVenda, int idNoticia){

        CalculoPesoPorNoticiaIAResponse calculo = new CalculoPesoPorNoticiaIAResponse();
                calculo.setId(idNoticia);
                calculo.setPorcentagemPesoCompra(Transformador(pesoCompra, pesoPensaEmCompra, pesoNeutro, pesoPenseEmVender, pesoVenda, pesoCompra));
                calculo.setPorcentagemPesoPensaEmCompra(Transformador(pesoCompra, pesoPensaEmCompra, pesoNeutro, pesoPenseEmVender, pesoVenda, pesoPensaEmCompra));
                calculo.setPorcentagemPesoNeutro(Transformador(pesoCompra, pesoPensaEmCompra, pesoNeutro, pesoPenseEmVender, pesoVenda, pesoNeutro));
                calculo.setPorcentagemPesoPenseEmVender(Transformador(pesoCompra, pesoPensaEmCompra, pesoNeutro, pesoPenseEmVender, pesoVenda, pesoPenseEmVender));
                calculo.setPorcentagemPesoVenda(Transformador(pesoCompra, pesoPensaEmCompra, pesoNeutro, pesoPenseEmVender, pesoVenda, pesoVenda));

        return calculo;
    }

    public String Transformador(Double pesoCompra, Double pesoPensaEmCompra, Double pesoNeutro, Double pesoPenseEmVender, Double pesoVenda, Double PesoMapping){

        Double contadorPesos = pesoCompra + pesoPensaEmCompra + pesoNeutro + pesoPensaEmCompra + pesoVenda;
        Double conversao = (Math.floor(((PesoMapping/contadorPesos) * 100)));

        String Porcentagem = String.valueOf(conversao + " %");

        return Porcentagem;
    }

    public  List<PesoComentariosRequest> PesosNulos(List<Comentario> comentariosBase, List<PesoComentariosRequest> pesoComentariosRequest){

        List<PesoComentariosRequest> ListaPesos = new ArrayList<>();

        for (int i = 0; i < pesoComentariosRequest.size(); i++){
            if (pesoComentariosRequest.get(i).getId() == comentariosBase.get(i).getId()
                    && comentariosBase.get(i).getPesoComentario() == null){

                PesoComentariosRequest PesoComentarioNovo = new PesoComentariosRequest();

                PesoComentarioNovo.setId(pesoComentariosRequest.get(i).getId());
                PesoComentarioNovo.setPeso(pesoComentariosRequest.get(i).getPeso());

                ListaPesos.add(PesoComentarioNovo);
            }
        }
        return ListaPesos;
    }

    public Usuario MappingAtualizacaoUsuario(Optional<Usuario> usuario, AnalisePerfilResponse analise){
        Usuario usuarioAtualizado = new Usuario().builder()
                .id(usuario.get().getId())
                .nome(usuario.get().getNome())
                .email(usuario.get().getEmail())
                .username(usuario.get().getUsername())
                .senha(usuario.get().getSenha())
                .influencer(usuario.get().isInfluencer())
                .autenticado(usuario.get().getAutenticado())
                .cpf(usuario.get().getCpf())
                .Admin(usuario.get().getAdmin())
                .foto(usuario.get().getFoto())
                .IdPerfil(analise.getIdPerfil())
                .LinkYoutube(usuario.get().getLinkYoutube())
                .LinkInstagram(usuario.get().getLinkInstagram())
                .LinkTikTok(usuario.get().getLinkTikTok())
                .DescPerfil(analise.getPerfil())
                .build();

       var salvar = _usuarioRepository.save(usuarioAtualizado);

       return salvar;
    }

    public PerfilUsuarioResponse MappingUsuarioRetorno(Usuario usuario){

        PerfilUsuarioResponse perfil = new PerfilUsuarioResponse();

        perfil.setNome(usuario.getNome());
        perfil.setFoto(usuario.getFoto());
        perfil.setIdPerfil(usuario.getIdPerfil());
        perfil.setLinkYoutube(usuario.getLinkYoutube());
        perfil.setLinkInstagram(usuario.getLinkInstagram());
        perfil.setLinkTikTok(usuario.getLinkTikTok());
        perfil.setDescPerfil(usuario.getDescPerfil());

        return perfil;
    }

    public List<PerfilUsuarioResponse> MappingTodosInfluencers(List<Usuario> buscaTodos) {
        List<PerfilUsuarioResponse> todosUsuariosInfluencers = new ArrayList<>();

        for (int i = 0; i < buscaTodos.size(); i++){
            PerfilUsuarioResponse perfil = new PerfilUsuarioResponse();

            perfil.setNome(buscaTodos.get(i).getNome());
            perfil.setFoto(buscaTodos.get(i).getFoto());
            perfil.setIdPerfil(buscaTodos.get(i).getIdPerfil());
            perfil.setLinkYoutube(buscaTodos.get(i).getLinkYoutube());
            perfil.setLinkInstagram(buscaTodos.get(i).getLinkInstagram());
            perfil.setLinkTikTok(buscaTodos.get(i).getLinkTikTok());
            perfil.setDescPerfil(buscaTodos.get(i).getDescPerfil());

            todosUsuariosInfluencers.add(perfil);
        }
        return todosUsuariosInfluencers;
    }

    public Usuario MappingDadosInfluencer(Usuario usuario, CadastroDadosInfluencerRequest cadastro){
        Usuario usuarioNovo = Usuario.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .username(usuario.getUsername())
                .senha(usuario.getSenha())
                .autenticado(usuario.getAutenticado())
                .influencer(usuario.isInfluencer())
                .cpf(usuario.getCpf())
                .cnpj(null)
                .LinkYoutube(cadastro.getYoutube())
                .LinkInstagram(cadastro.getInstagram())
                .LinkTikTok(cadastro.getTiktok())
                .build();
        _usuarioRepository.save(usuarioNovo);
        return usuarioNovo;
    }

    public FotoResponse mappingsalvarFoto(Long idUsuario, FotoRequest foto) {
        var buscaUsuario = _usuarioRepository.BuscaUsuario(idUsuario);

        if (buscaUsuario.isEmpty()){
            return null;
        }

        Usuario usuarioNovo = Usuario.builder()
                .id(buscaUsuario.get().getId())
                .nome(buscaUsuario.get().getNome())
                .email(buscaUsuario.get().getEmail())
                .username(buscaUsuario.get().getUsername())
                .senha(buscaUsuario.get().getSenha())
                .autenticado(buscaUsuario.get().getAutenticado())
                .influencer(buscaUsuario.get().isInfluencer())
                .cpf(buscaUsuario.get().getCpf())
                .cnpj(null)
                .foto(foto.getFoto())
                .LinkYoutube(buscaUsuario.get().getLinkYoutube())
                .LinkInstagram(buscaUsuario.get().getLinkInstagram())
                .LinkTikTok(buscaUsuario.get().getLinkTikTok())
                .build();
        _usuarioRepository.save(usuarioNovo);

        ZupLog log = new ZupLog();
        log.setDescricao("IdUsuario -> " + idUsuario + "ByteFoto" + foto.getFoto().toString());
        log.setDt_entrada(
                _dateUtil.formLocalDate(LocalDateTime.now())
        );
        _zupLogRepository.save(log);

        FotoResponse fotoResponse = new FotoResponse();

        fotoResponse.setFoto(foto.getFoto());

        return fotoResponse;
    }
}
