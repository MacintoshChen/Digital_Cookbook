/*
 Navicat Premium Data Transfer

 Source Server         : Mysql80
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : cookbookdb

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 27/06/2023 12:00:58
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_ingredient
-- ----------------------------
DROP TABLE IF EXISTS `tbl_ingredient`;
CREATE TABLE `tbl_ingredient`  (
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_ingredient
-- ----------------------------
INSERT INTO `tbl_ingredient` VALUES ('fish', 'g');
INSERT INTO `tbl_ingredient` VALUES ('green vegetable', 'g');
INSERT INTO `tbl_ingredient` VALUES ('meat', 'g');
INSERT INTO `tbl_ingredient` VALUES ('salt', 'g');
INSERT INTO `tbl_ingredient` VALUES ('soy sauce', 'ml');
INSERT INTO `tbl_ingredient` VALUES ('sugar', 'g');
INSERT INTO `tbl_ingredient` VALUES ('water', 'ml');

-- ----------------------------
-- Table structure for tbl_recipe
-- ----------------------------
DROP TABLE IF EXISTS `tbl_recipe`;
CREATE TABLE `tbl_recipe`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `steps` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `people_number` int NOT NULL,
  `video_URL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `img_URL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_recipe
-- ----------------------------
INSERT INTO `tbl_recipe` VALUES ('21f2a340a5944923853a2b371ea4335b', 'Sweet-sour Pork', 'put all ingredients into the pot and cook', 1, '///', '///');
INSERT INTO `tbl_recipe` VALUES ('9f3a5bbfe72d4a0e910367986d25e30a', 'Sweet Pork', 'Put all ingredients into the pot and cook', 5, '/a/', '/b/');
INSERT INTO `tbl_recipe` VALUES ('a86104ac399742dd9d848eaecd13cbc0', 'Red-cooked Pork', 'put all ingredients into the pot and cook', 2, '///', '///');
INSERT INTO `tbl_recipe` VALUES ('c3473e65eebf4dc29816b0bc5a0e1f9f', 'Red-cooked Pork', 'put all ingredients into the pot and cook', 2, '///', '///');
INSERT INTO `tbl_recipe` VALUES ('c6ea62fce487444b957add1e3fad8f57', 'Red-cooked Pork', 'put all ingredients into the pot and cook', 2, '///', '///');
INSERT INTO `tbl_recipe` VALUES ('cacc0c75085a485eb677a7b562a71ead', 'Sweet-sour Pork', 'put all ingredients into the pot and cook', 1, '///', '///');
INSERT INTO `tbl_recipe` VALUES ('df543b8a2fe54032915d4fc21b72546a', 'Sweet-sour Pork', 'put all ingredients into the pot and cook', 1, '///', '///');
INSERT INTO `tbl_recipe` VALUES ('e0e1fd1fa47247798c36cc8fafe9d4fb', 'Red-cooked Pork', 'put all ingredients into the pot and cook', 2, '///', '///');

-- ----------------------------
-- Table structure for tbl_recipe_ingredient_relation
-- ----------------------------
DROP TABLE IF EXISTS `tbl_recipe_ingredient_relation`;
CREATE TABLE `tbl_recipe_ingredient_relation`  (
  `recipe_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ingredient_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ingredient_unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ingredient_quantity` float NOT NULL,
  PRIMARY KEY (`recipe_id`, `ingredient_name`, `ingredient_unit`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_recipe_ingredient_relation
-- ----------------------------
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('21f2a340a5944923853a2b371ea4335b', 'fish', 'g', 75);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('21f2a340a5944923853a2b371ea4335b', 'green vegetable', 'g', 50);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('21f2a340a5944923853a2b371ea4335b', 'meat', 'g', 250);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('21f2a340a5944923853a2b371ea4335b', 'salt', 'g', 20.5);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('21f2a340a5944923853a2b371ea4335b', 'soy sauce', 'ml', 250);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('21f2a340a5944923853a2b371ea4335b', 'sugar', 'g', 50.5);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('9f3a5bbfe72d4a0e910367986d25e30a', 'green vegetable', 'g', 50);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('9f3a5bbfe72d4a0e910367986d25e30a', 'water', 'ml', 75);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('a86104ac399742dd9d848eaecd13cbc0', 'meat', 'g', 250);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('a86104ac399742dd9d848eaecd13cbc0', 'salt', 'g', 100);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('a86104ac399742dd9d848eaecd13cbc0', 'sugar', 'g', 50);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('c3473e65eebf4dc29816b0bc5a0e1f9f', 'fish', 'g', 75);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('c3473e65eebf4dc29816b0bc5a0e1f9f', 'green vegetable', 'g', 50);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('c3473e65eebf4dc29816b0bc5a0e1f9f', 'meat', 'g', 250);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('c3473e65eebf4dc29816b0bc5a0e1f9f', 'salt', 'g', 20.5);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('c3473e65eebf4dc29816b0bc5a0e1f9f', 'soy sauce', 'ml', 250);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('c3473e65eebf4dc29816b0bc5a0e1f9f', 'sugar', 'g', 50.5);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('c6ea62fce487444b957add1e3fad8f57', 'meat', 'g', 250);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('c6ea62fce487444b957add1e3fad8f57', 'salt', 'g', 100);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('c6ea62fce487444b957add1e3fad8f57', 'sugar', 'g', 50);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('cacc0c75085a485eb677a7b562a71ead', 'fish', 'g', 75);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('cacc0c75085a485eb677a7b562a71ead', 'green vegetable', 'g', 50);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('cacc0c75085a485eb677a7b562a71ead', 'meat', 'g', 250);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('cacc0c75085a485eb677a7b562a71ead', 'salt', 'g', 20.5);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('cacc0c75085a485eb677a7b562a71ead', 'soy sauce', 'ml', 250);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('cacc0c75085a485eb677a7b562a71ead', 'sugar', 'g', 50.5);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('df543b8a2fe54032915d4fc21b72546a', 'fish', 'g', 75);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('df543b8a2fe54032915d4fc21b72546a', 'green vegetable', 'g', 50);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('df543b8a2fe54032915d4fc21b72546a', 'meat', 'g', 250);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('df543b8a2fe54032915d4fc21b72546a', 'salt', 'g', 20.5);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('df543b8a2fe54032915d4fc21b72546a', 'soy sauce', 'ml', 250);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('df543b8a2fe54032915d4fc21b72546a', 'sugar', 'g', 50.5);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('e0e1fd1fa47247798c36cc8fafe9d4fb', 'meat', 'g', 250);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('e0e1fd1fa47247798c36cc8fafe9d4fb', 'salt', 'g', 100);
INSERT INTO `tbl_recipe_ingredient_relation` VALUES ('e0e1fd1fa47247798c36cc8fafe9d4fb', 'sugar', 'g', 50);

SET FOREIGN_KEY_CHECKS = 1;
