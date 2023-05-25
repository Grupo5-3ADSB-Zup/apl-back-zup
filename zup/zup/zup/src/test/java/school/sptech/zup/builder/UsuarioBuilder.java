package school.sptech.zup.builder;

import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.UsuarioComumPutRequestBody;
import school.sptech.zup.dto.UsuarioComumRequestBody;
import school.sptech.zup.dto.UsuarioEmpresaPutRequestBody;
import school.sptech.zup.dto.UsuarioEmpresaRequestBody;
import school.sptech.zup.dto.obj.ListaObj;
import school.sptech.zup.dto.obj.UsuarioObj;

import java.util.List;

public class UsuarioBuilder {
    private UsuarioBuilder(){
        throw new IllegalStateException("Classe Utilitária");
    }

    //metodos para mockar usuario
    //criação
    public static Usuario criarUsuarioComum(){
        return new Usuario(1L, "usuario 1", "usuario@usuario", "usuario user", "senhaUsuario",false, false, false , "12345678912", "", 1);
    }

    public static Usuario criarUsuarioEmpresa(){
        return new Usuario(1L, "usuario 1", "usuario@usuario", "usuario user", "senhaUsuario",false, false, false , "", "12345678912", 1);
    }

    public static Usuario criarUsuarioInfluencer(){
        return new Usuario(1L, "usuario 1", "usuario@usuario", "usuario user", "senhaUsuario",true, false, false , "12345678912", "", 1);
    }

    //criar consulto DTO
    public static UsuarioComumPutRequestBody criarUsuarioConsultaComumDto(){
        return new UsuarioComumPutRequestBody(1L, "usuario 1", "usuario@usuario", "usuario user", "senhaUsuario",false, false, false , "12345678912");
    }

    public static UsuarioObj criarUsuarioConsultaComumObj(){
        return new UsuarioObj(1L, "usuario 1", "usuario@usuario", "usuario user", "senhaUsuario",false, false, false , "12345678912", "");
    }

    public static UsuarioEmpresaPutRequestBody criarUsuarioConsultaEmpresaDto(){
        return new UsuarioEmpresaPutRequestBody(1L, "usuario 1", "usuario@usuario", "usuario user", "senhaUsuario",false, false, false , "12345678912");
    }

    public static UsuarioComumPutRequestBody criarUsuarioConsultaInfluencerDto(){
        return new UsuarioComumPutRequestBody(1L, "usuario 1", "usuario@usuario", "usuario user", "senhaUsuario",true, false, false, "12345678912");
    }

    //criar criacação e atualizacao dto

    public static UsuarioComumRequestBody criarUsuarioCriacaoComumDto(){
        return new UsuarioComumRequestBody( "usuario 1", "usuario@usuario", "usuario user", "senhaUsuario",false, false, false , "12345678912");
    }

    public static UsuarioEmpresaRequestBody criarUsuarioCriacaoEmpresaDto(){
        return new UsuarioEmpresaRequestBody( "usuario 1", "usuario@usuario", "usuario user", "senhaUsuario",false, false, false , "12345678912");
    }

    public static UsuarioComumRequestBody criarUsuarioCriacaoInfluencerDto(){
        return new UsuarioComumRequestBody( "usuario 1", "usuario@usuario", "usuario user", "senhaUsuario",true, false, false, "12345678912");
    }

    //listas
    public static List<Usuario> criarListaUsuarioComum(){
        return List.of(
                criarUsuarioComum(),
                criarUsuarioComum(),
                criarUsuarioComum()
        );
    }


    public static List<Usuario> criarListaUsuarioEmpresa(){
        return List.of(
                criarUsuarioEmpresa(),
                criarUsuarioEmpresa(),
                criarUsuarioEmpresa()
        );
    }

    public static List<Usuario> criarListaUsuarioInfluencer(){
        return List.of(
                criarUsuarioInfluencer(),
                criarUsuarioInfluencer(),
                criarUsuarioInfluencer()
        );
    }

    //listas dtos consulta
    public static List<UsuarioComumPutRequestBody> criarListaUsuarioComumDto(){
        return List.of(
                criarUsuarioConsultaComumDto(),
                criarUsuarioConsultaComumDto(),
                criarUsuarioConsultaComumDto()
        );
    }

    public static List<UsuarioObj> criarListaUsuarioComumObj(){
        return List.of(
                criarUsuarioConsultaComumObj(),
                criarUsuarioConsultaComumObj(),
                criarUsuarioConsultaComumObj()
        );
    }

    public static ListaObj<UsuarioObj> criarListaObjUsuarioComumObj(){
        ListaObj<UsuarioObj> retorno = new ListaObj<>(3);

        retorno.adiciona(criarUsuarioConsultaComumObj());
        retorno.adiciona(criarUsuarioConsultaComumObj());
        retorno.adiciona(criarUsuarioConsultaComumObj());

        return retorno;

    }

    public static List<UsuarioEmpresaPutRequestBody> criarListaUsuarioEmpresaDto(){
        return List.of(
                criarUsuarioConsultaEmpresaDto(),
                criarUsuarioConsultaEmpresaDto(),
                criarUsuarioConsultaEmpresaDto()
        );
    }

    public static List<UsuarioComumPutRequestBody> criarListaUsuarioInfluencerDto(){
        return List.of(
                criarUsuarioConsultaInfluencerDto(),
                criarUsuarioConsultaInfluencerDto(),
                criarUsuarioConsultaInfluencerDto()
        );
    }
}
