package in.rakshit.gatekeeper.api_management_service.dto.requestDto;

import in.rakshit.gatekeeper.api_management_service.entity.OrganizationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationStatusRequest {

    @NotNull
    private OrganizationStatus status;
}