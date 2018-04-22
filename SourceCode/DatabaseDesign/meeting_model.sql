/*
 Navicat Premium Data Transfer

 Source Server         : localhost_210
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:210
 Source Schema         : meeting_model

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 20/04/2018 12:29:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for meeting
-- ----------------------------
DROP TABLE IF EXISTS `meeting`;
CREATE TABLE `meeting`  (
  `m_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gContent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目类型',
  `vContent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目数据',
  `e_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结束时间',
  `s_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实际开始时间',
  `msign_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会议标识符',
  `m_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会议主题',
  `c_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `b_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开始时间',
  `m_place` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会议地点',
  `m_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会议内容',
  `mCreator_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者id',
  `mLabel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会议标签',
  PRIMARY KEY (`m_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of meeting
-- ----------------------------
INSERT INTO `meeting` VALUES (1, '5555', '555', '44', '777', NULL, '11', '22', '233', '3444', '555', '666', '专业性会议');
INSERT INTO `meeting` VALUES (2, NULL, NULL, NULL, '777', NULL, '11', '22', '233', '3444', '555', '666', '专业性会议');
INSERT INTO `meeting` VALUES (3, NULL, NULL, NULL, NULL, NULL, 'eduction', '2018-04-07 10:34:45', '2018-4-9', 'nanjing', 'gsbxgjdncbd', '4', '专业性会议');
INSERT INTO `meeting` VALUES (4, NULL, NULL, NULL, NULL, 'sdg5', '人工智能', '2018-04-07 13:06:30', '2018-4-8', 'beijing', '..........dhdbxbsjsbxggw', '554', '专业性会议');
INSERT INTO `meeting` VALUES (5, NULL, NULL, NULL, NULL, 'rr3', '人工智能', '2018-04-07 17:57:59', '2018-4-8', 'beijing', '........dhdbxbsjsbxggw', '554', '专业性会议');
INSERT INTO `meeting` VALUES (6, NULL, NULL, NULL, NULL, 'gg3', '互联网', '2018-04-07 17:59:49', '2018-4-8', 'beijing', '........dhdbxbsjsbxggw', '554', '专业性会议');
INSERT INTO `meeting` VALUES (7, NULL, NULL, NULL, NULL, NULL, 'it', '2018-04-07 18:01:24', '2018-4-8', 'beijing', '........dhdbxbsjsbxggw', '554', '专业性会议');
INSERT INTO `meeting` VALUES (8, 'sssss', 'nsjdbysd', '2018-04-17 21:50:36', '2018-04-17 21:51:51', '334nhhsji', 'my', '2018-04-07 18:19:03', '2018-4-8', 'beijing', '........dhdbxbsjsbxggw', '8', '');
INSERT INTO `meeting` VALUES (9, NULL, NULL, NULL, NULL, NULL, 'msy', '2018-04-07 18:45:27', '2018-4-8', 'beijing', '........dhdbxbsjsbxggw', '554', NULL);
INSERT INTO `meeting` VALUES (10, NULL, NULL, NULL, NULL, NULL, 'ww', 'ggd', 'mj', 'mshs', '234', 'dbcgn', '商务性会议');
INSERT INTO `meeting` VALUES (11, NULL, NULL, NULL, NULL, '351923e9-64f5-4256-96e3-7f0c986c128c', 'ww', 'ggd', 'mj', 'mshs', '234', 'dbcgn', '商务性会议');
INSERT INTO `meeting` VALUES (12, NULL, NULL, NULL, NULL, NULL, 'ww', 'ggd', 'mj', 'mshs', '234', 'dbcgn', '商务性会议');
INSERT INTO `meeting` VALUES (13, NULL, NULL, NULL, NULL, NULL, 'ww', 'ggd', 'mj', 'mshs', '234', 'dbcgn', '商务性会议');
INSERT INTO `meeting` VALUES (14, NULL, NULL, NULL, NULL, '243eebec-2962-4819-83ae-f1689e9e1ef7', 'ww', 'ggd', 'mj', 'mshs', '234', 'dbcgn', '商务性会议');
INSERT INTO `meeting` VALUES (15, NULL, NULL, NULL, NULL, NULL, 'ww', 'ggd', 'mj', 'mshs', '234', 'dbcgn', '商务性会议');
INSERT INTO `meeting` VALUES (16, NULL, NULL, NULL, NULL, '5302c9a7-cb6f-4d20-9db8-55ab83e2a578', 'confident', '2018-04-16 16:45:42', '2018-3-6', 'guangzhou', 'find it', '7', NULL);
INSERT INTO `meeting` VALUES (17, NULL, NULL, NULL, NULL, '165a27fa-3c0d-4db0-8bd6-70d048acb018', 'confident', '2018-04-16 16:49:58', '2018-3-7', 'guangzhou', 'find it', '7', NULL);
INSERT INTO `meeting` VALUES (18, NULL, NULL, NULL, NULL, '6e771a48-1fde-4a9c-98ed-ed373d8594ec', 'sss', '2018-04-17 16:11:03', '1234', 'ssss', 'ggss', '123', '联谊性会议');
INSERT INTO `meeting` VALUES (19, NULL, NULL, NULL, NULL, '165eafac-c864-44ea-a90b-23d69a9a460c', 'sss', '2018-04-17 16:12:28', '1234', 'ssss', 'ggss', '123', '联谊性会议');
INSERT INTO `meeting` VALUES (20, NULL, NULL, NULL, NULL, '9ebcb2c0-b42b-48a4-a688-f9e27618c00d', 'ss地点', '2018-04-17 16:17:17', '1234', 'ssss', 'ggss', '123', '联谊性会议');
INSERT INTO `meeting` VALUES (21, NULL, NULL, NULL, NULL, '2f0e1a42-e41f-4ca4-907c-670b0f3aaf21', 'ss地点', '2018-04-17 16:50:44', '1234', 'ssss', 'ggss', '123', '联谊性会议');
INSERT INTO `meeting` VALUES (22, NULL, NULL, NULL, NULL, '7ec316d3-4515-49cd-ad90-2ca5c0fa603e', 'ss地点', '2018-04-17 16:52:21', '1234', 'ssss', 'ggss', '123', '联谊性会议');
INSERT INTO `meeting` VALUES (23, NULL, NULL, NULL, NULL, '700f8080-173c-4ee6-ba16-4c2f698affc7', 'ss地点', '2018-04-17 17:17:51', '1234', 'ssss', 'ggss', '123', NULL);

-- ----------------------------
-- Table structure for meeting_user
-- ----------------------------
DROP TABLE IF EXISTS `meeting_user`;
CREATE TABLE `meeting_user`  (
  `m_id` int(11) NOT NULL COMMENT '会议id',
  `cu_id` int(11) NOT NULL COMMENT '用户id',
  `sign` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '签到标记',
  `admin` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '管理员标记',
  `vote` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '投票结果记录',
  `grade` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评分结果记录',
  PRIMARY KEY (`m_id`, `cu_id`) USING BTREE,
  INDEX `cu_id`(`cu_id`) USING BTREE,
  CONSTRAINT `meeting_user_ibfk_1` FOREIGN KEY (`m_id`) REFERENCES `meeting` (`m_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of meeting_user
-- ----------------------------
INSERT INTO `meeting_user` VALUES (3, 4, 1, NULL, NULL, NULL);
INSERT INTO `meeting_user` VALUES (3, 5, 1, 5, NULL, NULL);
INSERT INTO `meeting_user` VALUES (3, 6, NULL, NULL, NULL, NULL);
INSERT INTO `meeting_user` VALUES (3, 7, NULL, NULL, NULL, NULL);
INSERT INTO `meeting_user` VALUES (6, 4, 1, 4, NULL, NULL);
INSERT INTO `meeting_user` VALUES (6, 5, NULL, NULL, NULL, NULL);
INSERT INTO `meeting_user` VALUES (8, 2, 1, 2, 'A', NULL);
INSERT INTO `meeting_user` VALUES (8, 4, 1, 4, 'A', '4*');
INSERT INTO `meeting_user` VALUES (8, 6, 1, 6, 'A', '4*');
INSERT INTO `meeting_user` VALUES (8, 8, 1, 8, '4*', '8*');
INSERT INTO `meeting_user` VALUES (8, 9, NULL, NULL, NULL, '8*');
INSERT INTO `meeting_user` VALUES (8, 50, NULL, NULL, NULL, NULL);
INSERT INTO `meeting_user` VALUES (14, 2, NULL, NULL, NULL, NULL);
INSERT INTO `meeting_user` VALUES (16, 7, NULL, 7, NULL, NULL);
INSERT INTO `meeting_user` VALUES (17, 7, NULL, 7, NULL, NULL);
INSERT INTO `meeting_user` VALUES (18, 123, NULL, 123, NULL, NULL);
INSERT INTO `meeting_user` VALUES (19, 123, NULL, 123, NULL, NULL);
INSERT INTO `meeting_user` VALUES (20, 123, NULL, 123, NULL, NULL);
INSERT INTO `meeting_user` VALUES (21, 123, NULL, 123, NULL, NULL);
INSERT INTO `meeting_user` VALUES (22, 123, NULL, 123, NULL, NULL);
INSERT INTO `meeting_user` VALUES (23, 123, NULL, 123, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
