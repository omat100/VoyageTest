package in.th.transitops.dto;

import in.th.transitops.entity.Trip;
import in.th.transitops.enums.TripLifecycle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record TripRequest(
        @NotBlank(message = "Source is required")
        String source,

        @NotBlank(message = "Destination is required")
        String destination,

        @NotNull(message = "Vehicle id is required")
        Long vehicleId,

        @NotNull(message = "Driver id is required")
        Long driverId,

        @Positive(message = "Weight must be positive")
        int weight,

        @PositiveOrZero(message = "Planned distance must be zero or positive")
        float plannedDistance,

        TripLifecycle lifecycle
) {
}
