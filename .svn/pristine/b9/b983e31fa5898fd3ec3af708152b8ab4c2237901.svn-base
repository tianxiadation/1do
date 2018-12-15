/*
Navicat MySQL Data Transfer

Source Server         : 下城前置库
Source Server Version : 50617
Source Host           : 172.16.10.105:3306
Source Database       : do

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2018-06-21 09:39:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_1do_attr
-- ----------------------------
DROP TABLE IF EXISTS `t_1do_attr`;
CREATE TABLE `t_1do_attr` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `SHOW_ID` varchar(50) NOT NULL COMMENT '显示ID',
  `UPLOAD_USER` varchar(50) NOT NULL COMMENT '上传人员账号',
  `UPLOAD_TIME` datetime DEFAULT NULL COMMENT '上传时间',
  `ATTR_NAME` text COMMENT '文件名称',
  `ATTR_PATH` text COMMENT '文件云存储路径',
  `ATTR_ORDER` int(11) DEFAULT NULL COMMENT '文件顺序',
  `IS_FB` int(11) DEFAULT NULL COMMENT '是否反馈',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=616 DEFAULT CHARSET=utf8 COMMENT='1do附件表';

-- ----------------------------
-- Table structure for t_1do_base
-- ----------------------------
DROP TABLE IF EXISTS `t_1do_base`;
CREATE TABLE `t_1do_base` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `SHOW_ID` varchar(50) NOT NULL COMMENT '显示ID',
  `PARENT_ID` varchar(50) DEFAULT NULL COMMENT '父级工单显示ID(父级1do显示ID)',
  `O_TITLE` varchar(255) NOT NULL COMMENT '标题',
  `O_DESCRIBE` text NOT NULL COMMENT '问题描述(内容)',
  `O_CUSTOMER` text COMMENT '客户(发起人账号)',
  `O_CUSTOMER_NAME` text COMMENT '客户姓名(发起人姓名)',
  `O_START_TIME` datetime DEFAULT NULL COMMENT '开始时间(发起时间)',
  `CREATE_USER` varchar(50) NOT NULL COMMENT '创建人(创建人账号)',
  `CREATE_USER_NAME` varchar(50) NOT NULL COMMENT '创建人姓名',
  `O_CREATE_TIME` datetime DEFAULT NULL COMMENT '开始时间（创建时间）',
  `O_FINISH_TIME` datetime DEFAULT NULL COMMENT '结束时间（完成时间）',
  `O_RANGE` text COMMENT '阅读范围(抄送人)',
  `O_RANGE_NAME` text COMMENT '阅读范围人姓名（抄送人人姓名）',
  `LASTUPDATE_TIME` datetime DEFAULT NULL COMMENT '最后更新时间',
  `O_IS_DELETED` int(11) NOT NULL COMMENT '删除标识',
  `D_SHOW_ID` varchar(50) NOT NULL COMMENT '部门编号',
  `O_TYPE_ID` varchar(50) DEFAULT NULL COMMENT '工单分类（1do分类）',
  `message_id` text COMMENT '来源消息id',
  `groupid` text COMMENT '来源群id',
  `O_EXECUTOR` text COMMENT '受理人',
  `O_EXECUTOR_NAME` text COMMENT '受理人姓名',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=616 DEFAULT CHARSET=utf8 COMMENT='1do主表';

-- ----------------------------
-- Table structure for t_1do_eventtype
-- ----------------------------
DROP TABLE IF EXISTS `t_1do_eventtype`;
CREATE TABLE `t_1do_eventtype` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `EVENT_TYPE` int(11) DEFAULT NULL COMMENT '通知类型',
  `EVENT_TYPE_NAME` varchar(50) DEFAULT NULL COMMENT '通知类型名称',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=616 DEFAULT CHARSET=utf8 COMMENT='1do事件类型表';

-- ----------------------------
-- Table structure for t_1do_fbtype
-- ----------------------------
DROP TABLE IF EXISTS `t_1do_fbtype`;
CREATE TABLE `t_1do_fbtype` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `O_TYPE_ID` varchar(50) DEFAULT NULL COMMENT '1do反馈分类',
  `O_TYPE_NAME` varchar(50) DEFAULT NULL COMMENT '1do反馈分类名称',
  `O_PARENT_ID` varchar(50) DEFAULT NULL COMMENT '上级1do反馈分类ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=616 DEFAULT CHARSET=utf8 COMMENT='1do反馈分类表';

-- ----------------------------
-- Table structure for t_1do_feedback
-- ----------------------------
DROP TABLE IF EXISTS `t_1do_feedback`;
CREATE TABLE `t_1do_feedback` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `SHOW_ID` varchar(50) NOT NULL COMMENT '显示ID',
  `O_USER` varchar(50) NOT NULL COMMENT '相关人员账号',
  `FB_TIME` datetime DEFAULT NULL COMMENT '反馈时间',
  `FBCONTENT` text COMMENT '反馈内容',
  `FB_TYPE` text COMMENT '-反馈类型标签1，正常反馈；2，回复反馈；3，催办反馈，4，办结反馈；5，评价反馈，11，附件反馈',
  `ATTRID` text COMMENT '附件id组',
  `FB_USER` text COMMENT '对应用户账号组',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=616 DEFAULT CHARSET=utf8 COMMENT='1do反馈表';

-- ----------------------------
-- Table structure for t_1do_fw
-- ----------------------------
DROP TABLE IF EXISTS `t_1do_fw`;
CREATE TABLE `t_1do_fw` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `O_USER` varchar(50) NOT NULL COMMENT '整理人员账号',
  `D_SHOW_ID` varchar(50) DEFAULT NULL COMMENT '管理部门编号',
  `O_TYPE_ID` varchar(50) DEFAULT NULL COMMENT '1do分类',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=616 DEFAULT CHARSET=utf8 COMMENT='1do整理层管理范围';

-- ----------------------------
-- Table structure for t_1do_log
-- ----------------------------
DROP TABLE IF EXISTS `t_1do_log`;
CREATE TABLE `t_1do_log` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `SHOW_ID` varchar(50) NOT NULL COMMENT '显示ID',
  `O_USER` varchar(50) NOT NULL COMMENT '显示ID（相关人员账号）',
  `op_time` datetime DEFAULT NULL COMMENT '操作时间',
  `log` text COMMENT '操作日志',
  `log_type` int(11) DEFAULT NULL COMMENT '操作类型',
  `oprator` varchar(50) DEFAULT NULL COMMENT '实际操作人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=616 DEFAULT CHARSET=utf8 COMMENT='1do操作日志审计表';

-- ----------------------------
-- Table structure for t_1do_pset
-- ----------------------------
DROP TABLE IF EXISTS `t_1do_pset`;
CREATE TABLE `t_1do_pset` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `SHOW_ID` varchar(50) NOT NULL COMMENT '显示ID',
  `O_USER` varchar(50) NOT NULL COMMENT '相关人员账号',
  `EVENT_TYPE` text COMMENT '订阅事件',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=616 DEFAULT CHARSET=utf8 COMMENT='1do相关人员设置表';

-- ----------------------------
-- Table structure for t_1do_pstatus
-- ----------------------------
DROP TABLE IF EXISTS `t_1do_pstatus`;
CREATE TABLE `t_1do_pstatus` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `SHOW_ID` varchar(50) NOT NULL COMMENT '显示ID',
  `O_USER` varchar(50) NOT NULL COMMENT '显示ID（相关人员账号）',
  `O_STATUS` int(11) DEFAULT NULL COMMENT '处理状态',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=616 DEFAULT CHARSET=utf8 COMMENT='1do相关人员状态表';

-- ----------------------------
-- Table structure for t_1do_set
-- ----------------------------
DROP TABLE IF EXISTS `t_1do_set`;
CREATE TABLE `t_1do_set` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `SHOW_ID` varchar(50) NOT NULL COMMENT '显示ID',
  `EVENT_TYPE` text COMMENT '订阅事件',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=616 DEFAULT CHARSET=utf8 COMMENT='1do默认设置表';

-- ----------------------------
-- Table structure for t_1do_status
-- ----------------------------
DROP TABLE IF EXISTS `t_1do_status`;
CREATE TABLE `t_1do_status` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `SHOW_ID` varchar(50) NOT NULL COMMENT '显示ID',
  `O_STATUS` int(11) DEFAULT NULL COMMENT '处理状态',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=616 DEFAULT CHARSET=utf8 COMMENT='1do状态表';

-- ----------------------------
-- Table structure for t_1do_type
-- ----------------------------
DROP TABLE IF EXISTS `t_1do_type`;
CREATE TABLE `t_1do_type` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `O_TYPE_ID` varchar(50) DEFAULT NULL COMMENT '1do分类',
  `O_TYPE_NAME` varchar(50) DEFAULT NULL COMMENT '1do分类名称',
  `O_PARENT_ID` varchar(50) DEFAULT NULL COMMENT '上级分类ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=616 DEFAULT CHARSET=utf8 COMMENT='1do分类表';
