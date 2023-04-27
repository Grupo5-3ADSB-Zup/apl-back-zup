package school.sptech.zup.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.obj.ListaObj;
import school.sptech.zup.dto.obj.UsuarioObj;
import school.sptech.zup.repository.UsuarioRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.List;
import java.util.Objects;

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

    public static void gravarArquivoCsv(ListaObj<UsuarioObj> listaUsuarioObj, String nomeArquivo){
        FileWriter arq = null;
        Formatter saida = null;
        Boolean deuRuim = false;

        nomeArquivo += ".csv";

        // Bloco Try-catch para abrir o arquivo

        try {
            arq = new FileWriter(nomeArquivo); //Se colocar , true ele acrescenta no arquivo
            saida = new Formatter(arq);
        }
        catch (IOException erro){
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
        }

        // Bloco try-catch para gravar no arquivo

        try {
            for (int i =0; i < listaUsuarioObj.getTamanho(); i++){
                UsuarioObj user = listaUsuarioObj.getElemento(i);
                saida.format("%d;%s;%s;%s;%s;%s;%s;%s;%s;%s\n",
                        user.getId(), user.getNome(), user.getEmail(), user.getUsername(),
                        user.getSenha(), user.isInfluencer(), user.getAutenticado(), user.isLogado(), user.getCpf(),
                        user.getCnpj());
            }
        }
        catch (FormatterClosedException erro){
            System.out.println("Erro ao gravar o arquivo");
            deuRuim = true;
        } finally {
            saida.close();
            try {
                arq.close();
            }catch (IOException erro){
                System.out.println("Erro ao fechar o arquivo");
                deuRuim = true;
            }
            if (deuRuim){
                System.exit(1);
            }
        }
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


    public ResponseEntity<UsuarioObj> pesquisaBinaria(String x) {

        ResponseEntity<ListaObj<UsuarioObj>> consulta = getListUsuario();

        if (consulta.getStatusCodeValue() == 200){
            int esquerda = 0;

            int direita =  consulta.getBody().getTamanho() -1;

            for (; esquerda <= direita;){
                    int meio = (esquerda + direita) / 2;

                   int comparacao = consulta.getBody().getElemento(meio).getNome().compareToIgnoreCase(x);
                    if ( comparacao == 0) {

                        return ResponseEntity.status(200).body(consulta.getBody().getElemento(meio));

                    } else if (comparacao > 0) {
                        direita = meio - 1;
                    } else {
                        esquerda = meio + 1;
                    }
            }
        }
        return ResponseEntity.status(404).build();
    }

}
