-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `user_id`          varchar(64) NOT NULL,
    `username`         varchar(32) NOT NULL COMMENT '用户名',
    `phone`            varchar(16) NOT NULL COMMENT '手机号、登录账号',
    `password`         varchar(64) NOT NULL COMMENT '密码',
    `salt`             varchar(20)          DEFAULT '' COMMENT '加密盐',
    `face_image`       varchar(255)         DEFAULT NULL COMMENT '头像，如果没有，使用默认头像',
    `sex`              tinyint(3)           DEFAULT '0' COMMENT '用户性别：0、男性 1、女性',
    `age`              int(3)               DEFAULT '0' COMMENT '用户年龄',
    `upload_authority` int(3)      NOT NULL DEFAULT '1' COMMENT '上传视频权限：0、不能上传 1、普通视频 2、学术视频',
    `fans_num`         int(11)              DEFAULT '0' COMMENT '粉丝数量',
    `follow_num`       int(11)              DEFAULT '0' COMMENT '关注的人数量',
    `like_num`         int(11)              DEFAULT '0' COMMENT '收到的赞数量',
    `description`      varchar(128)         DEFAULT NULL COMMENT '个人简介',
    `email`            varchar(32)          DEFAULT NULL COMMENT '邮箱',
    `status`           tinyint(3)           DEFAULT '0' COMMENT '用户状态：0、正常使用 1、用户注销 2、违规用户',
    `create_time`      timestamp   not null default current_timestamp comment '创建时间',
    `update_time`      timestamp   not null default current_timestamp on update current_timestamp comment '修改时间',
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `phone` (`phone`),
    UNIQUE KEY `username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '用户信息表';

-- ----------------------------
-- 视频信息表
-- ----------------------------
DROP TABLE IF EXISTS `videos`;
CREATE TABLE `videos`
(
    `video_id`         varchar(64) NOT NULL,
    `user_id`          varchar(64) NOT NULL COMMENT '发布者id',
    `video_cover_path` varchar(255)         DEFAULT NULL COMMENT '视频封面路径',
    `title`            varchar(256)         DEFAULT NULL COMMENT '视频题目',
    `video_desc`       varchar(512)         DEFAULT NULL COMMENT '视频描述',
    `video_path`       varchar(255)         DEFAULT NULL COMMENT '视频存放的路径',
    `like_num`         bigint(20)  NOT NULL DEFAULT '0' COMMENT '喜欢/赞美的数量',
    `comment_num`      bigint(20)  NOT NULL DEFAULT '0' COMMENT '评论的数量',
    `forward_num`      bigint(20)  NOT NULL DEFAULT '0' COMMENT '转发的数量',
    `play_num`         bigint(20)  NOT NULL DEFAULT '0' COMMENT '视频播放数量',
    `status`           tinyint(3)  NOT NULL DEFAULT '0' COMMENT '视频状态：0、正在发布 1、发布成功 2、禁止播放(管理员操作) 3、视频删除(用户操作) ',
    `is_public`        tinyint(3)  NOT NULL DEFAULT '0' COMMENT '是否公开：0、私有、不公开 1、公开',
    `tag`              varchar(32) NOT NULL COMMENT '标签',
    `score`            double(16,2) NOT NULL DEFAULT '100.00' COMMENT '视频得分',
    `create_time`      timestamp   not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`video_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='视频信息表';

-- ----------------------------
-- 学术交流视频信息表
-- ----------------------------
DROP TABLE IF EXISTS `academic_videos`;
CREATE TABLE `academic_videos`
(
    `academic_video_id` varchar(64)  NOT NULL,
    `user_id`           varchar(64)  NOT NULL COMMENT '发布者id',
    `website_url`       varchar(256) COMMENT '网址',
    `video_cover_path`  varchar(255)          DEFAULT NULL COMMENT '视频封面路径',
    `title`             varchar(256)          DEFAULT NULL COMMENT '视频题目',
    `video_desc`        varchar(512)          DEFAULT NULL COMMENT '视频描述',
    `video_path`        varchar(255)          DEFAULT NULL COMMENT '视频存放的路径',
    `like_num`          bigint(20)   NOT NULL DEFAULT '0' COMMENT '喜欢/赞美的数量',
    `comment_num`       bigint(20)   NOT NULL DEFAULT '0' COMMENT '评论的数量',
    `forward_num`       bigint(20)   NOT NULL DEFAULT '0' COMMENT '转发的数量',
    `play_num`          bigint(20)   NOT NULL DEFAULT '0' COMMENT '视频播放数量',
    `score`             double(16,2)  NOT NULL DEFAULT '100.00' COMMENT '视频得分',
    `status`            tinyint(3)   NOT NULL DEFAULT '0' COMMENT '视频状态：0、正在发布 1、发布成功 2、禁止播放(管理员操作) 3、视频删除(用户操作) ',
    `is_public`         tinyint(3)   NOT NULL DEFAULT '0' COMMENT '是否公开：0、私有、不公开 1、公开',
    `tag`               varchar(32)  NOT NULL COMMENT '标签',
    `create_time`       timestamp    not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`academic_video_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='学术交流视频信息表';

-- ----------------------------
-- 学术视频图片表
-- ----------------------------
DROP TABLE IF EXISTS `academic_videos_picture`;
CREATE TABLE `academic_videos_picture`
(
    `picture_id`         varchar(64) NOT NULL,
    `academic_videos_id` varchar(64) NOT NULL COMMENT '所属的学术视频id',
    `url`                varchar(255)         DEFAULT NULL COMMENT '图片url地址',
    `sort`               tinyint(8)  NOT NULL COMMENT '图片顺序',
    `status`             tinyint(3)  NOT NULL DEFAULT '0' COMMENT '状态：0，正常；1，弃用',
    `create_time`        timestamp   not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`picture_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '学术视频图片表';

-- ----------------------------
-- 学术交流视频论文信息表
-- ----------------------------
DROP TABLE IF EXISTS `paper`;
CREATE TABLE `paper`
(
    `paper_id`          varchar(64)  NOT NULL COMMENT '论文id',
    `academic_video_id` varchar(64)  NOT NULL COMMENT '视频id',
    `title`             varchar(256)  COMMENT '论文标题',
    `periodical`        varchar(256)  COMMENT '视频论文期刊',
    `index`             varchar(256)  COMMENT '论文索引',
    `publish_time`      timestamp     COMMENT '论文发布时间',
    `website_url`       varchar(256)  COMMENT '网址',
    `text`              text          COMMENT '论文内容',
    `status`            tinyint(3)   NOT NULL DEFAULT '0' COMMENT '论文状态：0、正常显示 1、禁止显示',
    `create_time`       timestamp    not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`paper_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='论文信息表';

-- ----------------------------
-- 学术交流视频论文作者信息
-- ----------------------------
DROP TABLE IF EXISTS `paper_author`;
CREATE TABLE `paper_author`
(
    `author_id`   varchar(64)  NOT NULL COMMENT '作者id',
    `paper_id`    varchar(64)  NOT NULL COMMENT '论文id',
    `name`        varchar(64)  NOT NULL COMMENT '作者姓名',
    `company`     varchar(256)  COMMENT '学校或单位',
    `position`    varchar(256)  COMMENT '职位',
    `contact`     varchar(256)  COMMENT '联系方式',
    `sort`        tinyint(8)   NOT NULL COMMENT '论文作者顺序',
    `status`      tinyint(3)   NOT NULL DEFAULT '0' COMMENT '论文作者状态：0、正常显示 1、禁止显示',
    `create_time` timestamp    not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`author_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='论文信息表';

-- ----------------------------
-- 用户屏蔽/拉黑表
-- ----------------------------
DROP TABLE IF EXISTS `shield`;
CREATE TABLE `shield`
(
    `id`           varchar(64) NOT NULL,
    `from_user_id` varchar(64) NOT NULL COMMENT '执行屏蔽操作的用户id',
    `to_user_id`   varchar(64) NOT NULL COMMENT '被屏蔽的用户id',
    `create_time`  timestamp   not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '用户屏蔽/拉黑表';