package com.ssafy.entity.rdbms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class
User extends BaseEntity {

    @Column(unique = true,
            name = "user_email")
    private String userEmail;

    @ToString.Exclude
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "username")
    private String name;

    @Column
    private String department;

    @Column
    private String position;

    @Column
    private int grade;

    @Column(name = "class")
    private int classNum;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Debate> debates = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DebateHistory> debateHistories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserDebate> userDebates = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "file_manager_id")
    private FileManager fileManager;


    public void createFileManager(FileManager fileManager){
        this.fileManager = fileManager;

    }
    public void update(String department, int grade, int classNum){
        this.department = department;
        this.grade = grade;
        this.classNum = classNum;
    }
}
