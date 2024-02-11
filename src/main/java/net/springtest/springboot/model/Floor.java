package net.springtest.springboot.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "floors")
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long floorId;

    @Column(name = "floor_number")
    private int floorNumber;

    @Column(name = "building_id")
    private String buildingId;

    @Column(name = "floor_state")
    private String floorState;
}
