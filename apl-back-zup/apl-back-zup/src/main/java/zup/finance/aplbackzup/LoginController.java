package zup.finance.aplbackzup;

import org.springframework.web.bind.annotation.*;
import zup.finance.aplbackzup.Usuario;

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
    public Usuario get(@PathVariable int indice){
        if (usuarios.get(indice) instanceof UsuarioComum){
            return usuarios.get(indice);
        }
        return null;
    }
}
