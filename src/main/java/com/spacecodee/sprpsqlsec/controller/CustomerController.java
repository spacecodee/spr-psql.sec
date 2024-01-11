package com.spacecodee.sprpsqlsec.controller;

import com.spacecodee.sprpsqlsec.data.dto.RegisterUserDto;
import com.spacecodee.sprpsqlsec.data.vo.SaveUserVo;
import com.spacecodee.sprpsqlsec.service.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final IAuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<RegisterUserDto> registerOne(@RequestBody @Valid SaveUserVo newUser) {
        var user = this.authenticationService.registerOneCustomer(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
