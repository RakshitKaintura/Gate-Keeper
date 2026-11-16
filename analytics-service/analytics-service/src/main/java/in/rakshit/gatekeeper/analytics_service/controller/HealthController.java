package in.rakshit.gatekeeper.smart_gateway_service.controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.rakshit.gatekeeper.smart_gateway_service.dto.responseDto.HealthRespone;
import io.lettuce.core.dynamic.annotation.Value;

@RestController 
@RequestMapping("/health")
public class HealthController {
    
    private String serviceName;
    private String serviceDescription;

    public HealthController(@Value("${info.app.name}") String serviceName,
                            @Value("${info.app.description}")String serviceDescription  ){
                                this.serviceName=serviceName;
                                this.serviceDescription=serviceDescription;
                            }
    @GetMapping 
    public ResponseEntity<HealthRespone> health(){
         return ResponseEntity.ok(new HealthResponse(serviceName,serviceDescription,"UP"));
    }

}
