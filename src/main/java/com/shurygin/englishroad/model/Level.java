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
    private final Integer id;

    @NotEmpty
    private final String name;

    private final String description;

    @Column(name = "position_from")
    private final Integer positionFrom;

    @Column(name = "position_to")
    private final Integer positionTo;

    public String getDescription() {
        if (positionTo == 0)
            return String.format("Слова от %d", positionFrom);
        return String.format("Слова от %d до %d", positionFrom, positionTo);
    }
}
