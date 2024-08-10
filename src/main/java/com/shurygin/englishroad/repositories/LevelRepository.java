package com.shurygin.englishroad.repositories;

import com.shurygin.englishroad.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LevelRepository extends JpaRepository<Level, Integer> {
}
