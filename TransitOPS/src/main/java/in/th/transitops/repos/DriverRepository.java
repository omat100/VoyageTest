package in.th.transitops.repos;

import in.th.transitops.dto.DriverResponse;
import in.th.transitops.entity.Driver;
import in.th.transitops.enums.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    int countByStatus(DriverStatus status);

    Optional<List<DriverResponse>> findFirstByStatus(DriverStatus status);

    List<Driver> findByStatus(DriverStatus status);
}