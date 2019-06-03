package com.hooly.fpl.model.repository;

import com.hooly.fpl.model.entity.Translate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranslateRepository extends JpaRepository<Translate,Long> {

    @Query(value = "select t from Translate t where t.word.id = :wordId")
    List<Translate> findTranslateByWordId(@Param(value = "wordId")Long wordId);
}
