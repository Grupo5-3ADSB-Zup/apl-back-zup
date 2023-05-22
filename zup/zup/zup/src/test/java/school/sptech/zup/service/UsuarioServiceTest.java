package school.sptech.zup.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mock;
import org.mockito.Mockito;
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

import java.util.List;
import java.util.Optional;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private UsuarioService usuarioService;

    @InjectMocks
    private AdminService adminService;


    @Test
    void getUsernameTest(){
        int numero = 3;
        List<Usuario> usuarioos = UsuarioBuilder.criarListaUsuarioComum();

        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarioos);

        ListaObj<UsuarioObj> resultado = adminService.getListUsuario().getBody();
        assertEquals(numero, resultado.getTamanho());

//        Usuario usuarioEsperado = new Usuario(1L, "usuario 1", "usuario@usuario", "usuario user", "senhaUsuario",false, false, false , "12345678912", "", 1);
//
//        UsuarioLoginDto loginDto = new UsuarioLoginDto("userTeste", "1234");
//
//        Usuario usuario = UsuarioBuilder.criarUsuarioComum();
//        Mockito.when(usuarioRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(usuario));
//
//        ResponseEntity<Usuario> usuarioResposta = usuarioService.getUsername(loginDto.getUsername());
//
//        assertEquals(usuarioEsperado, usuarioResposta.getBody());

//        UsuarioLoginDto loginDto = new UsuarioLoginDto("userTeste", "1234");
//
//        Usuario retorno = this.usuarioService.getUsername(loginDto).getBody();
//
//        Mockito.when(usuarioRepository.findByUsername(usuarioEsperado.getUsername())).thenReturn(Optional.of(usuario));
//
//        assertEquals(usuarioEsperado, retorno);

    }




}
