package com.spacecodee.sprpsqlsec.data.vo;

import com.spacecodee.sprpsqlsec.enums.RoleEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * DTO for {@link com.spacecodee.sprpsqlsec.persistence.entity.UserEntity}
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UDUserVo implements UserDetails, Serializable {
    private long id;
    private String name;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == null) return Collections.emptyList();

        return this.role.getPermissions().stream()
                .map(Enum::name).map(each -> (GrantedAuthority) () -> each).toList();
    }

    public static UDUserVo build(UDUserVo user) {
        return user;
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