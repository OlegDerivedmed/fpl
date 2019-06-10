package com.hooly.fpl.model.repository;

import com.hooly.fpl.model.entity.User;
import com.hooly.fpl.model.entity.UserWord;
import com.hooly.fpl.model.entity.Word;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserWordRepository extends JpaRepository<UserWord,Long> {

    Optional<UserWord> findUserWordByUserIdAndWordId(Long userId, Long wordId);
    @Query("select uw.word from UserWord uw where uw.user = :user")
    List<Word> findWordsByUser(@Param(value = "user") User user, Pageable pageable);
    @Modifying
    @Query("delete from UserWord uw where uw.word.id = :wordId and uw.user.id = :userId")
    void removeWordById(@Param(value = "wordId") Long wordId, @Param(value = "userId") Long userId);
}
