package school.sptech.zup.service.AutenticacaoJWT;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import school.sptech.zup.domain.Usuario;

import java.util.Collection;

public class UsuarioDetalheDto implements UserDetails {
    private final String nome;
    private final String senha;
    private final String username;

    public UsuarioDetalheDto(Usuario usuario) {
        this.nome = usuario.getNome();
        this.senha = usuario.getSenha();
        this.username = usuario.getUsername();
    }

    public String getNome() {
        return nome;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
