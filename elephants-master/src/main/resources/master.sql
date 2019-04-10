
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
drop database if exists elephants;
create database elephants CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;


use elephants;

-- 机器分组管理
DROP TABLE IF EXISTS `host_group`;
CREATE TABLE `host_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL COMMENT '组描述',
  `effective` tinyint(2) DEFAULT '0' COMMENT '是否有效(1，有效，0，无效)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='机器组记录表';

-- 机器分组 对应的 机器
DROP TABLE IF EXISTS `work_host`;
CREATE TABLE `work_host` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `host` varchar(32) DEFAULT NULL COMMENT '机器ip',
  `host_group_id` int(11) DEFAULT NULL COMMENT '机器所在组id',
  `domain` varchar(16) DEFAULT '' COMMENT '机器域名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='机器与机器组关联表';


-- 任务信息
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `task_type` varchar(64) DEFAULT NULL COMMENT '任务类型',
  `state` tinyint(4) DEFAULT NULL COMMENT '任务实例状态：0 提交成功,1 正在运行,2 准备暂停,3 暂停,4 准备停止,5 停止,6 失败,7 成功,8 需要容错,9 kill,10 等待线程,11 等待依赖完成',
  `start_time` datetime DEFAULT NULL COMMENT '任务开始时间',
  `host` varchar(45) DEFAULT NULL COMMENT '执行任务的机器',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;















