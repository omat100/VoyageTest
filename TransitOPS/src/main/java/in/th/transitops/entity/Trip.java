package in.th.transitops.entity;

import in.th.transitops.enums.TripLifecycle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trips")
@Getter
@Setter
@NoArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotBlank
    private String source;

    @NotBlank
    private String destination;

    @ManyToOne(targetEntity = Vehicle.class, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    @NotNull
    private Vehicle vehicle;

    @ManyToOne(targetEntity = Driver.class, optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    @NotNull
    private Driver driver;

    @Positive
    private int weight;

    @Column(name = "planned_distance")
    @PositiveOrZero
    private float plannedDistance;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle", nullable = false)
    @NotNull
    private TripLifecycle lifecycle = TripLifecycle.DRAFT;
}
