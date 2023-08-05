package school.sptech.zup.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.UsuarioAdminPutRequest;
import school.sptech.zup.dto.UsuarioComumPutRequestBody;
import school.sptech.zup.dto.UsuarioEmpresaPutRequestBody;
import school.sptech.zup.dto.obj.ListaObj;
import school.sptech.zup.dto.obj.UsuarioObj;
import school.sptech.zup.repository.UsuarioRepository;
import school.sptech.zup.service.AutenticacaoJWT.UsuarioLoginDto;
import java.util.*;

@Service
public class UsuarioService {
    private final UsuarioRepository _usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        _usuarioRepository = usuarioRepository;
    }

    public ResponseEntity<ListaObj<UsuarioObj>> getListUsuario(){
        List<Usuario> usuarioConsulta = _usuarioRepository.findAll();

        if (usuarioConsulta.isEmpty()){
            return ResponseEntity.status(204).build();
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
            usuarioObj.setLogado(usuarioConsulta.get(i).isLogado());
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

        return ResponseEntity.status(200).body(listaUsuario);
    }

    public ResponseEntity<Usuario> getUsername(UsuarioLoginDto loginDto) {
        var consulta = buscaPorUsername(loginDto.getUsername());
        if (consulta.getStatusCodeValue() == 200
                && consulta.getBody().getSenha().equals(loginDto.getSenha())){

            return ResponseEntity.status(200).body(consulta.getBody());
        }
        return consulta;
    }

    public ResponseEntity<Usuario> buscaPorUsername(String username){
        List<Usuario> usuarioConsulta = _usuarioRepository.findAll();

        for (int i = 0; i < usuarioConsulta.size(); i++) {
            if (usuarioConsulta.get(i).getUsername().equals(username)){
                return ResponseEntity.status(200).body(usuarioConsulta.get(i));
            }
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<Usuario> buscaUsuarioPorId(Long id){
        Optional<Usuario> usuarioConsulta  = _usuarioRepository.findById(id);
            if (usuarioConsulta.get().getId() == id){
                return ResponseEntity.status(200).body(usuarioConsulta.get());
            }
            if(usuarioConsulta.isEmpty()) return ResponseEntity.status(404).build();

        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<byte[]> BuscarImagemPorId(@PathVariable Long idFoto){
        var consulta = _usuarioRepository.findAll();
        for (int i = 0; i < consulta.size(); i++){
            if (consulta.get(i).getId() == idFoto){
                return ResponseEntity.status(200).body(consulta.get(i).getFoto());
            }
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<Usuario> buscaPorId(Long id){
        Optional<Usuario> usuarioConsulta = _usuarioRepository.findById(id);
        if (usuarioConsulta.isPresent()){
            return ResponseEntity.status(200).body(usuarioConsulta.get());
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<Usuario> atualizarUsuarioComum(UsuarioComumPutRequestBody usuarioPutRequestBody) {
        var consulta = buscaPorId(usuarioPutRequestBody.getId());

        if (usuarioPutRequestBody.getSenha() == consulta.getBody().getSenha()) {
            return null;
        }

        if (consulta.getStatusCodeValue() == 200){
            Usuario usuario = Usuario.builder()
                    .id(consulta.getBody().getId())
                    .nome(usuarioPutRequestBody.getNome())
                    .email(usuarioPutRequestBody.getEmail())
                    .username(usuarioPutRequestBody.getUsername())
                    .senha(usuarioPutRequestBody.getSenha())
                    .autenticado(usuarioPutRequestBody.getAutenticado())
                    .influencer(usuarioPutRequestBody.isInfluencer())
                    .logado(usuarioPutRequestBody.isLogado())
                    .cpf(usuarioPutRequestBody.getCpf())
                    .cnpj(null)
                    .build();
            _usuarioRepository.save(usuario);

            return ResponseEntity.status(200).body(usuario);
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<Usuario> atualizarUsuarioEmpresa(UsuarioEmpresaPutRequestBody usuarioPutRequestBody) {
        var consulta = buscaPorId(usuarioPutRequestBody.getId());

        if (usuarioPutRequestBody.getSenha() == consulta.getBody().getSenha()) {
            return null;
        }

        if (consulta.getStatusCodeValue() == 200){
            Usuario usuario = Usuario.builder()
                    .id(consulta.getBody().getId())
                    .nome(usuarioPutRequestBody.getNome())
                    .email(usuarioPutRequestBody.getEmail())
                    .username(usuarioPutRequestBody.getUsername())
                    .senha(usuarioPutRequestBody.getSenha())
                    .autenticado(usuarioPutRequestBody.getAutenticado())
                    .influencer(usuarioPutRequestBody.isInfluencer())
                    .logado(usuarioPutRequestBody.isLogado())
                    .cnpj(usuarioPutRequestBody.getCnpj())
                    .cpf(null)
                    .build();
            _usuarioRepository.save(usuario);

            return ResponseEntity.status(200).body(usuario);
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<Usuario> atualizarUsuarioAdmin(UsuarioAdminPutRequest usuarioPutRequestBody) {
        var consulta = buscaPorId(usuarioPutRequestBody.getId());

        if (usuarioPutRequestBody.getSenha() == consulta.getBody().getSenha()) {
            return null;
        }

        if (consulta.getStatusCodeValue() == 200){
            Usuario usuario = Usuario.builder()
                    .id(consulta.getBody().getId())
                    .nome(usuarioPutRequestBody.getNome())
                    .email(usuarioPutRequestBody.getEmail())
                    .username(usuarioPutRequestBody.getUsername())
                    .senha(usuarioPutRequestBody.getSenha())
                    .autenticado(null)
                    .influencer(usuarioPutRequestBody.isInfluencer())
                    .logado(usuarioPutRequestBody.isLogado())
                    .cnpj(null)
                    .cpf(null)
                    .Admin(usuarioPutRequestBody.getAdmin())
                    .build();
            _usuarioRepository.save(usuario);

            return ResponseEntity.status(200).body(usuario);
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<Usuario> deleteUser(long id) {
        var retorno = buscaPorId(id);
        if (retorno.getStatusCodeValue() == 200){
            _usuarioRepository.deleteById(id);
            return ResponseEntity.status(200).build();
        }
        return retorno;
    }

    public Usuario InserirLinkTwitter(Long idUsuario, String link){
        var retorno = buscaUsuarioPorId(idUsuario);

        if (retorno.getStatusCodeValue() == 200){
            retorno.getBody().setLinkTwitter(link);
            _usuarioRepository.save(retorno.getBody());
            return retorno.getBody();
        }
        return null;
    }

    public Usuario InserirLinkLinkedin(Long idUsuario, String link){
        var retorno = buscaUsuarioPorId(idUsuario);

        if (retorno.getStatusCodeValue() == 200){
            retorno.getBody().setLinkTwitter(link);
            _usuarioRepository.save(retorno.getBody());
            retorno.getBody();
        }
        return null;
    }

}
