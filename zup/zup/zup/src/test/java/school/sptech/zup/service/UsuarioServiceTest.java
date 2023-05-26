package school.sptech.zup.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import school.sptech.zup.builder.UsuarioBuilder;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.UsuarioComumPutRequestBody;
import school.sptech.zup.dto.UsuarioComumRequestBody;
import school.sptech.zup.dto.UsuarioEmpresaPutRequestBody;
import school.sptech.zup.dto.UsuarioEmpresaRequestBody;
import school.sptech.zup.dto.obj.ListaObj;
import school.sptech.zup.dto.obj.UsuarioObj;
import school.sptech.zup.repository.UsuarioRepository;
import school.sptech.zup.service.AutenticacaoJWT.UsuarioLoginDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private UsuarioService usuarioService;

    @InjectMocks
    private AdminService adminService;


//    @Test
//    void findAllUsuariosTest(){
//        int numero = 3;
//        List<Usuario> usuarios = UsuarioBuilder.criarListaUsuarioComum();
//
//        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);
//
//        ListaObj<UsuarioObj> resultado = adminService.getListUsuario().getBody();
//        assertEquals(numero, resultado.getTamanho());
//    }

    @Test
    void findAllUsuariosTest(){

        List<Usuario> usuarios = UsuarioBuilder.criarListaUsuarioComum();

        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);
        Usuario usuario = UsuarioBuilder.criarUsuarioComum();

//        Mockito.when(this.usuarioService.buscaPorUsername("usuario user")).thenReturn(ResponseEntity.ok().body(usuario));

        HttpStatus re = usuarioService.getListUsuario().getStatusCode();
        assertEquals(HttpStatus.OK , re);
    }


    @Test
    void getUserNameOkTest(){
        UsuarioService espiao = Mockito.spy(usuarioService);

        Mockito.doReturn(ResponseEntity.ok().body(UsuarioBuilder.criarUsuarioComum())).when(espiao).buscaPorUsername(Mockito.any());

        HttpStatus re = espiao.getUsername(UsuarioBuilder.criarUsuarioConsultaComumLoginObj()).getStatusCode();
        assertEquals(HttpStatus.OK , re);
    }

    @Test
    void buscaPorUserNameOkTest(){

        List<Usuario> usuarios = UsuarioBuilder.criarListaUsuarioComum();

        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);
        Usuario usuario = UsuarioBuilder.criarUsuarioComum();

//        Mockito.when(this.usuarioService.buscaPorUsername("usuario user")).thenReturn(ResponseEntity.ok().body(usuario));

        HttpStatus re = usuarioService.buscaPorUsername("usuario user").getStatusCode();
        assertEquals(HttpStatus.OK , re);
    }

    @Test
    void buscaPorUserNameNotFoundTest(){

        List<Usuario> usuarios = new ArrayList<>();

        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);

        HttpStatus re = usuarioService.buscaPorUsername("usuario user").getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND , re);
    }

    @Test
    void buscaUsuarioPorIdOkTest(){

        Usuario usuario = UsuarioBuilder.criarUsuarioComum();
        Mockito.when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        HttpStatus re = usuarioService.buscaUsuarioPorId(usuario.getId()).getStatusCode();
        assertEquals(HttpStatus.OK , re);
    }

    @Test
    void buscaUsuarioPorIdNotFoundTest(){

        Usuario usuario = UsuarioBuilder.criarUsuarioComum();
        Usuario usuarioRe = UsuarioBuilder.criarUsuarioComum();
        usuarioRe.setId(5L);
        Mockito.when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuarioRe));

        HttpStatus re = usuarioService.buscaUsuarioPorId(usuario.getId()).getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND , re);
    }

    @Test
    void buscaPorIdOkTest(){

        Usuario usuario = UsuarioBuilder.criarUsuarioComum();
        Mockito.when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        HttpStatus re = usuarioService.buscaPorId(usuario.getId()).getStatusCode();
        assertEquals(HttpStatus.OK , re);
    }

    @Test
    void buscaPorIdNotFoundTest(){

        Usuario usuario = UsuarioBuilder.criarUsuarioComum();
        Usuario usuarioRe = UsuarioBuilder.criarUsuarioComum();
        usuarioRe.setId(5L);
        Mockito.when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.empty());

        HttpStatus re = usuarioService.buscaPorId(usuario.getId()).getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND , re);
    }

    @Test
    void buscarImagemPorIdOkTest(){

        List<Usuario> usuarios = UsuarioBuilder.criarListaUsuarioComum();


        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);

        HttpStatus re = usuarioService.BuscarImagemPorId(1L).getStatusCode();
        assertEquals(HttpStatus.OK , re);
    }

    @Test
    void buscarImagemPorIdNotFoundTest(){

        List<Usuario> usuarios = UsuarioBuilder.criarListaUsuarioComum();


        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);

        HttpStatus re = usuarioService.BuscarImagemPorId(5L).getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND, re);
    }

    @Test
    void atualizarUsuarioComumOkTest(){
        UsuarioService espiao = Mockito.spy(usuarioService);

        Mockito.doReturn(ResponseEntity.ok().body(UsuarioBuilder.criarUsuarioComum())).when(espiao).buscaPorId(Mockito.anyLong());

        HttpStatus re = espiao.atualizarUsuarioComum(UsuarioBuilder.criarUsuarioConsultaComumDto()).getStatusCode();
        assertEquals(HttpStatus.OK , re);
    }

    @Test
    void atualizarUsuarioEmpresaOkTest(){
        UsuarioService espiao = Mockito.spy(usuarioService);

        Mockito.doReturn(ResponseEntity.ok().body(UsuarioBuilder.criarUsuarioEmpresa())).when(espiao).buscaPorId(Mockito.anyLong());

        HttpStatus re = espiao.atualizarUsuarioEmpresa(UsuarioBuilder.criarUsuarioConsultaEmpresaDto()).getStatusCode();
        assertEquals(HttpStatus.OK , re);
    }

    @Test
    void atualizarUsuarioAdminOkTest(){
        UsuarioService espiao = Mockito.spy(usuarioService);

        Mockito.doReturn(ResponseEntity.ok().body(UsuarioBuilder.criarUsuarioEmpresa())).when(espiao).buscaPorId(Mockito.anyLong());

        HttpStatus re = espiao.atualizarUsuarioAdmin(UsuarioBuilder.criarUsuarioConsultaAdminDto()).getStatusCode();
        assertEquals(HttpStatus.OK , re);
    }

    @Test
    void deletarUserOkTest(){
        UsuarioService espiao = Mockito.spy(usuarioService);

        Mockito.doReturn(ResponseEntity.ok().body(UsuarioBuilder.criarUsuarioEmpresa())).when(espiao).buscaPorId(Mockito.anyLong());

        HttpStatus re = espiao.deleteUser(1L).getStatusCode();
        assertEquals(HttpStatus.OK , re);
    }


//    @Test
//    void getUserNameOkTest(){
////        List<Usuario> usuarios = UsuarioBuilder.criarListaUsuarioComum();
////        Mockito.doReturn(usuarios).when(usuarioRepository.findAll());
////        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);
//
////        Usuario usuario = UsuarioBuilder.criarUsuarioComum();
//
////        Mockito.when(this.usuarioService.buscaPorUsername("usuario user")).thenReturn(ResponseEntity.ok().body(usuario));
//
//        List<Usuario> usuarioos = UsuarioBuilder.criarListaUsuarioComum();
//
//        Mockito.doReturn(usuarioos).when(usuarioRepository.findAll());
//
//        UsuarioLoginDto loginDto = UsuarioBuilder.criarUsuarioConsultaComumLoginObj();
//        Usuario usuario = UsuarioBuilder.criarUsuarioComum();
//        Mockito.when(usuarioService.buscaPorUsername("usuario user")).thenReturn(ResponseEntity.status(200).body(usuario));
//  //      Mockito.doReturn(ResponseEntity.ok().body(usuario)).when(usuarioService.buscaPorUsername("usuario user"));
//
//        HttpStatus re = usuarioService.getUsername(loginDto).getStatusCode();
//        assertEquals(loginDto.getUsername(), usuario.getUsername());
//        assertEquals(HttpStatus.OK , re);
//
////        Mockito.when(usuarioService.buscaPorUsername(loginDto.getUsername())).thenReturn(ResponseEntity.ok().body(usuario));
//
////        Usuario usuario2 = UsuarioBuilder.criarUsuarioComum();
////        Mockito.when(this.usuarioService.buscaPorUsername("usuario user")).thenReturn(ResponseEntity.ok().body(usuario2));
//
////        UsuarioLoginDto loginDto = UsuarioBuilder.criarUsuarioConsultaComumLoginObj();
////        Usuario usuario = UsuarioBuilder.criarUsuarioComum();
////        Mockito.when(usuarioService.getUsername(loginDto)).thenReturn(ResponseEntity.ok().body(usuario));
//    }
//
//
//    @Test
//    void getUserNameNotFoundTest(){
//        UsuarioLoginDto loginDto = UsuarioBuilder.criarUsuarioConsultaComumLoginObj();
//        Usuario usuario = UsuarioBuilder.criarUsuarioComum();
//        var consulta = usuarioService.buscaPorUsername(loginDto.getUsername());
//
//        HttpStatus re = usuarioService.getUsername(loginDto).getStatusCode();
//        assertEquals(HttpStatus.NOT_FOUND , re);
//    }

}
