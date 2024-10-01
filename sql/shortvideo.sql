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
-- 广告视频信息表
-- ----------------------------
DROP TABLE IF EXISTS `advertisement_videos`;
CREATE TABLE `advertisement_videos`
(
    `video_id`         varchar(64) NOT NULL,
    `user_id`          varchar(64) NOT NULL COMMENT '发布者id',
    `department`       varchar(128)         DEFAULT NULL COMMENT '广告视频所属的机构',
    `video_cover_path` varchar(255)         DEFAULT NULL COMMENT '视频封面路径',
    `title`            varchar(256)         DEFAULT NULL COMMENT '视频题目',
    `video_desc`       varchar(512)         DEFAULT NULL COMMENT '视频描述',
    `video_path`       varchar(255)         DEFAULT NULL COMMENT '视频存放的路径',
    `like_num`         bigint(20)  NOT NULL DEFAULT '0' COMMENT '喜欢/赞美的数量',
    `comment_num`      bigint(20)  NOT NULL DEFAULT '0' COMMENT '评论的数量',
    `forward_num`      bigint(20)  NOT NULL DEFAULT '0' COMMENT '转发的数量',
    `play_num`         bigint(20)  NOT NULL DEFAULT '0' COMMENT '视频播放数量',
    `status`           tinyint(3)  NOT NULL DEFAULT '0' COMMENT '视频状态：0、正在发布 1、发布成功 2、禁止播放(管理员操作) ',
    `is_public`        tinyint(3)  NOT NULL DEFAULT '0' COMMENT '是否公开：0、私有、不公开 1、公开',
    `tag`              varchar(32) NOT NULL COMMENT '标签',
    `create_time`      timestamp   not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`video_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='广告视频信息表';

-- ----------------------------
-- 用户点赞的普通视频
-- ----------------------------
DROP TABLE IF EXISTS `user_like_video`;
CREATE TABLE `user_like_video`
(
    `id`          varchar(64) NOT NULL,
    `user_id`     varchar(64) NOT NULL COMMENT '用户id',
    `video_id`    varchar(64) NOT NULL COMMENT '视频id',
    `create_time` timestamp   not null default current_timestamp COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户点赞的普通视频';

-- ----------------------------
-- 用户点赞的学术视频
-- ----------------------------
DROP TABLE IF EXISTS `user_like_academic_video`;
CREATE TABLE `user_like_academic_video`
(
    `id`          varchar(64) NOT NULL,
    `user_id`     varchar(64) NOT NULL COMMENT '用户id',
    `academic_video_id`    varchar(64) NOT NULL COMMENT '视频id',
    `create_time` timestamp   not null default current_timestamp COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户点赞的学术视频';

-- ----------------------------
-- 评论表
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`
(
    `comment_id`   varchar(64)  NOT NULL,
    `video_id`     varchar(64)  NOT NULL COMMENT '视频id',
    `father_id`    varchar(64)           DEFAULT NULL COMMENT '父评论id',
    `from_user_id` varchar(64)  NOT NULL COMMENT '留言者，评论的用户id',
    `content`      varchar(255) NOT NULL COMMENT '评论内容',
    `like_num`     int(11)               DEFAULT '0' COMMENT '收到的赞数量',
    `status`       tinyint(3)   NOT NULL DEFAULT '0' COMMENT '评论状态：0、公开 1、禁止显示(管理员操作)',
    `create_time`  timestamp    not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`comment_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户评论表';

-- ----------------------------
-- 用户喜欢的评论
-- ----------------------------
DROP TABLE IF EXISTS `user_like_comment`;
CREATE TABLE `user_like_comment`
(
    `id`          varchar(64) NOT NULL,
    `user_id`     varchar(64) NOT NULL COMMENT '用户id',
    `comment_id`  varchar(64) NOT NULL COMMENT '评论id',
    `is_send`     tinyint(3)  NOT NULL COMMENT '是否推送至被点赞用户 0：否 1：是',
    `create_time` timestamp   not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户喜欢的评论';

-- ----------------------------
-- 粉丝表
-- ----------------------------
DROP TABLE IF EXISTS `following_follower`;
CREATE TABLE `following_follower`
(
    `id`           varchar(64) NOT NULL,
    `following_id` varchar(64) NOT NULL COMMENT '被关注者id',
    `follower_id`  varchar(64) NOT NULL COMMENT '关注者id',
    `create_time`  timestamp   not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='粉丝表';

-- ----------------------------
-- 搜索表
-- ----------------------------
DROP TABLE IF EXISTS `search`;
CREATE TABLE `search`
(
    `id`          varchar(64) NOT NULL,
    `content`     varchar(64) NOT NULL COMMENT '搜索内容',
    `user_id`     varchar(64) NOT NULL COMMENT '搜索者id',
    `create_time` timestamp   not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户喜欢的视频';

-- ----------------------------
-- 举报表  暂时先用
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report`
(
    `id`          varchar(64) NOT NULL,
    `user_id`     varchar(64) NOT NULL COMMENT '举报者id',
    `video_id`    varchar(64) NOT NULL COMMENT '视频id',
    `content`     varchar(255) COMMENT '举报内容',
    `tag`         varchar(64) NOT NULL COMMENT '举报标签，枚举类型，可选',
    `status`      tinyint(3)  NOT NULL DEFAULT '0' COMMENT '举报处理状态：0、未处理 1、已处理',
    `create_time` timestamp   not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='举报表';

-- ----------------------------
-- 举报视频表  后续改进
-- ----------------------------
DROP TABLE IF EXISTS `report_video`;
CREATE TABLE `report_video`
(
    `id`          varchar(64) NOT NULL,
    `user_id`     varchar(64) NOT NULL COMMENT '举报者id',
    `video_id`    varchar(64) NOT NULL COMMENT '视频id',
    `content`     varchar(255) COMMENT '举报内容',
    `tag`         varchar(64) NOT NULL COMMENT '举报标签，枚举类型，可选',
    `status`      tinyint(3)  NOT NULL DEFAULT '0' COMMENT '举报处理状态：0、未处理 1、已处理',
    `create_time` timestamp   not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='举报视频表';


-- ----------------------------
-- 举报用户表 后续改进
-- ----------------------------
DROP TABLE IF EXISTS `report_user`;
CREATE TABLE `report_user`
(
    `id`               varchar(64) NOT NULL,
    `user_id`          varchar(64) NOT NULL COMMENT '举报者id',
    `reported_user_id` varchar(64) NOT NULL COMMENT '被举报的视频id',
    `content`          varchar(255) COMMENT '举报内容',
    `tag`              varchar(64) NOT NULL COMMENT '举报标签，枚举类型，可选',
    `status`           tinyint(3)  NOT NULL DEFAULT '0' COMMENT '举报处理状态：0、未处理 1、已处理',
    `create_time`      timestamp   not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='举报视频表';


-- ----------------------------
-- 视频标签表
-- ----------------------------
DROP TABLE IF EXISTS `video_tag`;
CREATE TABLE `video_tag`
(
    `id`      varchar(64) NOT NULL,
    `content` varchar(32) NOT NULL COMMENT '标签内容',
    `status`  tinyint(3)  NOT NULL DEFAULT '0' COMMENT '标签是否启用：0、当前正在使用 1、已弃用',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='视频标签表';


-- ----------------------------
-- 举报标签表
-- ----------------------------
DROP TABLE IF EXISTS `report_tag`;
CREATE TABLE `report_tag`
(
    `id`      varchar(64) NOT NULL,
    `content` varchar(32) NOT NULL COMMENT '标签内容',
    `status`  tinyint(3)  NOT NULL DEFAULT '0' COMMENT '标签是否启用：0、当前正在使用 1、已弃用',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='举报标签表';


-- ----------------------------
-- 安装包版本号
-- ----------------------------
DROP TABLE IF EXISTS `package_info`;
CREATE TABLE `package_info`
(
    `id`          int       NOT NULL auto_increment,
    `number`      varchar(64)        DEFAULT NULL COMMENT '版本号',
    `description` varchar(512)       DEFAULT NULL COMMENT '版本描述',
    `url`         varchar(256)       DEFAULT NULL COMMENT '版本地址',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='app配置信息表';

-- ----------------------------
-- 静态成就表
-- ----------------------------
DROP TABLE IF EXISTS `achievement_static`;
CREATE TABLE `achievement_static`
(
    `achievement_id` varchar(64)   NOT NULL,
    `title`          varchar(64)   NOT NULL COMMENT '成就标题',
    `desc`           varchar(128)  NOT NULL COMMENT '成就描述',
    `type`           tinyint(3)    NOT NULL COMMENT '成就种类',
    `condition`      tinyint(3)    NOT NULL COMMENT '达成成就的条件',
    `score`          tinyint(8)    NOT NULL COMMENT '达成成就的分数',
    `level`          tinyint(8)    NOT NULL COMMENT '成就等级',
    `medal`          varchar(1024) NOT NULL COMMENT '成就勋章',
    `status`         tinyint(3)    NOT NULL DEFAULT '0' COMMENT '成就状态：0、正常 1、弃用',
    `create_time`    timestamp     not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`achievement_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='静态成就表';

-- ----------------------------
-- 用户静态成就表
-- ----------------------------
DROP TABLE IF EXISTS `user_achievement_static`;
CREATE TABLE `user_achievement_static`
(
    `id`             varchar(64) NOT NULL,
    `user_id`        varchar(64) NOT NULL,
    `achievement_id` varchar(64) NOT NULL,
    `status`         tinyint(3)  NOT NULL DEFAULT '0' COMMENT '状态：0，正常；1，弃用',
    `create_time`    timestamp   not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户静态成就表';

-- ----------------------------
-- 推广图片表
-- ----------------------------
DROP TABLE IF EXISTS `promotion_picture`;
CREATE TABLE `promotion_picture`
(
    `id`          varchar(64)  NOT NULL,
    `picture_url` varchar(512) NOT NULL,
    `news_url`    varchar(512),
    `status`      tinyint(3)   NOT NULL DEFAULT '0' COMMENT '状态：0，正常；1，弃用',
    `create_time` timestamp    not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='推广图片表';

-- ----------------------------
-- 实验室联盟信息表
-- ----------------------------
DROP TABLE IF EXISTS `lab`;
CREATE TABLE `lab`
(
    `lab_id`      varchar(64) NOT NULL,
    `lab_name`    varchar(16) NOT NULL COMMENT '实验室名称',
    `status`      tinyint(3)           DEFAULT '0' COMMENT '实验室状态状态：0、正常 1、禁用',
    `create_time` timestamp   not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`lab_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '实验室联盟信息表';

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
    `name`        varchar(64)  COMMENT '作者姓名',
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
-- 动态成就表
-- ----------------------------
DROP TABLE IF EXISTS `achievement_dynamic`;
CREATE TABLE `achievement_dynamic`
(
    `achievement_id` varchar(64)   NOT NULL,
    `title`          varchar(64)   NOT NULL COMMENT '成就标题',
    `desc`           varchar(128)  NOT NULL COMMENT '成就描述',
    `type`           tinyint(3)    NOT NULL COMMENT '成就种类',
    `condition`      tinyint(3)    NOT NULL COMMENT '达成成就的条件',
    `score`          tinyint(8)    NOT NULL COMMENT '达成成就的分数',
    `medal`          varchar(1024) NOT NULL COMMENT '成就勋章',
    `status`         tinyint(3)    NOT NULL DEFAULT '0' COMMENT '成就状态：0、正常 1、弃用',
    `create_time`    timestamp     not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`achievement_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='动态成就表';

-- ----------------------------
-- 用户动态成就表
-- ----------------------------
DROP TABLE IF EXISTS `user_achievement_dynamic`;
CREATE TABLE `user_achievement_dynamic`
(
    `id`             varchar(64) NOT NULL,
    `user_id`        varchar(64) NOT NULL,
    `achievement_id` varchar(64) NOT NULL,
    `status`         tinyint(3)  NOT NULL DEFAULT '0' COMMENT '状态：0，正常；1，弃用',
    `create_time`    timestamp   not null default current_timestamp comment '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户动态成就表';

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
