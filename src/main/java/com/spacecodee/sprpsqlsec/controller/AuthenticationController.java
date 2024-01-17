package com.spacecodee.sprpsqlsec.controller;

import com.spacecodee.sprpsqlsec.data.pojo.AuthenticationResponsePojo;
import com.spacecodee.sprpsqlsec.data.vo.AuthenticationRequestVo;
import com.spacecodee.sprpsqlsec.data.vo.UDUserVo;
import com.spacecodee.sprpsqlsec.service.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt) {
        boolean isTokenValid = this.authenticationService.validateToken(jwt);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponsePojo> authenticate(@RequestBody @Valid AuthenticationRequestVo request) {
        AuthenticationResponsePojo rsp = this.authenticationService.login(request);

        return ResponseEntity.ok(rsp);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'ASSISTANT_ADMINISTRATOR', 'CONSUMER')")
    @GetMapping("/profile")
    public ResponseEntity<UDUserVo> profile() {
        return ResponseEntity.ok(this.authenticationService.findLoggedInUser());
    }
}
