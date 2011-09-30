DROP TABLE IF EXISTS `wordfilter`.`spam_checklog`;
CREATE TABLE  `wordfilter`.`spam_checklog` (
  `id` int(11) NOT NULL auto_increment,
  `app` varchar(50) NOT NULL default '',
  `uid` int(11) NOT NULL default '0',
  `ip` varchar(15) NOT NULL default '',
  `agt` varchar(255) NOT NULL default '',
  `txt` text NOT NULL,
  `mode` tinyint(4) NOT NULL default '0',
  `result` int(11) NOT NULL default '0',
  `manual` tinyint(4) NOT NULL default '0',
  `add_time` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`),
  KEY `add_time` (`add_time`),
  KEY `app` (`app`),
  KEY `manual` (`manual`),
  KEY `result` (`result`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;



DROP TABLE IF EXISTS `wordfilter`.`spam_checklog_old`;
CREATE TABLE  `wordfilter`.`spam_checklog_old` (
  `id` int(11) NOT NULL auto_increment,
  `app` varchar(50) NOT NULL default '',
  `uid` int(11) NOT NULL default '0',
  `ip` varchar(15) NOT NULL default '',
  `agt` varchar(255) NOT NULL default '',
  `txt` text NOT NULL,
  `mode` tinyint(4) NOT NULL default '0',
  `result` int(11) NOT NULL default '0',
  `manual` tinyint(4) NOT NULL default '0',
  `add_time` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;



DROP TABLE IF EXISTS `wordfilter`.`spam_ip`;
CREATE TABLE  `wordfilter`.`spam_ip` (
  `ip` varchar(15) NOT NULL default '',
  `start_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `release_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `app` text NOT NULL,
  PRIMARY KEY  (`ip`),
  KEY `release_time` (`release_time`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;



DROP TABLE IF EXISTS `wordfilter`.`spam_user`;
CREATE TABLE  `wordfilter`.`spam_user` (
  `userID` int(11) NOT NULL default '0',
  `start_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `release_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `app` text NOT NULL,
  PRIMARY KEY  (`userID`),
  KEY `release_time` (`release_time`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;



DROP TABLE IF EXISTS `wordfilter`.`spam_word`;
CREATE TABLE  `wordfilter`.`spam_word` (
  `word` varchar(50) NOT NULL default '',
  `length` tinyint(4) NOT NULL default '0',
  `weight` tinyint(4) NOT NULL default '0',
  `category` varchar(20) NOT NULL default '',
  `infocome` int(3) NOT NULL,
  `remark` text NOT NULL,
  `adder` varchar(20) NOT NULL,
  `add_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `last_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `piclink` varchar(1000) NOT NULL,
  PRIMARY KEY  (`word`),
  KEY `length` (`length`),
  KEY `weight` (`weight`),
  KEY `category` (`category`),
  KEY `add_time` (`add_time`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;