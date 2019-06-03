package com.hooly.fpl.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@MappedSuperclass
@Data
public class TsBaseEntity {

    @Column(name = "update_ts")
    private LocalDateTime updateTs;
    @Column(name = "create_ts")
    private LocalDateTime createTs;

    @PrePersist
    public void populateDateTime() {
        createTs = LocalDateTime.now(ZoneOffset.UTC);
        updateTs = LocalDateTime.now(ZoneOffset.UTC);
    }

    @PreUpdate
    public void updateUpdateTime() {
        updateTs = LocalDateTime.now(ZoneOffset.UTC);
    }
}
