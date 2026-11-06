package in.th.transitops.entity;

import in.th.transitops.enums.VehicleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String model;
    private String type;
    @Column(name = "max_load")
    private float maxLoad;
    @Column(precision = 2)
    private float odometer;
    @Column(name = "acquisition_cost", precision = 2)
    private float acquisitionCost;
    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

    @Column(name = "registration_id", unique = true, nullable = false, length = 15)
    @Pattern(regexp = "^[A-Z]{2}-[0-9]{2}-[A-Z]{2}-[0-9]{4}$", message = "Invalid Registration id")
    private String regId;
}