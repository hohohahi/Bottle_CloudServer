DROP TABLE IF EXISTS template;
CREATE TABLE `template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(500) not null DEFAULT '0' COMMENT 'name',
  `barCode` varchar(13) not null DEFAULT '0',
  `price` double not null DEFAULT 0,
  `isMetal` bigint(20) not null DEFAULT 0,
  `weight` bigint(20) not null DEFAULT 0,
  `posNum` bigint(20) not null DEFAULT 0,
  `status` bigint(20) not null DEFAULT 0,
  `description` varchar(500) DEFAULT 'template description',
  `createdDate` TIMESTAMP(14) DEFAULT '2016-09-26 00:00:00',
  `createdBy` bigint(20) DEFAULT '0',
  `modifiedDate` TIMESTAMP(14) DEFAULT '2016-09-26 00:00:00',
  `modifiedBy` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `barCode_idx` (`barCode`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='template';

DROP TABLE IF EXISTS templatePosMap;
CREATE TABLE `templatePosMap` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `templateId` bigint(20) not null DEFAULT 0,
  `posOrder` bigint(20) not null DEFAULT 0,
  `xPos` bigint(20) not null DEFAULT 0,
  `yPos` bigint(20) not null DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `templateId_idx` (`templateId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='templatePosMap';

DROP TABLE IF EXISTS bottle;
CREATE TABLE `bottle` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(500) DEFAULT '0' COMMENT 'name',
  `status` bigint(20) DEFAULT '0',  
  `location` varchar(500) DEFAULT '湖南省长沙市',
  `identifier` varchar(200) DEFAULT '唯一标识符',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_identifier` (`identifier`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='bottle';

DROP TABLE IF EXISTS phoneAndCodeMap;
CREATE TABLE `phoneAndCodeMap` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `phoneNumber` bigint(20) DEFAULT '0' COMMENT 'phone number',
  `code` varchar(500) DEFAULT '0',  
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_phoneNumber` (`phoneNumber`) USING BTREE,
  KEY `idx_phoneNumber_And_Code` (`phoneNumber`,`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='PhoneAndCodeMap';

DROP TABLE IF EXISTS player;
CREATE TABLE `player` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) DEFAULT '0',
  `status` bigint(20) DEFAULT '0',
  `phoneNumber` bigint(20) DEFAULT '0' COMMENT 'phone number',
  `password` varchar(500) DEFAULT '0',
  `amount` double unsigned DEFAULT 0.0,
  `smsCode` varchar(500) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_phoneNumber` (`phoneNumber`) USING BTREE,
  UNIQUE KEY `idx_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='Player';

DROP TABLE IF EXISTS admin;
CREATE TABLE `admin` (
  `adminId` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `name` varchar(50) DEFAULT NULL COMMENT '管理员名称',
  `password` varchar(32) DEFAULT NULL COMMENT '密码 MD5加密',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`adminId`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='管理员';

DROP TABLE IF EXISTS admin_folder;
CREATE TABLE `admin_folder` (
  `adminId` bigint(20) DEFAULT NULL,
  `folderId` bigint(20) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

DROP TABLE IF EXISTS comment;
CREATE TABLE `comment` (
  `commentId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `fatherId` bigint(20) DEFAULT NULL COMMENT '父评论ID',
  `kindId` bigint(20) DEFAULT NULL,
  `kind` varchar(45) DEFAULT NULL COMMENT '文件ID',
  `name` varchar(45) DEFAULT NULL COMMENT '评论者',
  `email` varchar(45) DEFAULT NULL COMMENT '评论者邮件地址',
  `url` varchar(200) DEFAULT NULL COMMENT '评论者网址',
  `phone` bigint(20) DEFAULT NULL,
  `content` text COMMENT '内容',
  `ip` varchar(45) DEFAULT NULL COMMENT 'Ip',
  `status` varchar(20) DEFAULT NULL COMMENT '状态',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`commentId`),
  KEY `idx_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='评论';

DROP TABLE IF EXISTS config;
CREATE TABLE `config` (
  `key` varchar(45) NOT NULL COMMENT 'Key',
  `value` varchar(45) DEFAULT NULL COMMENT '值',
  `description` text COMMENT '描述',
  `createTime` datetime DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='网站配置';

DROP TABLE IF EXISTS folder;
CREATE TABLE `folder` (
  `folderId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '目录ID',
  `fatherId` bigint(20) NOT NULL DEFAULT '0' COMMENT '父亲Id，用于构建目录树',
  `ename` varchar(45) NOT NULL COMMENT '英文名',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '中文名',
  `path` varchar(200) NOT NULL DEFAULT '' COMMENT '路径',
  `content` text,
  `level` tinyint(4) DEFAULT '1' COMMENT '层级',
  `sort` tinyint(4) DEFAULT '0' COMMENT '排序',
  `width` int(11) DEFAULT '0',
  `height` int(11) DEFAULT '0',
  `count` int(11) DEFAULT '0' COMMENT '文件数',
  `status` varchar(20) DEFAULT 'hidden' COMMENT '状态：0 隐藏 1 现实',
  `check` enum('yes','no') DEFAULT 'no',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`folderId`),
  UNIQUE KEY `idx_ename` (`ename`) USING BTREE,
  KEY `idx_status` (`fatherId`,`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='目录';

DROP TABLE IF EXISTS guestbook;
CREATE TABLE `guestbook` (
  `guestbookId` bigint(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `website` varchar(100) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `reply` varchar(2000) DEFAULT NULL,
  `status` enum('display','hidden','init') DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `replyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`guestbookId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

DROP TABLE IF EXISTS headline;
CREATE TABLE `headline` (
  `headlineId` bigint(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `picture` varchar(100) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `sort` tinyint(4) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`headlineId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

DROP TABLE IF EXISTS media;
CREATE TABLE `media` (
  `mediaId` bigint(20) NOT NULL AUTO_INCREMENT,
  `kindId` bigint(20) DEFAULT '0',
  `name` varchar(200) DEFAULT NULL,
  `path` varchar(200) DEFAULT NULL,
  `size` int(11) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `kind` varchar(20) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`mediaId`),
  KEY `idx_kind` (`kind`,`kindId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=ucs2 ROW_FORMAT=COMPACT;

DROP TABLE IF EXISTS user;
CREATE TABLE `user` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `openId` bigint(20) DEFAULT NULL COMMENT '公共用户ID，只有是师说，QQ，微博等其它网站登录时才有。',
  `type` varchar(20) DEFAULT NULL COMMENT '帐号类型：0 本站 1 师说 2 QQ 3 微博',
  `name` varchar(45) DEFAULT NULL COMMENT '用户名',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户';


INSERT INTO `admin` VALUES (1,'shishuocms','6158f875bf826e15923779855b6eef2e','2012-08-08 00:00:00');

INSERT INTO `config` VALUES ('shishuo_headline_image_height','420','首页头图的高（px）','2012-08-08 00:00:00');
INSERT INTO `config` VALUES ('shishuo_headline_image_width','858','首页头图的宽（px）','2012-08-08 00:00:00');
INSERT INTO `config` VALUES ('shishuo_seo_headline','师说CMS是用Java开发的内容管理系统','网站口号','2012-08-08 00:00:00');
INSERT INTO `config` VALUES ('shishuo_seo_title','师说CMS','网站名称','2012-08-08 00:00:00');
INSERT INTO `config` VALUES ('shishuo_static','false','是否启用全站静态化','2012-08-08 00:00:00');
INSERT INTO `config` VALUES ('shishuo_template','blog','模板','2012-08-08 00:00:00');

INSERT INTO `bottle` (name, status, location, identifier) VALUES ('DEMO回收机', 1,'湖南省长沙市岳麓区', '40ec2351-af21-4d1c-9a92-85629f43a0bc');

INSERT INTO `template` (name, status, description, createdDate, createdBy, modifiedDate, modifiedBy) VALUES ('条形码模版', 1,'检测条形码特征', '2016-09-26 00:00:01', 1, '2016-09-26 00:00:01', 1);