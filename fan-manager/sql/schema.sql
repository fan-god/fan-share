-- 创建数据库脚本
create database seckill;
-- 使用数据库
 use seckill;
-- 创建秒杀库存表
CREATE TABLE seckill(
`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name`  VARCHAR(120) NOT  NULL COMMENT '商品名称',
`number` INT NOT NULL COMMENT '商品数量',
`start_time` TIMESTAMP NOT NULL COMMENT '秒杀开始时间',
`end_time` TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
`create_time` TIMESTAMP NOT NULL COMMENT '创建时间',
PRIMARY KEY(id),
KEY idx_start_time(start_time),
KEY idx_end_time(end_time),
KEY idx_crate_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- 用户登录认证相关信息，秒杀成功明细表
CREATE TABLE success_killed(
id BIGINT NOT NULL COMMENT '秒杀商品id',
user_phone BIGINT NOT NULL COMMENT '用户手机号',
state TINYINT NOT NULL default -1 COMMENT '状态，-1：无效，0：成功，1：已付款,2:已发货',
create_time TIMESTAMP NOT NULL COMMENT '创建时间',
PRIMARY KEY(id,user_phone),
KEY idx_create_time(create_time)
)ENGINE=INNODB default CHARSET=utf8 COMMENT='秒杀成功 明细表' 




