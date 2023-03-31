package school.sptech.zup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import school.sptech.zup.domain.Usuario;

@Service
@RequiredArgsConstructor
public class LoginService {
    //@Autowired
    private final CadastroService _cadastroService;

    public ResponseEntity<Usuario> getId(Long id) {
        var consulta = _cadastroService.buscaPorId(id);
        if (consulta.getStatusCodeValue() == 200){
            return ResponseEntity.status(200).body(consulta.getBody());
        }
        return consulta;
    }
}
