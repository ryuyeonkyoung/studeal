package com.studeal.team.domain.board.domain;

import com.studeal.team.domain.lesson.domain.Lesson;
import com.studeal.team.domain.negotiation.domain.Negotiation;
import com.studeal.team.domain.user.domain.Teacher;
import com.studeal.team.domain.user.domain.enums.MajorSubject;
import com.studeal.team.global.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "BOARDS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionBoard extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "boards_seq_gen")
    @SequenceGenerator(name = "boards_seq_gen", sequenceName = "BOARDS_SEQ", allocationSize = 1)
    private Long boardId;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 2000)
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    private Lesson lesson; // 추가

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;  // 선생님만 작성 가능

    @OneToMany(mappedBy = "auctionBoard", cascade = CascadeType.ALL)
    private Set<Negotiation> negotiations = new HashSet<>(); // 협상 정보

    @Column(nullable = false)
    private Long expectedPrice;  // 예상 수업 가격

    @Enumerated(EnumType.STRING)
    @Column(name = "major", nullable = false)
    private MajorSubject major;  // 과외 과목

    @Column(length = 200)
    private String specMajor;  // 구체적인 과외 주제

    @OneToMany(mappedBy = "auctionBoard", cascade = CascadeType.ALL)
    private List<AuctionBoardFile> files = new ArrayList<>();

    @Version
    private Integer version;
}

