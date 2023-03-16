package zup.finance.aplbackzup;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {
    private List<Usuario> usuarios = new ArrayList<>();

    @PostMapping("/comum")
    public ResponseEntity<Usuario> adiconarUser(@RequestBody UsuarioComum user){
        var retornoUserComum = autenticar(user);

        if (retornoUserComum == true){
            usuarios.add(user);
            return  ResponseEntity.status(200).body(user);
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/empresa")
    public ResponseEntity<Usuario> adiconarUser(@RequestBody UsuarioEmpresa user){
        var retornoUserEmpresa = autenticar(user);

        if (retornoUserEmpresa == true){
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
        if (indice >= 0 && indice < usuarios.size() && usuarios.get(indice).getAutenticado() == true){
            usuarios.get(indice).setAutenticado(false);
            return "Removido com sucesso";
        }
        return "Já excluído da nossa Base";
    }

    private boolean autenticar(Usuario user){
            if (user.getAutenticado() == false){
                if (user instanceof UsuarioComum && ((UsuarioComum) user).getCpf() != null){
                    user.setAutenticado(true);
                    return true;
                } else if (user instanceof UsuarioEmpresa && ((UsuarioEmpresa) user).getCnpj() != null) {
                    user.setAutenticado(true);
                    return true;
                }
            }
        return false;
    }
}