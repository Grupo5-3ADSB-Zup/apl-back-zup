package zup.finance.aplbackzup;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {
    private List<Usuario> usuarios;

    public LoginController() {
        this.usuarios = new ArrayList<>();
    }

    @PostMapping
    public List<Usuario> adiconarUser(@RequestBody UsuarioComum user){
        usuarios.add(user);
        return usuarios;
    }

    @GetMapping("/{indice}")
    public Usuario get(@PathVariable int indice) {
        if (usuarios.get(indice) instanceof UsuarioComum) {
            return usuarios.get(indice);
        }
        return null;
    }
    
    @DeleteMapping("/{indice}")
    public String excluir(@PathVariable int indice){
        if (indice >= 0 && indice < usuarios.size()){
            usuarios.remove(indice);
            return "Removido";
        }
        return "Não encontrado";
    }
}