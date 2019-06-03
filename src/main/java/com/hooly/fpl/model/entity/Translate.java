package com.hooly.fpl.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TRANSLATE", schema = "FPL")
@Data
public class Translate extends TsBaseEntity {

    @Id
    @SequenceGenerator(name = "IdGenerator", sequenceName = "fpl.translate_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdGenerator")
    @Column(name = "id")
    private Long id;
    @Column(name = "value")
    private String value;
    @JoinColumn(name = "word_id")
    @ManyToOne
    @JsonIgnore
    private Word word;

    @Override
    public String toString() {
        return "Translate{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Translate translate = (Translate) o;
        return Objects.equals(id, translate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
