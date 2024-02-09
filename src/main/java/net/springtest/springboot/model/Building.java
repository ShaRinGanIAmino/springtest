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
@Table(name = "buildings")
public class Building {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long buildingId;

    @Column(name = "building_name")
    private String buildingName;

    @Column(name = "building_localization")
    private String buildingLocalization;
}
