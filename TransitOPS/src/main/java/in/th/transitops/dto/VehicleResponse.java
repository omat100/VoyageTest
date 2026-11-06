package in.th.transitops.dto;

import in.th.transitops.entity.Vehicle;
import in.th.transitops.enums.VehicleStatus;

public record VehicleResponse(
        long id,
        String model,
        String type,
        float maxLoad,
        float odometer,
        float acquisitionCost,
        VehicleStatus status,
        String regId
) {

    public static VehicleResponse fromEntity(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getModel(),
                vehicle.getType(),
                vehicle.getMaxLoad(),
                vehicle.getOdometer(),
                vehicle.getAcquisitionCost(),
                vehicle.getStatus(),
                vehicle.getRegId()
        );
    }
}
