package zup.finance.aplbackzup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zup.finance.aplbackzup.model.Usuario;
import zup.finance.aplbackzup.model.UsuarioComum;
import zup.finance.aplbackzup.model.UsuarioEmpresa;

@Service
public class CadastroService {
    @Autowired
    public boolean autenticar(Usuario user){
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
