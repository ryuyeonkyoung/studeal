package com.studeal.team.domain.board.domain;

import com.studeal.team.global.common.converter.BooleanToYNConverter;
import com.studeal.team.global.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "BOARD_FILES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionBoardFile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_files_seq_gen")
    @SequenceGenerator(name = "board_files_seq_gen", sequenceName = "BOARD_FILES_SEQ", allocationSize = 1)
    private Long fileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private AuctionBoard auctionBoard;

    @Column(length = 255, nullable = false)
    private String fileName;

    @Column(length = 500, nullable = false)
    private String filePath;

    @Column(length = 50)
    private String fileType;

    @Column // Oracle에서는 DATETIME 대신 TIMESTAMP 사용
    private LocalDateTime uploadedAt;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private Boolean isThumbnail = false;
}