package com.drunar.coincorner.database.entity;

import com.drunar.coincorner.listener.CustomRevisionListener;
import com.querydsl.core.annotations.QueryExclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@RevisionEntity(CustomRevisionListener.class)
@QueryExclude
public class Revision{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    private Integer id;

    @RevisionTimestamp
    private Long timestamp;

    private String author;

    @Transient
    public Date getRevisionDate() {
        return new Date( timestamp );
    }

    public long getTimestamp() {
        return timestamp;
    }

}
