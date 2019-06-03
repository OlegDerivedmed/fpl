package com.hooly.fpl.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "app_user_word", schema = "fpl")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserWord extends TsBaseEntity {
    @Id
    @SequenceGenerator(name = "IdGenerator", sequenceName = "fpl.app_user_word_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdGenerator")
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word word;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserWord userWord = (UserWord) o;
        return Objects.equals(id, userWord.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
