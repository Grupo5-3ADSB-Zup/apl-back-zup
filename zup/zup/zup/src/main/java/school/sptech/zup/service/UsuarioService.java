package school.sptech.zup.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.UsuarioComumPutRequestBody;
import school.sptech.zup.dto.UsuarioEmpresaPutRequestBody;
import school.sptech.zup.dto.obj.ListaObj;
import school.sptech.zup.dto.obj.UsuarioObj;
import school.sptech.zup.repository.UsuarioRepository;
import school.sptech.zup.service.AutenticacaoJWT.UsuarioLoginDto;
import school.sptech.zup.util.enumerador.EnumUsuario;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository _usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        _usuarioRepository = usuarioRepository;
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
            saida.format("%s;%s;%s;%s;%s;%s;%s;%s\n",
                    "Id",
                    "Nome",
                    "Email",
                    "User name",
                    "Senha",
                    "Tipo Usuario",
                    "CPF ",
                    "CNPJ");

            for (int i =0; i < listaUsuarioObj.getTamanho(); i++){
                UsuarioObj user = listaUsuarioObj.getElemento(i);
                saida.format("%d;%s;%s;%s;%s;%s;%s;%s\n",
                        user.getId(),
                        user.getNome(),
                        user.getEmail(),
                        user.getUsername(),
                        user.getSenha(),
                        user.getEnumUsuario(),
                        user.getCpf(),
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

    public static void gravaRegistro (String registro, String nomeArq) {
        BufferedWriter saida = null;

        try {
            saida = new BufferedWriter(new FileWriter(nomeArq, true));
        }
        catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
        }
        try {
            saida.append(registro + "\n");
            saida.close();
        }
        catch (IOException erro) {
            System.out.println("Erro ao gravar no arquivo");
        }
    }
    public static void gravaArquivoTxt(ListaObj<UsuarioObj> lista, String nomeArq) {
        int contaRegDadosGravados = 0;

        String header = "00";
        header += "USUARIO";
        header += LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        header += "01";
        gravaRegistro(header, nomeArq);

        String corpo;
        for (int i = 0; i < lista.getTamanho(); i++) {
            UsuarioObj a = lista.getElemento(i);
            corpo = "02";
            corpo += String.format("-100.100s", a.getNome());
            corpo += String.format("-50.50s", a.getEmail());
            corpo += String.format("-10.10s", a.getUsername());
            corpo += String.format("-1.1s", a.getEnumUsuario().equals(EnumUsuario.COMUM) ? "S" : "N");
            corpo += String.format("-1.1s", a.getEnumUsuario().equals(EnumUsuario.INFLUENCER) ? "S" : "N");
            corpo += String.format("-11.11s", a.getCpf());
            corpo += String.format("-1.1s", a.getEnumUsuario().equals(EnumUsuario.EMPRESA) ? "S" : "N");
            corpo += String.format("-14.14s", a.getCnpj());

            gravaRegistro(corpo, nomeArq);
            contaRegDadosGravados++;
        }
        String trailer = "01";
        trailer += String.format("%010d", contaRegDadosGravados);
        gravaRegistro(trailer, nomeArq);

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
            usuarioObj.setEnumUsuario(usuarioConsulta.get(i).getTipoUsuario());
//            usuarioObj.setAutenticado(usuarioConsulta.get(i).getAutenticado());
//            usuarioObj.setLogado(usuarioConsulta.get(i).isLogado());
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

        if (consulta.getStatusCodeValue() == 200){
            Usuario usuario = Usuario.builder()
                    .id(consulta.getBody().getId())
                    .nome(usuarioPutRequestBody.getNome())
                    .email(usuarioPutRequestBody.getEmail())
                    .username(usuarioPutRequestBody.getUsername())
                    .senha(usuarioPutRequestBody.getSenha())
                    .tipoUsuario(usuarioPutRequestBody.getEnumUsuario())
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

        if (consulta.getStatusCodeValue() == 200){
            Usuario usuario = Usuario.builder()
                    .id(consulta.getBody().getId())
                    .nome(usuarioPutRequestBody.getNome())
                    .email(usuarioPutRequestBody.getEmail())
                    .username(usuarioPutRequestBody.getUsername())
                    .senha(usuarioPutRequestBody.getSenha())
//                    .autenticado(usuarioPutRequestBody.getAutenticado())
                    .tipoUsuario(usuarioPutRequestBody.getEnumUsuario())
//                    .logado(usuarioPutRequestBody.isLogado())
                    .cnpj(usuarioPutRequestBody.getCnpj())
                    .cpf(null)
                    .build();
            _usuarioRepository.save(usuario);

            return ResponseEntity.status(200).body(usuario);
        }
        return ResponseEntity.status(404).build();
    }

//    public ResponseEntity<Usuario> atualizarUsuarioAdmin(UsuarioAdminPutRequest usuarioPutRequestBody) {
//        var consulta = buscaPorId(usuarioPutRequestBody.getId());
//
//        if (consulta.getStatusCodeValue() == 200){
//            Usuario usuario = Usuario.builder()
//                    .id(consulta.getBody().getId())
//                    .nome(usuarioPutRequestBody.getNome())
//                    .email(usuarioPutRequestBody.getEmail())
//                    .username(usuarioPutRequestBody.getUsername())
//                    .senha(usuarioPutRequestBody.getSenha())
//                    .autenticado(null)
//                    .influencer(usuarioPutRequestBody.isInfluencer())
//                    .logado(usuarioPutRequestBody.isLogado())
//                    .cnpj(null)
//                    .cpf(null)
//                    .Admin(usuarioPutRequestBody.getAdmin())
//                    .build();
//            _usuarioRepository.save(usuario);
//
//            return ResponseEntity.status(200).body(usuario);
//        }
//        return ResponseEntity.status(404).build();
//    }

    public ResponseEntity<Usuario> deleteUser(long id) {
        var retorno = buscaPorId(id);
        if (retorno.getStatusCodeValue() == 200){
            _usuarioRepository.deleteById(id);
            return ResponseEntity.status(200).build();
        }
        return retorno;
    }

}
