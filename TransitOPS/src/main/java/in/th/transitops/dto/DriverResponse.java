package in.th.transitops.dto;

import in.th.transitops.entity.Driver;
import in.th.transitops.enums.DriverStatus;
import in.th.transitops.enums.LicenseCategory;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

public record DriverResponse(
        long id,
        String name,
        long contact,
        int safetyScore,
        DriverStatus status,
        String licenseNo,
        String categories,
        LocalDate expiry
) {

    public static DriverResponse fromEntity(Driver driver) {
        return new DriverResponse(
                driver.getId(),
                driver.getName(),
                driver.getContact(),
                driver.getSafetyScore(),
                driver.getStatus(),
                driver.getLicenseNo(),
                driver.getCategory(),
                driver.getExpiry()
        );
    }
}
