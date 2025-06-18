package com.studeal.team.domain.board.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuctionBoardFile is a Querydsl query type for AuctionBoardFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuctionBoardFile extends EntityPathBase<AuctionBoardFile> {

    private static final long serialVersionUID = -1784607283L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAuctionBoardFile auctionBoardFile = new QAuctionBoardFile("auctionBoardFile");

    public final com.studeal.team.global.common.domain.QBaseEntity _super = new com.studeal.team.global.common.domain.QBaseEntity(this);

    public final QAuctionBoard auctionBoard;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> fileId = createNumber("fileId", Long.class);

    public final StringPath fileName = createString("fileName");

    public final StringPath filePath = createString("filePath");

    public final StringPath fileType = createString("fileType");

    public final BooleanPath isThumbnail = createBoolean("isThumbnail");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final DateTimePath<java.time.LocalDateTime> uploadedAt = createDateTime("uploadedAt", java.time.LocalDateTime.class);

    public QAuctionBoardFile(String variable) {
        this(AuctionBoardFile.class, forVariable(variable), INITS);
    }

    public QAuctionBoardFile(Path<? extends AuctionBoardFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAuctionBoardFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAuctionBoardFile(PathMetadata metadata, PathInits inits) {
        this(AuctionBoardFile.class, metadata, inits);
    }

    public QAuctionBoardFile(Class<? extends AuctionBoardFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auctionBoard = inits.isInitialized("auctionBoard") ? new QAuctionBoard(forProperty("auctionBoard"), inits.get("auctionBoard")) : null;
    }

}

