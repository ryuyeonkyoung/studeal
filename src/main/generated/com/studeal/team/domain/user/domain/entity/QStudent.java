package com.studeal.team.domain.user.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudent is a Querydsl query type for Student
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudent extends EntityPathBase<Student> {

    private static final long serialVersionUID = -918938421L;

    public static final QStudent student = new QStudent("student");

    public final QUser _super = new QUser(this);

    //inherited
    public final StringPath bio = _super.bio;

    //inherited
    public final StringPath email = _super.email;

    public final SetPath<com.studeal.team.domain.lesson.domain.Grade, com.studeal.team.domain.lesson.domain.QGrade> grades = this.<com.studeal.team.domain.lesson.domain.Grade, com.studeal.team.domain.lesson.domain.QGrade>createSet("grades", com.studeal.team.domain.lesson.domain.Grade.class, com.studeal.team.domain.lesson.domain.QGrade.class, PathInits.DIRECT2);

    public final SetPath<com.studeal.team.domain.lesson.domain.LessonPresence, com.studeal.team.domain.lesson.domain.QLessonPresence> lessonPresences = this.<com.studeal.team.domain.lesson.domain.LessonPresence, com.studeal.team.domain.lesson.domain.QLessonPresence>createSet("lessonPresences", com.studeal.team.domain.lesson.domain.LessonPresence.class, com.studeal.team.domain.lesson.domain.QLessonPresence.class, PathInits.DIRECT2);

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final StringPath password = _super.password;

    //inherited
    public final EnumPath<com.studeal.team.domain.user.domain.entity.enums.UserRole> role = _super.role;

    //inherited
    public final NumberPath<Long> userId = _super.userId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QStudent(String variable) {
        super(Student.class, forVariable(variable));
    }

    public QStudent(Path<? extends Student> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudent(PathMetadata metadata) {
        super(Student.class, metadata);
    }

}

