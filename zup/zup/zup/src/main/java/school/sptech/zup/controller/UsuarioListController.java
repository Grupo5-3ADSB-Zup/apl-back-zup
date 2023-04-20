package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.service.UsuarioService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/list-usuario")
@Log4j2
@RequiredArgsConstructor
public class UsuarioListController {


    @Autowired
    private final UsuarioService _usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuario() {
        var retorno = _usuarioService.getListUsuario();
        if (retorno.getStatusCodeValue() == 200){
            return ResponseEntity.status(200).body(retorno.getBody());
        }
        return retorno;
    }

        private Usuario[] vetor;
        private int nroElem;

    public void adicionaListNoVet(List<Usuario> usuarios) {
            for (Usuario usu: usuarios) {
                adiciona(usu);
            }
        }

        public void adiciona(Usuario elemento) {
            if (nroElem >= vetor.length) {
                System.out.println("Lista está cheia");
            }
            else {
                vetor[nroElem++] = elemento;
            }
        }


//        public void exibe() {
//            if (nroElem == 0) {
//                System.out.println("\nA lista está vazia.");
//            }
//            else {
//                System.out.println("\nElementos da lista:");
//                for (int i = 0; i < nroElem; i++) {
//                    System.out.println(vetor[i]);
//                }
//            }
//        }
//        public int busca(Usuario elementoBuscado) {
//            for (int i = 0; i < nroElem; i++) {
//                if (vetor[i].equals(elementoBuscado)) {   // se encontrou
//                    return i;                        // retorna seu indice
//                }
//            }
//            return -1;                               // nao encontrou, retorna -1
//        }


//        public boolean removePeloIndice (int indice) {
//            if (indice < 0 || indice >= nroElem) {
//                System.out.println("\nIndice invalido!");
//                return false;
//            }
//            // Loop para "deslocar para a esquerda" os elementos do vetor
//            // sobrescrevendo o elemento removido
//            for (int i = indice; i < nroElem-1; i++) {
//                vetor[i] = vetor[i+1];
//            }
//
//            nroElem--;          // decrementa nroElem
//            return true;
//        }


//        public boolean removeElemento(Usuario elementoARemover) {
//            return removePeloIndice(busca(elementoARemover));
//        }

        /* getTamanho()  - retorna o tamanho da lista */
        public int getTamanho() {
            return nroElem;
        }

        /* getElemento() - recebe um indice e retorna o elemento desse indice */
        public Usuario getElemento(int indice) {
            if (indice < 0 || indice >= nroElem) {   // se indice invalido
                return null;                        // entao retorna null
            }
            else {
                return vetor[indice];
            }
        }

        /* limpa() - limpa a lista */
        public void limpa() {
            nroElem = 0;
        }

//    ListaObj<Cachorro> listDog = new ListaObj<>(7);
//    listDog.exibe();
//    gravaArquivoCsv(listDog, "dogs");
//    leArquivoCsv("dogs");


//        public static void gravaArquivoCsv(UsuarioListController listUsuario, String nomeArq){
    @GetMapping("arquivo-csv/{nome}")
    public Formatter gravaArquivoCsv(String nome){

            FileWriter arq = null;
            Formatter saida = null;
            Boolean deuRuim = false;

            nome += ".csv";

            try{
                arq = new FileWriter(nome, true);
                saida = new Formatter(arq);
            }catch (IOException e){
                System.out.println("Erro ao abrir o arquivo");
                System.exit(1);
            }

            // Bloco try catch para gravar no arquivo

            try {
                for (int i = 0; i < getTamanho(); i++) {
                    Usuario usu = getElemento(i);
                    // adicionar os outros campos
                    saida.format("%d;\n", usu.getId());
                }
            }catch(FormatterClosedException erro){
                System.out.println("Erro ao gravar o arquivo");
                deuRuim = true;
            }finally {
                saida.close();
                try{
                    arq.close();
                }catch (IOException erro){
                    System.out.println("Erro ao fechar o arquivo");
                    deuRuim = true;
                }
                if (deuRuim){
                    System.exit(1);
                }

            }
            return saida;
        }

        public static void leArquivoCsv(String nomeArq) {
            FileReader arq = null;
            Scanner entrada = null;
            Boolean deuRuim = false;

            nomeArq += ".csv";

            try {
                arq = new FileReader(nomeArq);
                entrada = new Scanner(arq).useDelimiter(";|\\n");
            } catch (FileNotFoundException erro) {
                System.out.println("Arquivo nao encotrado");
                System.exit(1);
            }

            // bloco try catch para ler o arquivo

            try {
                System.out.printf("%4S %-15S %-9S %4S\n", "id", "nome", "porte", "peso");
                while (entrada.hasNext()) {
                    int id = entrada.nextInt();
                    String nome = entrada.next();
                    String porte = entrada.next();
                    Double peso = entrada.nextDouble();
                    System.out.printf("%4d %-15s %-9s %4.1f\n", id, nome, porte, peso);

                }
            } catch (NoSuchElementException erro) {
                System.out.println("vai plantar batatas");
                deuRuim = true;
            } catch (IllegalStateException erro) {
                System.out.println("Erro na leitura do arquivo");
                deuRuim = true;
            } finally {
                entrada.close();
                try {
                    arq.close();
                } catch (IOException erro) {
                    System.out.println("Erro ao fechar o arquivo");
                    deuRuim = true;
                }
                if (deuRuim) {
                    System.exit(1);
                }
            }
        }
    }
