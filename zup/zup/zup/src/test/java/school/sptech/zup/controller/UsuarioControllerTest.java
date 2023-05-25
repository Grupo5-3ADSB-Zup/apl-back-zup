package school.sptech.zup.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import retrofit2.http.HTTP;
import school.sptech.zup.builder.NoticiasBuilder;
import school.sptech.zup.builder.UsuarioBuilder;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.obj.ListaObj;
import school.sptech.zup.dto.obj.NoticiaObj;
import school.sptech.zup.dto.obj.UsuarioObj;
import school.sptech.zup.repository.UsuarioRepository;
import school.sptech.zup.service.AdminService;
import school.sptech.zup.service.UsuarioService;
import school.sptech.zup.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    @Mock
    private UsuarioRepository usuarioRepository;


    @Mock
    private UsuarioService usuarioService;

    @Mock
    private NoticiaController noticiaController;


    @Mock
    private AdminService adminService;

    @Mock
    private DateUtil dateUtil;

    @InjectMocks
    private UsuarioController usuarioController;



    @Test
    public void adminControllerGravarArquivoCsv(){
        AdminController adminController = new AdminController(adminService, noticiaController);

        ListaObj<UsuarioObj> listaUsuario =  UsuarioBuilder.criarListaObjUsuarioComumObj();

        var retorno = ResponseEntity.status(200).body(listaUsuario);
        Mockito.when(adminService.getListUsuario()).thenReturn(retorno);


        String nomeArquivo = "teste";
        HttpStatus re = adminController.gravarArquivoCsv(nomeArquivo).getStatusCode();
        assertEquals(HttpStatus.CREATED , re);
    }

    @Test
    public void adminControllerGravarArquivoCsvNotFound(){
        AdminController adminController = new AdminController(adminService, noticiaController);

        ListaObj<UsuarioObj> listaUsuario =  UsuarioBuilder.criarListaObjUsuarioComumObj();

        var retorno = ResponseEntity.status(204).body(listaUsuario);
        Mockito.when(adminService.getListUsuario()).thenReturn(retorno);


        String nomeArquivo = "teste";
        HttpStatus re = adminController.gravarArquivoCsv(nomeArquivo).getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND , re);
    }

    @Test
    public void adminControllerGravarArquivoTxt(){
        AdminController adminController = new AdminController(adminService, noticiaController);

        ListaObj<UsuarioObj> listaUsuario =  UsuarioBuilder.criarListaObjUsuarioComumObj();

        var retorno = ResponseEntity.status(200).body(listaUsuario);
        Mockito.when(adminService.getListUsuario()).thenReturn(retorno);


        String nomeArquivo = "teste";
        HttpStatus re = adminController.gravarArquivoTXT(nomeArquivo).getStatusCode();
        assertEquals(HttpStatus.CREATED , re);
    }

    @Test
    public void adminControllerGravarArquivoTxtNotFound(){
        AdminController adminController = new AdminController(adminService, noticiaController);

        ListaObj<UsuarioObj> listaUsuario =  UsuarioBuilder.criarListaObjUsuarioComumObj();

        var retorno = ResponseEntity.status(204).body(listaUsuario);
        Mockito.when(adminService.getListUsuario()).thenReturn(retorno);


        String nomeArquivo = "teste";
        HttpStatus re = adminController.gravarArquivoTXT(nomeArquivo).getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND , re);
    }


    @Test
    public void adminControllerRetornarFilaPilhaObj(){
        AdminController adminController = new AdminController(adminService, noticiaController);

        List<NoticiaObj> listaNoticias = NoticiasBuilder.criarListaObjNoticia();
        Mockito.when(adminService.getNoticiasFilaPilha()).thenReturn(listaNoticias);


        HttpStatus re = adminController.retornarFilaPilhaObj().getStatusCode();
        assertEquals(HttpStatus.OK , re);
    }

    @Test
    public void adminControllerRetornarFilaPilhaObjNotFound(){
        AdminController adminController = new AdminController(adminService, noticiaController);

        List<NoticiaObj> listaNoticias = new ArrayList<>();
        Mockito.when(adminService.getNoticiasFilaPilha()).thenReturn(listaNoticias);


        HttpStatus re = adminController.retornarFilaPilhaObj().getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND , re);
    }
}
