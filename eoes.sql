-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               11.3.2-MariaDB-log - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.7.0.6850
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for eoes
CREATE DATABASE IF NOT EXISTS `eoes` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `eoes`;

-- Dumping structure for table eoes.accounts
CREATE TABLE IF NOT EXISTS `accounts` (
  `id` varchar(255) DEFAULT NULL,
  `gpid` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `created` bigint(255) DEFAULT NULL,
  `rank` int(1) DEFAULT 0,
  `session` int(255) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table eoes.accounts: ~0 rows (approximately)

-- Dumping structure for table eoes.characters
CREATE TABLE IF NOT EXISTS `characters` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(255) NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL DEFAULT '0',
  `faction` enum('Vampire','Lycan','Mortal') NOT NULL,
  `gender` enum('Male','Female','Non-Binary') NOT NULL,
  `health` bigint(255) NOT NULL DEFAULT 100,
  `max_health` bigint(255) NOT NULL DEFAULT 100,
  `mapid` int(50) NOT NULL DEFAULT 0,
  `pos` varchar(255) NOT NULL,
  `rot` varchar(255) NOT NULL,
  `money` float(255,2) DEFAULT 0.00,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table eoes.characters: ~0 rows (approximately)

-- Dumping structure for table eoes.maps
CREATE TABLE IF NOT EXISTS `maps` (
  `mapid` int(11) NOT NULL DEFAULT 0,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`mapid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table eoes.maps: ~3 rows (approximately)
INSERT INTO `maps` (`mapid`, `name`) VALUES
  (0, 'Terra Ignis'),
  (1, 'Noxloma'),
  (2, 'NewHamilton');

-- Dumping structure for table eoes.starter_zones
CREATE TABLE IF NOT EXISTS `starter_zones` (
  `faction` enum('Vampire','Lycan','Mortal') DEFAULT NULL,
  `map` int(255) DEFAULT 0,
  `pos` varchar(255) DEFAULT NULL,
  `rot` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table eoes.starter_zones: ~0 rows (approximately)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
