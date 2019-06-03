package com.hooly.fpl.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_token", schema = "fpl")
@Data
public class UserToken extends TsBaseEntity {

    @Id
    @SequenceGenerator(name = "IdGenerator", sequenceName = "fpl.user_token_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdGenerator")
    @Column(name = "id")
    private Long id;
    @Column(name = "token")
    private String token;
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;
    @Column(name = "token_state")
    @Enumerated(value = EnumType.STRING)
    private TokenState tokenState;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserToken that = (UserToken) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
