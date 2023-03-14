package zup.finance.aplbackzup;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {
    private List<Usuario> usuarios = new ArrayList<>();

    @PostMapping("/comum")
    public Usuario adiconarUser(@RequestBody UsuarioComum user){
        var retornoUserComum = autenticar(user);

        if (retornoUserComum == true){
            usuarios.add(user);
            return user;
        }
        return null;
    }

    @PostMapping("/empresa")
    public Usuario adiconarUser(@RequestBody UsuarioEmpresa user){
        var retornoUserEmpresa = autenticar(user);

        if (retornoUserEmpresa == true){
            usuarios.add(user);
            return user;
        }
        return null;
    }

    @GetMapping("/{indice}")
    public Usuario get(@PathVariable int indice) {
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
                user.setAutenticado(true);
                return true;
            }
        return false;
    }
}