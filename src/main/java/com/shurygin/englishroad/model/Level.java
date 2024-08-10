package com.shurygin.englishroad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@Entity
@Table(name = "levels")
public class Level {

    @Id
    @Column(name = "id")
    private final Integer id;

    @Column(name = "name")
    @NotEmpty
    private final String name;

    @Column(name = "position_from")
    private final Integer positionFrom;

    @Column(name = "position_to")
    private final Integer positionTo;

}
