package com.ssafy.entity.rdbms;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileManager extends BaseEntity{


    private Date registTime;

    @OneToOne
    @JoinColumn(name = "file_manager_id")
    private User user;

    @OneToMany(mappedBy = "fileManager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files = new ArrayList<>();





}
