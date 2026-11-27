package in.rakshit.gatekeeper.smart_gateway_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HeaderRule {

    private Long id;
    private String direction;
    private String action;
    private String headerName;
    private String headerValue;
}