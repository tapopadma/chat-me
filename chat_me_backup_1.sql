-- MySQL dump 10.13  Distrib 5.7.21, for Linux (x86_64)
--
-- Host: localhost    Database: chat_me
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_mst`
--

LOCK TABLES `account_mst` WRITE;
/*!40000 ALTER TABLE `account_mst` DISABLE KEYS */;
INSERT INTO `account_mst` VALUES (1,'TAPOPADMA TRIPATHY','tapopadmatripathy1995@gmail.com','84348608','tapopadma','passw0rd'),(2,'ff','fff','444','fff','ewew'),(3,'ggg','ggggg','555','ggg','ggggggg'),(4,'kkk','kkkk','646456','kkk','45456'),(5,'obama','obama','9898','obama','obama'),(6,'trump','trump','32932938','trump','trump'),(7,'xyz','xyz','98349348','xyz','iamaxyz'),(8,'huhu','huhu','9898','huhu','ldskfjlskdjf'),(9,'kk','kkk','4334','k','kkk'),(10,'B Soma Naik','testing123@email.com','123123123','somanaik','somanaik'),(11,'yyy','yyy@gmail.com','123456','yyyy','yyyy'),(12,'chandu','',NULL,'chandu','chandu'),(13,'Jingfei',NULL,NULL,'jingfei','955047');
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
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_trn`
--

LOCK TABLES `message_trn` WRITE;
/*!40000 ALTER TABLE `message_trn` DISABLE KEYS */;
INSERT INTO `message_trn` VALUES (8,'Your visa is approved!!!','obama','2018-04-16 11:10:32','31872f6a-a9d9-451a-a7b5-467e0058fbcb'),(9,'lc','k','2018-04-18 01:32:33','5b586b86-717e-40aa-b29f-3e33a922930b'),(10,'lll','k','2018-04-18 01:36:04','517f9413-aa42-4e54-8d36-faf292fade48'),(11,'kjkj','k','2018-04-18 01:38:15','de7b51e3-6779-4461-8b29-908dacfabcd2'),(12,'kkkk','k','2018-04-18 01:39:18','a892e850-9d1a-46a6-8285-e4c57718fd91'),(13,'hey','k','2018-04-18 01:40:20','eaf54794-9e80-431e-b568-707c32e5646e'),(14,'hey','k','2018-04-18 01:41:50','8a098aab-cbe4-4a2e-8ba3-e5d024bf7004'),(15,'bye','k','2018-04-18 01:42:16','5d0b904e-3b97-44ae-bd7d-e51fbde131c0'),(16,'hi','k','2018-04-18 01:46:01','b5c16df7-42c6-41d1-bc2b-146095611309'),(17,'what\'s up?','k','2018-04-18 01:46:13','c2ff0867-3931-4ae1-b2cd-bc07bcc7403a'),(18,'How are you\nI was a bit busy\nso didn\'t make a call.','k','2018-04-18 01:46:50','12e9fa19-8879-4553-9112-79a84dfcae35'),(19,'oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo','k','2018-04-18 01:47:41','cc148171-5896-403a-8ee6-dda37b5cb8f0'),(20,'sorry for spamming','k','2018-04-18 01:48:18','ed807772-29ce-4a38-a791-a2a89e7d423f'),(21,'I am a good.','obama','2018-04-18 04:38:10','8d36d1c6-f4ba-47d2-889b-bf1473a6807c'),(22,'how are you','obama','2018-04-18 04:38:16','19d9cfff-ce10-4ff1-befc-0155f9f9d915'),(23,'hey obama ! how you doing. sorry for the late reply.','tapopadma','2018-04-18 04:40:50','fc17cca5-2dfa-410d-91ae-5689ba670d7b'),(24,'hey i changed the entity.','tapopadma','2018-04-18 04:53:37','e988d803-c12c-4efb-9777-928f0927817f'),(25,'sdd','tapopadma','2018-04-18 07:10:19','b2069334-2af2-4efa-85ce-f69914002deb'),(26,'cc','tapopadma','2018-04-18 07:13:16','adee685f-d58d-49a4-af7f-0e8f21920b48'),(27,'Hi','tapopadma','2018-04-18 08:14:45','a98b7b13-c0c7-4c13-be0a-6b780e98fc0a'),(28,'It\'s fine :)','tapopadma','2018-04-18 08:15:11','8fd1ad7f-6acf-4ad6-98d2-d8b3a9ceebc0'),(29,'So r u free?','tapopadma','2018-04-18 08:17:18','fbd5621f-b165-4606-8338-2700340f1e42'),(30,'Yah sort of xD','k','2018-04-18 08:17:46','9fef8b0b-7229-42f3-b926-3b36dd963258'),(31,'hmm, i\'m kinda busy','k','2018-04-18 08:21:39','8306d700-f798-4b70-8ac0-4dce379714c1'),(32,'how about you','k','2018-04-18 08:21:56','b4b5e8f8-0727-4bd1-a0f5-8f8b719b9cb0'),(33,'fuck man','k','2018-04-18 08:23:46','ee98b8d3-e5a0-40bf-93c7-50efc869fcd0'),(34,'what >>','k','2018-04-18 08:25:01','1bbdc6f7-b89e-4eba-a956-8cb331fa1ff8'),(35,'what the hell','k','2018-04-18 08:25:28','75fec15e-6179-4c5f-82c6-a821834594c1'),(36,'ok','k','2018-04-18 08:57:38','1aa9d4e7-ef47-487e-9b94-00dc3aa093f5'),(37,'hi','k','2018-04-18 08:58:35','23c84267-9b63-4fa7-9828-e996e0e89e7e'),(38,'hey','k','2018-04-18 08:58:38','de27d25c-5659-4066-b36a-a599461beb32'),(39,'fyyy','k','2018-04-18 09:24:59','f2186bad-8c38-463a-aaf5-f0a6f84b974f'),(40,'dont angry me','tapopadma','2018-04-18 09:26:13','b7a2f337-aa8a-41e3-a9e9-8a2b6e067a3f'),(41,'sorry','tapopadma','2018-04-18 09:26:32','2959e814-ec89-4bb6-ab28-6b2a041ac9ca'),(42,'what','k','2018-04-18 09:26:36','9124a750-31cb-4fc0-addd-efa8abd8ca47'),(43,'hey dude!!','tapopadma','2018-04-18 09:32:16','2be3e5cd-4c8a-4f95-82c6-db31930a0da8'),(44,'hey hey dude!!!!!!!!!!!!!!!!','somanaik','2018-04-18 09:32:59','85afee20-5c3e-4343-89ba-8fcaf8af6b2d'),(45,'hmm','tapopadma','2018-04-18 09:33:06','66387876-b887-4ca2-a54d-c7f3b9c78f8e'),(46,'journal testing ka kya hua','tapopadma','2018-04-18 09:33:23','cfe3bd12-7dea-4dc0-8c34-0870c16bd96d'),(47,'lot of things are happening. goom raha hai','somanaik','2018-04-18 09:33:52','97637332-c3b9-4fd8-9163-02da4d96412c'),(48,'thanks , now you can logout . my testing done for today','tapopadma','2018-04-18 09:34:22','66613a51-6d9e-472b-8be5-0da898a4b0cb'),(49,'kaunsa db use kiya iskeliye','somanaik','2018-04-18 09:34:28','4279ea2e-c923-47e4-9359-610a9b623384'),(50,':)','tapopadma','2018-04-18 09:34:33','9642b5a3-76bd-4ac1-be1c-4f816cf2a0a6'),(51,'mysql','tapopadma','2018-04-18 09:34:42','28b07114-7699-4c88-9a0f-4bff4a183ca4'),(52,'u can\'t the user logout','somanaik','2018-04-18 09:34:47','d84c5219-cf46-452e-b9b2-62f0f16c49d8'),(53,':)','somanaik','2018-04-18 09:35:14','90be9a6e-27e6-45e5-94dc-ec1a09eeb510'),(54,'what do u mean','tapopadma','2018-04-18 09:35:18','6a4aa61f-9b51-428e-ad8e-5d330d75f3cf'),(55,'just kidding','somanaik','2018-04-18 09:35:43','8e679f87-1a36-48ba-88c4-f091262dbe43'),(56,'logging out','somanaik','2018-04-18 09:35:56','baaceaff-0ec7-437e-b09c-b1ca67a5c390'),(57,'thanks','tapopadma','2018-04-18 09:36:37','3a09a436-f19a-41a3-96c2-d7c419abe8c9'),(58,'logged in','somanaik','2018-04-18 09:43:10','326d1c47-0b4d-47c9-b49e-1e2bfc1103ed'),(59,'messaging myself','somanaik','2018-04-18 09:43:52','34d817ee-1120-49ce-a494-3d8595ccd8c1'),(60,'messaging myself','somanaik','2018-04-18 09:43:53','5ceea8a2-b2a9-4857-baeb-78557b5c31e1'),(61,'messaging myself','somanaik','2018-04-18 09:43:53','7264c1ae-7c8f-4384-a8fe-08d58e732e3a'),(62,'messaging myself','somanaik','2018-04-18 09:43:53','e7b271cc-6340-4f24-9f9c-4dec5261ba33'),(63,'fixed the issue','tapopadma','2018-04-18 09:46:05','374c42f1-0a8b-4061-91dd-ef67a8438f89'),(64,'do you see it ?','tapopadma','2018-04-18 09:46:17','0b6f4c13-2dd1-4aad-b66a-c4ca120b2312'),(65,'yup','somanaik','2018-04-18 09:46:50','b0b9713a-c124-4251-9ad4-2e3e58fe3b71'),(66,'it is fixed','somanaik','2018-04-18 09:46:56','77b191c3-5372-4815-86e4-bf0dea15d480'),(67,'msg myself','somanaik','2018-04-18 09:47:18','6108332e-b076-42b7-9702-56f840a9e110'),(68,'msg myself','somanaik','2018-04-18 09:47:19','f065dfc6-918d-4d24-94d5-1983202c4518'),(69,'msg myself','somanaik','2018-04-18 09:47:19','31fac919-221d-4272-a182-62a43cda36e0'),(70,'msg myself','somanaik','2018-04-18 09:47:56','7365f7a2-5055-4233-9927-f93bb5373fd5'),(71,'msg myself','somanaik','2018-04-18 09:47:56','3a731ccc-7d86-47cb-8365-775547f054da'),(72,'ok','tapopadma','2018-04-18 09:48:22','5cc4b342-c89b-48ff-b6b1-bc50aa50dec1'),(73,'hello','k','2018-04-18 09:48:57','a26a3170-918f-4f42-a3ed-c603e2252b4a'),(74,'hi','tapopadma','2018-04-18 09:49:08','bc168b64-914b-4526-a915-f69a7e5c0f64'),(75,'i can select my self from the user list\nbut when i try to message it does not send the msg.\nin future, either remove the current user from the list or they should be able to msg themselves.','somanaik','2018-04-18 09:49:31','d939eea2-db65-4378-a9ba-b9de64a58de8'),(76,'hye','tapopadma','2018-04-18 09:56:00','40a4ca0f-b80f-4855-b388-9e0ede8e9b47'),(77,'testing self msg','somanaik','2018-04-18 09:56:19','4333965e-597a-46b6-8893-ca38aabdd2f6'),(78,'testing self msg','somanaik','2018-04-18 09:56:57','a56e6952-3376-49a0-9375-b63d1ede914e'),(79,'hi','k','2018-04-18 09:57:10','7a5ef66e-e3ec-420a-9fa3-d30a768ab565'),(80,'hmm','tapopadma','2018-04-18 09:57:15','34716db7-cd8c-46e9-8de7-065a62aa75fa'),(81,'hi','yyyy','2018-04-18 10:01:09','2b8b07f5-9437-4923-ae67-a16afcc46b6e'),(82,'hi yenita','tapopadma','2018-04-18 10:01:13','4d236c52-2df6-4e6b-a498-ab6a2ae0c6ee'),(83,':)','yyyy','2018-04-18 10:01:28','86279ac4-794e-400c-ae14-0e9b748932ca'),(84,'xD','tapopadma','2018-04-18 10:01:34','118f5621-dcdc-4c8c-acbe-a11dddf9f5f4'),(85,'q\nq\nq\nq\nq\nq\nq\nq\nq\nq\nq\nq','yyyy','2018-04-18 10:01:50','c9cce2ac-7203-4211-a544-1bf1eac9ec6b'),(86,'q\nq\nq\nq\nq\nq\nq\nq','yyyy','2018-04-18 10:01:59','427905bb-ecfd-4379-b41a-41c2f34c3b09'),(87,'q\nq\nq\nq\nq\nq\nq\nq','yyyy','2018-04-18 10:02:01','0c7a4c11-fa7c-4290-a65d-d786460f97d9'),(88,'q\nq\nq\nq\nq\nq\nq\nq','yyyy','2018-04-18 10:02:34','cca6ae22-bbbf-46c2-9c81-6fee1eb0ce9d'),(89,'bye','tapopadma','2018-04-18 10:02:41','91ba0bb6-b350-4f47-ae76-7258e1efcc65'),(90,'bey','tapopadma','2018-04-19 08:07:21','eff20590-5406-4f14-b364-4db165019c47'),(91,'kya','somanaik','2018-04-19 08:07:26','ac3254ff-e171-441b-a1f0-9ca23e523587'),(92,'chup','tapopadma','2018-04-19 08:07:32','68282c4f-bf2a-41aa-a962-c4b27d028564'),(93,'bhak','somanaik','2018-04-19 08:07:36','cc1a8977-63dc-4ebe-add5-b6e6bd0e5523'),(94,'bsdk','tapopadma','2018-04-19 08:07:42','bf01a396-ccce-4f36-aa3a-312f491c5de5'),(95,':(','somanaik','2018-04-19 08:07:49','9e1d43d7-89a9-4bd9-9aa1-687dc6d2fe60'),(96,'hello there!!!','tapopadma','2018-04-20 07:41:49','ad4d11cf-8fae-4195-8a0c-53650d224c7d'),(97,'hi there','tapopadma','2018-04-20 08:08:22','b2cbb917-0cc2-4b5f-b398-8f20478fa4d7'),(98,'hello there','tapopadma','2018-04-20 08:13:11','6656e8ee-7eb6-4b9f-a840-2bba2bff4c8e'),(99,'whatever','tapopadma','2018-04-20 08:14:23','31dedef4-07b3-43e5-ac3b-32c01e11ad65'),(100,'hey','tapopadma','2018-04-20 08:16:07','ee00a594-abaa-40fe-853e-6de93fc95dc5'),(101,'wow dude','somanaik','2018-04-20 08:16:20','4ff964f7-fb0c-44d8-b1c7-43cb3fd3b237'),(102,'awesome','tapopadma','2018-04-20 08:16:29','3dfeaf3b-3511-4085-89fe-bfa03cd377b7'),(103,'it\'s done','somanaik','2018-04-20 08:16:36','dd038561-5ab6-4342-8491-25ad2ae7df12'),(104,'Yo','chandu','2018-04-20 08:30:41','59049cca-aa19-4e5b-be2f-807baef8b245'),(105,'yo dude','tapopadma','2018-04-20 08:31:00','e044ed08-f352-4681-9a64-67d6f1125c84'),(106,'welcome to chat me','tapopadma','2018-04-20 08:31:04','45f9b610-565c-4c84-a5a0-03a99fa7fa85'),(107,'Bye','chandu','2018-04-20 08:31:05','d4a76167-404f-4096-af85-c7d6c9970948'),(108,'wait','tapopadma','2018-04-20 08:31:10','d9941554-32f3-4a26-96d9-3886a425224c'),(109,'Nah nikal raha bhai','chandu','2018-04-20 08:31:17','e17caeba-5c57-4aa3-9d8b-3f35032c9386'),(110,'Ye kya hai','chandu','2018-04-20 08:31:24','428414a8-d2c0-4581-a24f-c0f57ef5d785'),(111,'kya','tapopadma','2018-04-20 08:31:25','fb07b217-2fdb-49f7-a08f-5da8bc339d04'),(112,'msg was shown two times','chandu','2018-04-20 08:31:38','faeab54b-dfa6-447c-bdd7-7ee0d2d20528'),(113,'see','chandu','2018-04-20 08:31:43','f431b40f-8e4b-4ae0-9139-9023c8d11c07'),(114,'Something wrong','chandu','2018-04-20 08:31:49','52d67faa-5903-4e6a-8c43-4c9d4f316765'),(115,'shit','tapopadma','2018-04-20 08:31:52','889f34a7-87dd-4a8d-899f-f5f29e21ca67'),(116,'lemme fix it','tapopadma','2018-04-20 08:32:01','2a0717de-2b62-4af1-85e7-e738675de520'),(117,'do bar','chandu','2018-04-20 08:32:41','2d96697a-4fef-4a80-9cfd-8acb3bb7db02'),(118,'nhi ana chahiy\\e','chandu','2018-04-20 08:33:05','ac93e4d4-bddb-4d0a-a622-237ba9339b26'),(119,'chutiye','tapopadma','2018-04-20 08:33:47','73c8edc1-2aec-472c-a554-9487e3a253e3'),(120,'gaand maraale :p','tapopadma','2018-04-20 08:33:55','de456091-0d26-43bb-810b-bfcdd91c4c40'),(121,'abe launde kuch kaam nhi hai kya','chandu','2018-04-20 08:34:03','e9a3024f-9741-4fd1-b7f3-b712d3cdd7f5'),(122,'free ka app he','tapopadma','2018-04-20 08:34:06','0a86431e-9dd2-4b91-8682-3d901de08bcc'),(123,'paisa bhej','tapopadma','2018-04-20 08:34:25','b107ac28-82ff-490a-bb16-0efba0204e14'),(124,'100$ ye le','chandu','2018-04-20 08:34:41','40faa0cf-207d-446a-814b-299ce1ff0f61'),(125,'phir tere request keliye ek patch banaunga','tapopadma','2018-04-20 08:34:44','f6ff943a-f003-4e69-a7b8-83de709a8381'),(126,'aur chahiye kya','chandu','2018-04-20 08:34:50','151d6fe6-fa42-4c61-a265-632e83686a48'),(127,'just kidding :D','tapopadma','2018-04-20 08:34:57','b551d6cb-87ed-4107-a52d-76553a2a3b55'),(128,'ye le blank kitna chahiye likhle','chandu','2018-04-20 08:35:02','9fc7cb1b-dfd2-4e22-9ff7-68d621bd1629'),(129,'thanks bro for your time','tapopadma','2018-04-20 08:35:05','1fefe48d-cf8c-478f-9265-f825301e7bb7'),(130,'bye xD','tapopadma','2018-04-20 08:35:16','6ecd7213-17e0-40e8-8d6d-acf4e6eb3956'),(131,'yo','chandu','2018-04-20 08:35:59','2160861a-882c-40e3-8ed8-85fbffef6a5a'),(132,'Hello','jingfei','2018-04-20 08:43:35','914d2986-45fe-4075-aafe-f0ea1efe8d08'),(133,'hi jingfei','tapopadma','2018-04-20 08:43:51','38090629-f45c-4bc4-8d37-282561ffe7ba'),(134,'welcome to chat me','tapopadma','2018-04-20 08:43:57','03c01f90-709f-498c-834c-da0a6b4ecbe5'),(135,'lol','jingfei','2018-04-20 08:44:03','79bcf963-64ea-451a-b8d1-e5eca1737bb1'),(136,':p','tapopadma','2018-04-20 08:44:04','a9239416-93ca-4521-978f-cd2836568ae6'),(137,'\"ya\"','jingfei','2018-04-20 08:44:34','d6ff7f3f-cdec-47de-9a87-b6edbdde7477'),(138,'<br/>','jingfei','2018-04-20 08:44:37','194d5e6a-7a33-407f-9ec8-997aa7b0aa58'),(139,'We at chat me take customer\'s experience very seriously','tapopadma','2018-04-20 08:44:42','bfb0e832-f020-415a-ae13-685e35285e1d'),(140,'</td><td>','jingfei','2018-04-20 08:44:53','bc7324af-b43e-4603-bd4f-7d302dfc7778'),(141,'<script>alert(\"ya\");</script>','jingfei','2018-04-20 08:45:07','df388eed-e22e-45e2-a538-ce6dfaf8b3da'),(142,'<ya>','jingfei','2018-04-20 08:45:16','5d0a65ec-5ec5-44ce-84f6-595160689b10'),(143,'hello','jingfei','2018-04-20 08:45:25','c0ff3d36-6aa4-4856-9747-f702b5f44fc1'),(144,'bye','tapopadma','2018-04-20 08:46:10','6a9e55f8-c56d-4193-a51f-b9c7cb952b90'),(145,'bye','jingfei','2018-04-20 08:46:15','e8ed76b9-7d64-47df-8764-117030e4bddd'),(146,'thanks for you time','tapopadma','2018-04-20 08:46:16','cdb65c55-665c-4f72-888c-15ebe883f496'),(147,'haha','jingfei','2018-04-20 08:46:24','d807e474-3c31-48cf-84cd-0390501781ae'),(148,'<?','jingfei','2018-04-20 08:46:43','18c64be6-83ff-4220-826c-69c27fa05440');
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
) ENGINE=InnoDB AUTO_INCREMENT=287 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_users_trn`
--

LOCK TABLES `message_users_trn` WRITE;
/*!40000 ALTER TABLE `message_users_trn` DISABLE KEYS */;
INSERT INTO `message_users_trn` VALUES (258,'03c01f90-709f-498c-834c-da0a6b4ecbe5','jingfei'),(257,'03c01f90-709f-498c-834c-da0a6b4ecbe5','tapopadma'),(234,'0a86431e-9dd2-4b91-8682-3d901de08bcc','chandu'),(233,'0a86431e-9dd2-4b91-8682-3d901de08bcc','tapopadma'),(121,'0b6f4c13-2dd1-4aad-b66a-c4ca120b2312','somanaik'),(120,'0b6f4c13-2dd1-4aad-b66a-c4ca120b2312','tapopadma'),(160,'118f5621-dcdc-4c8c-acbe-a11dddf9f5f4','tapopadma'),(161,'118f5621-dcdc-4c8c-acbe-a11dddf9f5f4','yyyy'),(28,'12e9fa19-8879-4553-9112-79a84dfcae35','k'),(29,'12e9fa19-8879-4553-9112-79a84dfcae35','tapopadma'),(241,'151d6fe6-fa42-4c61-a265-632e83686a48','chandu'),(242,'151d6fe6-fa42-4c61-a265-632e83686a48','tapopadma'),(285,'18c64be6-83ff-4220-826c-69c27fa05440','jingfei'),(286,'18c64be6-83ff-4220-826c-69c27fa05440','tapopadma'),(265,'194d5e6a-7a33-407f-9ec8-997aa7b0aa58','jingfei'),(266,'194d5e6a-7a33-407f-9ec8-997aa7b0aa58','tapopadma'),(36,'19d9cfff-ce10-4ff1-befc-0155f9f9d915','obama'),(37,'19d9cfff-ce10-4ff1-befc-0155f9f9d915','tapopadma'),(64,'1aa9d4e7-ef47-487e-9b94-00dc3aa093f5','k'),(65,'1aa9d4e7-ef47-487e-9b94-00dc3aa093f5','tapopadma'),(60,'1bbdc6f7-b89e-4eba-a956-8cb331fa1ff8','k'),(61,'1bbdc6f7-b89e-4eba-a956-8cb331fa1ff8','tapopadma'),(248,'1fefe48d-cf8c-478f-9265-f825301e7bb7','chandu'),(247,'1fefe48d-cf8c-478f-9265-f825301e7bb7','tapopadma'),(251,'2160861a-882c-40e3-8ed8-85fbffef6a5a','chandu'),(252,'2160861a-882c-40e3-8ed8-85fbffef6a5a','tapopadma'),(66,'23c84267-9b63-4fa7-9828-e996e0e89e7e','k'),(67,'23c84267-9b63-4fa7-9828-e996e0e89e7e','tapopadma'),(95,'28b07114-7699-4c88-9a0f-4bff4a183ca4','somanaik'),(94,'28b07114-7699-4c88-9a0f-4bff4a183ca4','tapopadma'),(75,'2959e814-ec89-4bb6-ab28-6b2a041ac9ca','k'),(74,'2959e814-ec89-4bb6-ab28-6b2a041ac9ca','tapopadma'),(222,'2a0717de-2b62-4af1-85e7-e738675de520','chandu'),(221,'2a0717de-2b62-4af1-85e7-e738675de520','tapopadma'),(155,'2b8b07f5-9437-4923-ae67-a16afcc46b6e','tapopadma'),(154,'2b8b07f5-9437-4923-ae67-a16afcc46b6e','yyyy'),(79,'2be3e5cd-4c8a-4f95-82c6-db31930a0da8','somanaik'),(78,'2be3e5cd-4c8a-4f95-82c6-db31930a0da8','tapopadma'),(223,'2d96697a-4fef-4a80-9cfd-8acb3bb7db02','chandu'),(224,'2d96697a-4fef-4a80-9cfd-8acb3bb7db02','tapopadma'),(8,'31872f6a-a9d9-451a-a7b5-467e0058fbcb','obama'),(9,'31872f6a-a9d9-451a-a7b5-467e0058fbcb','tapopadma'),(188,'31dedef4-07b3-43e5-ac3b-32c01e11ad65','somanaik'),(187,'31dedef4-07b3-43e5-ac3b-32c01e11ad65','tapopadma'),(130,'31fac919-221d-4272-a182-62a43cda36e0','somanaik'),(108,'326d1c47-0b4d-47c9-b49e-1e2bfc1103ed','somanaik'),(109,'326d1c47-0b4d-47c9-b49e-1e2bfc1103ed','tapopadma'),(153,'34716db7-cd8c-46e9-8de7-065a62aa75fa','k'),(152,'34716db7-cd8c-46e9-8de7-065a62aa75fa','tapopadma'),(110,'34d817ee-1120-49ce-a494-3d8595ccd8c1','somanaik'),(119,'374c42f1-0a8b-4061-91dd-ef67a8438f89','somanaik'),(118,'374c42f1-0a8b-4061-91dd-ef67a8438f89','tapopadma'),(256,'38090629-f45c-4bc4-8d37-282561ffe7ba','jingfei'),(255,'38090629-f45c-4bc4-8d37-282561ffe7ba','tapopadma'),(107,'3a09a436-f19a-41a3-96c2-d7c419abe8c9','somanaik'),(106,'3a09a436-f19a-41a3-96c2-d7c419abe8c9','tapopadma'),(134,'3a731ccc-7d86-47cb-8365-775547f054da','somanaik'),(194,'3dfeaf3b-3511-4085-89fe-bfa03cd377b7','somanaik'),(193,'3dfeaf3b-3511-4085-89fe-bfa03cd377b7','tapopadma'),(145,'40a4ca0f-b80f-4855-b388-9e0ede8e9b47','k'),(144,'40a4ca0f-b80f-4855-b388-9e0ede8e9b47','tapopadma'),(237,'40faa0cf-207d-446a-814b-299ce1ff0f61','chandu'),(238,'40faa0cf-207d-446a-814b-299ce1ff0f61','tapopadma'),(164,'427905bb-ecfd-4379-b41a-41c2f34c3b09','yyyy'),(90,'4279ea2e-c923-47e4-9359-610a9b623384','somanaik'),(91,'4279ea2e-c923-47e4-9359-610a9b623384','tapopadma'),(209,'428414a8-d2c0-4581-a24f-c0f57ef5d785','chandu'),(210,'428414a8-d2c0-4581-a24f-c0f57ef5d785','tapopadma'),(146,'4333965e-597a-46b6-8893-ca38aabdd2f6','somanaik'),(202,'45f9b610-565c-4c84-a5a0-03a99fa7fa85','chandu'),(201,'45f9b610-565c-4c84-a5a0-03a99fa7fa85','tapopadma'),(156,'4d236c52-2df6-4e6b-a498-ab6a2ae0c6ee','tapopadma'),(157,'4d236c52-2df6-4e6b-a498-ab6a2ae0c6ee','yyyy'),(191,'4ff964f7-fb0c-44d8-b1c7-43cb3fd3b237','somanaik'),(192,'4ff964f7-fb0c-44d8-b1c7-43cb3fd3b237','tapopadma'),(12,'517f9413-aa42-4e54-8d36-faf292fade48','k'),(13,'517f9413-aa42-4e54-8d36-faf292fade48','tapopadma'),(217,'52d67faa-5903-4e6a-8c43-4c9d4f316765','chandu'),(218,'52d67faa-5903-4e6a-8c43-4c9d4f316765','tapopadma'),(197,'59049cca-aa19-4e5b-be2f-807baef8b245','chandu'),(198,'59049cca-aa19-4e5b-be2f-807baef8b245','tapopadma'),(10,'5b586b86-717e-40aa-b29f-3e33a922930b','k'),(11,'5b586b86-717e-40aa-b29f-3e33a922930b','tapopadma'),(137,'5cc4b342-c89b-48ff-b6b1-bc50aa50dec1','somanaik'),(136,'5cc4b342-c89b-48ff-b6b1-bc50aa50dec1','tapopadma'),(112,'5ceea8a2-b2a9-4857-baeb-78557b5c31e1','somanaik'),(273,'5d0a65ec-5ec5-44ce-84f6-595160689b10','jingfei'),(274,'5d0a65ec-5ec5-44ce-84f6-595160689b10','tapopadma'),(22,'5d0b904e-3b97-44ae-bd7d-e51fbde131c0','k'),(23,'5d0b904e-3b97-44ae-bd7d-e51fbde131c0','tapopadma'),(126,'6108332e-b076-42b7-9702-56f840a9e110','somanaik'),(83,'66387876-b887-4ca2-a54d-c7f3b9c78f8e','somanaik'),(82,'66387876-b887-4ca2-a54d-c7f3b9c78f8e','tapopadma'),(186,'6656e8ee-7eb6-4b9f-a840-2bba2bff4c8e','somanaik'),(185,'6656e8ee-7eb6-4b9f-a840-2bba2bff4c8e','tapopadma'),(89,'66613a51-6d9e-472b-8be5-0da898a4b0cb','somanaik'),(88,'66613a51-6d9e-472b-8be5-0da898a4b0cb','tapopadma'),(174,'68282c4f-bf2a-41aa-a962-c4b27d028564','somanaik'),(173,'68282c4f-bf2a-41aa-a962-c4b27d028564','tapopadma'),(101,'6a4aa61f-9b51-428e-ad8e-5d330d75f3cf','somanaik'),(100,'6a4aa61f-9b51-428e-ad8e-5d330d75f3cf','tapopadma'),(278,'6a9e55f8-c56d-4193-a51f-b9c7cb952b90','jingfei'),(277,'6a9e55f8-c56d-4193-a51f-b9c7cb952b90','tapopadma'),(250,'6ecd7213-17e0-40e8-8d6d-acf4e6eb3956','chandu'),(249,'6ecd7213-17e0-40e8-8d6d-acf4e6eb3956','tapopadma'),(114,'7264c1ae-7c8f-4384-a8fe-08d58e732e3a','somanaik'),(132,'7365f7a2-5055-4233-9927-f93bb5373fd5','somanaik'),(228,'73c8edc1-2aec-472c-a554-9487e3a253e3','chandu'),(227,'73c8edc1-2aec-472c-a554-9487e3a253e3','tapopadma'),(62,'75fec15e-6179-4c5f-82c6-a821834594c1','k'),(63,'75fec15e-6179-4c5f-82c6-a821834594c1','tapopadma'),(124,'77b191c3-5372-4815-86e4-bf0dea15d480','somanaik'),(125,'77b191c3-5372-4815-86e4-bf0dea15d480','tapopadma'),(259,'79bcf963-64ea-451a-b8d1-e5eca1737bb1','jingfei'),(260,'79bcf963-64ea-451a-b8d1-e5eca1737bb1','tapopadma'),(150,'7a5ef66e-e3ec-420a-9fa3-d30a768ab565','k'),(151,'7a5ef66e-e3ec-420a-9fa3-d30a768ab565','tapopadma'),(54,'8306d700-f798-4b70-8ac0-4dce379714c1','k'),(55,'8306d700-f798-4b70-8ac0-4dce379714c1','tapopadma'),(80,'85afee20-5c3e-4343-89ba-8fcaf8af6b2d','somanaik'),(81,'85afee20-5c3e-4343-89ba-8fcaf8af6b2d','tapopadma'),(159,'86279ac4-794e-400c-ae14-0e9b748932ca','tapopadma'),(158,'86279ac4-794e-400c-ae14-0e9b748932ca','yyyy'),(220,'889f34a7-87dd-4a8d-899f-f5f29e21ca67','chandu'),(219,'889f34a7-87dd-4a8d-899f-f5f29e21ca67','tapopadma'),(20,'8a098aab-cbe4-4a2e-8ba3-e5d024bf7004','k'),(21,'8a098aab-cbe4-4a2e-8ba3-e5d024bf7004','tapopadma'),(34,'8d36d1c6-f4ba-47d2-889b-bf1473a6807c','obama'),(35,'8d36d1c6-f4ba-47d2-889b-bf1473a6807c','tapopadma'),(102,'8e679f87-1a36-48ba-88c4-f091262dbe43','somanaik'),(103,'8e679f87-1a36-48ba-88c4-f091262dbe43','tapopadma'),(49,'8fd1ad7f-6acf-4ad6-98d2-d8b3a9ceebc0','k'),(48,'8fd1ad7f-6acf-4ad6-98d2-d8b3a9ceebc0','tapopadma'),(98,'90be9a6e-27e6-45e5-94dc-ec1a09eeb510','somanaik'),(99,'90be9a6e-27e6-45e5-94dc-ec1a09eeb510','tapopadma'),(76,'9124a750-31cb-4fc0-addd-efa8abd8ca47','k'),(77,'9124a750-31cb-4fc0-addd-efa8abd8ca47','tapopadma'),(253,'914d2986-45fe-4075-aafe-f0ea1efe8d08','jingfei'),(254,'914d2986-45fe-4075-aafe-f0ea1efe8d08','tapopadma'),(167,'91ba0bb6-b350-4f47-ae76-7258e1efcc65','tapopadma'),(168,'91ba0bb6-b350-4f47-ae76-7258e1efcc65','yyyy'),(93,'9642b5a3-76bd-4ac1-be1c-4f816cf2a0a6','somanaik'),(92,'9642b5a3-76bd-4ac1-be1c-4f816cf2a0a6','tapopadma'),(86,'97637332-c3b9-4fd8-9163-02da4d96412c','somanaik'),(87,'97637332-c3b9-4fd8-9163-02da4d96412c','tapopadma'),(179,'9e1d43d7-89a9-4bd9-9aa1-687dc6d2fe60','somanaik'),(180,'9e1d43d7-89a9-4bd9-9aa1-687dc6d2fe60','tapopadma'),(245,'9fc7cb1b-dfd2-4e22-9ff7-68d621bd1629','chandu'),(246,'9fc7cb1b-dfd2-4e22-9ff7-68d621bd1629','tapopadma'),(52,'9fef8b0b-7229-42f3-b926-3b36dd963258','k'),(53,'9fef8b0b-7229-42f3-b926-3b36dd963258','tapopadma'),(138,'a26a3170-918f-4f42-a3ed-c603e2252b4a','k'),(139,'a26a3170-918f-4f42-a3ed-c603e2252b4a','tapopadma'),(148,'a56e6952-3376-49a0-9375-b63d1ede914e','somanaik'),(16,'a892e850-9d1a-46a6-8285-e4c57718fd91','k'),(17,'a892e850-9d1a-46a6-8285-e4c57718fd91','tapopadma'),(262,'a9239416-93ca-4521-978f-cd2836568ae6','jingfei'),(261,'a9239416-93ca-4521-978f-cd2836568ae6','tapopadma'),(47,'a98b7b13-c0c7-4c13-be0a-6b780e98fc0a','k'),(46,'a98b7b13-c0c7-4c13-be0a-6b780e98fc0a','tapopadma'),(171,'ac3254ff-e171-441b-a1f0-9ca23e523587','somanaik'),(172,'ac3254ff-e171-441b-a1f0-9ca23e523587','tapopadma'),(225,'ac93e4d4-bddb-4d0a-a622-237ba9339b26','chandu'),(226,'ac93e4d4-bddb-4d0a-a622-237ba9339b26','tapopadma'),(182,'ad4d11cf-8fae-4195-8a0c-53650d224c7d','somanaik'),(181,'ad4d11cf-8fae-4195-8a0c-53650d224c7d','tapopadma'),(45,'adee685f-d58d-49a4-af7f-0e8f21920b48','obama'),(44,'adee685f-d58d-49a4-af7f-0e8f21920b48','tapopadma'),(122,'b0b9713a-c124-4251-9ad4-2e3e58fe3b71','somanaik'),(123,'b0b9713a-c124-4251-9ad4-2e3e58fe3b71','tapopadma'),(236,'b107ac28-82ff-490a-bb16-0efba0204e14','chandu'),(235,'b107ac28-82ff-490a-bb16-0efba0204e14','tapopadma'),(43,'b2069334-2af2-4efa-85ce-f69914002deb','obama'),(42,'b2069334-2af2-4efa-85ce-f69914002deb','tapopadma'),(184,'b2cbb917-0cc2-4b5f-b398-8f20478fa4d7','somanaik'),(183,'b2cbb917-0cc2-4b5f-b398-8f20478fa4d7','tapopadma'),(56,'b4b5e8f8-0727-4bd1-a0f5-8f8b719b9cb0','k'),(57,'b4b5e8f8-0727-4bd1-a0f5-8f8b719b9cb0','tapopadma'),(244,'b551d6cb-87ed-4107-a52d-76553a2a3b55','chandu'),(243,'b551d6cb-87ed-4107-a52d-76553a2a3b55','tapopadma'),(24,'b5c16df7-42c6-41d1-bc2b-146095611309','k'),(25,'b5c16df7-42c6-41d1-bc2b-146095611309','tapopadma'),(73,'b7a2f337-aa8a-41e3-a9e9-8a2b6e067a3f','k'),(72,'b7a2f337-aa8a-41e3-a9e9-8a2b6e067a3f','tapopadma'),(104,'baaceaff-0ec7-437e-b09c-b1ca67a5c390','somanaik'),(105,'baaceaff-0ec7-437e-b09c-b1ca67a5c390','tapopadma'),(141,'bc168b64-914b-4526-a915-f69a7e5c0f64','k'),(140,'bc168b64-914b-4526-a915-f69a7e5c0f64','tapopadma'),(269,'bc7324af-b43e-4603-bd4f-7d302dfc7778','jingfei'),(270,'bc7324af-b43e-4603-bd4f-7d302dfc7778','tapopadma'),(178,'bf01a396-ccce-4f36-aa3a-312f491c5de5','somanaik'),(177,'bf01a396-ccce-4f36-aa3a-312f491c5de5','tapopadma'),(268,'bfb0e832-f020-415a-ae13-685e35285e1d','jingfei'),(267,'bfb0e832-f020-415a-ae13-685e35285e1d','tapopadma'),(275,'c0ff3d36-6aa4-4856-9747-f702b5f44fc1','jingfei'),(276,'c0ff3d36-6aa4-4856-9747-f702b5f44fc1','tapopadma'),(26,'c2ff0867-3931-4ae1-b2cd-bc07bcc7403a','k'),(27,'c2ff0867-3931-4ae1-b2cd-bc07bcc7403a','tapopadma'),(163,'c9cce2ac-7203-4211-a544-1bf1eac9ec6b','tapopadma'),(162,'c9cce2ac-7203-4211-a544-1bf1eac9ec6b','yyyy'),(30,'cc148171-5896-403a-8ee6-dda37b5cb8f0','k'),(31,'cc148171-5896-403a-8ee6-dda37b5cb8f0','tapopadma'),(175,'cc1a8977-63dc-4ebe-add5-b6e6bd0e5523','somanaik'),(176,'cc1a8977-63dc-4ebe-add5-b6e6bd0e5523','tapopadma'),(166,'cca6ae22-bbbf-46c2-9c81-6fee1eb0ce9d','tapopadma'),(165,'cca6ae22-bbbf-46c2-9c81-6fee1eb0ce9d','yyyy'),(282,'cdb65c55-665c-4f72-888c-15ebe883f496','jingfei'),(281,'cdb65c55-665c-4f72-888c-15ebe883f496','tapopadma'),(85,'cfe3bd12-7dea-4dc0-8c34-0870c16bd96d','somanaik'),(84,'cfe3bd12-7dea-4dc0-8c34-0870c16bd96d','tapopadma'),(203,'d4a76167-404f-4096-af85-c7d6c9970948','chandu'),(204,'d4a76167-404f-4096-af85-c7d6c9970948','tapopadma'),(263,'d6ff7f3f-cdec-47de-9a87-b6edbdde7477','jingfei'),(264,'d6ff7f3f-cdec-47de-9a87-b6edbdde7477','tapopadma'),(283,'d807e474-3c31-48cf-84cd-0390501781ae','jingfei'),(284,'d807e474-3c31-48cf-84cd-0390501781ae','tapopadma'),(96,'d84c5219-cf46-452e-b9b2-62f0f16c49d8','somanaik'),(97,'d84c5219-cf46-452e-b9b2-62f0f16c49d8','tapopadma'),(142,'d939eea2-db65-4378-a9ba-b9de64a58de8','somanaik'),(143,'d939eea2-db65-4378-a9ba-b9de64a58de8','tapopadma'),(206,'d9941554-32f3-4a26-96d9-3886a425224c','chandu'),(205,'d9941554-32f3-4a26-96d9-3886a425224c','tapopadma'),(195,'dd038561-5ab6-4342-8491-25ad2ae7df12','somanaik'),(196,'dd038561-5ab6-4342-8491-25ad2ae7df12','tapopadma'),(68,'de27d25c-5659-4066-b36a-a599461beb32','k'),(69,'de27d25c-5659-4066-b36a-a599461beb32','tapopadma'),(230,'de456091-0d26-43bb-810b-bfcdd91c4c40','chandu'),(229,'de456091-0d26-43bb-810b-bfcdd91c4c40','tapopadma'),(14,'de7b51e3-6779-4461-8b29-908dacfabcd2','k'),(15,'de7b51e3-6779-4461-8b29-908dacfabcd2','tapopadma'),(271,'df388eed-e22e-45e2-a538-ce6dfaf8b3da','jingfei'),(272,'df388eed-e22e-45e2-a538-ce6dfaf8b3da','tapopadma'),(200,'e044ed08-f352-4681-9a64-67d6f1125c84','chandu'),(199,'e044ed08-f352-4681-9a64-67d6f1125c84','tapopadma'),(207,'e17caeba-5c57-4aa3-9d8b-3f35032c9386','chandu'),(208,'e17caeba-5c57-4aa3-9d8b-3f35032c9386','tapopadma'),(116,'e7b271cc-6340-4f24-9f9c-4dec5261ba33','somanaik'),(279,'e8ed76b9-7d64-47df-8764-117030e4bddd','jingfei'),(280,'e8ed76b9-7d64-47df-8764-117030e4bddd','tapopadma'),(41,'e988d803-c12c-4efb-9777-928f0927817f','fff'),(40,'e988d803-c12c-4efb-9777-928f0927817f','tapopadma'),(231,'e9a3024f-9741-4fd1-b7f3-b712d3cdd7f5','chandu'),(232,'e9a3024f-9741-4fd1-b7f3-b712d3cdd7f5','tapopadma'),(18,'eaf54794-9e80-431e-b568-707c32e5646e','k'),(19,'eaf54794-9e80-431e-b568-707c32e5646e','tapopadma'),(32,'ed807772-29ce-4a38-a791-a2a89e7d423f','k'),(33,'ed807772-29ce-4a38-a791-a2a89e7d423f','tapopadma'),(190,'ee00a594-abaa-40fe-853e-6de93fc95dc5','somanaik'),(189,'ee00a594-abaa-40fe-853e-6de93fc95dc5','tapopadma'),(58,'ee98b8d3-e5a0-40bf-93c7-50efc869fcd0','k'),(59,'ee98b8d3-e5a0-40bf-93c7-50efc869fcd0','tapopadma'),(170,'eff20590-5406-4f14-b364-4db165019c47','somanaik'),(169,'eff20590-5406-4f14-b364-4db165019c47','tapopadma'),(128,'f065dfc6-918d-4d24-94d5-1983202c4518','somanaik'),(70,'f2186bad-8c38-463a-aaf5-f0a6f84b974f','k'),(71,'f2186bad-8c38-463a-aaf5-f0a6f84b974f','tapopadma'),(215,'f431b40f-8e4b-4ae0-9139-9023c8d11c07','chandu'),(216,'f431b40f-8e4b-4ae0-9139-9023c8d11c07','tapopadma'),(240,'f6ff943a-f003-4e69-a7b8-83de709a8381','chandu'),(239,'f6ff943a-f003-4e69-a7b8-83de709a8381','tapopadma'),(213,'faeab54b-dfa6-447c-bdd7-7ee0d2d20528','chandu'),(214,'faeab54b-dfa6-447c-bdd7-7ee0d2d20528','tapopadma'),(212,'fb07b217-2fdb-49f7-a08f-5da8bc339d04','chandu'),(211,'fb07b217-2fdb-49f7-a08f-5da8bc339d04','tapopadma'),(51,'fbd5621f-b165-4606-8338-2700340f1e42','k'),(50,'fbd5621f-b165-4606-8338-2700340f1e42','tapopadma'),(39,'fc17cca5-2dfa-410d-91ae-5689ba670d7b','obama'),(38,'fc17cca5-2dfa-410d-91ae-5689ba670d7b','tapopadma');
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

-- Dump completed on 2018-04-20 16:47:37
