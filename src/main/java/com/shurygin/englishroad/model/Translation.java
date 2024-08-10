package com.shurygin.englishroad.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor(force = true)
@Entity
@Table(name = "translations")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Translation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private final UUID id;

    @Column(name = "name")
    @NotEmpty
    @EqualsAndHashCode.Include
    private final String name;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @NotEmpty
//    @JsonIgnore // prevent recursion
//    private Word word;

    @Column(name = "position")
    private final Integer position;

}
