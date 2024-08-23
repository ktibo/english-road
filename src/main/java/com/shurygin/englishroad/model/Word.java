package com.shurygin.englishroad.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Arrays;
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

    private String translations;

    @JsonIgnore
    public List<String> getTranslationsList() {
        return Arrays.stream(translations.split(","))
                .map(String::trim)
                .toList();
    }

}
