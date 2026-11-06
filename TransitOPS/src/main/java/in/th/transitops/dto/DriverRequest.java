package in.th.transitops.dto;

import in.th.transitops.entity.Driver;
import in.th.transitops.enums.DriverStatus;
import in.th.transitops.enums.LicenseCategory;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public record DriverRequest(
        @NotBlank(message = "Name is required")
        String name,

        @Positive(message = "Contact must be positive")
        long contact,

        @Max(value = 100, message = "Safety score should be less than or equal to 100")
        int safetyScore,

        DriverStatus status,

        @NotBlank(message = "License number is required")
        @Pattern(regexp = "^[A-Z]{2}[0-9]{2}[0-9]{4}[0-9]{7}$", message = "Invalid Driving license")
        String licenseNo,

        @NotNull(message = "License category is required")
        String categories,

        @NotNull(message = "License expiry date is required")
        @Future(message = "License expiry date must be in the future")
        LocalDate expiry
) {

    public Driver toEntity() {
        Driver driver = new Driver();
        driver.setName(name);
        driver.setContact(contact);
        driver.setSafetyScore(safetyScore);
        driver.setStatus(status != null ? status : DriverStatus.AVAILABLE);
        driver.setLicenseNo(licenseNo);
        driver.setCategory(categories);
        driver.setExpiry(expiry);
        return driver;
    }

    public void applyTo(Driver driver) {
        driver.setName(name);
        driver.setContact(contact);
        driver.setSafetyScore(safetyScore);
        if (status != null) {
            driver.setStatus(status);
        }
        driver.setLicenseNo(licenseNo);
        driver.setCategory(categories);
        driver.setExpiry(expiry);
    }
}
