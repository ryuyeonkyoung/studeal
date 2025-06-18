package com.studeal.team.domain.enrollment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEnrollment is a Querydsl query type for Enrollment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEnrollment extends EntityPathBase<Enrollment> {

    private static final long serialVersionUID = -1612563880L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEnrollment enrollment = new QEnrollment("enrollment");

    public final com.studeal.team.global.common.domain.QBaseEntity _super = new com.studeal.team.global.common.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> enrolledAt = createDateTime("enrolledAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> enrollmentId = createNumber("enrollmentId", Long.class);

    public final BooleanPath isActive = createBoolean("isActive");

    public final com.studeal.team.domain.negotiation.domain.QNegotiation negotiation;

    public final NumberPath<Long> paidAmount = createNumber("paidAmount", Long.class);

    public final EnumPath<com.studeal.team.domain.enrollment.domain.enums.EnrollmentStatus> status = createEnum("status", com.studeal.team.domain.enrollment.domain.enums.EnrollmentStatus.class);

    public final com.studeal.team.domain.user.domain.entity.QStudent student;

    public final com.studeal.team.domain.user.domain.entity.QTeacher teacher;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QEnrollment(String variable) {
        this(Enrollment.class, forVariable(variable), INITS);
    }

    public QEnrollment(Path<? extends Enrollment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEnrollment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEnrollment(PathMetadata metadata, PathInits inits) {
        this(Enrollment.class, metadata, inits);
    }

    public QEnrollment(Class<? extends Enrollment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.negotiation = inits.isInitialized("negotiation") ? new com.studeal.team.domain.negotiation.domain.QNegotiation(forProperty("negotiation"), inits.get("negotiation")) : null;
        this.student = inits.isInitialized("student") ? new com.studeal.team.domain.user.domain.entity.QStudent(forProperty("student")) : null;
        this.teacher = inits.isInitialized("teacher") ? new com.studeal.team.domain.user.domain.entity.QTeacher(forProperty("teacher")) : null;
    }

}

