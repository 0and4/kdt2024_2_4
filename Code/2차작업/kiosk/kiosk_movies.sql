CREATE DATABASE  IF NOT EXISTS `kiosk` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `kiosk`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: kiosk
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `movies`
--

DROP TABLE IF EXISTS `movies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movies` (
  `mid` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `poster` text,
  `runtime` int DEFAULT NULL,
  `rating` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies`
--

LOCK TABLES `movies` WRITE;
/*!40000 ALTER TABLE `movies` DISABLE KEYS */;
INSERT INTO `movies` VALUES (1,'우리가 끝이야','드라마,멜로/로맨스','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',130,'15'),(3,'수유천','드라마','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',111,'15'),(5,'베테랑2','액션,범죄','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',118,'15'),(6,'행복의 나라','드라마','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',124,'12'),(7,'트랜스포머 ONE','애니메이션,액션,어드벤처','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',104,'all'),(8,'안녕, 할부지','다큐멘터리,애니메이션','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',95,'all'),(9,'브레드이발소: 빵스타의 탄생','애니메이션','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',77,'all'),(10,'1923 간토대학살','다큐멘터리','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',117,'12'),(12,'조커: 폴리 아 되','범죄,드라마,뮤지컬','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',138,'15'),(13,'와일드 로봇','애니메이션','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',102,'all'),(14,'필사의 추격','코미디,액션','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',109,'15'),(15,'아침바다 갈매기는','드라마,가족','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',113,'12'),(16,'비틀쥬스 비틀쥬스','코미디,판타지,공포(호러)','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',105,'12'),(20,'마녀들의 카니발','다큐멘터리','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',82,'12'),(24,'해야 할 일','드라마','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',100,'12'),(26,'캐시 아웃','액션,범죄','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',90,'12'),(27,'문경','드라마','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',111,'12'),(29,'[마이 아티 필름] 온앤오프 : 뷰티풀 뷰티풀','드라마','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',45,'all'),(30,'빅토리','드라마','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',120,'12'),(31,'탈주','액션','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',94,'12'),(32,'더 라이언: 사막의 생존자들','액션,스릴러','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',86,'15'),(34,'에이리언: 로물루스','공포(호러),SF','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',119,'15'),(37,'쥬라기캅스 극장판: 전설의 고대생물을 찾아라','애니메이션','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',75,'all'),(38,'그리고 목련이 필때면','다큐멘터리','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',84,'12'),(40,'트위스터스','액션,어드벤처,드라마','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',112,'12'),(41,'베베핀 플레이타임','애니메이션','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',61,'all'),(43,'자유로운 사람은 혼자 남는다','미스터리,판타지','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',107,'15'),(45,'하이재킹','범죄,액션','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',100,'12'),(48,'조선인 여공의 노래','다큐멘터리','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',83,'all'),(49,'사랑의 하츄핑','애니메이션','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',86,'all'),(50,'데드풀과 울버린','액션,코미디,SF','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',128,'19'),(51,'땅에 쓰는 시','다큐멘터리','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',113,'all'),(52,'헬로카봇 올스타 스페셜','애니메이션','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',67,'all'),(54,'공즉시색 3','멜로/로맨스,코미디','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',72,'19'),(55,'슈퍼배드 4','애니메이션,액션,어드벤처,코미디','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',94,'all'),(56,'더 납작 엎드릴게요','드라마','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',63,'all'),(57,'노무현과 바보들: 못다한 이야기','다큐멘터리','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',98,'12'),(58,'플라이 미 투 더 문','멜로/로맨스,코미디','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',132,'12'),(59,'막걸리가 알려줄거야 ','드라마,SF,코미디','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',91,'all'),(60,'정직한 사람들','드라마','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',78,'12'),(61,'다큐 황은정 : 스마트폰이 뭐길래','드라마','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',70,'12'),(63,'목화솜 피는 날','드라마','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',90,'12'),(64,'댓글부대','범죄,드라마','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',109,'15'),(65,'도토리','드라마','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',74,'15'),(66,'인사이드 아웃 2','애니메이션','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',96,'all'),(67,'감독판 김일성의 아이들','다큐멘터리','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',88,'12'),(68,'판문점','다큐멘터리','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',82,'12'),(69,'몽키맨','액션,스릴러','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',121,'19'),(70,'대치동 스캔들','드라마,멜로/로맨스','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',109,'15'),(71,'원더랜드','드라마','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',113,'12'),(72,'양치기','드라마,스릴러','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',94,'15'),(73,'나쁜 녀석들: 라이드 오어 다이','액션,코미디','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',115,'15'),(75,'소풍','드라마','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',114,'12'),(77,'범죄도시4','액션,범죄','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',109,'15'),(78,'다시 김대중-함께 합시다','다큐멘터리','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',84,'12'),(79,'1980','드라마','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',99,'12'),(80,'이프: 상상의 친구','코미디,드라마,가족','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',104,'all'),(81,'퓨리오사: 매드맥스 사가','액션','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',148,'15'),(83,'스턴트맨','액션,코미디,멜로/로맨스','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',126,'15'),(85,'혹성탈출: 새로운 시대','액션,SF','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',145,'12'),(87,'챌린저스','드라마,멜로/로맨스','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',131,'15'),(88,'여행자의 필요','드라마','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',90,'12'),(89,'바람의 세월','다큐멘터리','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',104,'12'),(90,'아서','어드벤처,드라마','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',107,'12'),(91,'8인의 용의자들','드라마,미스터리,범죄','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',105,'15'),(92,'쿵푸팬더4','애니메이션,액션,코미디','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',94,'all'),(93,'유미의 세포들 더 무비','애니메이션','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',93,'all'),(94,'세월: 라이프 고즈 온','다큐멘터리','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',99,'all'),(95,'고질라 X 콩: 뉴 엠파이어','액션,어드벤처,SF','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',115,'12'),(96,'랜드 오브 배드','액션,전쟁','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',113,'15'),(98,'듄: 파트2','액션','C:/Users/HP/JAVA/kioskproject/src/fxml/image/movie.png',166,'12');
/*!40000 ALTER TABLE `movies` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-12 16:47:24
