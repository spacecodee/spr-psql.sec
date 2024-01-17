package com.spacecodee.sprpsqlsec.service.impl;

import com.spacecodee.sprpsqlsec.data.vo.SaveUserVo;
import com.spacecodee.sprpsqlsec.data.vo.UDUserVo;
import com.spacecodee.sprpsqlsec.enums.RoleEnum;
import com.spacecodee.sprpsqlsec.exceptions.InvalidPasswordException;
import com.spacecodee.sprpsqlsec.persistence.entity.UserEntity;
import com.spacecodee.sprpsqlsec.persistence.repository.IUserRepository;
import com.spacecodee.sprpsqlsec.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UDUserVo registerOneCustomer(SaveUserVo newUser) {
        this.validatePassword(newUser);

        var user = new UserEntity();
        user.setName(newUser.getName());
        user.setUsername(newUser.getUsername());
        user.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
        user.setRole(RoleEnum.CUSTOMER);

        this.userRepository.save(user);

        var udUser = new UDUserVo();
        udUser.setId(user.getId());
        udUser.setName(user.getName());
        udUser.setUsername(user.getUsername());
        udUser.setRole(user.getRole());

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
                    udUser.setRole(user.getRole());

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
