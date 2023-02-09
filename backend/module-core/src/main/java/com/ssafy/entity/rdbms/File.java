package com.ssafy.entity.rdbms;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File extends BaseEntity{


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_manager_id")
    private FileManager fileManager;

    private String originFileName;

    @Column
    private String savedFileName;

    @Column
    private String savedPath;

    @Column
    private long size;

    @Column
    private String extension;

    @Column
    private boolean deleted;

    @Column
    private String source;

    public void updateDeleted(){
        this.deleted = true;
    }
}
