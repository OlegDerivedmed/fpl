package com.hooly.fpl.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "WORD", schema = "FPL")
@Data
public class Word extends TsBaseEntity{

    @Id
    @SequenceGenerator(name = "IdGenerator", sequenceName = "fpl.word_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdGenerator")
    @Column(name = "id")
    private Long id;
    @Column(name = "word")
    private String word;
    @OneToMany(mappedBy = "word",fetch = FetchType.LAZY)
    private List<Translate> translates;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Word word = (Word) o;
        return Objects.equals(id, word.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
