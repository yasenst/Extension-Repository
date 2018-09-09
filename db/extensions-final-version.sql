-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.21-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for extensions_db
CREATE DATABASE IF NOT EXISTS `extensions_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `extensions_db`;

-- Dumping structure for table extensions_db.extensions
CREATE TABLE IF NOT EXISTS `extensions` (
  `extension_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` text NOT NULL,
  `version` varchar(50) NOT NULL,
  `user_id` int(11) NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `number_of_downloads` int(11) NOT NULL,
  `repository_link` text NOT NULL,
  `pending` int(11) NOT NULL DEFAULT '1',
  `featured` int(11) NOT NULL DEFAULT '0',
  `file_name` varchar(255) NOT NULL,
  `open_issues` int(11) NOT NULL,
  `pull_requests` int(11) NOT NULL,
  `last_commit` datetime NOT NULL,
  `last_sync` datetime NOT NULL,
  `image_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`extension_id`),
  KEY `FK__users` (`user_id`),
  CONSTRAINT `FK__users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;

-- Dumping data for table extensions_db.extensions: ~3 rows (approximately)
/*!40000 ALTER TABLE `extensions` DISABLE KEYS */;
INSERT INTO `extensions` (`extension_id`, `name`, `description`, `version`, `user_id`, `date`, `number_of_downloads`, `repository_link`, `pending`, `featured`, `file_name`, `open_issues`, `pull_requests`, `last_commit`, `last_sync`, `image_name`) VALUES
	(87, 'Test extension 2', 'saljkfd', 'askld', 3, '2018-09-06 00:07:40', 0, 'https://github.com/yasenst/Coursework-1', 0, 1, '84fc5a0d-37bd-4051-9d8c-ec3c7c9b476fc++Revision.txt', 0, 0, '2017-05-13 21:04:58', '2018-09-09 14:59:19', 'no_image_available.png'),
	(89, 'Test my key', 'dsklf', 'skld', 3, '2018-09-06 16:27:37', 0, 'https://github.com/andri27-ts/60_Days_RL_Challenge', 0, 0, '4a377d9c-efd9-4885-8a6f-638859b10360Tasks.txt', 1, 0, '2018-09-03 23:35:41', '2018-09-09 14:59:20', 'no_image_available.png'),
	(95, 'Extension 53', 'sdaf', 'safd', 3, '2018-09-09 13:53:49', 1, 'https://github.com/yasenst/Coursework-2', 0, 0, '3f2bca8a-0afe-4b53-a19b-223af8505f50Tasks.txt', 0, 0, '2017-09-05 23:53:55', '2018-09-09 14:59:22', 'Blue_umbrella-Transparent.png');
/*!40000 ALTER TABLE `extensions` ENABLE KEYS */;

-- Dumping structure for table extensions_db.extensions_tags
CREATE TABLE IF NOT EXISTS `extensions_tags` (
  `extension_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`extension_id`,`tag_id`),
  KEY `FK__tags` (`tag_id`),
  CONSTRAINT `FK__extensions` FOREIGN KEY (`extension_id`) REFERENCES `extensions` (`extension_id`),
  CONSTRAINT `FK__tags` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table extensions_db.extensions_tags: ~1 rows (approximately)
/*!40000 ALTER TABLE `extensions_tags` DISABLE KEYS */;
INSERT INTO `extensions_tags` (`extension_id`, `tag_id`) VALUES
	(95, 5);
/*!40000 ALTER TABLE `extensions_tags` ENABLE KEYS */;

-- Dumping structure for table extensions_db.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table extensions_db.roles: ~2 rows (approximately)
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`role_id`, `name`) VALUES
	(1, 'ROLE_USER'),
	(2, 'ROLE_ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

-- Dumping structure for table extensions_db.tags
CREATE TABLE IF NOT EXISTS `tags` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- Dumping data for table extensions_db.tags: ~15 rows (approximately)
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` (`tag_id`, `name`) VALUES
	(1, 'c'),
	(2, 'linked-list'),
	(3, 'files'),
	(4, 'stupidTag'),
	(5, ''),
	(6, 'asdf'),
	(7, 'tag1'),
	(8, 'tag2'),
	(9, 'css'),
	(10, 'js'),
	(11, 'Hello'),
	(12, 'Java'),
	(13, 'laravel'),
	(14, 'lisp'),
	(15, 'go');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;

-- Dumping structure for table extensions_db.users
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(68) NOT NULL,
  `fullName` varchar(255) NOT NULL,
  `enabled` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- Dumping data for table extensions_db.users: ~10 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`user_id`, `username`, `password`, `fullName`, `enabled`) VALUES
	(1, 'test', '$2a$10$ALFi79fgvO9SplCNyUmOu.egoBgwEQOClU6u.2zpHa3GUpxljiNe.', 'test test', 1),
	(2, 'user', '$2a$10$74LyirelVFBKQX9LK.W.k.Wm7gqBYY2qI2rExBaERtSa3tf30VzOC', 'User User', 1),
	(3, 'admin', '$2a$10$EXD43xYRLmctyWkWTVFy8eKd6Gni1rPgBW1C.ybLyAHy3kEkssacC', 'Admin Admin', 1),
	(4, 'user1', '$2a$10$EhQHspxiuxmgTfbr9Lm.ceA6/arAGa/vw6LtBbgwGk6rMzzyeRLGG', 'userov', 1),
	(5, 'test2', '$2a$10$HdYHjHF4Cd3FPXcQtw1YvOTmnCeFGBgeELr.J1i92CLOGCGJ4JbLS', 'test2', 1),
	(6, 'Test4', '$2a$10$Snq7DFdXH4OplZbsJwGoQ.lagXl5P9y3UuGiXH918F0PUvtEgiMb.', 'Test', 1),
	(7, 'Rose', '$2a$10$VCn8BbqDDEfTF6SQbu8F3uufgwcTR74vWxefU6kn2WdNCLwgHKBaC', 'Rose Rozova', 1),
	(8, 'Ronaldo', '$2a$10$dugA.f0V4SdX/MLRx8C8SeEAigu/nVWflIk75uh.19j01GzgqvjCe', 'Ronny', 1),
	(9, 'mock703', '$2a$10$oZKfi8p1GdMImtFB7fa06.W5NCrcl8YzRr6cTfUN80NFtB83w6PKK', 'Mock Mocker', 1),
	(12, 'John', '$2a$10$cM6nRbEOpFXSx3osNgBhU.yLwIAXM2f5q9Yh1yRPdRK4KHrXB8B.2', '2389150', 1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- Dumping structure for table extensions_db.users_roles
CREATE TABLE IF NOT EXISTS `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_users_roles_roles` (`role_id`),
  CONSTRAINT `FK_users_roles_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`),
  CONSTRAINT `FK_users_roles_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table extensions_db.users_roles: ~11 rows (approximately)
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES
	(1, 1),
	(2, 1),
	(3, 1),
	(4, 1),
	(5, 1),
	(6, 1),
	(7, 1),
	(8, 1),
	(9, 1),
	(12, 1),
	(3, 2);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
