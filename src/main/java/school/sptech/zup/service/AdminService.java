package school.sptech.zup.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.zup.controller.NoticiaController;
import school.sptech.zup.controller.UsuarioController;
import school.sptech.zup.domain.Carteira;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.obj.*;
import school.sptech.zup.repository.CarteiraRepository;
import school.sptech.zup.repository.ComentarioRepository;
import school.sptech.zup.repository.NoticiaRepository;
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

    private final UsuarioController _usuarioController;
    private final CarteiraRepository _carteiraRepository;

    private final ComentarioRepository _comentarioRepository;

    public AdminService(UsuarioRepository _usuarioRepository, NoticiaController _noticiaController,
                        UsuarioController _usuarioController, CarteiraRepository _carteiraRepository,
                        ComentarioRepository _comentarioRepository) {

        this._usuarioRepository = _usuarioRepository;
        this._noticiaController = _noticiaController;
        this._usuarioController = _usuarioController;
        this._carteiraRepository = _carteiraRepository;
        this._comentarioRepository = _comentarioRepository;
    }

    public ResponseEntity<byte[]> gravarArquivoCsv(ListaObj<UsuarioObj> listaUsuarioObj, String nomeArquivo){
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
        }

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

        try {

            File file = new File("./" + nomeArquivo);
            FileInputStream fileInputStream = new FileInputStream(file);

            return ResponseEntity
                    .status(200)
                    .header("Content-Disposition",
                            "attachment; filename="+nomeArquivo)
                    .contentType(MediaType.parseMediaType("application/csv"))
                    .body(fileInputStream.readAllBytes());
        } catch (FileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
      // return ResponseEntity.status(404).build();
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
    public BufferedWriter gravarRegistro(String registro, String nomeArq){
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
        return saida;
    }

    public BufferedWriter gravarArquivoTxt(ListaObj<UsuarioObj> lista, String nomeArq){
        int contadorRegistroDadosGravados = 0;

        nomeArq += ".txt";

        // Monta o registro de header
        String header = "00USUARIO";
        header += LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        header += "11";

        // grava o registro de header

        gravarRegistro(header, nomeArq);

        // Monta e grava os registros de dados ou registros de body

        String corpo;
        for (int i = 0; i < lista.getTamanho(); i++){
            UsuarioObj a = lista.getElemento(i);
            corpo = "02";
            corpo += String.format("%-49.49s", a.getNome());
            corpo += String.format("%-49.49s", a.getEmail());
            corpo += String.format("%-10.10s", a.getUsername());
            corpo += String.format("%-11.11s", a.getCpf());
            corpo += String.format("%-14.14s", a.getCnpj());
            gravarRegistro(corpo, nomeArq);
            contadorRegistroDadosGravados++;
        }

        // Monta e grava o registro de Trailer
        String trailer = "01";
        trailer += String.format("%010d", contadorRegistroDadosGravados++);

        return (gravarRegistro(trailer, nomeArq));
    }

    public ResponseEntity lerArquivoTxt(MultipartFile nomeArq){
        BufferedReader entrada = null;
        String registro, tipoRegistro;
        String nome, email, corretora, perfil, patrimonio;
        String username = null;
        //Usuario user;

        int contaRegistroDadosLidos = 0;
        int qtdRegistroDadosGravados;

        List<UsuarioObj> listaLida = new ArrayList<>();

        // bloco abrir arquivo
        try {
            entrada = new BufferedReader(new InputStreamReader(nomeArq.getInputStream()));
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
                    System.out.println("Usuário: " + registro.substring(3,10));
                    System.out.println("Data e hora de gravação do arquivo: " + registro.substring(11,27));
                    System.out.println("Versão do documento: " + registro.substring(28,30));
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
                    nome = registro.substring(2,51).trim();
                    email = registro.substring(51,100).trim();
                    username = registro.substring(100,110).trim();
                    contaRegistroDadosLidos++;

                    // Para importar essa informação ao banco de dados:

                    //repository.save(a);

                    // Por n estar conectado ao banco de dados, add numa lista msm

                    //MEXER AQUIII
                    //listaLida.add(a);

                } else if (tipoRegistro.equals("03")) {
                    System.out.println("É um registro de dados ou corpo");
                    corretora = registro.substring(2,51).trim();
                    perfil = registro.substring(51,100).trim();
                    patrimonio = registro.substring(100,114).trim();
                    contaRegistroDadosLidos++;

                   var consulta = _usuarioRepository.findByUsername(username);

                   if (consulta.isPresent()){

                       Carteira carteira = new Carteira().builder()
                               .corretora(corretora)
                               .perfil(perfil)
                               .patrimonio(patrimonio)
                               .usuario(consulta.get())
                               .build();

                       // fazer tratativa de exceção

                       _carteiraRepository.save(carteira);
                   }

                } else {
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

        return ResponseEntity.status(201).build();

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

    public List<NoticiaObj> getNoticiasFilaPilha(){

        var consultaNoticia = _noticiaController.getNoticia();
        var consultaComentario = _comentarioRepository.findAll();

        FilaObj<NoticiaObj> filaNoticias = new FilaObj(consultaNoticia.getBody().size());
        for (int i = 1; i < consultaNoticia.getBody().size(); i++){
            NoticiaObj noticiaObj = new NoticiaObj();

            noticiaObj.setId(consultaNoticia.getBody().get(i).getId());
            noticiaObj.setTitulo(consultaNoticia.getBody().get(i).getTitulo());
            noticiaObj.setDescricao(consultaNoticia.getBody().get(i).getDescricao());
            noticiaObj.setLink(consultaNoticia.getBody().get(i).getLink());
            noticiaObj.setEmissora(consultaNoticia.getBody().get(i).getEmissora());
            noticiaObj.setDtNoticia(consultaNoticia.getBody().get(i).getDtNoticia());
            noticiaObj.setLikes(consultaNoticia.getBody().get(i).getLikes());
            noticiaObj.setFotoNoticia(consultaNoticia.getBody().get(i).getFoto());

                if (consultaComentario.get(i).getNoticias().getId() == consultaNoticia.getBody().get(i).getId()){
                    noticiaObj.setIdComentario(consultaComentario.get(i).getId());
                    // noticiasObj.get(i).setNomeUsuario(consultaComentario.get(i).getUsuario().getNome());
                    // noticiasObj.get(i).setDescricaoComentario(consultaComentario.get(i).getDescricao());
                    //  noticiaObj.setFotoUsuario(consultaComentario.get(i).getUsuario().getFoto());

                }

            filaNoticias.insert(noticiaObj);
        }

        PilhaObj<NoticiaObj> noticiaPilha = new PilhaObj(filaNoticias.getTamanho());
        int tamanhoFila = filaNoticias.getTamanho();

        for (int a = 0; a < tamanhoFila; a++){
            noticiaPilha.push(filaNoticias.poll());
        }

        List<NoticiaObj> noticiasObj = new ArrayList(noticiaPilha.getTopo());
        int tamanhoPilha = noticiaPilha.getTopo();

        for (int i = 0; i < tamanhoPilha; i++){
            noticiasObj.add(noticiaPilha.pop());
        }

        return noticiasObj;
    }

    public ResponseEntity<List<Usuario>> getListTodosUsuario() {
        List<Usuario> usuarioConsulta = _usuarioRepository.findAll();

        if (usuarioConsulta.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(usuarioConsulta);
    }
}
