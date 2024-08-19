package com.shurygin.englishroad.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor(force = true)
@Table(name = "words")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Word {

    @Id
    @NotEmpty
    @EqualsAndHashCode.Include
    private final String name;

    @NotEmpty
    private final Integer position;

    private final String transcription;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "word_name")
//    @BatchSize(size = 1000)
//    private List<Translation> translations;

    private String translations;

    @JsonIgnore
    public String getRandomTranslation() {
        List<String> translationsList = getTranslationsList();
        if (translationsList.size() == 0) throw new RuntimeException("Word '" + name + "' has no translations!");
        return translationsList.get((int) (Math.random() * translationsList.size()));
    }

    @JsonIgnore
    public List<String> getTranslationsList() {
        return List.of(translations.split(","));
    }

}
