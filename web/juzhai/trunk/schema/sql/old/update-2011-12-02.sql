-- -----------------------------------------------------
-- Table `juzhai`.`tb_act_detail`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_act_detail` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_act_detail` (
  `act_id` BIGINT NOT NULL ,
  `detail` MEDIUMTEXT NULL ,
  `display` TINYINT(1) NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`act_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_town`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_town` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_town` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(60) NOT NULL ,
  `city_id` BIGINT NOT NULL ,
  `sequence` INT(4) NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_name` ON `juzhai`.`tb_town` (`name` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_cityid_sequence` ON `juzhai`.`tb_town` (`city_id` ASC, `sequence` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_profession`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_profession` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_profession` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(60) NOT NULL ,
  `sequence` INT(4) NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_constellation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_constellation` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_constellation` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `start_month` INT(2) NOT NULL DEFAULT 0 ,
  `start_date` INT(2) NOT NULL DEFAULT 0 ,
  `end_month` INT(2) NOT NULL DEFAULT 0 ,
  `end_date` INT(2) NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;


-- -----------------------------------------------------
-- Table `juzhai`.`tb_act_link`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_act_link` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_act_link` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `act_id` BIGINT NOT NULL ,
  `name` VARCHAR(100) NOT NULL ,
  `link` VARCHAR(300) NOT NULL ,
  `source` VARCHAR(60) NOT NULL ,
  `source_link` VARCHAR(300) NULL ,
  `sequence` INT(4) NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE INDEX `idx_actid` ON `juzhai`.`tb_act_link` (`act_id` ASC) ;

SHOW WARNINGS;


-- -----------------------------------------------------
-- Table `juzhai`.`tb_act_ad`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_act_ad` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_act_ad` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `act_id` BIGINT NOT NULL ,
  `name` VARCHAR(300) NOT NULL ,
  `pic_url` VARCHAR(300) NOT NULL ,
  `link` VARCHAR(300) NOT NULL ,
  `city` BIGINT NOT NULL DEFAULT 0 ,
  `district` VARCHAR(200) NULL ,
  `address` VARCHAR(200) NULL ,
  `start_time` DATETIME NULL ,
  `end_time` DATETIME NULL ,
  `prime_price` VARCHAR(45) NULL ,
  `price` VARCHAR(45) NULL ,
  `discount` DOUBLE NULL ,
  `source` VARCHAR(60) NOT NULL ,
  `source_link` VARCHAR(300) NULL ,
  `sequence` INT(4) NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE INDEX `idx_actid` ON `juzhai`.`tb_act_ad` (`act_id` ASC) ;

SHOW WARNINGS;


-- -----------------------------------------------------
-- Table `juzhai`.`tb_dating`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_dating` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_dating` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `starter_uid` BIGINT NOT NULL ,
  `receiver_uid` BIGINT NOT NULL ,
  `act_id` BIGINT NOT NULL ,
  `starter_contact_type` INT(2) NOT NULL ,
  `starter_contact_value` VARCHAR(100) NOT NULL ,
  `receiver_contact_type` INT(2) ,
  `receiver_contact_value` VARCHAR(100) ,
  `consume_type` INT(2) NOT NULL ,
  `has_read` TINYINT(1) NOT NULL DEFAULT 0 ,
  `response` INT(1) NOT NULL DEFAULT 0 ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE INDEX `idx_starteruid` ON `juzhai`.`tb_dating` (`starter_uid` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_receiveruid` ON `juzhai`.`tb_dating` (`receiver_uid` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `juzhai`.`tb_interest_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_interest_user` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_interest_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `interest_uid` BIGINT NOT NULL ,
  `uid` BIGINT NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

SHOW WARNINGS;
CREATE INDEX `idx_uid` ON `juzhai`.`tb_interest_user` (`uid` ASC) ;

SHOW WARNINGS;
CREATE INDEX `idx_interestuid` ON `juzhai`.`tb_interest_user` (`interest_uid` ASC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `uidx_uid_interestuid` ON `juzhai`.`tb_interest_user` (`uid` ASC, `interest_uid` ASC) ;

SHOW WARNINGS;


-- -----------------------------------------------------
-- Table `juzhai`.`tb_raw_act`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `juzhai`.`tb_raw_act` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `juzhai`.`tb_raw_act` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(100) NOT NULL ,
  `detail` MEDIUMTEXT NOT NULL ,
  `category_ids` VARCHAR(45) NULL ,
  `logo` VARCHAR(100) NULL ,
  `province` BIGINT NOT NULL DEFAULT 0 ,
  `city` BIGINT NOT NULL DEFAULT 0 ,
  `town` BIGINT NOT NULL DEFAULT -1 ,
  `address` VARCHAR(200) NULL ,
  `start_time` DATETIME NULL ,
  `end_time` DATETIME NULL ,
  `create_uid` BIGINT NOT NULL ,
  `create_time` DATETIME NOT NULL ,
  `last_modify_time` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci, 
COMMENT = '用户上传的未发布的项目' ;

SHOW WARNINGS;
CREATE INDEX `idx_createuid` ON `juzhai`.`tb_raw_act` (`create_uid` ASC) ;

SHOW WARNINGS;


alter table tb_profile add feature varchar(800) AFTER shape;
alter table tb_profile add profession_id BIGINT NOT NULL DEFAULT 0 AFTER company;
alter table tb_profile change constellation constellation_id BIGINT NOT NULL DEFAULT 0;
alter table tb_profile change gender gender INT(1);
alter table tb_profile change town town BIGINT NOT NULL DEFAULT -1;
alter table tb_profile add has_modify_nickname TINYINT(1) NOT NULL DEFAULT 0 AFTER nickname;
alter table tb_profile add has_modify_gender TINYINT(1) NOT NULL DEFAULT 0 AFTER gender;
alter table tb_profile add birth_secret TINYINT(1) NOT NULL DEFAULT 0 AFTER birth_day;
alter table tb_act add town BIGINT NOT NULL DEFAULT -1 AFTER city;
alter table tb_act add key_words varchar(500);
alter table tb_user_act add gender INT(1) AFTER hot_lev;
alter table tb_user_act add city BIGINT NOT NULL DEFAULT 0 AFTER gender;
alter table tb_user_act add top TINYINT NOT NULL DEFAULT 0 AFTER city;



INSERT INTO `tb_constellation` (name,start_month,start_date,end_month,end_date) VALUES ('白羊座',3,21,4,19),('金牛座',4,20,5,20),('双子座',5,21,6,21),('巨蟹座',6,22,7,22),('狮子座',7,23,8,22),('处女座',8,23,9,22),('天秤座',9,23,10,23),('天蝎座',10,24,11,22),('射手座',11,23,12,21),('摩羯座',12,22,1,19),('水瓶座',1,20,2,18),('双鱼座',2,19,3,20);
INSERT INTO `tb_profession` (name,sequence) VALUES ('公司职员',1),('中层管理',2),('企业高管',3),('私营业主',4),('艺术类',5),('学生',6),('教师',7),('医务人员',8),('机关职员',9);

INSERT INTO `tb_town` VALUES (1,'朝阳区',1,1,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(2,'东城区',1,2,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(3,'西城区',1,3,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(4,'海淀区',1,4,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(5,'宣武区',1,5,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(6,'崇文区',1,6,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(7,'丰台区',1,7,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(8,'石景山区',1,8,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(9,'大兴区',1,9,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(10,'通州区',1,10,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(11,'昌平区',1,11,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(12,'卢湾区',2,1,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(13,'徐汇区',2,2,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(14,'静安区',2,3,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(15,'长宁区',2,4,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(16,'闵行区',2,5,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(17,'浦东新区',2,6,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(18,'黄浦区',2,7,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(19,'普陀区',2,8,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(20,'闸北区',2,9,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(21,'虹口区',2,10,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(22,'杨浦区',2,11,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(23,'宝山区',2,12,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(24,'松江区',2,13,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(25,'嘉定区',2,14,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(26,'青浦区',2,15,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(27,'和平区',3,1,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(28,'南开区',3,2,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(29,'河西区',3,3,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(30,'河东区',3,4,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(31,'河北区',3,5,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(32,'红桥区',3,6,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(33,'滨海新区',3,7,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(34,'巴南区',4,1,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(35,'北碚区',4,2,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(36,'渝中区',4,3,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(37,'沙坪坝区',4,4,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(38,'江北区',4,5,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(39,'渝北区',4,6,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(40,'南岸区',4,7,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(41,'九龙坡区',4,8,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(42,'大渡口区',4,9,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(43,'天河区',181,1,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(44,'越秀区',181,2,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(45,'海珠区',181,3,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(46,'荔湾区',181,4,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(47,'白云区',181,5,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(48,'番禺区',181,6,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(49,'福田区',183,1,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(50,'罗湖区',183,2,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(51,'南山区',183,3,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(52,'盐田区',183,4,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(53,'宝安区',183,5,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(54,'龙岗区',183,6,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(55,'江岸区',108,1,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(56,'武昌区',108,2,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(57,'江汉区',108,3,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(58,'硚口区',108,4,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(59,'汉阳区',108,5,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(60,'青山区',108,6,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(61,'洪山区',108,7,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(62,'东西湖区',108,8,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(63,'武侯区',241,1,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(64,'锦江区',241,2,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(65,'金牛区',241,3,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(66,'青羊区',241,4,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(67,'成华区',241,5,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(68,'西湖区',343,1,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(69,'上城区',343,2,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(70,'下城区',343,3,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(71,'拱墅区',343,4,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(72,'江干区',343,5,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(73,'萧山区',343,6,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(74,'滨江区',343,7,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(75,'长安区',69,1,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(76,'灞桥区',69,2,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(77,'阎良区',69,3,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(78,'临潼区',69,4,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(79,'碑林区',69,5,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(80,'新城区',69,6,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(81,'莲湖区',69,7,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(82,'雁塔区',69,8,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(83,'未央区',69,9,'2011-12-04 11:44:29','2011-12-04 11:44:29'),(84,'高新区',69,10,'2011-12-04 11:44:29','2011-12-04 11:44:29');
