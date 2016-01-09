-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2011 年 11 月 29 日 05:53
-- 服务器版本: 5.5.8
-- PHP 版本: 5.3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- 数据库: `notify`
--

CREATE DATABASE IF NOT EXISTS `notify` DEFAULT CHARACTER SET utf8;

USE `notify`;

-- --------------------------------------------------------

--
-- 表的结构 `admin`
--


CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '',
  `pass` varchar(100) NOT NULL DEFAULT '',
  `uptime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `admin`
--

INSERT INTO `admin` (`id`, `name`, `pass`, `uptime`) VALUES
(1, 'admin', 'admin', '2011-11-29 12:52:49');

-- --------------------------------------------------------

--
-- 表的结构 `activity'
--

CREATE TABLE IF NOT EXISTS `activity`
(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`personal` tinyint(1) NOT NULL DEFAULT '0',
	`customerid` int(11) NOT NULL DEFAULT '0',
	`title` varchar(255) NOT NULL DEFAULT '',
	`content` varchar(1000) NOT NULL DEFAULT '',
	`picture` varchar(100) NOT NULL DEFAULT '',
	`commentcount` int(11) NOT NULL DEFAULT '0',
	`partcount` int(11) NOT NULL DEFAULT '0',
	`likecount` int(11) NOT NULL DEFAULT '0',
	`uptime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`)
)  ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;
--
-- 转存表中的数据 `activity`
--


-- --------------------------------------------------------

--
-- 表的结构 `comment`
--

CREATE TABLE IF NOT EXISTS `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activityid` int(11) NOT NULL DEFAULT '0',
  `customerid` int(11) NOT NULL DEFAULT '0',
  `personal` tinyint(1) NOT NULL DEFAULT '0',
  `content` varchar(1000) NOT NULL DEFAULT '',
  `uptime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `comment`
--


-- --------------------------------------------------------

--
-- 表的结构 `config`
--

CREATE TABLE IF NOT EXISTS `config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerid` int(11) NOT NULL DEFAULT '0',
  `name` varchar(100) NOT NULL DEFAULT '',
  `value` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `cid` (`customerid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `config`
--


-- --------------------------------------------------------

--
-- 表的结构 `customer_person`
--

CREATE TABLE IF NOT EXISTS `customer_person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `studentid` varchar(100) NOT NULL DEFAULT '',
  `name` varchar(100) NOT NULL DEFAULT '',
  `pass` varchar(100) NOT NULL DEFAULT '',
  `contact` varchar(100) NOT NULL DEFAULT '',
  `face` varchar(100) NOT NULL DEFAULT '',
  `school` varchar(100) NOT NULL DEFAULT '',
  `activitycount` int(11) NOT NULL DEFAULT '0',
  `uptime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `customer_club` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin` varchar(100) NOT NULL DEFAULT '',
  `name` varchar(100) NOT NULL DEFAULT '',
  `pass` varchar(100) NOT NULL DEFAULT '',
  `contact` varchar(100) NOT NULL DEFAULT '',
  `face` varchar(100) NOT NULL DEFAULT '',
  `school` varchar(100) NOT NULL DEFAULT '',
  `activitycount` int(11) NOT NULL DEFAULT '0',
  `uptime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;
--
-- 转存表中的数据 `customer_person`
--

INSERT INTO customer_person (`studentid`, `name`, `pass`, `contact`, `school`, `activitycount`, `uptime`) VALUES
(1, 'james', 'james', '1231231231', '山东大学', 0, '2011-11-29 18:11:24'),
(2, 'huang', 'huang', 'safsadf', '山东大学', 0, '2011-11-29 18:17:12');

INSERT INTO customer_club (`id`, `admin`,`name`, `pass`, `contact`, `school`, `activitycount`, `uptime`) VALUES
(1, 'bin','james', 'james', '1231231231', '山东大学', 0, '2011-11-29 18:11:24'),
(2,'bin','huang', 'huang', 'safsadf', '山东大学', 0, '2011-11-29 18:17:12');

-- --------------------------------------------------------

--
-- 表的结构 `notice`
--



CREATE TABLE IF NOT EXISTS `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerid` int(11) NOT NULL DEFAULT '0',
  `likecount` int(11) NOT NULL DEFAULT '0',
  `partcount` int(11) NOT NULL DEFAULT '0',
  `message` varchar(255) NOT NULL DEFAULT '',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `uptime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 表的结构 `likes`
--
CREATE TABLE IF NOT EXISTS `part` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`customerid` int(11) NOT NULL DEFAULT '0',
	`activityid` int(11) NOT NULL DEFAULT '0',
	`personal` tinyint(1) NOT NULL DEFAULT '0',
	 `uptime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`)
);
CREATE TABLE IF NOT EXISTS `like` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`customerid` int(11) NOT NULL DEFAULT '0',
	`activityid` int(11) NOT NULL DEFAULT '0',
	`personal` tinyint(1) NOT NULL DEFAULT '0',
	 `uptime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`)
);



