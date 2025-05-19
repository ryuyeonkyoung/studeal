package com.studeal.team.domain.board.domain;

import com.studeal.team.domain.board.domain.enums.BoardType;
import com.studeal.team.domain.negotiation.domain.Negotiation;
import com.studeal.team.domain.user.domain.Student;
import com.studeal.team.domain.user.domain.Teacher;
import com.studeal.team.global.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "BOARDS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "boards_seq_gen")
    @SequenceGenerator(name = "boards_seq_gen", sequenceName = "BOARDS_SEQ", allocationSize = 1)
    private Long boardId;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 2000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student; // 학생이 작성한 경우

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;  // 선생님이 작성한 경우

    @OneToOne
    @JoinColumn(name = "negotiation_id")
    private Negotiation negotiation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoardType type;  // 학생의 요청인지, 선생님의 제안인지

    @Column(nullable = false)
    private Long expectedPrice;  // 예상 수업 가격

    @Column(length = 100)
    private String subject;  // 수업 주제

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private Set<BoardFile> files = new HashSet<>();

    @Version
    private Integer version;
}