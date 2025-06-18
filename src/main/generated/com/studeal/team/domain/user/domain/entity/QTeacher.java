package com.studeal.team.domain.user.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeacher is a Querydsl query type for Teacher
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeacher extends EntityPathBase<Teacher> {

    private static final long serialVersionUID = -479369614L;

    public static final QTeacher teacher = new QTeacher("teacher");

    public final QUser _super = new QUser(this);

    //inherited
    public final StringPath bio = _super.bio;

    //inherited
    public final StringPath email = _super.email;

    public final SetPath<com.studeal.team.domain.lesson.domain.Lesson, com.studeal.team.domain.lesson.domain.QLesson> lessons = this.<com.studeal.team.domain.lesson.domain.Lesson, com.studeal.team.domain.lesson.domain.QLesson>createSet("lessons", com.studeal.team.domain.lesson.domain.Lesson.class, com.studeal.team.domain.lesson.domain.QLesson.class, PathInits.DIRECT2);

    public final EnumPath<com.studeal.team.domain.user.domain.entity.enums.MajorSubject> major = createEnum("major", com.studeal.team.domain.user.domain.entity.enums.MajorSubject.class);

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final StringPath password = _super.password;

    public final NumberPath<Float> rating = createNumber("rating", Float.class);

    //inherited
    public final EnumPath<com.studeal.team.domain.user.domain.entity.enums.UserRole> role = _super.role;

    //inherited
    public final NumberPath<Long> userId = _super.userId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QTeacher(String variable) {
        super(Teacher.class, forVariable(variable));
    }

    public QTeacher(Path<? extends Teacher> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTeacher(PathMetadata metadata) {
        super(Teacher.class, metadata);
    }

}

