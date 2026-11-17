package in.rakshit.gatekeeper.api_management_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.rakshit.gatekeeper.api_management_service.dto.requestDto.LoginRequest;
import in.rakshit.gatekeeper.api_management_service.dto.responseDto.LoginResponse;
import in.rakshit.gatekeeper.api_management_service.service.AuthenticationService;
import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/auth")
public class AuthenticationController {
        private final AuthenticationService authenticationService;

        public AuthenticationController(AuthenticationService authenticationService){
            this.authenticationService=authenticationService;
        }

        @PostMapping("/login")
        public ResponseEntity<LoginResponse> login(@Valid  @RequestBody  LoginRequest request){
            return  ResponseEntity.ok(authenticationService.login(request));
        }
}
