package com.studeal.team.domain.lesson.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLesson is a Querydsl query type for Lesson
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLesson extends EntityPathBase<Lesson> {

    private static final long serialVersionUID = -1343297600L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLesson lesson = new QLesson("lesson");

    public final com.studeal.team.global.common.domain.QBaseEntity _super = new com.studeal.team.global.common.domain.QBaseEntity(this);

    public final com.studeal.team.domain.board.domain.QAuctionBoard auctionBoard;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final SetPath<Grade, QGrade> grades = this.<Grade, QGrade>createSet("grades", Grade.class, QGrade.class, PathInits.DIRECT2);

    public final NumberPath<Long> lessonId = createNumber("lessonId", Long.class);

    public final SetPath<LessonImage, QLessonImage> lessonImages = this.<LessonImage, QLessonImage>createSet("lessonImages", LessonImage.class, QLessonImage.class, PathInits.DIRECT2);

    public final SetPath<LessonPresence, QLessonPresence> lessonPresences = this.<LessonPresence, QLessonPresence>createSet("lessonPresences", LessonPresence.class, QLessonPresence.class, PathInits.DIRECT2);

    public final com.studeal.team.domain.negotiation.domain.QNegotiation negotiation;

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final com.studeal.team.domain.user.domain.entity.QTeacher teacher;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QLesson(String variable) {
        this(Lesson.class, forVariable(variable), INITS);
    }

    public QLesson(Path<? extends Lesson> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLesson(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLesson(PathMetadata metadata, PathInits inits) {
        this(Lesson.class, metadata, inits);
    }

    public QLesson(Class<? extends Lesson> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auctionBoard = inits.isInitialized("auctionBoard") ? new com.studeal.team.domain.board.domain.QAuctionBoard(forProperty("auctionBoard"), inits.get("auctionBoard")) : null;
        this.negotiation = inits.isInitialized("negotiation") ? new com.studeal.team.domain.negotiation.domain.QNegotiation(forProperty("negotiation"), inits.get("negotiation")) : null;
        this.teacher = inits.isInitialized("teacher") ? new com.studeal.team.domain.user.domain.entity.QTeacher(forProperty("teacher")) : null;
    }

}

