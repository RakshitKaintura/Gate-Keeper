package in.rakshit.gatekeeper.api_management_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.rakshit.gatekeeper.api_management_service.dto.requestDto.OrganizationRequest;
import in.rakshit.gatekeeper.api_management_service.dto.requestDto.OrganizationStatusRequest;
import in.rakshit.gatekeeper.api_management_service.dto.requestDto.TenantAdminRequest;
import in.rakshit.gatekeeper.api_management_service.dto.responseDto.OrganizationResponse;
import in.rakshit.gatekeeper.api_management_service.dto.responseDto.TenantAdminResponse;
import in.rakshit.gatekeeper.api_management_service.service.OrganisationService;
import in.rakshit.gatekeeper.api_management_service.service.OrganizationService;
import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/platform/organizations")
public class PlatformOrganizationController {
    
    private final OrganizationService organizationService;

    public PlatformOrganizationController(OrganizationService organizationService){
        this.organizationService=organizationService;
    }

    @PostMapping
    public ResponseEntity<OrganizationResponse> create(@Valid @RequestBody  OrganizationRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                            .body(organizationService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<OrganizationResponse>> findAll(){
        return ResponseEntity.ok(organizationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationResponse> findById(@PathVariable  Long id){
        return ResponseEntity.ok(organizationService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganizationResponse>  update(@PathVariable Long id,@Valid @RequestBody OrganizationRequest request){
        return ResponseEntity.ok(organizationService.update(id,request);

    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrganizationResponse> changeStatus(@PathVariable Long id, @Valid @RequestBody OrganizationStatusRequest request){
        return ResponseEntity.ok(organizationService.changeStatus(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        organizationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // CRUD --> Tenant Admins
    @PostMapping("/{organizationId}/tenant-admin")
    public ResponseEntity<TenantAdminResponse> createTenantAdmin(@PathVariable  Long organizationId,@Valid @RequestBody TenantAdminRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(organizationService.createTenantAdmin(organizationId,request));
    }

    @GetMapping("/{organizationId}/tenant-admin")
    public ResponseEntity<TenantAdminResponse> getTenantAdmin(@PathVariable Long organizationId) {
        return ResponseEntity.ok(organizationService.getTenantAdmin(organizationId));
    }

    @PutMapping("/{organizationId}/tenant-admin")
    public ResponseEntity<TenantAdminResponse> updateTenantAdmin(@PathVariable Long organizationId,
                                                                 @Valid @RequestBody TenantAdminRequest request) {
        return ResponseEntity.ok(organizationService.updateTenantAdmin(organizationId, request));
    }

    @DeleteMapping("/{organizationId}/tenant-admin")
    public ResponseEntity<Void> deleteTenantAdmin(@PathVariable Long organizationId) {
        organizationService.deleteTenantAdmin(organizationId);
        return ResponseEntity.noContent().build();
    }

}
