package in.th.transitops.repos;

import in.th.transitops.entity.Vehicle;
import in.th.transitops.enums.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    int countByStatus(VehicleStatus status);

    List<Vehicle> findByStatus(VehicleStatus status);
}