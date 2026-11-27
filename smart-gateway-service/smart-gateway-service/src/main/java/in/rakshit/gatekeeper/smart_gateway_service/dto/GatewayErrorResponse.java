package in.rakshit.gatekeeper.smart_gateway_service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GatewayErrorResponse {

    private int status;
    private String message;
    private String correlationId;
    private LocalDateTime timestamp;
}
