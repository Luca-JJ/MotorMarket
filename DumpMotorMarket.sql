CREATE DATABASE  IF NOT EXISTS `ecommerce` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ecommerce`;
-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: ecommerce
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `accessori`
--

DROP TABLE IF EXISTS `accessori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accessori` (
  `IdAccessorio` int NOT NULL AUTO_INCREMENT,
  `NomeAccessorio` varchar(50) NOT NULL,
  PRIMARY KEY (`IdAccessorio`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accessori`
--

LOCK TABLES `accessori` WRITE;
/*!40000 ALTER TABLE `accessori` DISABLE KEYS */;
INSERT INTO `accessori` VALUES (1,'Sensori di Parcheggio'),(2,'Tetto Panoramico'),(3,'Sedili Riscaldabili'),(4,'Apple CarPlay'),(5,'Android Auto'),(6,'Fari LED Matrix'),(7,'Ruota di scorta'),(8,'Portabici');
/*!40000 ALTER TABLE `accessori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aggiunte`
--

DROP TABLE IF EXISTS `aggiunte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aggiunte` (
  `IdServizio` int NOT NULL,
  `IdOrdine` int NOT NULL,
  PRIMARY KEY (`IdServizio`,`IdOrdine`),
  KEY `FKAgg_ORD` (`IdOrdine`),
  CONSTRAINT `FKAgg_ORD` FOREIGN KEY (`IdOrdine`) REFERENCES `ordini` (`IdOrdine`),
  CONSTRAINT `FKAgg_SER` FOREIGN KEY (`IdServizio`) REFERENCES `servizi` (`IdServizio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aggiunte`
--

LOCK TABLES `aggiunte` WRITE;
/*!40000 ALTER TABLE `aggiunte` DISABLE KEYS */;
INSERT INTO `aggiunte` VALUES (2,1),(3,1),(1,2),(2,4),(2,19),(3,19),(2,20),(3,20),(3,21),(2,22),(2,23),(1,24);
/*!40000 ALTER TABLE `aggiunte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `annunci`
--

DROP TABLE IF EXISTS `annunci`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `annunci` (
  `idAnnuncio` int NOT NULL AUTO_INCREMENT,
  `IdAuto` int DEFAULT NULL,
  `IdPezzo` int DEFAULT NULL,
  `DataInserimento` date NOT NULL,
  `Stato` enum('Attivo','Disattivato','Venduto') NOT NULL,
  `Prezzo` decimal(9,2) NOT NULL,
  `TipoAnnuncio` enum('Auto','Pezzo') NOT NULL,
  `IdUtente` int NOT NULL,
  PRIMARY KEY (`idAnnuncio`),
  UNIQUE KEY `FKAuto_in_Vendita_ID` (`IdAuto`),
  UNIQUE KEY `FKR_ID` (`IdPezzo`),
  KEY `FKInserimento` (`IdUtente`),
  CONSTRAINT `FKAuto_in_Vendita_FK` FOREIGN KEY (`IdAuto`) REFERENCES `automobili` (`IdAuto`),
  CONSTRAINT `FKInserimento` FOREIGN KEY (`IdUtente`) REFERENCES `utenti` (`IdUtente`),
  CONSTRAINT `FKR_FK` FOREIGN KEY (`IdPezzo`) REFERENCES `pezzi_di_ricambio` (`IdPezzo`),
  CONSTRAINT `chk_esclusivita_annuncio` CHECK ((((`TipoAnnuncio` = _utf8mb4'Auto') and (`IdAuto` is not null) and (`IdPezzo` is null)) or ((`TipoAnnuncio` = _utf8mb4'Pezzo') and (`IdPezzo` is not null) and (`IdAuto` is null)))),
  CONSTRAINT `chk_prezzo_annuncio` CHECK ((`Prezzo` > 0.00))
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `annunci`
--

LOCK TABLES `annunci` WRITE;
/*!40000 ALTER TABLE `annunci` DISABLE KEYS */;
INSERT INTO `annunci` VALUES (1,1,NULL,'2026-05-01','Venduto',11900.00,'Auto',8),(2,2,NULL,'2026-05-10','Attivo',28500.00,'Auto',8),(3,3,NULL,'2026-05-12','Attivo',31000.00,'Auto',9),(4,4,NULL,'2026-05-15','Venduto',21500.00,'Auto',9),(5,5,NULL,'2026-05-18','Attivo',26900.00,'Auto',10),(6,6,NULL,'2026-05-20','Venduto',24500.00,'Auto',10),(7,8,NULL,'2026-06-01','Venduto',36500.00,'Auto',8),(8,NULL,1,'2026-04-20','Attivo',35.00,'Pezzo',9),(9,NULL,3,'2026-04-22','Attivo',180.00,'Pezzo',9),(10,NULL,6,'2026-05-02','Attivo',65.00,'Pezzo',8),(11,NULL,10,'2026-05-05','Attivo',110.00,'Pezzo',8),(12,NULL,14,'2026-05-10','Attivo',25.00,'Pezzo',10),(13,13,NULL,'2026-06-15','Disattivato',42000.00,'Auto',23),(14,14,NULL,'2026-06-18','Venduto',13500.00,'Auto',21),(15,NULL,17,'2026-06-10','Attivo',45.00,'Pezzo',22),(16,NULL,19,'2026-06-12','Attivo',29.99,'Pezzo',22),(17,7,NULL,'2026-07-09','Venduto',20000.00,'Auto',9),(18,NULL,20,'2026-07-09','Attivo',1000.00,'Pezzo',9),(21,10,NULL,'2026-07-16','Venduto',17500.00,'Auto',8),(22,NULL,21,'2026-07-16','Attivo',50.00,'Pezzo',8),(23,NULL,22,'2026-07-24','Attivo',65.00,'Pezzo',29),(24,9,NULL,'2026-07-24','Attivo',18600.00,'Auto',29),(25,15,NULL,'2026-07-24','Attivo',38000.00,'Auto',29);
/*!40000 ALTER TABLE `annunci` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `automobili`
--

DROP TABLE IF EXISTS `automobili`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `automobili` (
  `IdAuto` int NOT NULL AUTO_INCREMENT,
  `VIN` char(17) NOT NULL,
  `Targa` char(7) NOT NULL,
  `AnnoPrimaImmatricolazione` int NOT NULL,
  `Potenza` int NOT NULL,
  `TipoCambio` enum('Manuale','Automatico') NOT NULL,
  `Disponibilita` enum('Disponibile','NonDisponibile') NOT NULL DEFAULT 'Disponibile',
  `IdModello` int NOT NULL,
  `IdAlimentazione` int NOT NULL,
  `IdColoreInterni` int NOT NULL,
  `IdColoreEsterni` int NOT NULL,
  `IdUtente` int NOT NULL,
  PRIMARY KEY (`IdAuto`),
  UNIQUE KEY `IDAUTOMOBILE_1` (`VIN`),
  KEY `FKEsemplare` (`IdModello`),
  KEY `FKColore_Interni` (`IdColoreInterni`),
  KEY `FKColore_Esterni` (`IdColoreEsterni`),
  KEY `FKRegistrazione` (`IdUtente`),
  KEY `FKAlimentazione` (`IdAlimentazione`),
  CONSTRAINT `FKAlimentazione` FOREIGN KEY (`IdAlimentazione`) REFERENCES `tipi_alimentazione` (`IdAlimentazione`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKColore_Esterni` FOREIGN KEY (`IdColoreEsterni`) REFERENCES `colori` (`IdColore`),
  CONSTRAINT `FKColore_Interni` FOREIGN KEY (`IdColoreInterni`) REFERENCES `colori` (`IdColore`),
  CONSTRAINT `FKEsemplare` FOREIGN KEY (`IdModello`) REFERENCES `modelli` (`IdModello`),
  CONSTRAINT `FKRegistrazione` FOREIGN KEY (`IdUtente`) REFERENCES `utenti` (`IdUtente`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `automobili`
--

LOCK TABLES `automobili` WRITE;
/*!40000 ALTER TABLE `automobili` DISABLE KEYS */;
INSERT INTO `automobili` VALUES (1,'ZFA15300009876543','FZ456BB',2022,70,'Manuale','NonDisponibile',2,2,1,2,8),(2,'ZAR95200001122334','GH111AA',2020,190,'Automatico','Disponibile',5,2,1,5,9),(3,'ZAR96500004455667','GJ333CC',2023,160,'Automatico','Disponibile',7,4,1,3,10),(4,'WVWZZZCDZ11112222','GF888EE',2021,130,'Manuale','NonDisponibile',9,1,3,1,8),(5,'WBATY31010K123456','GK999ZZ',2019,190,'Automatico','Disponibile',15,2,1,3,9),(6,'WAUZZZ8V000999888','FX444DD',2018,150,'Automatico','NonDisponibile',18,4,3,4,10),(7,'W1K1770121J654321','GC222BB',2021,136,'Automatico','NonDisponibile',21,1,1,2,8),(8,'5YJYGDEF711223344','GL777YY',2024,299,'Automatico','NonDisponibile',26,3,2,1,10),(9,'WF00XXGJK00112233','GG555FF',2023,125,'Manuale','Disponibile',31,4,1,3,9),(10,'VNKKH3D3000554433','GA123XX',2022,116,'Automatico','NonDisponibile',33,4,3,2,8),(11,'SHHFL486000111222','GJ000AA',2023,184,'Automatico','Disponibile',37,4,1,4,10),(12,'SJNTAAT32U0987654','GB666HH',2022,140,'Manuale','Disponibile',41,4,1,1,9),(13,'W1K1770121J999999','HA111AA',2022,160,'Automatico','Disponibile',22,4,1,3,23),(14,'ZFA15300001111111','HA222BB',2021,70,'Manuale','NonDisponibile',1,1,3,5,21),(15,'5YJYGDEF711000000','HA333CC',2023,351,'Automatico','Disponibile',25,3,2,1,23),(18,'TTUZZZ8V000999888','DV765GG',2009,105,'Automatico','Disponibile',21,1,1,2,9),(19,'AAAA1111BBBB2222','GT777XX',2024,220,'Manuale','Disponibile',10,1,1,11,8),(20,'AA123BBB45767','GT999FF',2023,125,'Manuale','Disponibile',17,1,2,8,29);
/*!40000 ALTER TABLE `automobili` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colori`
--

DROP TABLE IF EXISTS `colori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `colori` (
  `IdColore` int NOT NULL AUTO_INCREMENT,
  `NomeColore` varchar(50) NOT NULL,
  PRIMARY KEY (`IdColore`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colori`
--

LOCK TABLES `colori` WRITE;
/*!40000 ALTER TABLE `colori` DISABLE KEYS */;
INSERT INTO `colori` VALUES (1,'Nero'),(2,'Bianco'),(3,'Grigio'),(4,'Blu'),(5,'Rosso'),(6,'Verde'),(7,'Giallo'),(8,'Arancione'),(9,'Viola'),(10,'Marrone'),(11,'Azzurro');
/*!40000 ALTER TABLE `colori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compatibilita`
--

DROP TABLE IF EXISTS `compatibilita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compatibilita` (
  `IdPezzo` int NOT NULL,
  `IdModello` int NOT NULL,
  PRIMARY KEY (`IdModello`,`IdPezzo`),
  KEY `FKCom_PEZ` (`IdPezzo`),
  CONSTRAINT `FKCom_MOD` FOREIGN KEY (`IdModello`) REFERENCES `modelli` (`IdModello`),
  CONSTRAINT `FKCom_PEZ` FOREIGN KEY (`IdPezzo`) REFERENCES `pezzi_di_ricambio` (`IdPezzo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compatibilita`
--

LOCK TABLES `compatibilita` WRITE;
/*!40000 ALTER TABLE `compatibilita` DISABLE KEYS */;
INSERT INTO `compatibilita` VALUES (1,1),(1,2),(1,3),(1,4),(2,2),(2,3),(3,9),(3,10),(6,5),(6,6),(7,9),(7,18),(7,19),(10,9),(10,12),(10,18),(14,29),(14,30),(16,25),(16,26),(16,44),(17,5),(17,6),(18,9),(18,10),(19,25),(19,26),(20,1),(20,2),(20,3),(21,6),(21,7),(21,8),(22,30);
/*!40000 ALTER TABLE `compatibilita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipaggiamenti`
--

DROP TABLE IF EXISTS `equipaggiamenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipaggiamenti` (
  `IdAuto` int NOT NULL,
  `IdAccessorio` int NOT NULL,
  PRIMARY KEY (`IdAccessorio`,`IdAuto`),
  KEY `FKEqu_AUT` (`IdAuto`),
  CONSTRAINT `FKEqu_ACC` FOREIGN KEY (`IdAccessorio`) REFERENCES `accessori` (`IdAccessorio`),
  CONSTRAINT `FKEqu_AUT` FOREIGN KEY (`IdAuto`) REFERENCES `automobili` (`IdAuto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipaggiamenti`
--

LOCK TABLES `equipaggiamenti` WRITE;
/*!40000 ALTER TABLE `equipaggiamenti` DISABLE KEYS */;
INSERT INTO `equipaggiamenti` VALUES (1,4),(2,1),(2,3),(2,5),(4,1),(4,4),(5,2),(5,3),(5,5),(8,2),(8,3),(8,4),(11,1),(11,5),(13,3),(13,6),(14,4),(15,2),(18,1),(18,2),(18,3),(19,4),(19,5),(20,4),(20,5),(20,6);
/*!40000 ALTER TABLE `equipaggiamenti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modelli`
--

DROP TABLE IF EXISTS `modelli`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `modelli` (
  `IdModello` int NOT NULL AUTO_INCREMENT,
  `NomeModello` varchar(50) NOT NULL,
  `IdProduttore` int NOT NULL,
  PRIMARY KEY (`IdModello`),
  KEY `FKProduzione` (`IdProduttore`),
  CONSTRAINT `FKProduzione` FOREIGN KEY (`IdProduttore`) REFERENCES `produttori` (`IdProduttore`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modelli`
--

LOCK TABLES `modelli` WRITE;
/*!40000 ALTER TABLE `modelli` DISABLE KEYS */;
INSERT INTO `modelli` VALUES (1,'500',1),(2,'Panda',1),(3,'Punto',1),(4,'Tipo',1),(5,'Giulia',2),(6,'Stelvio',2),(7,'Tonale',2),(8,'Giulietta',2),(9,'Golf',3),(10,'Polo',3),(11,'T-Roc',3),(12,'Tiguan',3),(13,'Serie 1',4),(14,'Serie 3',4),(15,'X3',4),(16,'X5',4),(17,'A1',5),(18,'A3',5),(19,'A4',5),(20,'Q3',5),(21,'Classe A',6),(22,'Classe C',6),(23,'GLA',6),(24,'GLC',6),(25,'Model 3',7),(26,'Model Y',7),(27,'Model S',7),(28,'Model X',7),(29,'Fiesta',8),(30,'Focus',8),(31,'Puma',8),(32,'Kuga',8),(33,'Yaris',9),(34,'GT86',9),(35,'Corolla',9),(36,'RAV4',9),(37,'Civic',10),(38,'Jazz',10),(39,'CR-V',10),(40,'HR-V',10),(41,'Qashqai',11),(42,'Juke',11),(43,'Micra',11),(44,'Leaf',11);
/*!40000 ALTER TABLE `modelli` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordini`
--

DROP TABLE IF EXISTS `ordini`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordini` (
  `IdOrdine` int NOT NULL AUTO_INCREMENT,
  `Indirizzo` varchar(150) NOT NULL,
  `SpesaTotale` decimal(11,2) NOT NULL,
  `Data` date NOT NULL,
  `IdUtente` int NOT NULL,
  PRIMARY KEY (`IdOrdine`),
  KEY `FKAcquisto` (`IdUtente`),
  CONSTRAINT `FKAcquisto` FOREIGN KEY (`IdUtente`) REFERENCES `utenti` (`IdUtente`),
  CONSTRAINT `chk_spesa_totale` CHECK ((`SpesaTotale` > 0.00))
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordini`
--

LOCK TABLES `ordini` WRITE;
/*!40000 ALTER TABLE `ordini` DISABLE KEYS */;
INSERT INTO `ordini` VALUES (1,'Via Roma 12, Torino',25230.00,'2026-06-08',3),(2,'Corso Milano 45, Milano',36850.00,'2026-06-10',4),(3,'Via Dante 7, Firenze',95.00,'2026-06-11',5),(4,'Via Garibaldi 89, Bologna',69.98,'2026-06-23',6),(8,'Via Roma 10, Napoli',21680.00,'2026-07-06',7),(19,'Via Roma 10, Napoli',21090.00,'2026-07-09',7),(20,'Via Garbaldi 89, Bologna',12990.00,'2026-07-09',4),(21,'Via roma Antica 12 ,Pesaro',13580.00,'2026-07-14',7),(22,'Via Roma 10, Fano',160.00,'2026-07-16',3),(23,'Via Monte 2,Arezzo',39.99,'2026-07-21',3),(24,'Via Aprile 28, Firenze',280.00,'2026-07-21',3),(25,'Via Gatti 7, Firenze',17500.00,'2026-07-24',3),(26,'Via Gatti 7, Firenze',130.00,'2026-07-24',3);
/*!40000 ALTER TABLE `ordini` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pezzi_di_ricambio`
--

DROP TABLE IF EXISTS `pezzi_di_ricambio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pezzi_di_ricambio` (
  `IdPezzo` int NOT NULL AUTO_INCREMENT,
  `NomePezzo` varchar(100) NOT NULL,
  `QuantitàMagazzino` int NOT NULL,
  `Tipologia` varchar(50) NOT NULL,
  `PezziVenduti` int NOT NULL DEFAULT '0',
  `IdProduttore` int NOT NULL,
  PRIMARY KEY (`IdPezzo`),
  KEY `FKProdotto` (`IdProduttore`),
  CONSTRAINT `FKProdotto` FOREIGN KEY (`IdProduttore`) REFERENCES `produttori` (`IdProduttore`),
  CONSTRAINT `chk_ricambi_quantita` CHECK ((`QuantitàMagazzino` >= 0)),
  CONSTRAINT `chk_ricambi_venduti` CHECK ((`PezziVenduti` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pezzi_di_ricambio`
--

LOCK TABLES `pezzi_di_ricambio` WRITE;
/*!40000 ALTER TABLE `pezzi_di_ricambio` DISABLE KEYS */;
INSERT INTO `pezzi_di_ricambio` VALUES (1,'Filtro Olio Motore Scatola da 10',120,'Motore',14,1),(2,'Cinghia di Distribuzione Dentata',25,'Motore',3,1),(3,'Kit Frizione Completo',11,'Trasmissione',6,3),(4,'Pompa dell\'Acqua Raffreddamento',18,'Motore',5,8),(5,'Candela di Accensione Iridium',200,'Accensione',45,9),(6,'Pastiglie Freno Anteriori Brembo',45,'Freni',22,2),(7,'Dischi Freno Ventilati (Coppia)',30,'Freni',8,5),(8,'Ammortizzatore Anteriore Destro',12,'Sospensioni',4,4),(9,'Kit Braccetti Oscillanti Sospensione',10,'Sospensioni',1,6),(10,'Batteria Auto 74Ah Start&Stop',33,'Impianto Elettrico',21,3),(11,'Faro Anteriore Full LED Sinistro',6,'Illuminazione',2,5),(12,'Alternatore 12V ricarica',8,'Impianto Elettrico',0,11),(13,'Specchietto Retrovisore Elettrico Dx',14,'Carrozzeria',6,9),(14,'Spazzole Tergicristallo (Coppia)',83,'Tergi',40,8),(15,'Filtro Abitacolo Carboni Attivi',150,'Climatizzazione',52,10),(16,'Cavo Ricarica Rapida Tipo 2',40,'Ricarica',11,7),(17,'Pasticche Freno Posteriori Brembo',50,'Freni',5,2),(18,'Filtro Aria Abitacolo High-Flow',80,'Climatizzazione',12,3),(19,'Sensore Pressione Pneumatici Bluetooth',99,'Impianto Elettrico',26,7),(20,'ADAS Livello 2',8,'Aiuti alla guida',2,1),(21,'Olio Alfa Romeo Speciale',31,'Olio',3,2),(22,'Fanale Anteriore Focus',19,'Fanale',2,8);
/*!40000 ALTER TABLE `pezzi_di_ricambio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produttori`
--

DROP TABLE IF EXISTS `produttori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produttori` (
  `IdProduttore` int NOT NULL AUTO_INCREMENT,
  `NomeProduttore` varchar(50) NOT NULL,
  `Nazionalita` varchar(50) NOT NULL,
  `AutoVendute` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`IdProduttore`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produttori`
--

LOCK TABLES `produttori` WRITE;
/*!40000 ALTER TABLE `produttori` DISABLE KEYS */;
INSERT INTO `produttori` VALUES (1,'Fiat','Italia',2),(2,'Alfa Romeo','Italia',0),(3,'Volkswagen','Germania',1),(4,'BMW','Germania',0),(5,'Audi','Germania',0),(6,'Mercedes-Benz','Germania',1),(7,'Tesla','USA',0),(8,'Ford','USA',0),(9,'Toyota','Giappone',1),(10,'Honda','Giappone',0),(11,'Nissan','Giappone',0);
/*!40000 ALTER TABLE `produttori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recensioni`
--

DROP TABLE IF EXISTS `recensioni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recensioni` (
  `IdRecensione` int NOT NULL AUTO_INCREMENT,
  `Voto` int NOT NULL,
  `Titolo` varchar(100) NOT NULL,
  `DataRecensione` date NOT NULL,
  `Testo` varchar(1000) NOT NULL,
  `TipoRecensione` enum('Pezzo','Modello') NOT NULL,
  `IdUtente` int NOT NULL,
  `IdPezzo` int DEFAULT NULL,
  `IdModello` int DEFAULT NULL,
  PRIMARY KEY (`IdRecensione`),
  KEY `FKScrittura` (`IdUtente`),
  KEY `FKValuazione_Pezzo` (`IdPezzo`),
  KEY `FKValutazione_Modello` (`IdModello`),
  CONSTRAINT `FKScrittura` FOREIGN KEY (`IdUtente`) REFERENCES `utenti` (`IdUtente`),
  CONSTRAINT `FKValuazione_Pezzo` FOREIGN KEY (`IdPezzo`) REFERENCES `pezzi_di_ricambio` (`IdPezzo`),
  CONSTRAINT `FKValutazione_Modello` FOREIGN KEY (`IdModello`) REFERENCES `modelli` (`IdModello`),
  CONSTRAINT `chk_esclusivita_recensione` CHECK ((((`TipoRecensione` = _utf8mb4'Pezzo') and (`IdPezzo` is not null) and (`IdModello` is null)) or ((`TipoRecensione` = _utf8mb4'Modello') and (`IdModello` is not null) and (`IdPezzo` is null)))),
  CONSTRAINT `chk_voto_recensione` CHECK ((`Voto` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recensioni`
--

LOCK TABLES `recensioni` WRITE;
/*!40000 ALTER TABLE `recensioni` DISABLE KEYS */;
INSERT INTO `recensioni` VALUES (1,5,'Ottima tenuta e frenata','2026-06-20','Pastiglie freni Brembo eccezionali sul bagnato.','Pezzo',4,6,NULL),(2,4,'Perfetta per la città','2026-06-22','La Panda è una garanzia, consumi ridotti al minimo.','Modello',3,NULL,2),(3,4,'Una ottima auto','2026-07-08','La volkswagen Golf che ho comprato è affidabile','Modello',7,NULL,9),(4,2,'Normale','2026-07-08','Normale','Pezzo',7,3,NULL),(5,4,'Buono per la mia Alfa','2026-07-16','Ottimo olio!!','Pezzo',3,21,NULL),(6,5,'Ottima Auto','2026-07-21','Ottima auto per uso quotidiano','Modello',7,NULL,21);
/*!40000 ALTER TABLE `recensioni` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servizi`
--

DROP TABLE IF EXISTS `servizi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servizi` (
  `IdServizio` int NOT NULL AUTO_INCREMENT,
  `Nome` varchar(50) NOT NULL,
  `Prezzo` decimal(9,2) NOT NULL,
  `TipoServizio` enum('Auto','Pezzo') NOT NULL,
  PRIMARY KEY (`IdServizio`),
  CONSTRAINT `chk_prezzo_servizio` CHECK ((`Prezzo` >= 0.00))
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servizi`
--

LOCK TABLES `servizi` WRITE;
/*!40000 ALTER TABLE `servizi` DISABLE KEYS */;
INSERT INTO `servizi` VALUES (1,'Estensione Garanzia 12 Mesi ricambio',10.00,'Pezzo'),(2,'Spedizione Veloce',10.00,'Pezzo'),(3,'Lavaggio Completo Sanificante',80.00,'Auto'),(4,'Estensione Garanzia 12 Mesi automobile',350.00,'Auto'),(5,'Consegna a Domicilio con Bisarca',250.00,'Auto'),(6,'Estensione garanzia 6 mesi automobile',200.00,'Auto'),(7,'Kit Portaoggetti',30.00,'Auto'),(8,'Kit Montaggio',30.00,'Pezzo');
/*!40000 ALTER TABLE `servizi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipi_alimentazione`
--

DROP TABLE IF EXISTS `tipi_alimentazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipi_alimentazione` (
  `IdAlimentazione` int NOT NULL AUTO_INCREMENT,
  `NomeAlimentazione` varchar(25) NOT NULL,
  PRIMARY KEY (`IdAlimentazione`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipi_alimentazione`
--

LOCK TABLES `tipi_alimentazione` WRITE;
/*!40000 ALTER TABLE `tipi_alimentazione` DISABLE KEYS */;
INSERT INTO `tipi_alimentazione` VALUES (1,'Benzina'),(2,'Diesel'),(3,'Elettrica'),(4,'Ibrida');
/*!40000 ALTER TABLE `tipi_alimentazione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utenti`
--

DROP TABLE IF EXISTS `utenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utenti` (
  `IdUtente` int NOT NULL AUTO_INCREMENT,
  `Email` varchar(100) NOT NULL,
  `Nome` varchar(50) NOT NULL,
  `Cognome` varchar(50) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `TipoUtente` enum('Privato','Aziendale','Amministratore') NOT NULL,
  PRIMARY KEY (`IdUtente`),
  UNIQUE KEY `IDUTENTE_1` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utenti`
--

LOCK TABLES `utenti` WRITE;
/*!40000 ALTER TABLE `utenti` DISABLE KEYS */;
INSERT INTO `utenti` VALUES (1,'admin@ecommerce.it','Luca','Verdi','hash_password_sicura_1','Amministratore'),(2,'supporto@ecommerce.it','Elena','Ferrari','hash_password_sicura_2','Amministratore'),(3,'mario.rossi@email.it','Mario','Rossi','pass1','Privato'),(4,'giuseppe.bianchi@outlook.it','Giuseppe','Bianchi','qwerty2026','Privato'),(5,'francesca.neri@yahoo.com','Francesca','Neri','sole2026!','Privato'),(6,'anna.russo@hotmail.it','Anna','Russo','segreta99','Privato'),(7,'antonio.esposito@gmail.com','Antonio','Esposito','auto_95','Privato'),(8,'info@autovendita.it','Tony','Mobili','azienda_pass_1','Aziendale'),(9,'commerciale@ricambipro.com','Roberto','Spagna','azienda_pass_2','Aziendale'),(10,'gestione@autoexpert.net','Andrea','Di Giovanni','azienda_pass_3','Aziendale'),(21,'stefano.bianchi@gmail.com','Stefano','Bianchi','pass_stefano_2026','Privato'),(22,'info@ricambisprint.it','Marco','Logistica','sprint_ricambi_pass','Aziendale'),(23,'direzione@motorhub.org','Valeria','Massimi','hub_motor_secure','Aziendale'),(28,'luca.bruno@gmail.com','Luca','Bruno','LB123','Privato'),(29,'luca.bianchi@motormarket.it','Luca','Bianchi','pass2','Aziendale'),(30,'admin@motormarket.it','Marco','Verdi','pass3','Amministratore');
/*!40000 ALTER TABLE `utenti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendite`
--

DROP TABLE IF EXISTS `vendite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendite` (
  `idAnnuncio` int NOT NULL,
  `IdOrdine` int NOT NULL,
  `Quantità` int NOT NULL,
  `PrezzoUnitario` decimal(9,2) NOT NULL,
  PRIMARY KEY (`IdOrdine`,`idAnnuncio`),
  KEY `FKVen_ANN` (`idAnnuncio`),
  CONSTRAINT `FKVen_ANN` FOREIGN KEY (`idAnnuncio`) REFERENCES `annunci` (`idAnnuncio`),
  CONSTRAINT `FKVen_ORD` FOREIGN KEY (`IdOrdine`) REFERENCES `ordini` (`IdOrdine`),
  CONSTRAINT `chk_vendite_prezzo` CHECK ((`PrezzoUnitario` > 0.00)),
  CONSTRAINT `chk_vendite_quantita` CHECK ((`Quantità` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendite`
--

LOCK TABLES `vendite` WRITE;
/*!40000 ALTER TABLE `vendite` DISABLE KEYS */;
INSERT INTO `vendite` VALUES (6,1,1,24500.00),(7,2,1,36500.00),(8,3,2,35.00),(12,3,1,25.00),(16,4,2,29.99),(4,8,1,21500.00),(9,8,1,180.00),(17,19,1,20000.00),(18,19,1,1000.00),(1,20,1,11900.00),(18,20,1,1000.00),(14,21,1,13500.00),(22,22,3,50.00),(16,23,1,29.99),(11,24,2,110.00),(12,24,2,25.00),(21,25,1,17500.00),(23,26,2,65.00);
/*!40000 ALTER TABLE `vendite` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-24 16:39:44
