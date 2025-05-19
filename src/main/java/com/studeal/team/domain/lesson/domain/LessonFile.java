package com.studeal.team.domain.lesson.domain;

import com.studeal.team.global.common.converter.BooleanToYNConverter;
import com.studeal.team.global.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "LESSON_FILES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonFile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_files_seq_gen")
    @SequenceGenerator(name = "lesson_files_seq_gen", sequenceName = "LESSON_FILES_SEQ", allocationSize = 1)
    private Long fileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Column(length = 255, nullable = false)
    private String fileName;

    @Column(length = 500, nullable = false)
    private String filePath;

    @Column(length = 50)
    private String fileType;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private Boolean isThumbnail = false;
}