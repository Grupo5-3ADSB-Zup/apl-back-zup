package school.sptech.zup.dto;

import school.sptech.zup.domain.Usuario;
import school.sptech.zup.service.AutenticacaoJWT.UsuarioTokenDto;

public class MapperJWT {
    public static UsuarioTokenDto of(Usuario usuario, String token){
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();
        usuarioTokenDto.setId(usuario.getId());
        usuarioTokenDto.setNome(usuario.getNome());
        usuarioTokenDto.setUsername(usuario.getUsername());
        usuarioTokenDto.setToken(token);
        return usuarioTokenDto;
    }
}
