package com.studeal.team.domain.board.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuctionBoard is a Querydsl query type for AuctionBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuctionBoard extends EntityPathBase<AuctionBoard> {

    private static final long serialVersionUID = 1276339377L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAuctionBoard auctionBoard = new QAuctionBoard("auctionBoard");

    public final com.studeal.team.global.common.domain.QBaseEntity _super = new com.studeal.team.global.common.domain.QBaseEntity(this);

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> expectedPrice = createNumber("expectedPrice", Long.class);

    public final ListPath<AuctionBoardFile, QAuctionBoardFile> files = this.<AuctionBoardFile, QAuctionBoardFile>createList("files", AuctionBoardFile.class, QAuctionBoardFile.class, PathInits.DIRECT2);

    public final com.studeal.team.domain.lesson.domain.QLesson lesson;

    public final EnumPath<com.studeal.team.domain.user.domain.entity.enums.MajorSubject> major = createEnum("major", com.studeal.team.domain.user.domain.entity.enums.MajorSubject.class);

    public final SetPath<com.studeal.team.domain.negotiation.domain.Negotiation, com.studeal.team.domain.negotiation.domain.QNegotiation> negotiations = this.<com.studeal.team.domain.negotiation.domain.Negotiation, com.studeal.team.domain.negotiation.domain.QNegotiation>createSet("negotiations", com.studeal.team.domain.negotiation.domain.Negotiation.class, com.studeal.team.domain.negotiation.domain.QNegotiation.class, PathInits.DIRECT2);

    public final StringPath specMajor = createString("specMajor");

    public final com.studeal.team.domain.user.domain.entity.QTeacher teacher;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QAuctionBoard(String variable) {
        this(AuctionBoard.class, forVariable(variable), INITS);
    }

    public QAuctionBoard(Path<? extends AuctionBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAuctionBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAuctionBoard(PathMetadata metadata, PathInits inits) {
        this(AuctionBoard.class, metadata, inits);
    }

    public QAuctionBoard(Class<? extends AuctionBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lesson = inits.isInitialized("lesson") ? new com.studeal.team.domain.lesson.domain.QLesson(forProperty("lesson"), inits.get("lesson")) : null;
        this.teacher = inits.isInitialized("teacher") ? new com.studeal.team.domain.user.domain.entity.QTeacher(forProperty("teacher")) : null;
    }

}

