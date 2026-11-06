package in.th.transitops.entity;

import com.opencsv.bean.CsvDate;
import in.th.transitops.enums.DriverStatus;
import in.th.transitops.enums.LicenseCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "drivers",
        uniqueConstraints = @UniqueConstraint(columnNames = {"licence_number"})
)
@Getter
@Setter
@NoArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String name;
    @Positive
    private long contact;
    @Max(value = 100, message = "Safety score should be less than or equal to 100")
    private int safetyScore;
    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    @NotBlank
    @Column(name = "licence_number", unique = true, nullable = false, length = 20)
    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}[0-9]{4}[0-9]{7}$", message = "Invalid Driving license")
    private String licenseNo;

    @Column(name = "category")
    private String category;

    @Column(name = "license_expiry_date", nullable = false)
    @CsvDate("dd-MM-yyyy")
    private LocalDate expiry;
}