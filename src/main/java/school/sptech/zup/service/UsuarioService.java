package school.sptech.zup.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.UsuarioAdminPutRequest;
import school.sptech.zup.dto.UsuarioComumPutRequestBody;
import school.sptech.zup.dto.UsuarioEmpresaPutRequestBody;
import school.sptech.zup.dto.obj.ListaObj;
import school.sptech.zup.dto.obj.UsuarioObj;
import school.sptech.zup.repository.UsuarioRepository;
import school.sptech.zup.service.AutenticacaoJWT.UsuarioLoginDto;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UsuarioService {
    private final UsuarioRepository _usuarioRepository;
    public UsuarioService(UsuarioRepository usuarioRepository) {
        _usuarioRepository = usuarioRepository;
    }
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
}
