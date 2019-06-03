package com.hooly.fpl.model.repository;

import com.hooly.fpl.model.entity.UserWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserWordRepository extends JpaRepository<UserWord,Long> {

    Optional<UserWord> findUserWordByUserIdAndWordId(Long userId, Long wordId);
}
