package school.sptech.zup.builder;

import school.sptech.zup.dto.obj.ListaObj;
import school.sptech.zup.dto.obj.NoticiaObj;

import java.time.LocalDateTime;
import java.util.List;

public class NoticiasBuilder {
    private NoticiasBuilder(){
        throw new IllegalStateException("Classe Utilitária");
    }

    public static NoticiaObj criarNoticiaObj(){
        return new NoticiaObj(1, "noticia", "noticia", "link", "emissora", LocalDateTime.now(), 1, "comentario", new byte[4]);
    }

    public static ListaObj<NoticiaObj> criarListaObjNoticiaObj(){
        ListaObj<NoticiaObj> retorno = new ListaObj<>(3);

        retorno.adiciona(criarNoticiaObj());
        retorno.adiciona(criarNoticiaObj());
        retorno.adiciona(criarNoticiaObj());

        return retorno;

    }

    public static List<NoticiaObj> criarListaObjNoticia(){
        return List.of(
                criarNoticiaObj(),
                criarNoticiaObj(),
                criarNoticiaObj()
        );
    }


}
