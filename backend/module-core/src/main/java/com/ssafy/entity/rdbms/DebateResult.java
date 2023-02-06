package com.ssafy.entity.rdbms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DebateResult extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "debate_id")
    private Debate debate;

    @Column
    private String summary;

    @Column(name = "mvp_result")
    private String mvp;
}