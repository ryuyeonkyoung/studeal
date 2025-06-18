package com.studeal.team.domain.lesson.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGrade is a Querydsl query type for Grade
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGrade extends EntityPathBase<Grade> {

    private static final long serialVersionUID = -1987242929L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGrade grade = new QGrade("grade");

    public final com.studeal.team.global.common.domain.QBaseEntity _super = new com.studeal.team.global.common.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> gradeId = createNumber("gradeId", Long.class);

    public final QLesson lesson;

    public final NumberPath<Float> score = createNumber("score", Float.class);

    public final com.studeal.team.domain.user.domain.entity.QStudent student;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QGrade(String variable) {
        this(Grade.class, forVariable(variable), INITS);
    }

    public QGrade(Path<? extends Grade> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGrade(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGrade(PathMetadata metadata, PathInits inits) {
        this(Grade.class, metadata, inits);
    }

    public QGrade(Class<? extends Grade> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lesson = inits.isInitialized("lesson") ? new QLesson(forProperty("lesson"), inits.get("lesson")) : null;
        this.student = inits.isInitialized("student") ? new com.studeal.team.domain.user.domain.entity.QStudent(forProperty("student")) : null;
    }

}

