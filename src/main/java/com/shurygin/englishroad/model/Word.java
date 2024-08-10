package com.shurygin.englishroad.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.LazyCollection;

import java.util.List;

@Data
@Entity
@NoArgsConstructor(force = true)
@Table(name = "words")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Word {

    @Id
    @Column(name = "name")
    @NotEmpty
    @EqualsAndHashCode.Include
    private final String name;

    @Column(name = "position")
    @NotEmpty
    private final Integer position;

    @Column(name = "transcription")
    private final String transcription;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "word_name")
    @BatchSize(size = 1000)
    private List<Translation> translations;

    @JsonIgnore
    public Translation getRandomTranslation() {

        if (translations.size() == 0) throw new RuntimeException("Word '"+name+"' has no translations!");

        return translations.get((int) (Math.random()*translations.size()));

    }
}
