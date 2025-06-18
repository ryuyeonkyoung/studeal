package com.studeal.team.domain.negotiation.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNegotiation is a Querydsl query type for Negotiation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNegotiation extends EntityPathBase<Negotiation> {

    private static final long serialVersionUID = -967322632L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNegotiation negotiation = new QNegotiation("negotiation");

    public final com.studeal.team.global.common.domain.QBaseEntity _super = new com.studeal.team.global.common.domain.QBaseEntity(this);

    public final com.studeal.team.domain.board.domain.QAuctionBoard auctionBoard;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.studeal.team.domain.enrollment.domain.QEnrollment enrollment;

    public final NumberPath<Long> negotiationId = createNumber("negotiationId", Long.class);

    public final NumberPath<Long> proposedPrice = createNumber("proposedPrice", Long.class);

    public final EnumPath<com.studeal.team.domain.negotiation.domain.enums.NegotiationStatus> status = createEnum("status", com.studeal.team.domain.negotiation.domain.enums.NegotiationStatus.class);

    public final com.studeal.team.domain.user.domain.entity.QStudent student;

    public final com.studeal.team.domain.user.domain.entity.QTeacher teacher;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QNegotiation(String variable) {
        this(Negotiation.class, forVariable(variable), INITS);
    }

    public QNegotiation(Path<? extends Negotiation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNegotiation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNegotiation(PathMetadata metadata, PathInits inits) {
        this(Negotiation.class, metadata, inits);
    }

    public QNegotiation(Class<? extends Negotiation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auctionBoard = inits.isInitialized("auctionBoard") ? new com.studeal.team.domain.board.domain.QAuctionBoard(forProperty("auctionBoard"), inits.get("auctionBoard")) : null;
        this.enrollment = inits.isInitialized("enrollment") ? new com.studeal.team.domain.enrollment.domain.QEnrollment(forProperty("enrollment"), inits.get("enrollment")) : null;
        this.student = inits.isInitialized("student") ? new com.studeal.team.domain.user.domain.entity.QStudent(forProperty("student")) : null;
        this.teacher = inits.isInitialized("teacher") ? new com.studeal.team.domain.user.domain.entity.QTeacher(forProperty("teacher")) : null;
    }

}

