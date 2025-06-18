package com.studeal.team.domain.lesson.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLessonPresence is a Querydsl query type for LessonPresence
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLessonPresence extends EntityPathBase<LessonPresence> {

    private static final long serialVersionUID = -1926420261L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLessonPresence lessonPresence = new QLessonPresence("lessonPresence");

    public final com.studeal.team.global.common.domain.QBaseEntity _super = new com.studeal.team.global.common.domain.QBaseEntity(this);

    public final DatePath<java.time.LocalDate> attendanceDate = createDate("attendanceDate", java.time.LocalDate.class);

    public final NumberPath<Long> attendanceId = createNumber("attendanceId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QLesson lesson;

    public final EnumPath<com.studeal.team.domain.enrollment.domain.enums.AttendanceStatus> status = createEnum("status", com.studeal.team.domain.enrollment.domain.enums.AttendanceStatus.class);

    public final com.studeal.team.domain.user.domain.entity.QStudent student;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QLessonPresence(String variable) {
        this(LessonPresence.class, forVariable(variable), INITS);
    }

    public QLessonPresence(Path<? extends LessonPresence> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLessonPresence(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLessonPresence(PathMetadata metadata, PathInits inits) {
        this(LessonPresence.class, metadata, inits);
    }

    public QLessonPresence(Class<? extends LessonPresence> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lesson = inits.isInitialized("lesson") ? new QLesson(forProperty("lesson"), inits.get("lesson")) : null;
        this.student = inits.isInitialized("student") ? new com.studeal.team.domain.user.domain.entity.QStudent(forProperty("student")) : null;
    }

}

