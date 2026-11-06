package in.th.transitops.dto;

import in.th.transitops.entity.Vehicle;
import in.th.transitops.enums.VehicleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

public record VehicleRequest(
        @NotBlank(message = "Model is required")
        String model,

        @NotBlank(message = "Type is required")
        String type,

        @PositiveOrZero(message = "Max load must be zero or positive")
        float maxLoad,

        @PositiveOrZero(message = "Odometer must be zero or positive")
        float odometer,

        @PositiveOrZero(message = "Acquisition cost must be zero or positive")
        float acquisitionCost,

        VehicleStatus status,

        @NotBlank(message = "Registration id is required")
        @Pattern(regexp = "^[A-Z]{2}-[0-9]{2}-[A-Z]{2}-[0-9]{4}$", message = "Invalid Registration id")
        String regId
) {

    public Vehicle toEntity() {
        Vehicle vehicle = new Vehicle();
        vehicle.setModel(model);
        vehicle.setType(type);
        vehicle.setMaxLoad(maxLoad);
        vehicle.setOdometer(odometer);
        vehicle.setAcquisitionCost(acquisitionCost);
        vehicle.setStatus(status != null ? status : VehicleStatus.AVAILABLE);
        vehicle.setRegId(regId);
        return vehicle;
    }

    public void applyTo(Vehicle vehicle) {
        vehicle.setModel(model);
        vehicle.setType(type);
        vehicle.setMaxLoad(maxLoad);
        vehicle.setOdometer(odometer);
        vehicle.setAcquisitionCost(acquisitionCost);
        if (status != null) {
            vehicle.setStatus(status);
        }
        vehicle.setRegId(regId);
    }
}
