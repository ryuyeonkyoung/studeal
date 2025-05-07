package com.studeal.team.domain;

import com.studeal.team.converter.BooleanToYNConverter;
import com.studeal.team.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "CLASS_FILE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassFile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private Course course;

    @Column(length = 255, nullable = false)
    private String fileName;

    @Column(length = 500, nullable = false)
    private String filePath;

    @Column(length = 50)
    private String fileType;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime uploadedAt;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(columnDefinition = "CHAR(1)", nullable = false)
    private Boolean isThumbnail = false;
}