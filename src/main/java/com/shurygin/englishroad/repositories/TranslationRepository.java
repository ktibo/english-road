package com.shurygin.englishroad.repositories;

import com.shurygin.englishroad.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TranslationRepository extends JpaRepository<Translation, UUID> {
}