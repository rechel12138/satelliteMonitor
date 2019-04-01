/*
 Navicat Premium Data Transfer

 Source Server         : 37数据正式服务器数据库
 Source Server Type    : MySQL
 Source Server Version : 50537
 Source Host           : 182.92.216.245:3336
 Source Schema         : htdxjk

 Target Server Type    : MySQL
 Target Server Version : 50537
 File Encoding         : 65001

 Date: 23/02/2019 19:36:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for htdxjk_balarmfx
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_balarmfx`;
CREATE TABLE `htdxjk_balarmfx`  (
  `balarmfx_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `balarmfx_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfx_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfx_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfx_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfx_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfx_atpsort` int(11) NULL DEFAULT 0,
  `balarmfx_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfx_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfx_alarmid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `balarmfx_satid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键模块主键',
  `balarmfx_begintime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfx_endtime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfx_alarmlevel` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfx_alarmmsg` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfx_responetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfx_username` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfx_response` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfx_alarmvalue` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfx_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfx_remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfx_savetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfx_saveuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfx_plan` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`balarmfx_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_balarmfxyy
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_balarmfxyy`;
CREATE TABLE `htdxjk_balarmfxyy`  (
  `balarmfxyy_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `balarmfxyy_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfxyy_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfxyy_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfxyy_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfxyy_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfxyy_atpsort` int(11) NULL DEFAULT 0,
  `balarmfxyy_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfxyy_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfxyy_alarmid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `balarmfxyy_satid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键模块主键',
  `balarmfxyy_begintime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfxyy_endtime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfxyy_alarmlevel` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfxyy_alarmmsg` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfxyy_responetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfxyy_username` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfxyy_response` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfxyy_alarmvalue` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfxyy_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfxyy_remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfxyy_savetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfxyy_saveuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfxyy_plan` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balarmfxyy_createtime` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`balarmfxyy_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_bckplan
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_bckplan`;
CREATE TABLE `htdxjk_bckplan`  (
  `bckplan_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bckplan_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bckplan_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bckplan_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bckplan_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bckplan_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bckplan_atpsort` int(11) NULL DEFAULT 0,
  `bckplan_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bckplan_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bckplan_satname` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `bckplan_madetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键模块主键',
  `bckplan_start_time` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bckplan_end_time` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bckplan_cnt` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bckplan_devname` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bckplan_is_delete` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bckplan_state` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bckplan_modify_time` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bckplan_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_bcscfyc
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_bcscfyc`;
CREATE TABLE `htdxjk_bcscfyc`  (
  `bcscfyc_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bcscfyc_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bcscfyc_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bcscfyc_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bcscfyc_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bcscfyc_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bcscfyc_atpsort` int(11) NULL DEFAULT 0,
  `bcscfyc_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bcscfyc_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bcscfyc_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `bcscfyc_code` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键模块主键',
  `bcscfyc_satellitecode` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bcscfyc_exceptionname` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bcscfyc_occurredtime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bcscfyc_appearance` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bcscfyc_result` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bcscfyc_owner` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bcscfyc_status` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bcscfyc_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_bdwgl
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_bdwgl`;
CREATE TABLE `htdxjk_bdwgl`  (
  `bdwgl_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bdwgl_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdwgl_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdwgl_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdwgl_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdwgl_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdwgl_atpsort` int(11) NULL DEFAULT 0,
  `bdwgl_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdwgl_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdwgl_xh` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `bdwgl_satname` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键模块主键',
  `bdwgl_zrr` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdwgl_dtr` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdwgl_ckdw` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdwgl_zbdh` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdwgl_czdh` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdwgl_htzcdh` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdwgl_yhdw` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdwgl_yhlxr` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdwgl_lxfs` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdwgl_dgpfax` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdwgl_dgphone` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdwgl_ggfax` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdwgl_ggphone` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bdwgl_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_bdxznzdxtssbj
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_bdxznzdxtssbj`;
CREATE TABLE `htdxjk_bdxznzdxtssbj`  (
  `bdxznzdxtssbj_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bdxznzdxtssbj_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdxznzdxtssbj_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdxznzdxtssbj_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdxznzdxtssbj_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdxznzdxtssbj_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdxznzdxtssbj_atpsort` int(11) NULL DEFAULT 0,
  `bdxznzdxtssbj_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdxznzdxtssbj_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdxznzdxtssbj_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `bdxznzdxtssbj_sat_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键模块主键',
  `bdxznzdxtssbj_satname` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdxznzdxtssbj_satcode` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdxznzdxtssbj_record_time` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdxznzdxtssbj_event_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdxznzdxtssbj_title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdxznzdxtssbj_tmlist` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdxznzdxtssbj_explain` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdxznzdxtssbj_subsystem` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdxznzdxtssbj_component` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdxznzdxtssbj_isolate` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdxznzdxtssbj_begintime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdxznzdxtssbj_endtime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdxznzdxtssbj_alarm_level` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bdxznzdxtssbj_val_level` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bdxznzdxtssbj_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_bfile
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_bfile`;
CREATE TABLE `htdxjk_bfile`  (
  `bfile_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bfile_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bfile_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bfile_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bfile_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bfile_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bfile_atpsort` int(11) NULL DEFAULT 0,
  `bfile_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bfile_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bfile_appinfo_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `bfile_local_dirc` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键模块主键',
  `bfile_appname` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bfile_shown_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bfile_sourc_dirc` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bfile_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_bmonthpre
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_bmonthpre`;
CREATE TABLE `htdxjk_bmonthpre`  (
  `bmonthpre_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bmonthpre_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bmonthpre_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bmonthpre_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bmonthpre_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bmonthpre_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bmonthpre_atpsort` int(11) NULL DEFAULT 0,
  `bmonthpre_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bmonthpre_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bmonthpre_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `bmonthpre_btime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键模块主键',
  `bmonthpre_etime` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bmonthpre_satellite` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bmonthpre_mstype` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bmonthpre_span` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bmonthpre_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_borbit1
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_borbit1`;
CREATE TABLE `htdxjk_borbit1`  (
  `borbit1_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `borbit1_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit1_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit1_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit1_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit1_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit1_atpsort` int(11) NULL DEFAULT 0,
  `borbit1_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit1_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit1_time_stamp` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `borbit1_satname` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键模块主键',
  `borbit1_a` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit1_e` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit1_i` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit1_o` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit1_w` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit1_m` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`borbit1_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_borbit2
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_borbit2`;
CREATE TABLE `htdxjk_borbit2`  (
  `borbit2_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `borbit2_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit2_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit2_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit2_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit2_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit2_atpsort` int(11) NULL DEFAULT 0,
  `borbit2_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit2_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit2_time_stamp` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `borbit2_satname` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键模块主键',
  `borbit2_a` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit2_e` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit2_i` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit2_o` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit2_w` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit2_m` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`borbit2_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_borbit3
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_borbit3`;
CREATE TABLE `htdxjk_borbit3`  (
  `borbit3_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `borbit3_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit3_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit3_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit3_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit3_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit3_atpsort` int(11) NULL DEFAULT 0,
  `borbit3_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit3_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit3_time_stamp` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `borbit3_satname` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键模块主键',
  `borbit3_a` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit3_e` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit3_i` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit3_o` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit3_w` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit3_m` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit3_p_dot1` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit3_p_dot2` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit3_p` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit3_hp` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `borbit3_ha` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`borbit3_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_bpt
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_bpt`;
CREATE TABLE `htdxjk_bpt`  (
  `bpt_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bpt_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bpt_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bpt_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bpt_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bpt_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bpt_atpsort` int(11) NULL DEFAULT 0,
  `bpt_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bpt_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bpt_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `bpt_last_modifed_time` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键模块主键',
  `bpt_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bpt_remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bpt_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_bsat
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_bsat`;
CREATE TABLE `htdxjk_bsat`  (
  `bsat_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bsat_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsat_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsat_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsat_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsat_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsat_atpsort` int(11) NULL DEFAULT 0,
  `bsat_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsat_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsat_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `bsat_last_modifed_time` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键模块主键',
  `bsat_active` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsat_code` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsat_endoflife_time` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsat_full_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsat_launch_time` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsat_lifeyear` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsat_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsat_admin_subs_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsat_admin_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsat_platform_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsat_remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsat_outside_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bsat_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_bsjckgzhd
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_bsjckgzhd`;
CREATE TABLE `htdxjk_bsjckgzhd`  (
  `bsjckgzhd_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bsjckgzhd_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjckgzhd_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjckgzhd_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjckgzhd_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjckgzhd_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjckgzhd_atpsort` int(11) NULL DEFAULT 0,
  `bsjckgzhd_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjckgzhd_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjckgzhd_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `bsjckgzhd_satname` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键模块主键',
  `bsjckgzhd_start_time` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjckgzhd_end_time` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjckgzhd_devname` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bsjckgzhd_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_bsjmxss
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_bsjmxss`;
CREATE TABLE `htdxjk_bsjmxss`  (
  `bsjmxss_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bsjmxss_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxss_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxss_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxss_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxss_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxss_atpsort` int(11) NULL DEFAULT 0,
  `bsjmxss_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxss_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxss_satid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `bsjmxss_begintime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键模块主键',
  `bsjmxss_endtime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxss_alarmlevel` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxss_alarmmsg` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxss_responetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxss_username` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxss_response` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxss_alarmvalue` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxss_tmid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxss_tmcode` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bsjmxss_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_bsjmxys
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_bsjmxys`;
CREATE TABLE `htdxjk_bsjmxys`  (
  `bsjmxys_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bsjmxys_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxys_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxys_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxys_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxys_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxys_atpsort` int(11) NULL DEFAULT 0,
  `bsjmxys_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxys_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxys_satid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxys_begintime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxys_endtime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxys_alarmlevel` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxys_alarmmsg` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxys_responetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxys_username` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxys_response` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxys_alarmvalue` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxys_createtime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bsjmxys_updatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bsjmxys_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_bxhzcdw
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_bxhzcdw`;
CREATE TABLE `htdxjk_bxhzcdw`  (
  `bxhzcdw_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bxhzcdw_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxhzcdw_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxhzcdw_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxhzcdw_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxhzcdw_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxhzcdw_atpsort` int(11) NULL DEFAULT 0,
  `bxhzcdw_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxhzcdw_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxhzcdw_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `bxhzcdw_unit` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键模块主键',
  `bxhzcdw_phone` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxhzcdw_telephone` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxhzcdw_remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxhzcdw_post` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxhzcdw_seq` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxhzcdw_satcode` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxhzcdw_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bxhzcdw_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_bxxrjl
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_bxxrjl`;
CREATE TABLE `htdxjk_bxxrjl`  (
  `bxxrjl_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bxxrjl_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxxrjl_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxxrjl_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxxrjl_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxxrjl_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxxrjl_atpsort` int(11) NULL DEFAULT 0,
  `bxxrjl_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxxrjl_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxxrjl_appinfo_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `bxxrjl_local_dirc` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键模块主键',
  `bxxrjl_app_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxxrjl_shown_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxxrjl_sourc_dirc` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxxrjl_app_oaran` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxxrjl_app_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxxrjl_app_name2` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bxxrjl_app_param2` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bxxrjl_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_bzbbwl
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_bzbbwl`;
CREATE TABLE `htdxjk_bzbbwl`  (
  `bzbbwl_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bzbbwl_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbbwl_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbbwl_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbbwl_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbbwl_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbbwl_atpsort` int(11) NULL DEFAULT 0,
  `bzbbwl_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbbwl_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbbwl_remarkid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `bzbbwl_showdate` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键模块主键',
  `bzbbwl_showtitle` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbbwl_showdetail` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbbwl_addtime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbbwl_addperson` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbbwl_satcode` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbbwl_serialnum` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbbwl_showdays` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbbwl_createbykettle` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbbwl_idinnorbit` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbbwl_state` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bzbbwl_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_bzbjl
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_bzbjl`;
CREATE TABLE `htdxjk_bzbjl`  (
  `bzbjl_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bzbjl_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbjl_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbjl_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbjl_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbjl_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbjl_atpsort` int(11) NULL DEFAULT 0,
  `bzbjl_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbjl_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbjl_infoid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `bzbjl_person1` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbjl_person2` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbjl_addperson` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbjl_addtime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbjl_week` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbjl_dutydate` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbjl_infomodifytime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bzbjl_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_bzbsw
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_bzbsw`;
CREATE TABLE `htdxjk_bzbsw`  (
  `bzbsw_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bzbsw_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_atpsort` int(11) NULL DEFAULT 0,
  `bzbsw_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_detailid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `bzbsw_infoid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键模块主键',
  `bzbsw_satcode` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_infotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_phemdesc` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_resltdesc` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_addtime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_addperson` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_modifytime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_state` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_sendto` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_serialnum` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_shownotifyflag` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_phemtime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_reslttime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_createbykettle` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzbsw_idinnorbit` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bzbsw_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_bzgwxxhfzlxr
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_bzgwxxhfzlxr`;
CREATE TABLE `htdxjk_bzgwxxhfzlxr`  (
  `bzgwxxhfzlxr_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bzgwxxhfzlxr_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgwxxhfzlxr_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgwxxhfzlxr_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgwxxhfzlxr_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgwxxhfzlxr_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgwxxhfzlxr_atpsort` int(11) NULL DEFAULT 0,
  `bzgwxxhfzlxr_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgwxxhfzlxr_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bzgwxxhfzlxr_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_bzgyc
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_bzgyc`;
CREATE TABLE `htdxjk_bzgyc`  (
  `bzgyc_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bzgyc_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_atpsort` int(11) NULL DEFAULT 0,
  `bzgyc_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_data_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_tamplate_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_astellite_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_is_big_event` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_512` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_xml_sata_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_8081` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_8083` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_8787` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_8788` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_8812` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_8813` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_8817` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_8818` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_8820` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_8871` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_is_delted` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_9012` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_9060` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_9064` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_exception_state` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_first_data_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_depariment` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_submit_time` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_9079` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_9117` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_submit_state` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_exception_report` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_9594` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_9997` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_9998` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_9999` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_10000` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_import_time` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_520` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_521` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_522` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_9739` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_device_code` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_email_sended` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_is_planned` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_9011` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_9595` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_9996` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bzgyc_c_8819` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bzgyc_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for htdxjk_sconfig
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_sconfig`;
CREATE TABLE `htdxjk_sconfig`  (
  `sc_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sc_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sc_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sc_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sc_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sc_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sc_atpsort` int(11) NULL DEFAULT 0,
  `sc_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sc_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sc_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类',
  `sc_mode` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模式(字典状态码 字符串、图片、等)',
  `sc_key` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sc_value` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sc_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of htdxjk_sconfig
-- ----------------------------
INSERT INTO `htdxjk_sconfig` VALUES ('1', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `htdxjk_sconfig` VALUES ('2', 'qqq', NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for htdxjk_sdict
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_sdict`;
CREATE TABLE `htdxjk_sdict`  (
  `sd_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sd_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sd_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sd_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sd_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sd_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sd_atpsort` int(11) NULL DEFAULT 0,
  `sd_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sd_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sd_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典类别 字典状态码(系统、自定义，（系统表示不能删除）)',
  `sd_indexvalue` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '引用值',
  `sd_showvalue` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示值',
  `sd_remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sd_desc` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sd_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of htdxjk_sdict
-- ----------------------------
INSERT INTO `htdxjk_sdict` VALUES ('001', '只在', NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for htdxjk_slog
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_slog`;
CREATE TABLE `htdxjk_slog`  (
  `sl_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sl_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sl_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sl_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sl_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sl_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sl_atpsort` int(11) NULL DEFAULT 0,
  `sl_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sl_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sl_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类',
  `sl_logtime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时间',
  `sl_content` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `sl_fksaccountid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键账户主键',
  `sl_ip` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sl_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of htdxjk_slog
-- ----------------------------
INSERT INTO `htdxjk_slog` VALUES ('1be6f4112748a16cd37847da9c55dc2c', 'sss', NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for htdxjk_smodule
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_smodule`;
CREATE TABLE `htdxjk_smodule`  (
  `sm_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sm_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sm_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sm_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sm_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sm_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sm_atpsort` int(11) NULL DEFAULT 0,
  `sm_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sm_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sm_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `sm_remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sm_webpath` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sm_fksmoduleid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sm_icon` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sm_isdisplay` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0-显示，1-不显示',
  `sm_action` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '1：置顶按钮\r\n2:   在轨型号责任联系人详情\r\n3:   在轨技术支持队伍详情\r\n',
  `sm_islogineddisplay` int(1) NULL DEFAULT 0 COMMENT '0-需要登录显示，1-不需要登录显示',
  `sm_iscurrent` int(1) NULL DEFAULT 0 COMMENT ' 是否首次打开，0-不打开，1-打开',
  PRIMARY KEY (`sm_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of htdxjk_smodule
-- ----------------------------
INSERT INTO `htdxjk_smodule` VALUES ('sm001', '2019-02-22 20:53:18', NULL, '2019-02-22 20:53:18', NULL, NULL, 1, NULL, NULL, '报警确认', NULL, '/htdxjk/alarmConfirm/index', '', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm002', '2019-02-22 20:55:05', NULL, '2019-02-22 20:55:05', NULL, NULL, 2, NULL, NULL, '报警分类分析', NULL, '/htdxjk/alarmAnalysis/index', '', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm003', '2019-02-22 20:55:05', NULL, '2019-02-22 20:55:05', NULL, NULL, 3, NULL, NULL, '常用文件', NULL, '/htdxjk/documentList/index', '', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm004', '2019-02-22 20:55:05', NULL, '2019-02-22 20:55:05', NULL, NULL, 4, NULL, NULL, '常用查询软件', NULL, '', '', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm005', '2019-02-22 20:55:05', NULL, '2019-02-22 20:55:05', NULL, NULL, 5, NULL, NULL, '在轨责任人平台', NULL, '/htdxjk/systemUrl/zgzrrpt', 'sm004', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm006', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 6, NULL, NULL, '在轨数据管理与应用系统', NULL, '/htdxjk/systemUrl/zgsjglyyyxt', 'sm004', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm007', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 7, NULL, NULL, '基础数据检索', NULL, '', '', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm008', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 8, NULL, NULL, '测控计划', NULL, '/htdxjk/basicData/ckjh', 'sm007', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm009', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 9, NULL, NULL, '实际跟踪弧段', NULL, '/htdxjk/basicData/sjgzhd', 'sm007', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm010', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 10, NULL, NULL, '轨道根数', NULL, '/htdxjk/basicData/gdgs', 'sm007', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm011', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 11, NULL, NULL, '航天器基本信息', NULL, '/htdxjk/basicData/htqjdxx', 'sm007', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm012', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 12, NULL, NULL, '显示软件', NULL, '', '', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm013', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 13, NULL, NULL, '所有卫星', NULL, '/htdxjk/runSoft/sywx', 'sm012', NULL, '0', '1，2，3', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm014', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 14, NULL, NULL, '传输型遥感卫星', NULL, '/htdxjk/runSoft/csxygwx', 'sm012', NULL, '0', '1，2，3', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm015', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 15, NULL, NULL, '通信卫星', NULL, '/htdxjk/runSoft/txwx', 'sm012', NULL, '0', '1，2，3', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm016', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 16, NULL, NULL, '导航卫星', NULL, '/htdxjk/runSoft/dhwx', 'sm012', NULL, '0', '1，2，3', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm017', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 17, NULL, NULL, '小卫星', NULL, '/htdxjk/runSoft/xwx', 'sm012', NULL, '0', '1，2，3', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm018', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 18, NULL, NULL, '文件共享区', NULL, '/htdxjk/fileShare/index', '', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm019', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 19, NULL, NULL, '工作空间管理', NULL, '', '', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm020', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 20, NULL, NULL, '页面管理', NULL, '/htdxjk/workSpace/ymgl', 'sm019', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm021', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 21, NULL, NULL, '个人网盘', NULL, '/htdxjk/workSpace/grwp', 'sm019', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm022', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 22, NULL, NULL, '型号分类管理', NULL, '/htdxjk/workSpace/xhflgl', 'sm019', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm023', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 23, NULL, NULL, '显示软件管理', NULL, '', '', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm024', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 24, NULL, NULL, '分类管理', NULL, '/htdxjk/xsrjgl/category', 'sm023', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm025', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 25, NULL, NULL, '显示软件', NULL, '/htdxjk/xsrjgl/soft', 'sm023', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm026', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 26, NULL, NULL, '基础数据管理', NULL, '', '', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm027', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 27, NULL, NULL, '分类管理', NULL, '/htdxjk/basicData/category', 'sm026', NULL, '0', '', 1, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm028', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 28, NULL, NULL, '基础数据', NULL, '/htdxjk/basicData/data', 'sm026', NULL, '0', '', 1, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm029', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 29, NULL, NULL, '通知管理', NULL, '/htdxjk/notice/index', '', NULL, '0', '', 0, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm030', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 30, NULL, NULL, '系统配置', NULL, '', '', NULL, '0', '', 0, 1);
INSERT INTO `htdxjk_smodule` VALUES ('sm031', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 31, NULL, NULL, '皮肤管理', NULL, '/htdxjk/system/skin', 'sm030', NULL, '0', '', 1, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm032', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 32, NULL, NULL, '菜单编辑', NULL, '/htdxjk/system/module', 'sm030', NULL, '0', '', 1, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm033', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 33, NULL, NULL, '角色管理', NULL, '/htdxjk/system/role', 'sm030', NULL, '0', '', 1, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm034', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 34, NULL, NULL, '用户管理', NULL, '/htdxjk/system/user', 'sm030', NULL, '0', '', 1, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm035', '2019-02-22 20:55:06', NULL, '2019-02-22 20:55:06', NULL, NULL, 35, NULL, NULL, '操作日志管理', NULL, '/htdxjk/system/operationLog', 'sm030', NULL, '0', '', 1, 0);
INSERT INTO `htdxjk_smodule` VALUES ('sm036', '2019-02-22 20:55:07', NULL, '2019-02-22 20:55:07', NULL, NULL, 36, NULL, NULL, '修改密码', NULL, '/htdxjk/system/changePwd', 'sm030', NULL, '0', '', 1, 1);

-- ----------------------------
-- Table structure for htdxjk_srole
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_srole`;
CREATE TABLE `htdxjk_srole`  (
  `sr_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sr_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sr_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sr_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sr_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sr_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sr_atpsort` int(11) NULL DEFAULT 0,
  `sr_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sr_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sr_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账户编号系统自动生成',
  `sr_remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sr_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of htdxjk_srole
-- ----------------------------
INSERT INTO `htdxjk_srole` VALUES ('sr001', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, '管理员', NULL);

-- ----------------------------
-- Table structure for htdxjk_srolemodule
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_srolemodule`;
CREATE TABLE `htdxjk_srolemodule`  (
  `srm_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `srm_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `srm_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `srm_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `srm_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `srm_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `srm_atpsort` int(11) NULL DEFAULT 0,
  `srm_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `srm_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `srm_fksroleid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  `srm_fksmoduleid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键模块主键',
  PRIMARY KEY (`srm_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of htdxjk_srolemodule
-- ----------------------------
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_001', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm001');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_002', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm002');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_003', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm003');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_004', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm004');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_005', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm005');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_006', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm006');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_007', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm007');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_008', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm008');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_009', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm009');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_010', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm010');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_011', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm011');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_012', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm012');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_013', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm013');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_014', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm014');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_015', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm015');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_016', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm016');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_017', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm017');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_018', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm018');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_019', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm019');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_020', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm020');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_021', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm021');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_022', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm022');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_023', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm023');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_024', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm024');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_025', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm025');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_026', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm026');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_027', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm027');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_028', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm028');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_029', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm029');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_030', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm030');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_031', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm031');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_032', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm032');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_033', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm033');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_034', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm034');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_035', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm035');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_036', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm036');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_test001', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm_test001');
INSERT INTO `htdxjk_srolemodule` VALUES ('srm_test002', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'sr001', 'sm_test002');

-- ----------------------------
-- Table structure for htdxjk_suser
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_suser`;
CREATE TABLE `htdxjk_suser`  (
  `su_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `su_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `su_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `su_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `su_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `su_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `su_atpsort` int(11) NULL DEFAULT 0,
  `su_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `su_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `su_bizcode` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账户编号系统自动生成',
  `su_empcode` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '员工编号',
  `su_chinesename` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `su_englishname` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `su_nickname` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `su_gender` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别（男、女、未知）',
  `su_remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `su_account` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `su_password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `su_lastlogindatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `su_telephonenum` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `su_workphonenum` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `su_userjobroleid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `su_mangeplatformgroupsates` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`su_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of htdxjk_suser
-- ----------------------------
INSERT INTO `htdxjk_suser` VALUES ('111', NULL, NULL, NULL, NULL, NULL, 0, '111', '111', '222', '3333', '魏浩志', NULL, NULL, NULL, NULL, 'admin', '111111', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `htdxjk_suser` VALUES ('su001', 'eeeeeee', NULL, NULL, NULL, NULL, 0, NULL, NULL, '001', '001', '管理员', 'admin', 'admin', '男', NULL, 'admin', '123456', '2019-2-21 20:15:01', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for htdxjk_suserrole
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_suserrole`;
CREATE TABLE `htdxjk_suserrole`  (
  `sur_atpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sur_atpcreatedatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sur_atpcreateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sur_atplastmodifydatetime` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sur_atplastmodifyuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sur_atpstatus` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sur_atpsort` int(11) NULL DEFAULT 0,
  `sur_atpdotype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sur_atpremark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sur_fksuserid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键账户主键',
  `sur_fksroleid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键角色主键',
  PRIMARY KEY (`sur_atpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of htdxjk_suserrole
-- ----------------------------
INSERT INTO `htdxjk_suserrole` VALUES ('sur001', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 'su001', 'sr001');

-- ----------------------------
-- Table structure for htdxjk_user1
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_user1`;
CREATE TABLE `htdxjk_user1`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of htdxjk_user1
-- ----------------------------
INSERT INTO `htdxjk_user1` VALUES (1, '曹操', 18, 'test1@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (2, '曹昂', 20, 'test2@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (3, '曹真', 28, 'test3@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (4, '曹爽', 21, 'test4@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (5, '司马懿', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (6, '曹丕', 18, 'test1@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (7, '曹植', 20, 'test2@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (8, '杨修', 28, 'test3@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (9, '荀彧', 21, 'test4@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (10, '许攸', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (11, '邓艾', 18, 'test1@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (12, '司马昭', 20, 'test2@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (13, '曹睿', 28, 'test3@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (14, '甄宓', 21, 'test4@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (15, '张辽', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (16, '郭嘉', 18, 'test1@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (17, '荀攸', 20, 'test2@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (18, '程昱', 28, 'test3@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (19, '钟会', 21, 'test4@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (20, '典韦', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (21, '许诸', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (22, '徐晃', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (23, '夏侯渊', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (24, '曹仁', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (25, '于禁', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (26, '乐进', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (27, '徐庶', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (28, '郭照', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (29, '张春华', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (30, '柏灵筠', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user1` VALUES (31, '夏侯惇', 24, 'test5@baomidou.com');

-- ----------------------------
-- Table structure for htdxjk_user2
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_user2`;
CREATE TABLE `htdxjk_user2`  (
  `iid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`iid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 125 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of htdxjk_user2
-- ----------------------------
INSERT INTO `htdxjk_user2` VALUES (1, '曹操', 18, 'test1@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (2, '曹昂', 20, 'test2@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (3, '曹真', 28, 'test3@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (4, '曹爽', 21, 'test4@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (5, '司马懿', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (6, '曹丕', 18, 'test1@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (7, '曹植', 20, 'test2@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (8, '杨修', 28, 'test3@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (9, '荀彧', 21, 'test4@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (10, '许攸', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (11, '邓艾', 18, 'test1@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (12, '司马昭', 20, 'test2@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (13, '曹睿', 28, 'test3@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (14, '甄宓', 21, 'test4@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (15, '张辽', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (16, '郭嘉', 18, 'test1@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (17, '荀攸', 20, 'test2@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (18, '程昱', 28, 'test3@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (19, '钟会', 21, 'test4@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (20, '典韦', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (21, '许诸', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (22, '徐晃', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (23, '夏侯渊', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (24, '曹仁', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (25, '于禁', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (26, '乐进', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (27, '徐庶', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (28, '郭照', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (29, '张春华', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (30, '柏灵筠', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (31, '夏侯惇', 24, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (94, '曹操', NULL, 'test1@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (95, '曹昂', NULL, 'test2@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (96, '曹真', NULL, 'test3@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (97, '曹爽', NULL, 'test4@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (98, '司马懿', NULL, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (99, '曹丕', NULL, 'test1@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (100, '曹植', NULL, 'test2@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (101, '杨修', NULL, 'test3@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (102, '荀彧', NULL, 'test4@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (103, '许攸', NULL, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (104, '邓艾', NULL, 'test1@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (105, '司马昭', NULL, 'test2@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (106, '曹睿', NULL, 'test3@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (107, '甄宓', NULL, 'test4@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (108, '张辽', NULL, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (109, '郭嘉', NULL, 'test1@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (110, '荀攸', NULL, 'test2@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (111, '程昱', NULL, 'test3@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (112, '钟会', NULL, 'test4@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (113, '典韦', NULL, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (114, '许诸', NULL, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (115, '徐晃', NULL, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (116, '夏侯渊', NULL, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (117, '曹仁', NULL, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (118, '于禁', NULL, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (119, '乐进', NULL, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (120, '徐庶', NULL, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (121, '郭照', NULL, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (122, '张春华', NULL, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (123, '柏灵筠', NULL, 'test5@baomidou.com');
INSERT INTO `htdxjk_user2` VALUES (124, '夏侯惇', NULL, 'test5@baomidou.com');

-- ----------------------------
-- Table structure for htdxjk_user3
-- ----------------------------
DROP TABLE IF EXISTS `htdxjk_user3`;
CREATE TABLE `htdxjk_user3`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
