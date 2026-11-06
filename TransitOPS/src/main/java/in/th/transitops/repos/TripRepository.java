package in.th.transitops.repos;

import in.th.transitops.entity.Trip;
import in.th.transitops.enums.TripLifecycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query("SELECT t FROM Trip t WHERE t.lifecycle = 'ACTIVE' OR t.lifecycle = 'PENDING'")
    List<Trip> findByActiveAndPending();
}