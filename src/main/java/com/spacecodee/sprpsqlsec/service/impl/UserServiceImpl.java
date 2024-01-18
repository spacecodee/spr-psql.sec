package com.spacecodee.sprpsqlsec.service.impl;

import com.spacecodee.sprpsqlsec.data.dto.RoleDto;
import com.spacecodee.sprpsqlsec.data.vo.SaveUserVo;
import com.spacecodee.sprpsqlsec.data.vo.UDUserVo;
import com.spacecodee.sprpsqlsec.exceptions.InvalidPasswordException;
import com.spacecodee.sprpsqlsec.exceptions.ObjectNotFoundException;
import com.spacecodee.sprpsqlsec.persistence.entity.security.RoleEntity;
import com.spacecodee.sprpsqlsec.persistence.entity.security.UserEntity;
import com.spacecodee.sprpsqlsec.persistence.repository.IUserRepository;
import com.spacecodee.sprpsqlsec.service.IRoleService;
import com.spacecodee.sprpsqlsec.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final IRoleService roleService;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UDUserVo registerOneCustomer(SaveUserVo newUser) {
        this.validatePassword(newUser);

        var user = new UserEntity();
        user.setName(newUser.getName());
        user.setUsername(newUser.getUsername());
        user.setPassword(this.passwordEncoder.encode(newUser.getPassword()));

        var defaultRole = this.roleService.findDefaultRole().orElseThrow(() -> new ObjectNotFoundException("Role isn't found with Name:"));

        var roleEntity = new RoleEntity();
        roleEntity.setId(defaultRole.getId());
        roleEntity.setName(defaultRole.getName());

        user.setRole(roleEntity);

        this.userRepository.save(user);

        var udUser = new UDUserVo();
        udUser.setId(user.getId());
        udUser.setName(user.getName());
        udUser.setUsername(user.getUsername());
        var roleDto = new RoleDto();
        roleDto.setId(user.getRole().getId());
        roleDto.setName(user.getRole().getName());

        udUser.setRole(roleDto);

        return udUser;
    }

    @Override
    public Optional<UDUserVo> findOneByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .map(user -> {
                    var udUser = new UDUserVo();
                    udUser.setId(user.getId());
                    udUser.setName(user.getName());
                    udUser.setUsername(user.getUsername());

                    var roleDto = new RoleDto();
                    roleDto.setId(user.getRole().getId());
                    roleDto.setName(user.getRole().getName());


                    udUser.setRole(roleDto);

                    return udUser;
                });
    }

    private void validatePassword(SaveUserVo newUser) {
        if (!StringUtils.hasText(newUser.getPassword()) || !StringUtils.hasText(newUser.getRepeatedPassword())) {
            throw new InvalidPasswordException("Passwords do not match");
        }

        if (!newUser.getPassword().equals(newUser.getRepeatedPassword())) {
            throw new InvalidPasswordException("Passwords do not match");
        }
    }
}
