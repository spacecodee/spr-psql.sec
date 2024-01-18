package com.spacecodee.sprpsqlsec.data.vo;

import com.spacecodee.sprpsqlsec.data.dto.RoleDto;
import com.spacecodee.sprpsqlsec.persistence.entity.security.UserEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * DTO for {@link UserEntity}
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
    private RoleDto role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == null) return Collections.emptyList();

        List<SimpleGrantedAuthority> authoritiesList = new ArrayList<>(this.role.getPermissions().stream()
                .map(each -> each.getOperationId().getTag())
                .map(SimpleGrantedAuthority::new)
                .toList());

        authoritiesList.add(new SimpleGrantedAuthority("ROLE_" + this.role.getName()));

        return authoritiesList;
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