package com.studeal.team.domain.lesson.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLessonImage is a Querydsl query type for LessonImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLessonImage extends EntityPathBase<LessonImage> {

    private static final long serialVersionUID = -1876711045L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLessonImage lessonImage = new QLessonImage("lessonImage");

    public final com.studeal.team.global.common.domain.QBaseEntity _super = new com.studeal.team.global.common.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> fileId = createNumber("fileId", Long.class);

    public final StringPath fileName = createString("fileName");

    public final StringPath filePath = createString("filePath");

    public final StringPath fileType = createString("fileType");

    public final BooleanPath isThumbnail = createBoolean("isThumbnail");

    public final QLesson lesson;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QLessonImage(String variable) {
        this(LessonImage.class, forVariable(variable), INITS);
    }

    public QLessonImage(Path<? extends LessonImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLessonImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLessonImage(PathMetadata metadata, PathInits inits) {
        this(LessonImage.class, metadata, inits);
    }

    public QLessonImage(Class<? extends LessonImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lesson = inits.isInitialized("lesson") ? new QLesson(forProperty("lesson"), inits.get("lesson")) : null;
    }

}

