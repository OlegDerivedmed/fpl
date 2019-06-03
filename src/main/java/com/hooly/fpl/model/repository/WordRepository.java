package com.hooly.fpl.model.repository;

import com.hooly.fpl.model.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word,Long> {

    @Query(value = "select w from Word w where w.word = :word")
    Optional<Word> findWordByWord(@Param(value = "word") String word);
}
