package zup.finance.aplbackzup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zup.finance.aplbackzup.model.Usuario;
import zup.finance.aplbackzup.model.UsuarioComum;
import zup.finance.aplbackzup.model.UsuarioEmpresa;
import zup.finance.aplbackzup.service.CadastroService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cadastro")
public class CadastroController {
    @Autowired
    private CadastroService cadastroService;
    private List<Usuario> usuarios = new ArrayList<>();

    @PostMapping("/comum")
    public ResponseEntity<Usuario> adiconarUser(@RequestBody UsuarioComum user){
        var retornoUserComum = cadastroService.autenticar(user);

        if (retornoUserComum){
            usuarios.add(user);
            return  ResponseEntity.status(200).body(user);
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/empresa")
    public ResponseEntity<Usuario> adiconarUser(@RequestBody UsuarioEmpresa user){
        var retornoUserEmpresa = cadastroService.autenticar(user);

        if (retornoUserEmpresa){
            usuarios.add(user);
            return  ResponseEntity.status(200).body(user);
        }
        return ResponseEntity.status(401).build();
    }

    @GetMapping
    public List<Usuario> get(){
        return usuarios;
    }

    @GetMapping("/{indice}")
    public Usuario getIndice(@PathVariable int indice) {
            if (indice >= 0 && indice < usuarios.size() && usuarios.get(indice) instanceof UsuarioComum){
                return usuarios.get(indice);
            }else if (usuarios.get(indice) instanceof UsuarioEmpresa ){
                return usuarios.get(indice);
            }
        return null;
    }

    @PutMapping("/comum/{indice}")
    public UsuarioComum atualizarComum(@PathVariable int indice, @RequestBody UsuarioComum usuario){
        for (int i = 0; i < usuarios.size(); i++){
            if (indice >= 0 && indice < usuarios.size()){
                if (usuarios.get(indice) instanceof UsuarioComum){
                    usuarios.set(indice, usuario);
                    return usuario;
                }
            }
        }
        return null;
    }

    @PutMapping("/empresa/{indice}")
    public UsuarioEmpresa atualizarEmpresa(@PathVariable int indice, @RequestBody UsuarioEmpresa usuario){
        for (int i = 0; i < usuarios.size(); i++){
            if (indice >= 0 && indice < usuarios.size()){
                if (usuarios.get(indice) instanceof UsuarioEmpresa){
                    usuarios.set(indice, usuario);
                    return usuario;
                }
            }
        }
        return null;
    }
    
    @DeleteMapping("/{indice}")
    public String excluir(@PathVariable int indice){
        if (indice >= 0 && indice < usuarios.size() && usuarios.get(indice).getAutenticado()){
            usuarios.get(indice).setAutenticado(false);
            return "Removido com sucesso";
        }
        return "Já excluído da nossa Base";
    }
}