package in.rakshit.gatekeeper.api_management_service.service;

import in.rakshit.gatekeeper.api_management_service.dto.requestDto.LoginRequest;
import in.rakshit.gatekeeper.api_management_service.dto.responseDto.LoginResponse;
import in.rakshit.gatekeeper.api_management_service.entity.ManagementUser;
import in.rakshit.gatekeeper.api_management_service.entity.OrganizationStatus;
import in.rakshit.gatekeeper.api_management_service.exception.ApiException;
import in.rakshit.gatekeeper.api_management_service.repository.ManagementUserRepository;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusds.jwt.JWTClaimsSet;

@Service 
public class AuthenticationService {

   
    private final ManagementUserRepository managementUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final long expirationSeconds;

    public AuthenticationService(ManagementUserRepository managementUserRepository,
                                 PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder,
                                 @Value("${security.jwt.expiration-seconds}") long expirationSeconds) {
        this.managementUserRepository = managementUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.expirationSeconds = expirationSeconds;
    }
    @Transactional(readOnly=true)
    public LoginResponse login(LoginRequest request) {
        ManagementUser  user=managementUserRepository.findByUsername(request.getUsername())
        .orElseThrow(()->invalidCredentials());

        if(!user.isEnabled() || !passwordEncoder.matches(request.getPassword(),user.getPasswordHash())){
            throw invalidCredentials();
        }

        if(user.getOrganization()!=null && user.getOrganization().getStatus()!=OrganizationStatus.ACTIVE){
            throw new ApiException(HttpStatus.FORBIDDEN, "The Organization is not active");
        }

        Instant issuedAt=Instant.now();
        String role=user.getRole().getName();
        Long organizationId=user.getOrganization()==null ?null :user.getOrganization().getId();

        JwtClaimsSet.Builder claims=JwtClaimsSet.builder()
                                    .issuer("gatekeeper-api-management")
                                    .issuedAt(issuedAt)
                                    .expiresAt(issuedAt.plusSeconds(expirationSeconds))
                                    .subject(user.getUsername())
                                    .claim("role", role);

        if(organizationId!=null){
            claims.claim("organizationId",organizationId);
        }
        JwsHeader header=JwsHeader.with(MacAlgorithm.HS256).build();
        String token=jwtEncoder.encode(
            JwtEncoderParameters.from(header,claims.build())).getTokenValue();

        return new LoginResponse(token, "Bearer", expirationSeconds, role, organizationId);


    }

    private RuntimeException invalidCredentials(){
        return new ApiException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
    }

}
