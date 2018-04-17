-- MySQL dump 10.13  Distrib 5.7.21, for Linux (x86_64)
--
-- Host: localhost    Database: chat_messenger
-- ------------------------------------------------------
-- Server version	5.7.21-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account_mst`
--

DROP TABLE IF EXISTS `account_mst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_mst` (
  `account_mst_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL,
  `username` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`account_mst_id`),
  UNIQUE KEY `uidx_account_mst_01` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_mst`
--

LOCK TABLES `account_mst` WRITE;
/*!40000 ALTER TABLE `account_mst` DISABLE KEYS */;
INSERT INTO `account_mst` VALUES (1,'TAPOPADMA TRIPATHY','tapopadmatripathy1995@gmail.com','84348608','tapopadma','passw0rd'),(2,'ff','fff','444','fff','ewew'),(3,'ggg','ggggg','555','ggg','ggggggg'),(4,'kkk','kkkk','646456','kkk','45456'),(5,'obama','obama','9898','obama','obama'),(6,'trump','trump','32932938','trump','trump'),(7,'xyz','xyz','98349348','xyz','iamaxyz'),(8,'huhu','huhu','9898','huhu','ldskfjlskdjf');
/*!40000 ALTER TABLE `account_mst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_trn`
--

DROP TABLE IF EXISTS `message_trn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_trn` (
  `message_trn_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message` varchar(1024) DEFAULT NULL,
  `username` varchar(256) DEFAULT NULL,
  `last_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `message_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`message_trn_id`),
  UNIQUE KEY `uidx_message_trn_01` (`message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_trn`
--

LOCK TABLES `message_trn` WRITE;
/*!40000 ALTER TABLE `message_trn` DISABLE KEYS */;
INSERT INTO `message_trn` VALUES (8,'Your visa is approved!!!','obama','2018-04-16 11:10:32','31872f6a-a9d9-451a-a7b5-467e0058fbcb');
/*!40000 ALTER TABLE `message_trn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_users_trn`
--

DROP TABLE IF EXISTS `message_users_trn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_users_trn` (
  `message_users_trn_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_id` varchar(256) DEFAULT NULL,
  `username` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`message_users_trn_id`),
  UNIQUE KEY `uidx_message_users_trn_01` (`message_id`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_users_trn`
--

LOCK TABLES `message_users_trn` WRITE;
/*!40000 ALTER TABLE `message_users_trn` DISABLE KEYS */;
INSERT INTO `message_users_trn` VALUES (8,'31872f6a-a9d9-451a-a7b5-467e0058fbcb','obama'),(9,'31872f6a-a9d9-451a-a7b5-467e0058fbcb','tapopadma');
/*!40000 ALTER TABLE `message_users_trn` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-17 11:00:12
