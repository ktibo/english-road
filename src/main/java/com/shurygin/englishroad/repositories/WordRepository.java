package com.shurygin.englishroad.repositories;

import com.shurygin.englishroad.model.Word;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, String> {
    @Query("FROM Word w\n"
            + "inner join Level l on l.id = :levelIndex\n"
            + "and w.position >= l.positionFrom\n"
            + "and (l.positionTo = 0 or w.position <= l.positionTo)"
            //+ "left join Translation t on w = t.word"
    )
    List<Word> findByLevelIndex(@Param("levelIndex") Integer levelIndex);

    List<Word> findByPositionBetween(@NotEmpty Integer positionFrom, @NotEmpty Integer positionTo);

}
