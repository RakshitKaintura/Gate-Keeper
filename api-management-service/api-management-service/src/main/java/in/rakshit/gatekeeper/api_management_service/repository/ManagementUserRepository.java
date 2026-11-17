package in.rakshit.gatekeeper.api_management_service.repository;

import java.util.Optional;

import in.rakshit.gatekeeper.api_management_service.entity.ManagementUser;

public class ManagementUserRepository extends JpaRepository <ManagementUser,Long> {
    Optional<ManagementUser> findByUsername(String username);
    
}
