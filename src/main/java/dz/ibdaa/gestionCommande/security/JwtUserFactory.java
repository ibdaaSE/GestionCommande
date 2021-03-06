package dz.ibdaa.gestionCommande.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import dz.ibdaa.gestionCommande.security.model.Authority;
import dz.ibdaa.gestionCommande.security.model.Previlege;
import dz.ibdaa.gestionCommande.security.model.User;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRole().getPrevileges()),
                user.getEnabled(),
                user.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Previlege> authorities) {
        return authorities.stream()
                .map(previlege -> new SimpleGrantedAuthority(previlege.getName()))
                .collect(Collectors.toList());
    }
}
