CREATE TABLE htdxjk_user1
(
    id bigint NOT NULL COMMENT '主键ID',
    name VARCHAR(30) COLLATE utf8_unicode_ci COMMENT '姓名',
    age INT COMMENT '年龄',
    email VARCHAR(50) COLLATE utf8_unicode_ci COMMENT '邮箱',
    PRIMARY KEY (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (1, '曹操', 18, 'test1@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (2, '曹昂', 20, 'test2@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (3, '曹真', 28, 'test3@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (4, '曹爽', 21, 'test4@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (5, '司马懿', 24, 'test5@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (6, '曹丕', 18, 'test1@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (7, '曹植', 20, 'test2@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (8, '杨修', 28, 'test3@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (9, '荀彧', 21, 'test4@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (10, '许攸', 24, 'test5@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (11, '邓艾', 18, 'test1@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (12, '司马昭', 20, 'test2@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (13, '曹睿', 28, 'test3@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (14, '甄宓', 21, 'test4@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (15, '张辽', 24, 'test5@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (16, '郭嘉', 18, 'test1@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (17, '荀攸', 20, 'test2@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (18, '程昱', 28, 'test3@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (19, '钟会', 21, 'test4@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (20, '典韦', 24, 'test5@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (21, '许诸', 24, 'test5@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (22, '徐晃', 24, 'test5@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (23, '夏侯渊', 24, 'test5@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (24, '曹仁', 24, 'test5@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (25, '于禁', 24, 'test5@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (26, '乐进', 24, 'test5@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (27, '徐庶', 24, 'test5@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (28, '郭照', 24, 'test5@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (29, '张春华', 24, 'test5@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (30, '柏灵筠', 24, 'test5@baomidou.com');
INSERT INTO htdxjk_user1 (id, name, age, email) VALUES (31, '夏侯惇', 24, 'test5@baomidou.com');

CREATE TABLE htdxjk_user2
(
    iid bigint NOT NULL COMMENT '主键ID',
    name VARCHAR(30) COLLATE utf8_unicode_ci COMMENT '姓名',
    age INT COMMENT '年龄',
    email VARCHAR(50) COLLATE utf8_unicode_ci COMMENT '邮箱',
    PRIMARY KEY (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE htdxjk_user3
(
    id bigint NOT NULL COMMENT '主键ID',
    name VARCHAR(30) COLLATE utf8_unicode_ci COMMENT '姓名',
    age INT COMMENT '年龄',
    email VARCHAR(50) COLLATE utf8_unicode_ci COMMENT '邮箱',
    PRIMARY KEY (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;


