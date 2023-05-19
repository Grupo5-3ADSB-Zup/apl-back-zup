package school.sptech.zup.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import school.sptech.zup.controller.NoticiaController;
import school.sptech.zup.domain.Noticia;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.obj.*;
import school.sptech.zup.repository.UsuarioRepository;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.List;

@Service
public class AdminService {

    private final UsuarioRepository _usuarioRepository;
    private final NoticiaController _noticiaController;

    public AdminService(UsuarioRepository _usuarioRepository, NoticiaController _noticiaController) {
        this._usuarioRepository = _usuarioRepository;
        this._noticiaController = _noticiaController;
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
    public static void gravarRegistro(String registro, String nomeArq){
        BufferedWriter saida = null;

        // bloco para abrir o arquivo
        try {
            saida = new BufferedWriter(new FileWriter(nomeArq, true));
        }
        catch (IOException e){
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
        }

        try {
            saida.append(registro + "\n");
            saida.close();
        }
        catch (IOException erro){
            System.out.println("Erro ao gravar o arquivo");
        }
    }

    public static void gravarArquivoTxt(ListaObj<UsuarioObj> lista, String nomeArq){
        int contadorRegistroDadosGravados = 0;

        // Monta o registro de header
        String header = "00NOTA20321";
        header += LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        header += "01";

        // grava o registro de header

        gravarRegistro(header, nomeArq);

        // Monta e grava os registros de dados ou registros de body

        String corpo;
        for (int i = 0; i < lista.getTamanho(); i++){
            UsuarioObj a = lista.getElemento(i);
            corpo = "02";
            corpo += String.format("%-5.5s", a.getId());
            corpo += String.format("%-8.8s", a.getNome());
            corpo += String.format("%-50.50s", a.getEmail());
            corpo += String.format("%-40.40s", a.getUsername());
            corpo += String.format("%05.2f", a.getCpf());
            corpo += String.format("%03d", a.getCnpj());
            corpo += String.format("%03d", a.getAutenticado());
            corpo += String.format("%03d", a.getSenha());
            gravarRegistro(corpo, nomeArq);
            contadorRegistroDadosGravados++;
        }

        // Monta e grava o registro de Trailer
        String trailer = "01";
        trailer += String.format("%010d", contadorRegistroDadosGravados++);
        gravarRegistro(trailer, nomeArq);
        System.out.println("Cheguei até aqui");

    }

    public static void lerArquivoTxt(String nomeArq){
        BufferedReader entrada = null;
        String registro, tipoRegistro;
        String curso, ra, nome, disciplina;
        Double media;
        int qtdFalta;
        int contaRegistroDadosLidos = 0;
        int qtdRegistroDadosGravados;

        List<UsuarioObj> listaLida = new ArrayList<>();

        // bloco abrir arquivo
        try {
            entrada = new BufferedReader(new FileReader(nomeArq));
        }
        catch (IOException erro){
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
        }

        // ler o arquivo e fechar

        try {
            registro = entrada.readLine();
            while (registro != null){
                tipoRegistro = registro.substring(0,2);
                if (tipoRegistro.equals("00")){
                    System.out.println("É um registro de header");
                    System.out.println("Tipo do arquivo: " + registro.substring(2,6));
                    System.out.println("Ano e Semestre: " + registro.substring(6,11));
                    System.out.println("Data e hora de gravação do arquivo: " + registro.substring(11,30));
                    System.out.println("Versão do documento: " + registro.substring(30,32));
                } else if (tipoRegistro.equals("01")) {
                    System.out.println("É um registro de trailer");
                    qtdRegistroDadosGravados = Integer.parseInt(registro.substring(2, 12));
                    if (qtdRegistroDadosGravados == contaRegistroDadosLidos){
                        System.out.println("Quantidade de registros de dados gravados compatíveis com quantidade de " +
                                "registros de dados lidos");
                    }
                    else {
                        System.out.println("Quantidade de registros de dados gravados imcompatíveis com quantidade de " +
                                "registros de dados lidos");
                    }
                    // AQUI TBM
                } else if (tipoRegistro.equals("02")) {
                    System.out.println("É um registro de dados ou corpo");
                    curso = registro.substring(2,7).trim();
                    ra = registro.substring(7,15).trim();
                    nome = registro.substring(15,65).trim();
                    disciplina = registro.substring(65,105).trim();
                    media = Double.parseDouble(registro.substring(105,110).replace(',' , '.'));
                    qtdFalta = Integer.parseInt(registro.substring(110,113));
                    //UsuarioObj a = new UsuarioObj(ra, nome, curso, disciplina, media, qtdFalta);
                    contaRegistroDadosLidos++;

                    // Para importar essa informação ao banco de dados:

                    //repository.save(a);

                    // Por n estar conectado ao banco de dados, add numa lista msm

                    //MEXER AQUIII
                    //listaLida.add(a);
                }
                else {
                    System.out.println("É um registro inválido");
                }
                // Lê o próximo registro
                registro = entrada.readLine();
            }
            entrada.close();
        }
        catch(IOException erro){
            System.out.println("Erro ao ler arquivo");
            System.exit(1);
        }

        // Exibe a lista lida
        System.out.println("\n lista contendo os dados lidos do arquivo: ");
        for (UsuarioObj a: listaLida){
            System.out.println(a);
        }

        // Se quiser importar a lista toda de uma vez para o banco:

        //repository.saveAll(listalida);
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

    public ResponseEntity<PilhaObj<NoticiaObj>> getNoticiasFilaPilha(){

        var consulta = _noticiaController.getNoticias();

        FilaObj<NoticiaObj> filaNoticias = new FilaObj(consulta.getBody().size());
        for (int i = 0; i < consulta.getBody().size(); i++){
            NoticiaObj noticiaObj = new NoticiaObj();

            noticiaObj.setId(consulta.getBody().get(i).getId());
            noticiaObj.setTitulo(consulta.getBody().get(i).getTitulo());
            noticiaObj.setDescricao(consulta.getBody().get(i).getDescricao());
            noticiaObj.setLink(consulta.getBody().get(i).getLink());
            noticiaObj.setEmissora(consulta.getBody().get(i).getEmissora());
            noticiaObj.setDtNoticia(consulta.getBody().get(i).getDtNoticia());

            filaNoticias.insert(noticiaObj);
        }
        PilhaObj<NoticiaObj> noticiaPilha = new PilhaObj(filaNoticias.getTamanho());
        for (int a = 0; a < filaNoticias.getTamanho(); a++){
            noticiaPilha.push(filaNoticias.poll());
        }
        return ResponseEntity.status(200).body(noticiaPilha);
    }
}
