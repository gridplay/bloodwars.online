
-- Dumping database structure for bwo
CREATE DATABASE IF NOT EXISTS `bwo` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `bwo`;

-- Dumping structure for table bwo.accounts
CREATE TABLE IF NOT EXISTS `accounts` (
  `id` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `created` bigint(255) DEFAULT NULL,
  `updated` bigint(255) DEFAULT NULL,
  `rank` int(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table bwo.accounts: ~0 rows (approximately)

-- Dumping structure for table bwo.characters
CREATE TABLE IF NOT EXISTS `characters` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(255) NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL DEFAULT '0',
  `faction` int(1) NOT NULL DEFAULT 0,
  `gender` int(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table bwo.characters: ~0 rows (approximately)

-- Dumping structure for table bwo.maps
CREATE TABLE IF NOT EXISTS `maps` (
  `mapid` int(11) DEFAULT 0,
  `name` varchar(50) DEFAULT NULL
  `buildid` int(11) DEFAULT 0,
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table bwo.maps: ~2 rows (approximately)
INSERT INTO `maps` (`mapid`, `name`, `buildid`) VALUES
	(0, 'New Hamilton', 3),
	(1, 'Noxloma', 4);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
