package com.ssafy.entity.rdbms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Debate extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column
    private Long category;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Boolean moderatorOnOff;

    @Column
    private String debateMode;

    @Column
    private String thumbnailUrl;

    @Column(name = "state")
    private String state;

    @Column
    @CreationTimestamp
    private LocalDateTime insertedTime;

    @Column
    private String debateModeOption;

    @Column
    private LocalDateTime callStartTime;

    @Column
    private LocalDateTime callEndTime;

    @OneToOne(mappedBy = "debate")
    private DebateResult debateResult;

    @ManyToOne(fetch = FetchType.LAZY)
    private FileManager fileManager;
}