package com.shurygin.englishroad.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@Data
@Entity
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
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

    private final String translations;

    @JsonIgnore
    public List<String> getTranslationsList() {
        return Arrays.stream(translations.split(","))
                .map(String::trim)
                .toList();
    }

}
