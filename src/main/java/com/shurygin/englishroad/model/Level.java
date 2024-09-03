package com.shurygin.englishroad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Entity
@Table(name = "levels")
public class Level {

    @Id
    private final Integer id;

    @NotEmpty
    private final String name;

    private final String description;

    @Column(name = "position_from")
    private final Integer positionFrom;

    @Column(name = "position_to")
    private final Integer positionTo;

}
