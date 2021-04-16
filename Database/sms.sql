-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : sam. 17 avr. 2021 à 00:51
-- Version du serveur :  10.4.11-MariaDB
-- Version de PHP : 7.4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `sms`
--

DELIMITER $$
--
-- Procédures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertAdminPassWord` (IN `MotDePasse` VARCHAR(50), IN `IdAdmin` INT)  BEGIN

set @mykeystr = "AvBgnsd_09m_0ll";
 set @shahex = SHA2(@mykeystr, 512);
 SET @key = UNHEX(@shahex);

 update administration set MotDePasse = AES_ENCRYPT(MotDePasse, @key) where ID = IdAdmin;
 end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `selectConnexion` (IN `Username` VARCHAR(50), IN `MDP` VARCHAR(50))  BEGIN
set @mykeystr = "AvBgnsd_09m_0ll";
 set @shahex = SHA2(@mykeystr, 512);
 SET @key = UNHEX(@shahex);

SELECT * FROM `administration` WHERE NomUtilisateur = Username and MotDePasse = AES_ENCRYPT(MDP, @key);
 end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `administration`
--

CREATE TABLE `administration` (
  `ID` int(11) NOT NULL,
  `NomUtilisateur` varchar(30) NOT NULL,
  `MotDePasse` varbinary(50) NOT NULL,
  `Prenom` varchar(30) NOT NULL,
  `Nom` varchar(30) NOT NULL,
  `Telephone` varchar(15) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `AdresseDeMaison` varchar(50) NOT NULL,
  `Ville` varchar(50) NOT NULL,
  `CodePostal` varchar(7) NOT NULL,
  `Province` varchar(25) NOT NULL,
  `Poste` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `administration`
--

INSERT INTO `administration` (`ID`, `NomUtilisateur`, `MotDePasse`, `Prenom`, `Nom`, `Telephone`, `Email`, `AdresseDeMaison`, `Ville`, `CodePostal`, `Province`, `Poste`) VALUES
(1, 'Admin', 0xd723031797ae66792a71a179b936a90a, 'Anthony', 'Chicoine', '5148313934', 'anthony.chicoine@live.ca', '60 avenue du parc', 'Ile-Bizard', 'H9C 2N3', 'Quebec', 'Administrateur'),
(31, 'Admin_1', 0x8bbe2428927dc4e2858c9b8397152457, 'Jean-Denis', 'Chicoine', '5140001234', 'jdc@live.ca', '60 avenue du parc', 'Montreal', 'H9C 2N3', 'Quebec', 'Administrateur'),
(33, 'JMR76', 0x78530f650de770556a08a7edb2014ecd, 'Jean-Marc', 'Robert', '4389010890', 'jeanmarc@live.ca', '109 rue Marick', 'Quebec', 'H9C2N3', 'Quebec', 'Professeur'),
(44, 'asd', 0x28f8a59e3c922ace305caa9cd137985206cc08e9eb731663c1c314c4583c8c06, 'sad', 'asd', '12312312', 'asdasd', 'sad', 'asd', 'asd', 'asd', 'Professeur');

--
-- Déclencheurs `administration`
--
DELIMITER $$
CREATE TRIGGER `after_delete_administration` AFTER DELETE ON `administration` FOR EACH ROW BEGIN
	declare Utilisateur varchar(50);
   	Select Createur from tableutilisateur into Utilisateur;
    
    INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
    VALUES (Utilisateur, OLD.NomUtilisateur, "", "Administration", "Suppression", CURRENT_DATE );
	
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_update_administration` AFTER UPDATE ON `administration` FOR EACH ROW BEGIN
	declare Utilisateur varchar(50);
   	Select Createur from tableutilisateur into Utilisateur;
 
    IF OLD.NomUtilisateur <> new.NomUtilisateur THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.NomUtilisateur, new.NomUtilisateur, "Administration", "Modification", CURRENT_DATE );
        
    END IF;
    IF OLD.MotDePasse <> new.MotDePasse THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.MotDePasse, new.MotDePasse, "Administration", "Modification", CURRENT_DATE);
        
    END IF;
    
     IF OLD.Prenom <> new.Prenom THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Prenom, new.Prenom, "Administration", "Modification", CURRENT_DATE);     
    END IF;
    
    IF OLD.Nom <> new.Nom THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Nom, new.Nom, "Administration", "Modification", CURRENT_DATE);     
    END IF;
    
    IF OLD.Telephone <> new.Telephone THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Telephone, new.Telephone, "Administration", "Modification", CURRENT_DATE);
        
    END IF;
     IF OLD.Email <> new.Email THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Email, new.Email, "Administration", "Modification", CURRENT_DATE);
        
    END IF;
     IF OLD.AdresseDeMaison <> new.AdresseDeMaison THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.AdresseDeMaison, new.AdresseDeMaison, "Administration", "Modification", CURRENT_DATE);
        
    END IF;
     IF OLD.Ville <> new.Ville THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Ville, new.Ville, "Administration", "Modification", CURRENT_DATE);
        
    END IF;
     IF OLD.Province <> new.Province THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Province, new.Province, "Administration", "Modification", CURRENT_DATE);
        
    END IF;
     IF OLD.CodePostal <> new.CodePostal THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.CodePostal, new.CodePostal, "Administration", "Modification", CURRENT_DATE);
       
    END IF;
     IF OLD.Poste <> new.Poste THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Poste, new.Poste, "Administration", "Modification", CURRENT_DATE);
        
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `ancienenseignant`
--

CREATE TABLE `ancienenseignant` (
  `ID` int(11) NOT NULL,
  `NomUtilisateur` varchar(30) NOT NULL,
  `MotDePasse` varbinary(50) NOT NULL,
  `Prenom` varchar(30) NOT NULL,
  `Nom` varchar(30) NOT NULL,
  `Telephone` varchar(20) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `AdresseDeMaison` varchar(40) NOT NULL,
  `Ville` varchar(30) NOT NULL,
  `CodePostal` varchar(6) NOT NULL,
  `Province` varchar(20) NOT NULL,
  `Poste` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `ancienenseignant`
--

INSERT INTO `ancienenseignant` (`ID`, `NomUtilisateur`, `MotDePasse`, `Prenom`, `Nom`, `Telephone`, `Email`, `AdresseDeMaison`, `Ville`, `CodePostal`, `Province`, `Poste`) VALUES
(10, 'JMP67', 0x313233, 'Jean-Marc', 'Robert', '5148313935', 'jmp@live.ca', '87 rue Montigny', 'Ile-Bizard', 'H9C2N4', 'Quebec', 'Professeur'),
(11, 'JMR67', 0x313233, 'Jean-Marc', 'Robert', '5140934231', 'jeanmarc@live.ca', '0 rue inconnue', 'Montreal', 'Y6C8J1', 'Quebec', 'Professeur'),
(12, 'abc', 0x313233, 'etsd', 'sdt', '2123412312', 'asdasd', 'asdasd', 'asd', 'asd', 'asd', 'Professeur'),
(13, 'EQ', 0x313233, 'QWE', 'QWEQWE', '12323', 'QWEW', 'QWE', 'QWE', 'QWE', 'QWE', 'Professeur'),
(14, 'QWEQWE', 0x313233, 'WQE', 'WQE', '2133213221', 'QWSEQWE', 'WQE', 'WEQ', 'WQE', 'WEQ', 'Professeur'),
(15, 'qweasd', 0x313233, 'wqe', 'wqe', '2314124235', 'sdasda', 'wqe', 'wqe', 'weqwe', 'qweqweqw', 'Professeur'),
(16, 'WQEQWEWQ', 0x313233, 'QWEQWE', 'WQE', '1231231231', 'QWEQWE', 'WQE', 'WQE', 'WQEEE', 'EEEEE', 'Professeur'),
(17, 'wqeewqqqqq', 0x313233, 'qwe', 'weq', '123231123', 'ewqe', 'ewqwqe', 'ewqe', 'wqewqe', 'wqe', 'Professeur'),
(18, 'asd', 0x313233, 'sad', 'asd', '1231266666', 'asd', 'asd', 'asd', 'asd', 'asd', 'Professeur'),
(19, 'SADASDASDV', 0x313233, 'DDD', 'DDDD', '6655443322', 'DFDDQSD', 'DDD', 'DDDD', 'DD', 'DDD', 'Professeur'),
(20, 'ASDASD', 0x313233, 'QWEQWE', 'QWE', '1312311112', 'ASASD', 'QWE', 'QWEQ', 'WE', 'QWE', 'Professeur'),
(21, 'qweqweeee', 0x313233, 'eee', 'eeee', '2221111222', 'ewqeqwe', 'ee', 'eeee', 'ee', 'eeee', 'Professeur'),
(22, 'qweqw', 0x313233, 'eeqweq', 'eeeeqweq', '123578963', 'qweqwe', 'eeeqeweq', 'weqwe', 'qwe', 'qwe', 'Professeur'),
(23, 'test', 0x313233, 'test', 'test', '6666666666', 'test', 'test', 'test', 'test', 'test', 'Professeur'),
(24, 'GB78', 0x313233, 'Goerges', 'Boisvert', '5146713232', 'georges.boisvert@live.ca', '78 rue montigny', 'Ile-Bizard', 'H8C5N4', 'Quebec', 'Professeur'),
(25, 'QWEWQEQ', 0x313233, 'QWE', 'WQE', '123123123', 'QWSEQWE', 'WQE', 'WQE', 'WQE', 'WEQ', 'Professeur'),
(26, 'GB78', 0x313233, 'Georges', 'Boisvert', '4386571122', 'georgesboisvert@live.ca', '56 rue montigny', 'Ile-Bizard', 'H7C3N4', 'Quebec', 'Professeur'),
(27, 'QWEQWE', 0x313233, 'WQE', 'QWE', '213123123', 'QWEQWE', 'WQE', 'WEQ', 'EWQ', 'WEQ', 'Professeur'),
(28, 'GB78', 0x313233, 'Georges', 'Boisvert', '5146107865', 'georgesboisvert@live.ca', '1001 rue Notre-Dame', 'Montreal', 'G7V8J2', 'Quebec', 'Professeur'),
(29, 'GB87', 0x313233, 'Georges', 'Boisvert', '5147851923', 'georgesboisvert@live.ca', '789 avenue du parc', 'Montreal', 'H7C4N1', 'Quebec', 'Professeur'),
(30, 'GB87', 0x313233, 'Georges', 'Boisvert', '5148930921', 'georgesboisvert@live.ca', '789 avenue du parc', 'Montreal', 'H8C2N1', 'Quebec', 'Professeur'),
(32, 'dqwdqwdqwd', 0x313233, 'wadawd', 'awdaw', '1212312312', 'wdqdqdq', 'dawdad', 'wadawdwad', 'wadawd', 'addwadawd', 'Professeur'),
(34, 'test', 0x313233, 'test', 'test', '1231212312', 'test', 'test', 'test', 'test', 'test', 'Professeur'),
(35, 'sdadasd', 0x313233, 'awd', 'awd', '21312312', 'dasdasd', 'awd', 'wad', 'wad', 'wad', 'Professeur'),
(36, 'test', 0x313233, 'test', 'test', '1231231231', 'test', 'test', 'test', 'test', 'test', 'Professeur'),
(37, '123123123', 0x313233, 'Antho', 'Chico', '1231231231', '123', '123', '123', '123', '123', 'Professeur'),
(38, 'qwe', 0x313233, 'wqe', 'wqe', '12321312', 'sqaeqw', 'wqeqwe', 'wqe', 'qwe', 'wqe', 'Professeur'),
(39, ' sadasdas d', 0x313233, 'wqe', 'qwe', '312331313', 'asd', 'qweeq', 'wqeqw', 'qwe', 'qwe', 'Professeur'),
(43, 'qwe', 0x50e722da49ee241342581320a5efa453db61261ab727c5f5915403d36494e31b, 'qwe', 'wqe', '12312312', 'edqwewqewqe', 'qwe', 'qwe', 'qwe', 'qwe', 'Professeur');

-- --------------------------------------------------------

--
-- Structure de la table `ancienetudiant`
--

CREATE TABLE `ancienetudiant` (
  `ID` int(11) NOT NULL,
  `Utilisateur` varchar(30) NOT NULL,
  `Prenom` varchar(30) NOT NULL,
  `Nom` varchar(30) NOT NULL,
  `Telephone` varchar(20) NOT NULL,
  `Email` varchar(35) NOT NULL,
  `AdresseDeMaison` varchar(30) NOT NULL,
  `Ville` varchar(30) NOT NULL,
  `Province` varchar(30) NOT NULL,
  `CodePostal` varchar(10) NOT NULL,
  `IdProgramme` int(11) NOT NULL,
  `Langue` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `ancienetudiant`
--

INSERT INTO `ancienetudiant` (`ID`, `Utilisateur`, `Prenom`, `Nom`, `Telephone`, `Email`, `AdresseDeMaison`, `Ville`, `Province`, `CodePostal`, `IdProgramme`, `Langue`) VALUES
(10, 'Antho7641', 'Anthony', 'Chicoine', '5148313934', 'anthony.chicoine@live.ca', '60 avenue du parc', 'Ile-Bizard', 'Quebec', 'H9C2N3', 1, 'Fran?ais'),
(13, 'JMM88', 'Jean-Marc', 'Martin', '5148434543', 'jm.m@live.ca', '7 rue inconnue', 'Montréal', 'Quebec', 'H7C3G5', 0, ''),
(22, 'Jmr80', 'Jean-Marc', 'Robert', '5140123456', 'JMR@live.ca', '600 rue inconnu', 'Montréal', 'Québec', 'G9J4U5', 1, 'English'),
(26, 'test', 'test', 'testtest', '1213123123', 'test', 'test', 'test', 'test', 'test', 1, 'Fran?ais'),
(27, 'test1', 'test1', 'test1', '2313213231', 'test1', 'test1', 'test1', 'test1', 'test1', 1, 'Fran?ais'),
(28, 'Test2', 'Test2', 'Test2', '12312312', 'Test2', 'Test2', 'Test2', 'Test2', 'Test2', 2, 'Fran?ais'),
(29, 'Test4', 'Test4', 'Test4', '3413241241', 'Test4', 'Test4', 'Test4', 'Test4', 'Test4', 0, 'Fran?ais'),
(30, 'PG97', 'Philippe', 'Gendrons', '5146781256', 'PG@hotmail.com', '600 inconnu', 'Montreal', 'Quebec', 'G9C 4N5', 1, 'English'),
(34, 'antho7642', 'Anthony', 'Chicoine', '5148313934', 'anthony.chicoine@live.ca', '60 avenue du parc', 'Ile-Bizard', 'Quebec', 'H9C2N3', 0, 'Francais'),
(36, 'Morin99', 'Olivier', 'Morin', '5147467899', 'oli.morin@live.ca', '678 rue Sainte-Catherine', 'Montreal', 'Quebec', 'G6C7N2', 0, 'Francais'),
(38, '123123123qwdq', 'wadawd', 'wad', '2131231231', 'weqweqweqw', 'wdawad', 'wad', 'dwadwaw', 'dwadwdwa', 0, 'English'),
(39, 'Adams97', 'Adams97', 'Adams97', 'Adams97', 'Adams97', 'Adams97', 'Adams97', 'Adams97', 'Adams97', 4, 'Adams97'),
(44, 'Tim98', 'Timothé', 'Richardson', '5148089293', 'tim.rich@hotmail.com', '34 rue Notre-Dame', 'Vaudreuil-Dorion', 'Quebec', 'J7N2M4', 2, 'Francais');

-- --------------------------------------------------------

--
-- Structure de la table `ancienexamen`
--

CREATE TABLE `ancienexamen` (
  `IdEtudiant` int(11) NOT NULL,
  `IdCours` int(11) NOT NULL,
  `IdEvalutation` int(11) NOT NULL,
  `ResultatFinal` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `ancienexamen`
--

INSERT INTO `ancienexamen` (`IdEtudiant`, `IdCours`, `IdEvalutation`, `ResultatFinal`) VALUES
(44, 21, 38, 78),
(44, 21, 39, 89);

-- --------------------------------------------------------

--
-- Structure de la table `clavardage`
--

CREATE TABLE `clavardage` (
  `IdMessage` int(11) NOT NULL,
  `IdEnvoyeur` int(11) NOT NULL,
  `IdReceveur` int(11) NOT NULL,
  `Message` longtext NOT NULL,
  `Date` date NOT NULL,
  `Etat` int(11) NOT NULL COMMENT '1 = lu / 0 = non lu',
  `Reponse` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `cour`
--

CREATE TABLE `cour` (
  `ID` int(11) NOT NULL,
  `Titre` varchar(100) NOT NULL,
  `Description` varchar(2555) NOT NULL,
  `TitreAnglais` varchar(100) NOT NULL,
  `DescriptionAnglais` varchar(2555) NOT NULL,
  `Temps` varchar(5) NOT NULL,
  `Cout` double NOT NULL,
  `Credit` int(3) NOT NULL,
  `Etat` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `cour`
--

INSERT INTO `cour` (`ID`, `Titre`, `Description`, `TitreAnglais`, `DescriptionAnglais`, `Temps`, `Cout`, `Credit`, `Etat`) VALUES
(1, 'Profession de programmeur-analyste', 'Historique. Fonctions de travail du programmeur-analyste. Exercice du métier en fonction des différents milieux de travail. Rôle du programmeur-analyste et ceux des professions connexes. Exigences des milieux. Programmation (Tendances, Utilité, Risques, enjeux et défis). Éthique professionnelle au travail. Principaux composants matériels et logiciels d’un ordinateur. Gestion des fichiers. Personnalisation d’un poste de travail. Création de documents ? l’aide d’outils de communication et de gestion courants en milieu corporatifs ? l’aide de la suite Microsoft Office™ (Word, Excel, PowerPoint, Visio et Project). Utilisation d’Internet pour la communication et la recherche. Méthodes de travail et ergonomie.', 'Programmer Analyst Profession', 'Historical perspective. Duties of the programmer analyst. Exercise of the profession according to the different work environments. Role of the programmer analyst and those of related professions. Workplace demands. Programming (trends, utility, risks, issues and challenges). Professional ethics at work. Main hardware and software components of a computer. File management. Customizing a workstation. Create documents using common corporate communication and management tools using the Microsoft Office ™ suite (Word, Excel, PowerPoint, Visio and Project). Internet use for communication and research. Working methods and ergonomics.', '65', 100, 4, 1),
(2, 'Développement web 1', 'Bases de la création d’un site web. Emploi d’un langage de balisage con?u pour représenter les pages web (HTML). Feuilles de style en cascade (CSS). Création d’applications Web. Utilisation de scripts pour valider l’intégrité des données. R?gles d’ergonomie et de bonne conception. Techniques permettant de créer un site se déployant sur plusieurs plateformes. Intégration du multimédia.', 'Web Development 1', 'Basics of website building. Use of markup languages designed to represent web pages (HTML). Cascading Style Sheets (CSS). Creation of Web applications. Using scripts to validate data integrity. Rules of ergonomics and good design. Techniques to create a site deploying on multiple platforms. Multimedia integration.', '60', 125, 5, 1),
(3, 'Approche structurée à la résolution de problèmes', 'Identification des données d’entrée et de sortie. Détermination des entités pertinentes et leurs attributs. Esquisse de la conception. Identification des concepts relatifs aux données, aux opérateurs et aux fonctions. Priorité des opérateurs mathématiques. Distinction des types de données de base, les variables et les constantes. Évaluation des expressions utilisant des opérateurs. Développement de l’algorithme. Création d’algorithmes pour l’utilisation des tableaux. Représentation de la logique au moyen de pseudocodes et d’organigrammes. Traduction d’algorithmes en un langage de programmation. Logique (décision et boucles, etc.). Utilisation des outils de débogage de code grâce aux outils de développement permettant de générer des applications web (Microsoft Visual Studio).', 'Structured Approach to Problem Solving ', 'Identification of input and output data. Determination of relevant entities and their attributes. Sketch of the design. Identification of concepts related to data, operators and functions. Priority of mathematical operators. Distinction of basic data types, variables and constants. Evaluation of expressions using operators. Algorithm development. Creation of algorithms for the use of tables. Representation of logic using pseudocodes and flow charts. Translation of algorithms into a programming language. Logic (decision and loops, etc.). Use of code-debugging tools with development tools to generate web applications (Microsoft Visual Studio).', '70', 150, 5, 1),
(4, 'Développement web 2', 'Programmation côté client. Dynamiser un site WEB avec langage de programmation de scripts JavaScript. Langage de programmation approprié au développement du côté client d’une application et d’un site web. Outils. Bibliothèques. Plateformes. Programmation de la logique applicative. Gestion des interactions entre l’interface web et l’utilisateur. Techniques d’animation et de manipulation des éléments d’une page web. Conception des interfaces graphiques riches.', 'Web Development 2 ', 'Client-side programming. Boosting a website with JavaScript programming language. Programming language suitable for developing the client side of an application and a website. Tools. Libraries. Platforms. Programming of the application logic. Management of interactions between the web interface and the user. Techniques of animation and manipulation of the elements of a web page. Design of rich graphical interfaces.', '60', 150, 5, 1),
(5, 'Animation Web', 'Éléments et composants. Scripting (Création, Débogage, Cycle de vie). Coroutines. Interactions avec l’utilisateur (Entrées clavier, Souris, Tactile). Assets graphiques et audio. Système d’animation. Moteur physique. Création d’une interface utilisateur (Canvas, Système de positionnement et d\'ancrage, Éléments d\'affichage). Communication réseau. Création d’un jeu. Utilisation des Sprites.', 'Web Animation ', 'Elements and components. Scripting (Creation, Debug, Life Cycle). Coroutines. Interactions with users (Keyboard, Mouse, Touch). Graphics and audio assets. Animation system. Physical engine. Creating a user interface (Canvas, Positioning and Anchoring System, Display Elements). Network communication. Creating a game. Using Sprites.', '70', 150, 5, 1),
(6, 'Programmation orientée objet 1', 'Possibilités d’un langage de programmation orientée objet. Adaptation des algorithmes et pseudocodes en fonction d’un langage de programmation orienté objet. Langage de modélisation graphique ? base de pictogrammes. Déclaration et utilisation de variables et constantes. Utilisation des opérateurs et expressions. Codage des différentes structures de contrôle. Utilisation d’une biblioth?que de code permettant de produire des applications de gestion ? interface graphique riche. Déclaration et utilisation des variables complexes (tableaux, énumération et structures). Écriture des fonctions. Écriture des gestionnaires d’erreurs. Outils de compilation et de débogage de l’environnement de développement. Architecture logicielle. Repérage et correction des erreurs de compilation. Validation des résultats. Correction des algorithmes et ou pseudocode. Application des jeux d’essais. Analyser les résultats des jeux d’essais. Validation du fonctionnement du programme. Documentation.', 'Object-Oriented Programming 1 ', 'Opportunities derived from object-oriented programming. Adaptation of algorithms and pseudocodes according to an object-oriented programming language. Graphic modeling language based on pictograms. Declaration and use of variables and constants. Using operators and expressions. Coding of the different control structures. Using a code library to produce rich GUI management applications. Declaration and use of complex variables (tables, enumeration and structures). Writing functions. Write error handlers. Tools for compiling and debugging development environments. Software architecture. Tracking and correcting compilation errors. Validation of results. Correction of algorithms and/or pseudocode. Application of test games. Analyze the results of test games. Validation of program operation. Documentation.', '70', 150, 5, 1),
(7, 'Programmation orientée objet 2', 'Possibilités avancées d’un langage de programmation orientée objet. Les classes et les interfaces. Gestion de classes à l’aide de l’héritage, de l’encapsulation et du polymorphisme. Conception d’interfaces utilisateurs graphiques conviviales.', 'Object-Oriented Programming 2 ', 'Advanced possibilities of an object-oriented programming language. Classes and interfaces. Class management using inheritance, encapsulation, and polymorphism. Design of user-friendly graphical user interfaces.', '80', 200, 6, 1),
(8, 'Bases de donnees 1', 'Application des concepts clés d’un système de gestion de base de données. Utilisation de Microsoft Access™ pour créer une base de données. Création des requêtes, des formulaires, des états et des rapports. Tables. Relations. Normalisation. Clés primaires et secondaires. Utilisation d’interfaces pour la gestion d’entrées et de sorties de données. Conception, modélisation et normalisation de bases de données relationnelles et orientées objet. Conception d’interfaces utilisateurs graphiques conviviales.', 'Databases 1 ', 'Application of key concepts of a database management system. Using Microsoft Access ™ to create a database. Create queries, forms, reports, and reports. Tables. Relations. Standardization. Primary and secondary keys. Use of interfaces for the management of data inputs and outputs. Design, modeling and standardization of relational and object-oriented databases. Design of user-friendly graphical user interfaces.', '70', 150, 5, 1),
(9, 'Bases de donnees 2', 'Création, modification et exploitation d’une base de données relationnelle ou d’autre nature. Réplication de données. Gestion des données et utilisation de déclencheurs et de procédures stockées. Optimisation de l’accès aux données grâce aux index et aux jointures. Conception d’un plan de sécurité pour une base de données. Interprétation et conception des modèles de données conceptuels, logiques et physiques. Opérations de base de l’administration d’une base de données. Les scripts et les lots. Les blocs de code et les structures de contrôle. L’imbrication de structures. Les structures de répétition. Les entités, les attributs et les relations. Normalisation des bases de données. Modification de la mise en page d’un état. Mises en forme évoluées. Fonction Reproductrice de mise en forme et les mises en forme automatiques. Création de sous-formulaires. Mise en forme des contrôles. Production du guide utilisateur propre à l’application développée.', 'Databases 2 ', 'Creation, modification and exploitation of a relational or other type of database. Replication of data. Data management and use of triggers and stored procedures. Optimizing data access with indexes and joins. Design of a security plan for a database. Interpretation and design of conceptual, logical and physical data models. Basic operations of administering a database. Scripts and lots. Code blocks and control structures. Nesting of structures. Repetition structures. Entities, attributes and relationships. Standardization of databases. Changing the layout of a report. Advanced fitness. Reproductive formatting function and automatic formatting. Creating subforms. Formatting controls. Production of user guides specific to developed applications.', '70', 150, 6, 1),
(10, 'Traitement de donnees', 'Analyse des besoins en fonction de l’application et des utilisateurs. Production de rapports. Production de plans et diagrammes démontrant l’aperçu de l’interface. Définition des entrées et sorties et les traitements. Production d’une base de données pour héberger les données. Type de connexion entre l’application et la base de données. Assemblage .NET en fonction de l’application (privé ou partagé). Pseudocode et algorithmes. Préparation de la connexion entre l’application et la base de données. Traduire le pseudocode et algorithme en langage de programmation (C#). Création d’applications client–serveur. Conception et création de solutions multi-niveaux. Programmation de la connexion entre l’application et la base de données. Production du guide de l’utilisateur. Documentation de l’application (interne et externe).', 'Data Processing ', 'Needs analysis according to applications and users. Reporting. Production of plans and diagrams showing the interface preview. Definition of inputs and outputs and treatments. Production of a database to host data. Type of connections between applications and databases. .NET assembly based on the application (private or shared). Pseudocode and algorithms. Preparing the connection between the application and the database. Translate the pseudocode and algorithm into a programming language (C #). Creation of client-server applications. Design and creation of multi-level solutions. Programming the connection between applications and databases. Production of user guides. Application documentation (internal and external).', '85', 225, 6, 1),
(11, 'Developpement web cote serveur', 'Introduction au contenu web dynamique. Mettre en place le serveur de développement. Langage impératif orienté objet (PHP). Expressions et contrôle de flux en PHP. Fonctions et objets en PHP. Tableaux en PHP. Syst?me de gestion de bases de données relationnelle (MySQL). Accéder ? MySQL ? l\'aide de PHP. Gestion de formulaires. Cookies, sessions et authentification.', 'Server Side Web Development ', 'Introduction to dynamic web content. Setting up development servers. Object-oriented imperative language (PHP). Expressions and flow control in PHP. Functions and objects in PHP. Tables in PHP. Relational database management system (MySQL). Access MySQL using PHP. Forms Management. Cookies, sessions and authentication.', '70', 175, 4, 1),
(12, 'Programmation web', 'Système de gestion de contenu (WordPress ou autre). L\'administration et les menus (Tableau de bord, Articles, Médias, Pages, Commentaires, Apparence, Extensions, Utilisateurs, Outils, Réglages). Les thèmes enfants. Personnalisation d’un site. Les champs personnalisés. Les modèles de page. Création d’un thème. Les extensions et les widgets. Création d’une extension en PHP. Le thème et les extensions. Optimisation et sécurité d’un site. Sauvegarde d’un site. Mise en ligne d’un site. Migration d’un site.', 'Web Programmation ', 'Content Management System (WordPress or other). Administration and menus (Dashboard, Articles, Media, Pages, Comments, Appearance, Extensions, Users, Tools, Settings). Customization of a site. Custom fields. Page templates. Creating a theme. Extensions and widgets. Creation of an extension in PHP. Themes and extensions. Optimization and security of a site. Backing up a site. Uploading a site. Migration of a site.', '60', 150, 5, 1),
(13, 'Développement application mobiles 1', 'Historique. Philosophie de conception. Design, forme et fonctionnalités. Interface et caractéristiques. Applications les plus populaires. Essai, analyse et particularités techniques. Caractéristiques qui ont suscité l’intérêt du public. Fonctionnement général de l’application. Application multiplateforme sur des appareils différents (Android, Windows, etc.). Clone d’une application populaire. Modes et tendances en lien avec les applications. Prévisions. La plateforme Android. Environnement de développement. Principes de programmation. Création d\'interfaces simples. Navigation et gestion des évènements. Débogage et gestion des erreurs. Personnalisation. Notifications.', 'Mobile Application Development 1 ', 'Background. Design philosophy. Design, form and features. Interface and features. Most popular apps. Test, analysis and technical features. Characteristics that arouse public interest. General operation of applications. Multiplatform application on different devices (Android, Windows, etc.). Clone of a popular application. Modes and trends related to applications. Forecasts. The Android platform. Development environment. Principles of programming. Creating simple interfaces. Navigation and event management. Debugging and error handling. Customization. Notifications.', '85', 250, 6, 1),
(14, 'Developpement application mobiles 2', 'Création d\'interfaces avancées. Persistance et partage de données. Traitement en t?che de fond. Navigation et gestion des év?nements. Débogage et gestion des erreurs. Personnalisation. Notifications. Applications. Langage de programmation objet compilé, multi-paradigmes. Contrôles textuels et délégation. Combinaison de vues. Animations. Liste d\'éléments. Stockage d\'informations. Édition d\'une liste d\'éléments. Géolocalisation et plans. Caméra et photos. Acc?s aux services web. Gestes et dessin. Débogage. Installation et déploiement.', 'Mobile Application Development 2 ', 'Creating advanced interfaces. Persistence and data sharing. Processing in the background. Navigation and event management. Debugging and error handling. Customization. Notifications. Applications. Compiled object programming language, multi-paradigms. Textual controls and delegation. Combination of views. Animations. List of components. Information storage. Editing a list of components. Geolocation and plans. Camera and photos. Access to web services. Gestures and drawing. Debugging. Installation and deployment.', '85', 190, 4, 1),
(15, 'Infonuagique', 'Le cloud computing (Points forts, Points faibles, Amazon Web Services (AWS)). Création et gestion du compte (AWS) et des utilisateurs. Amazon Elastic Compute Cloud. Le stockage des données (Objet, Bucket, Sécurité et cryptage des données). Création et gestion des buckets et objets. Les bases de données (SQL, Amazon Relational Database Service, Amazon DynamoDB). ElastiCache (Notions de grappe et noeud, Création d’une grappe, Gestion des noeuds). Administration réseau. Surveillance et dimensionnement automatique. La solution tout-en-un Elastic Beanstalk. Développement d’applications (Flexibilité, Évolutivité, Fiabilité).', 'Cloud Computing ', 'Cloud computing (Strengths, Weaknesses, Amazon Web Services (AWS)). Creating and managing the account (AWS) and users. Amazon Elastic Compute Cloud. Data storage (Object, Bucket, Security and data encryption). Creation and management of buckets and objects. Databases (SQL, Amazon Relational Database Service, Amazon DynamoDB). ElastiCache (Cluster and Node Concepts, Cluster Creation, Node Management). Network administration. Automatic monitoring and sizing. The all-in-one Elastic Beanstalk solution. Application Development (Flexibility, Scalability, Reliability).', '65', 150, 5, 1),
(16, 'Nouvelles technologies', 'Plateforme Node.js. Syst?me de gestion de bases de données NoSQL MongoDB. Introduction au framework applicatif Angular. Mise en place d\'une application Angular. Les décorateurs. Création et cycle de vie du composant. Templates, bindings et directives. Connexion ? Node.js : les services. Gestion des routes internes. Visualisation d’informations. Test et déploiementNote : Éléments de contenu fournis ? titre indicatif seulement puisqu’ils devront ?tre adaptés réguli?rement en fonction des développements technologies et des besoins des employeurs.', 'New Technologies ', 'Platform Node.js. MongoDB NoSQL database management system. Introduction to the Angular application framework. Setting up an Angular application. Decorators. Creation and life cycle of the component. Templates, bindings and directives. Connecting to Node.js: the services. Management of internal roads. Visualization of information. Test and deploymentNote: Content provided for illustrative purposes only as they will need to be adapted regularly to reflect technology development and employer needs.', '70', 175, 4, 1),
(17, 'Developpement de gestions de projets', 'Périm?tre du projet. Méthode de gestion de projet - Cycles et méthodes (Cycle en cascade, Cycle en V, Cycle en spirale, Cycle itératif). Méthode Agile. Organisation du projet – Contraintes (Contraintes d\'image, Contraintes contextuelles, Contraintes opérationnelles, Contraintes techniques, Contraintes légales, Contraintes sociales, Contraintes écoresponsables). Évaluation des risques. Constitution de l\'équipe. Planification de la production - Plan de communication. Validation du projet. Spécificités du Web. Version zéro ou prototype. Gestion des itérations . Organisation des réunions. Pilotage du projet. Clore le projet sur le plan opérationnel. Clore le projet sur le plan humain.', 'Project Development and Management ', 'Project scope. Project management methodology - Cycles and methods (Cascade Cycle, V Cycle, Spiral Cycle, Iterative Cycle). Agile method. Project organization - Constraints (Image Constraints, Contextual Constraints, Operational Constraints, Technical Constraints, Legal Constraints, Social Constraints, Eco-Responsible Constraints). Risk assessment. Team constitution. Production planning - Communication plan. Project validation. Web specificities. Zero or prototype version. Iteration management. Organization of meetings. Project management. Closing the project operationally. Closing a project on a human level.', '70', 175, 4, 1),
(18, 'Projet integration 1 Programmation orienté objet', 'Examen des spécifications d’origine de l’application. Identification des possibilités d’amélioration. Détermination des fonctionnalités ? ajouter ? l’application. Détermination des modifications ? apporter aux fonctionnalités existantes. Détermination des classes et objets pertinents ? l’application. Identification des relations hiérarchiques entre les classes. Détermination des fonctions propres aux classes identifiées. Détermination des attributs pertinents ? chaque classe d’objets. Production des diagrammes pertinent. Modification du code et contenu de l’application en fonction des modifications identifiées. Validation du fonctionnement de l’application. Analyse des résultats et modification des mod?les, algorithmes et code.', 'Integration Project 1 – Object-Oriented Programming ', 'Review of the original specifications of the application. Identification of opportunities for improvement. Determination of the features to add to the application. Determination of changes to existing features. Determination of classes and objects relevant to the application. Identification of hierarchical relationships between classes. Determination of the functions specific to the identified classes. Determination of attributes relevant to each class of objects. Production of relevant diagrams. Modification of the code and content of the application according to the modifications identified. Validation of the operation of the application. Analysis of results and modification of models, algorithms and code.', '100', 350, 8, 1),
(19, 'Projet integration 2 - Programmation web', 'Examen des spécifications d’origine de l’application. Identification des possibilités d’amélioration. Détermination des fonctionnalités ? ajouter ? l’application. Détermination des modifications ? apporter aux fonctionnalités existantes. Production des diagrammes pertinent. Modification du code et contenu de l’application en fonction des modifications identifiées. Validation du fonctionnement de l’application. Analyse des résultats et modification des mod?les, algorithmes et code.', 'Integration Project 2 – Web Programming ', 'Review of the original specifications of the application. Identification of opportunities for improvement. Determination of the features to add to the application. Determination of changes to existing features. Production of relevant diagrams. Modification of the code and content of the application according to the modifications identified. Validation of the operation of the application. Analysis of results and modification of models, algorithms and code.', '95', 350, 8, 1),
(20, 'Projet de fin etudes Integration', 'Mise en pratique et intégration des compétences personnelles et professionnelles nécessaires ? l’exercice de la profession. Application des connaissances et stratégies apprises en classe dans un contexte d’entreprise. Intégration au milieu professionnel. Collaboration avec l\'équipe de travail. Participation ? des réunions. Prise en charge de projets. Familiarisation avec les outils de fonctionnement. Adaptation ? une culture d’entreprise. Conduite professionnelle conforme ? l’éthique de la profession.Note : Le Projet de fin d\'études – Intégration peut prendre la forme d’un stage de formation en entreprise.', 'End of Studies – Project (Integration) ', 'Application of the technical elements seen during the program. Application of the knowledge, know-how and skills developed during training. Adoption of the rules of professional ethics related to the practice of the profession. The project can take the form of an internship in a professional environment.Note: This project may take the form of an internship in a company.', '125', 460, 10, 0),
(21, 'Profession de gestionnaire en résautique', 'Historique. Fonctions de travail du gestionnaire de réseaux. Exercice du métier en fonction des différents milieux de travail. Rôle du gestionnaire de réseaux et ceux des professions connexes. Exigences des milieux. Gestion de réseaux (Tendances, Utilité, Risques, enjeux et défis). Éthique professionnelle au travail. Principaux composants matériels et logiciels d’un ordinateur. Gestion des fichiers. Personnalisation d’un poste de travail. Création de documents ? l’aide d’outils de communication et de gestion courants en milieu corporatif (Microsoft Word™, Microsoft Excel™, Microsoft Powerpoint™). Utilisation d’Internet pour la communication et la recherche. Méthodes de travail et ergonomie.', 'Network manager profession', 'Historical perspective. Duties of the network manager. Exercise of the profession according to the different work environments. Role of the network manager and those of related professions. Workplace demands. Network Management (Trends, Utility, Risks, Issues and Challenges). Professional ethics at work. Main hardware and software components of a computer. File management. Customizing a workstation. Create documents using common corporate communication and management tools (Microsoft Word ™, Microsoft Excel ™, Microsoft Powerpoint ™). Internet use for communication and research. Working methods and ergonomics.', '60', 175, 5, 1),
(22, 'wqe', 'qwe', 'qwe', 'qwe', '12', 123, 123, 0),
(23, 'wad', 'awd', 'awd', 'awd', '12', 12, 12, 0),
(24, 'wqe', 'qwe', 'qwe', 'qwe', '123', 123, 123, 0),
(25, 'wad', 'wad', 'awd', 'awdawd', '11', 11, 11, 0),
(26, 'asd', 'sadasd', 'asd', 'asd', '12', 12, 12, 0);

--
-- Déclencheurs `cour`
--
DELIMITER $$
CREATE TRIGGER `after_update_cours` AFTER UPDATE ON `cour` FOR EACH ROW BEGIN
	declare Utilisateur varchar(50);
   	Select Createur from tableutilisateur into Utilisateur;
 
    IF OLD.Titre <> new.Titre THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Titre, new.Titre, "Cours", "Modification", CURRENT_DATE );
        
    END IF;
    IF OLD.Description <> new.Description THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Description, new.Description, "Cours", "Modification", CURRENT_DATE);
        
    END IF;
    IF OLD.TitreAnglais <> new.TitreAnglais THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.TitreAnglais, new.TitreAnglais, "Cours", "Modification", CURRENT_DATE);    
    END IF;
    IF OLD.DescriptionAnglais <> new.DescriptionAnglais THEN INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.DescriptionAnglais, new.DescriptionAnglais, "Cours", "Modification", CURRENT_DATE);    
      END IF;
      IF OLD.Temps <> new.Temps THEN 
      INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Temps, new.Temps, "Cours", "Modification", CURRENT_DATE);    
      END IF;
      IF OLD.Cout <> new.Cout THEN 
      INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Cout, new.Cout, "Cours", "Modification", CURRENT_DATE);    
      END IF;
       IF OLD.Credit <> new.Credit THEN 
       INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Credit, new.Credit, "Cours", "Modification", CURRENT_DATE);    
      END IF;
        IF OLD.Etat <> new.Etat THEN 
       INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Etat, new.Etat, "Cours", "Suppression", CURRENT_DATE);    
      END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `coursdetails`
--

CREATE TABLE `coursdetails` (
  `IdEvalutation` int(11) NOT NULL,
  `IdEnseignant` int(11) NOT NULL COMMENT 'Lui qui donne le projet/exam',
  `IdCours` int(11) NOT NULL,
  `Titre` varchar(50) NOT NULL,
  `Description` varchar(255) NOT NULL,
  `Ponctuation` int(3) NOT NULL COMMENT '% de la note finale sur 100'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `coursdetails`
--

INSERT INTO `coursdetails` (`IdEvalutation`, `IdEnseignant`, `IdCours`, `Titre`, `Description`, `Ponctuation`) VALUES
(33, 33, 2, 'Examen 1 - Test litteraire ', 'examen a choix multiple et choix developpement', 50),
(34, 33, 2, 'Projet mi-session', 'Creer un site internet en HTML/CSS. Veuillez commenter votre code(15 points)', 20),
(35, 33, 2, 'Examen Final', 'Examen Final, 100 question a choix multiple et 10 questions a developpement', 25),
(38, 33, 21, 'Examen 1 - Comprehension', 'Examen de 25 question a choix multiple et 5 questions a choix de developpement', 40),
(39, 33, 21, 'Projet final', 'Veuillez concevoir robot', 60);

--
-- Déclencheurs `coursdetails`
--
DELIMITER $$
CREATE TRIGGER `After_update_Examen` AFTER UPDATE ON `coursdetails` FOR EACH ROW BEGIN
	declare Utilisateur varchar(50);
   	Select Createur from tableutilisateur into Utilisateur;
 
    IF OLD.Titre <> new.Titre THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Titre, new.Titre, "CoursDetails", "Modification", CURRENT_DATE );    
    END IF;
    
    IF OLD.Description <> new.Description THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Description, new.Description, "CoursDetails", "Modification", CURRENT_DATE);    
    END IF;
      IF OLD.Ponctuation <> new.Ponctuation THEN 
      INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Ponctuation, new.Ponctuation, "CoursDetails", "Modification", CURRENT_DATE);    
      END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_delete_Examen` AFTER DELETE ON `coursdetails` FOR EACH ROW BEGIN
	declare Utilisateur varchar(50);
   	Select Createur from tableutilisateur into Utilisateur;
    
    INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
    VALUES (Utilisateur, OLD.IdEvalutation, "", "CoursDetails", "Suppression", CURRENT_DATE );
	
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `enseignant`
--

CREATE TABLE `enseignant` (
  `ID` int(11) NOT NULL,
  `NomUtilisateur` varchar(30) NOT NULL,
  `Prenom` varchar(30) NOT NULL,
  `Nom` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `enseignant`
--

INSERT INTO `enseignant` (`ID`, `NomUtilisateur`, `Prenom`, `Nom`) VALUES
(33, 'JMR76', 'Jean-Marc', 'Robert'),
(41, 'adssdadsa', 'qwe', 'wqe'),
(44, 'asd', 'sad', 'asd');

--
-- Déclencheurs `enseignant`
--
DELIMITER $$
CREATE TRIGGER `after_delete_enseignant` AFTER DELETE ON `enseignant` FOR EACH ROW BEGIN
	declare Utilisateur varchar(50);
   	Select Createur from tableutilisateur into Utilisateur;
    
    INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
    VALUES (Utilisateur, OLD.NomUtilisateur, "", "Enseignant", "Suppression", CURRENT_DATE );
	
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_update_enseignant` AFTER UPDATE ON `enseignant` FOR EACH ROW BEGIN
	declare Utilisateur varchar(50);
   	Select Createur from tableutilisateur into Utilisateur;
 
    IF OLD.NomUtilisateur <> new.NomUtilisateur THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.NomUtilisateur, new.NomUtilisateur, "Enseignant", "Modification", CURRENT_DATE );
        
    END IF;
    IF OLD.Prenom <> new.Prenom THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Prenom, new.Prenom, "Enseignant", "Modification", CURRENT_DATE);
        
    END IF;
    IF OLD.Nom <> new.Nom THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Nom, new.Nom, "Enseignant", "Modification", CURRENT_DATE);    
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `enseignantcour`
--

CREATE TABLE `enseignantcour` (
  `IdEnseignant` int(11) NOT NULL,
  `IdCour` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `enseignantcour`
--

INSERT INTO `enseignantcour` (`IdEnseignant`, `IdCour`) VALUES
(11, 2),
(11, 3),
(11, 4),
(11, 5),
(11, 7),
(11, 12),
(11, 13),
(11, 15),
(11, 1),
(11, 6),
(11, 11),
(11, 14),
(11, 16),
(11, 17),
(11, 18),
(11, 19),
(11, 20),
(11, 21),
(28, 21),
(33, 2),
(33, 3),
(33, 4),
(33, 5),
(33, 7),
(33, 8),
(33, 9),
(33, 10),
(33, 12),
(33, 13),
(33, 15),
(33, 1),
(33, 6),
(33, 11),
(33, 14),
(33, 16),
(33, 17),
(33, 18),
(33, 19),
(33, 20),
(33, 21);

-- --------------------------------------------------------

--
-- Structure de la table `enseignantprogramme`
--

CREATE TABLE `enseignantprogramme` (
  `IdEnseignant` int(11) NOT NULL,
  `IdProgramme` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `enseignantprogramme`
--

INSERT INTO `enseignantprogramme` (`IdEnseignant`, `IdProgramme`) VALUES
(11, 1),
(11, 2),
(28, 2),
(33, 1),
(33, 2),
(33, 0);

-- --------------------------------------------------------

--
-- Structure de la table `etat`
--

CREATE TABLE `etat` (
  `ID` int(11) NOT NULL,
  `Etat` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `etat`
--

INSERT INTO `etat` (`ID`, `Etat`) VALUES
(-1, 'Annuler'),
(0, 'Non-Payer'),
(1, 'Payer');

-- --------------------------------------------------------

--
-- Structure de la table `etudiant`
--

CREATE TABLE `etudiant` (
  `ID` int(11) NOT NULL,
  `Utilisateur` varchar(30) NOT NULL,
  `Prenom` varchar(30) NOT NULL,
  `Nom` varchar(30) NOT NULL,
  `Telephone` varchar(20) NOT NULL,
  `Email` varchar(35) NOT NULL,
  `AdresseDeMaison` varchar(30) NOT NULL,
  `Ville` varchar(30) NOT NULL,
  `Province` varchar(30) NOT NULL,
  `CodePostal` varchar(10) NOT NULL,
  `IdProgramme` int(11) NOT NULL,
  `Langue` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `etudiant`
--

INSERT INTO `etudiant` (`ID`, `Utilisateur`, `Prenom`, `Nom`, `Telephone`, `Email`, `AdresseDeMaison`, `Ville`, `Province`, `CodePostal`, `IdProgramme`, `Langue`) VALUES
(37, 'MuertosQc', 'Anthony', 'Chicoine', '5148313934', 'anthony.chicoine@live.ca', '60 avenue du parc', 'Ile-Bizard', 'Quebec', 'H9C2N3', 1, 'Francais'),
(40, 'Charles98', 'Charles', 'Colins', '4389091823', 'charles.colins@live.ca', '1610 avenue du parc', 'Montreal', 'Quebec', 'H9C7N6', 1, 'Francais'),
(41, 'DavyD98', 'Davy', 'Dawson', '4509010232', 'dawson.davy@hotmail.ca', '89 rue de Laval', 'Montreal', 'Quebec', 'G9C3N4', 2, 'Francais'),
(42, 'Charlie89', 'Charlie', 'Loukas', '5148100293', 'charlie@hotmail.com', '791 rue Du Manoir', 'Montréal', 'Quebec', 'H7C3N1', 1, 'Francais'),
(43, 'Ame_boyer1', 'Amélie', 'Boyer', '4389010293', 'amelou@hotmail.ca', '112 rue du Dollard', 'Chateauguay', 'Quebec', '8J12N3', 2, 'English');

--
-- Déclencheurs `etudiant`
--
DELIMITER $$
CREATE TRIGGER `after_delete_etudiant` AFTER DELETE ON `etudiant` FOR EACH ROW BEGIN
	declare Utilisateur varchar(50);
   	Select Createur from tableutilisateur into Utilisateur;
    
    INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
    VALUES (Utilisateur, OLD.Utilisateur, "", "Etudiant", "Suppression", CURRENT_DATE );
	
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_update_etudiant` AFTER UPDATE ON `etudiant` FOR EACH ROW BEGIN
	declare Utilisateur varchar(50);
   	Select Createur from tableutilisateur into 		Utilisateur;
 	
    
    IF OLD.Utilisateur <> new.Utilisateur THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Utilisateur, new.Utilisateur, "Etudiant", "Modification", CURRENT_DATE);
        
    END IF;
    IF OLD.Prenom <> new.Prenom THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Prenom, new.Prenom, "Etudiant", "Modification", CURRENT_DATE);
        
    END IF;
    IF OLD.Nom <> new.Nom THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Nom, new.Nom, "Etudiant", "Modification", CURRENT_DATE);
        
    END IF;
    IF OLD.Telephone <> new.Telephone THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Telephone, new.Telephone, "Etudiant", "Modification", CURRENT_DATE);
        
    END IF;
     IF OLD.Email <> new.Email THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Email, new.Email, "Etudiant", "Modification", CURRENT_DATE);
        
    END IF;
     IF OLD.AdresseDeMaison <> new.AdresseDeMaison THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.AdresseDeMaison, new.AdresseDeMaison, "Etudiant", "Modification", CURRENT_DATE);
        
    END IF;
     IF OLD.Ville <> new.Ville THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Ville, new.Ville, "Etudiant", "Modification", CURRENT_DATE);
        
    END IF;
     IF OLD.Province <> new.Province THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Province, new.Province, "Etudiant", "Modification", CURRENT_DATE);
        
    END IF;
     IF OLD.CodePostal <> new.CodePostal THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.CodePostal, new.CodePostal, "Etudiant", "Modification", CURRENT_DATE);
       
    END IF;
     IF OLD.IdProgramme <> new.IdProgramme THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.IdProgramme, new.IdProgramme, "Etudiant", "Modification", CURRENT_DATE);
        
    END IF;
     IF OLD.Langue <> new.Langue THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Langue, new.Langue, "Etudiant", "Modification", CURRENT_DATE);
       
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `etudiantcour`
--

CREATE TABLE `etudiantcour` (
  `IdResultat` int(11) NOT NULL,
  `IdEtudiant` int(11) NOT NULL,
  `IdCour` int(11) NOT NULL,
  `Resultat` double NOT NULL COMMENT '0 cour commencé\r\n-1 cour pas commencé\r\n-2 Échec/annulé'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `etudiantcour`
--

INSERT INTO `etudiantcour` (`IdResultat`, `IdEtudiant`, `IdCour`, `Resultat`) VALUES
(21, 40, 2, 0),
(22, 40, 3, 0),
(23, 40, 4, 0),
(24, 40, 5, 0),
(25, 40, 7, 0),
(26, 40, 8, 0),
(27, 40, 9, 0),
(28, 40, 10, 0),
(29, 40, 12, 0),
(30, 40, 13, 0),
(31, 40, 15, 0),
(32, 40, 1, 0),
(33, 40, 6, 0),
(34, 40, 11, 0),
(35, 40, 14, 0),
(36, 40, 16, 0),
(37, 40, 17, 0),
(38, 40, 18, 0),
(39, 40, 19, 0),
(40, 40, 20, 0),
(41, 41, 21, 0),
(64, 43, 21, 0),
(125, 37, 2, 0),
(126, 37, 3, 0),
(127, 37, 4, 0),
(128, 37, 5, 0),
(129, 37, 7, 0),
(130, 37, 8, 0),
(131, 37, 9, 0),
(132, 37, 10, 0),
(133, 37, 12, 0),
(134, 37, 13, 0),
(135, 37, 15, 0),
(136, 37, 1, 0),
(137, 37, 6, 0),
(138, 37, 11, 0),
(139, 37, 14, 0),
(140, 37, 16, 0),
(141, 37, 17, 0),
(142, 37, 18, 0),
(143, 37, 19, 0),
(144, 37, 20, 0);

--
-- Déclencheurs `etudiantcour`
--
DELIMITER $$
CREATE TRIGGER `after_update_resultat` BEFORE UPDATE ON `etudiantcour` FOR EACH ROW BEGIN
	declare Utilisateur varchar(50);
   	Select Createur from tableutilisateur into Utilisateur;
 
    IF OLD.Resultat <> new.Resultat THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Resultat, new.Resultat, "Resultat", "Modification", CURRENT_DATE );       
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `etudiantevaluation`
--

CREATE TABLE `etudiantevaluation` (
  `IdEtudiant` int(11) NOT NULL,
  `IdCours` int(11) NOT NULL,
  `IdEvalutation` int(11) NOT NULL,
  `ResultatFinal` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `etudiantevaluation`
--

INSERT INTO `etudiantevaluation` (`IdEtudiant`, `IdCours`, `IdEvalutation`, `ResultatFinal`) VALUES
(34, 2, 30, 67.9),
(34, 2, 31, 78.4),
(34, 2, 32, 56.9),
(37, 2, 33, 67),
(37, 2, 34, 93),
(37, 2, 35, 34),
(41, 21, 38, 0),
(43, 21, 38, 0),
(41, 21, 39, 0),
(43, 21, 39, 0);

-- --------------------------------------------------------

--
-- Structure de la table `frais`
--

CREATE TABLE `frais` (
  `IdFrais` int(11) NOT NULL,
  `IdEtudiant` int(11) NOT NULL,
  `Cout` int(11) NOT NULL,
  `Type` varchar(50) NOT NULL,
  `Etat` int(11) NOT NULL COMMENT '-1 Paiement annuler/ 0 paiement non payer/ 1 Paiement payer'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `frais`
--

INSERT INTO `frais` (`IdFrais`, `IdEtudiant`, `Cout`, `Type`, `Etat`) VALUES
(21, 22, 0, 'Paiement cours', 0),
(37, 35, 0, 'Paiement cours', 0),
(48, 39, 0, 'Paiement cours', 0),
(49, 40, 3950, 'Paiement cours', 0),
(50, 41, 175, 'Paiement cours', 0),
(54, 44, 0, 'Paiement cours', 0),
(55, 42, 0, 'Paiement cours', 0),
(56, 43, 175, 'Paiement cours', 0),
(60, 37, 3975, 'Paiement cours', 0);

--
-- Déclencheurs `frais`
--
DELIMITER $$
CREATE TRIGGER `after_delete_frais` AFTER DELETE ON `frais` FOR EACH ROW BEGIN
	declare Utilisateur varchar(50);
   	Select Createur from tableutilisateur into Utilisateur;
    
    INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
    VALUES (Utilisateur, OLD.IdFrais, "", "Frais", "Suppression", CURRENT_DATE );
	
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_update_frais` AFTER UPDATE ON `frais` FOR EACH ROW BEGIN
	declare Utilisateur varchar(50);
   	Select Createur from tableutilisateur into Utilisateur;
 
    IF OLD.IdEtudiant <> new.IdEtudiant THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.IdEtudiant, new.IdEtudiant, "Frais", "Modification", CURRENT_DATE );       
    END IF;
    
    IF OLD.Cout <> new.Cout THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Cout, new.Cout, "Frais", "Modification", CURRENT_DATE);       
    END IF;
    IF OLD.Type <> new.Type THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Type, new.Type, "Frais", "Modification", CURRENT_DATE);    
    END IF;
    IF OLD.Etat <> new.Etat THEN INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Etat, new.Etat, "Frais", "Modification", CURRENT_DATE);    
      END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `logcreatedaccount`
--

CREATE TABLE `logcreatedaccount` (
  `Id` int(11) NOT NULL,
  `Createur` varchar(50) NOT NULL COMMENT 'Nom administrateur qui a créé le compte',
  `IdUtilisateur` int(11) NOT NULL,
  `TypeAccount` varchar(50) NOT NULL COMMENT 'Si c''est un enseignant, étudiant etc',
  `DateAjout` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `logcreatedaccount`
--

INSERT INTO `logcreatedaccount` (`Id`, `Createur`, `IdUtilisateur`, `TypeAccount`, `DateAjout`) VALUES
(7, 'Anthony Chicoine', 35, 'Etudiant', '2021-03-15'),
(9, 'Anthony Chicoine', 30, 'Professeur', '2021-03-16'),
(10, 'Anthony Chicoine', 32, 'Professeur', '2021-03-22'),
(11, 'Anthony Chicoine', 38, 'Etudiant', '2021-03-22'),
(12, 'Anthony Chicoine', 33, 'Professeur', '2021-03-22'),
(13, 'Anthony Chicoine', 39, 'Etudiant', '2021-03-23'),
(14, 'Anthony Chicoine', 40, 'Etudiant', '2021-03-23'),
(15, 'Anthony Chicoine', 41, 'Etudiant', '2021-03-23'),
(16, 'Anthony Chicoine', 34, 'Professeur', '2021-03-25'),
(17, 'Anthony Chicoine', 42, 'Etudiant', '2021-03-25'),
(18, 'Anthony Chicoine', 43, 'Etudiant', '2021-03-25'),
(19, 'Anthony Chicoine', 44, 'Etudiant', '2021-03-25'),
(20, 'Anthony Chicoine', 35, 'Professeur', '2021-03-29'),
(21, 'Anthony Chicoine', 36, 'Professeur', '2021-03-29'),
(22, 'Jean-Denis Chicoine', 41, 'Professeur', '2021-04-14'),
(23, 'Anthony Chicoine', 43, 'Professeur', '2021-04-14'),
(24, 'Anthony Chicoine', 44, 'Professeur', '2021-04-14');

-- --------------------------------------------------------

--
-- Structure de la table `logcreatedprogcours`
--

CREATE TABLE `logcreatedprogcours` (
  `ID` int(11) NOT NULL,
  `Createur` varchar(50) NOT NULL,
  `IDProgCours` int(11) NOT NULL,
  `Type` varchar(50) NOT NULL,
  `Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `logcreatedprogcours`
--

INSERT INTO `logcreatedprogcours` (`ID`, `Createur`, `IDProgCours`, `Type`, `Date`) VALUES
(3, 'Anthony Chicoine', 4, 'Programme', '2021-03-22'),
(4, 'Anthony Chicoine', 23, 'Cours', '2021-03-22'),
(5, 'Jean-Marc Robert', 5, 'Programme', '2021-03-24'),
(6, 'Anthony Chicoine', 24, 'Cours', '2021-03-26'),
(7, 'Anthony Chicoine', 25, 'Cours', '2021-03-30'),
(8, 'Anthony Chicoine', 26, 'Cours', '2021-03-30');

-- --------------------------------------------------------

--
-- Structure de la table `logupdatedeleteaccount`
--

CREATE TABLE `logupdatedeleteaccount` (
  `ID` int(11) NOT NULL,
  `Createur` varchar(50) NOT NULL,
  `AncienneValeur` varchar(255) NOT NULL,
  `NouvelleValeur` varchar(255) NOT NULL,
  `Table1` varchar(50) NOT NULL,
  `Type1` varchar(50) NOT NULL,
  `Date1` date NOT NULL,
  `NewAdd` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `logupdatedeleteaccount`
--

INSERT INTO `logupdatedeleteaccount` (`ID`, `Createur`, `AncienneValeur`, `NouvelleValeur`, `Table1`, `Type1`, `Date1`, `NewAdd`) VALUES
(45, 'Anthony Chicoine', 'Morin91', 'Morin97', 'Etudiant', 'Modification', '2021-03-18', 0),
(46, 'Anthony Chicoine', '678 rue Sainte-Catherine1', '678 rue Sainte-Catherine', 'Etudiant', 'Modification', '2021-03-18', 0),
(47, 'Anthony Chicoine', 'JMR67', 'JMR671', 'Administration', 'Modification', '2021-03-18', 0),
(48, 'Anthony Chicoine', 'Jean-Marc', 'Jean-Marc1', 'Administration', 'Modification', '2021-03-18', 0),
(49, 'Anthony Chicoine', 'Robert', 'Robert1', 'Administration', 'Modification', '2021-03-18', 0),
(50, 'Anthony Chicoine', '5140934232', '5140934231', 'Administration', 'Modification', '2021-03-18', 0),
(51, 'Anthony Chicoine', 'jeanmarc@live.ca', 'jeanmarc@live.com', 'Administration', 'Modification', '2021-03-18', 0),
(52, 'Anthony Chicoine', '0 rue inconnue', '0 rue inconnue1', 'Administration', 'Modification', '2021-03-18', 0),
(53, 'Anthony Chicoine', 'Montreal', 'Montreal1', 'Administration', 'Modification', '2021-03-18', 0),
(54, 'Anthony Chicoine', 'Quebec', 'Quebec1', 'Administration', 'Modification', '2021-03-18', 0),
(55, 'Anthony Chicoine', 'Y6C8J0', 'Y6C8J1', 'Administration', 'Modification', '2021-03-18', 0),
(56, 'Anthony Chicoine', 'JMR67', 'JMR671', 'Enseignant', 'Modification', '2021-03-18', 0),
(57, 'Anthony Chicoine', 'Jean-Marc', 'Jean-Marc1', 'Enseignant', 'Modification', '2021-03-18', 0),
(58, 'Anthony Chicoine', 'Robert', 'Robert1', 'Enseignant', 'Modification', '2021-03-18', 0),
(59, 'Anthony Chicoine', 'JMR671', 'JMR67', 'Administration', 'Modification', '2021-03-18', 0),
(60, 'Anthony Chicoine', 'Jean-Marc1', 'Jean-Marc', 'Administration', 'Modification', '2021-03-18', 0),
(61, 'Anthony Chicoine', 'Robert1', 'Robert', 'Administration', 'Modification', '2021-03-18', 0),
(62, 'Anthony Chicoine', 'jeanmarc@live.com', 'jeanmarc@live.ca', 'Administration', 'Modification', '2021-03-18', 0),
(63, 'Anthony Chicoine', '0 rue inconnue1', '0 rue inconnue', 'Administration', 'Modification', '2021-03-18', 0),
(64, 'Anthony Chicoine', 'Montreal1', 'Montreal', 'Administration', 'Modification', '2021-03-18', 0),
(65, 'Anthony Chicoine', 'Quebec1', 'Quebec', 'Administration', 'Modification', '2021-03-18', 0),
(66, 'Anthony Chicoine', 'JMR671', 'JMR67', 'Enseignant', 'Modification', '2021-03-18', 0),
(67, 'Anthony Chicoine', 'Jean-Marc1', 'Jean-Marc', 'Enseignant', 'Modification', '2021-03-18', 0),
(68, 'Anthony Chicoine', 'Robert1', 'Robert', 'Enseignant', 'Modification', '2021-03-18', 0),
(69, 'Anthony Chicoine', 'GB871', 'GB87', 'Administration', 'Modification', '2021-03-18', 0),
(70, 'Anthony Chicoine', 'Georges1', 'Georges', 'Administration', 'Modification', '2021-03-18', 0),
(71, 'Anthony Chicoine', 'Boisvert1', 'Boisvert', 'Administration', 'Modification', '2021-03-18', 0),
(72, 'Anthony Chicoine', 'georgesboisvert@live.com', 'georgesboisvert@live.ca', 'Administration', 'Modification', '2021-03-18', 0),
(73, 'Anthony Chicoine', '789 avenue du parc1', '789 avenue du parc', 'Administration', 'Modification', '2021-03-18', 0),
(74, 'Anthony Chicoine', 'Montreal1', 'Montreal', 'Administration', 'Modification', '2021-03-18', 0),
(75, 'Anthony Chicoine', 'Quebec1', 'Quebec', 'Administration', 'Modification', '2021-03-18', 0),
(76, 'Anthony Chicoine', 'GB871', 'GB87', 'Enseignant', 'Modification', '2021-03-18', 0),
(77, 'Anthony Chicoine', 'Georges1', 'Georges', 'Enseignant', 'Modification', '2021-03-18', 0),
(78, 'Anthony Chicoine', 'Boisvert1', 'Boisvert', 'Enseignant', 'Modification', '2021-03-18', 0),
(79, 'Jean-Denis Chicoine', 'Morin97', 'Morin99', 'Etudiant', 'Modification', '2021-03-19', 0),
(80, 'Jean-Denis Chicoine', '', '', 'Etudiant', 'Modification', '2021-03-19', 0),
(81, 'Jean-Denis Chicoine', '2', '0', 'Etudiant', 'Modification', '2021-03-19', 0),
(82, 'Jean-Denis Chicoine', 'Morin99', '', 'Etudiant', 'Suppression', '2021-03-19', 0),
(83, 'Anthony Chicoine', 'GB87', '', 'Administration', 'Suppression', '2021-03-19', 0),
(84, 'Anthony Chicoine', 'GB87', '', 'Enseignant', 'Suppression', '2021-03-19', 0),
(85, 'Anthony Chicoine', '1', '0', 'Etudiant', 'Modification', '2021-03-20', 0),
(86, 'Anthony Chicoine', 'antho7642', '', 'Etudiant', 'Suppression', '2021-03-20', 0),
(87, 'Anthony Chicoine', 'JMR67', '', 'Administration', 'Suppression', '2021-03-22', 0),
(88, 'Anthony Chicoine', 'JMR67', '', 'Enseignant', 'Suppression', '2021-03-22', 0),
(89, 'Anthony Chicoine', '12321312312', 'MuertosQc', 'Etudiant', 'Modification', '2021-03-22', 0),
(90, 'Anthony Chicoine', 'wqe', 'Anthony', 'Etudiant', 'Modification', '2021-03-22', 0),
(91, 'Anthony Chicoine', 'qwe', 'Chicoine', 'Etudiant', 'Modification', '2021-03-22', 0),
(92, 'Anthony Chicoine', '21312312', '5148313934', 'Etudiant', 'Modification', '2021-03-22', 0),
(93, 'Anthony Chicoine', 'qwqweqw', 'anthony.chicoine@live.ca', 'Etudiant', 'Modification', '2021-03-22', 0),
(94, 'Anthony Chicoine', 'qwe', '60 avenue du parc', 'Etudiant', 'Modification', '2021-03-22', 0),
(95, 'Anthony Chicoine', 'wqe', 'Ile-Bizard', 'Etudiant', 'Modification', '2021-03-22', 0),
(96, 'Anthony Chicoine', 'qwe', 'Quebec', 'Etudiant', 'Modification', '2021-03-22', 0),
(97, 'Anthony Chicoine', 'qwe', 'H9C2N3', 'Etudiant', 'Modification', '2021-03-22', 0),
(98, 'Anthony Chicoine', '2', '1', 'Etudiant', 'Modification', '2021-03-22', 0),
(99, 'Anthony Chicoine', '2', '0', 'Etudiant', 'Modification', '2021-03-22', 0),
(100, 'Anthony Chicoine', '123123123qwdq', '', 'Etudiant', 'Suppression', '2021-03-22', 0),
(101, 'Anthony Chicoine', 'dqwdqwdqwd', '', 'Administration', 'Suppression', '2021-03-22', 0),
(102, 'Anthony Chicoine', 'dqwdqwdqwd', '', 'Enseignant', 'Suppression', '2021-03-22', 0),
(103, 'Test', 'Test', 'Test', 'Etudiant', 'Modification', '2021-03-23', 0),
(104, 'Anthony Chicoine', '123456', '132421', 'Etudiant', 'Modification', '2021-03-23', 0),
(105, 'Anthony', 'adams98', 'Adams97', 'Etudiant', 'Modification', '2021-03-23', 0),
(106, 'Anthony', 'Adams', 'Adams97', 'Etudiant', 'Modification', '2021-03-23', 0),
(107, 'Anthony', 'Abbott', 'Adams97', 'Etudiant', 'Modification', '2021-03-23', 0),
(108, 'Anthony', '609 rue Notre dame', 'Adams97', 'Etudiant', 'Modification', '2021-03-23', 0),
(109, 'Anthony', '132421', 'Adams97', 'Etudiant', 'Modification', '2021-03-23', 0),
(110, 'Anthony', 'adamsabb@hotmail.com', 'Adams97', 'Etudiant', 'Modification', '2021-03-23', 0),
(111, 'Anthony', '1', '4', 'Etudiant', 'Modification', '2021-03-23', 0),
(112, 'Anthony', 'Francais', 'Adams97', 'Etudiant', 'Modification', '2021-03-23', 0),
(113, 'Anthony', 'Quebec', 'Adams97', 'Etudiant', 'Modification', '2021-03-23', 0),
(114, 'Anthony', '5147861023', 'Adams97', 'Etudiant', 'Modification', '2021-03-23', 0),
(115, 'Anthony', 'Montréal', 'Adams97', 'Etudiant', 'Modification', '2021-03-23', 0),
(116, 'Anthony Chicoine', 'Adams97', '', 'Etudiant', 'Suppression', '2021-03-23', 0),
(117, 'Anthony Chicoine', 'CC98', 'Charles98', 'Etudiant', 'Modification', '2021-03-23', 0),
(118, 'Anthony Chicoine', 'DD98', 'DavyD98', 'Etudiant', 'Modification', '2021-03-24', 0),
(119, 'Anthony Chicoine', 'test', '', 'Administration', 'Suppression', '2021-03-25', 0),
(120, 'Anthony Chicoine', 'test', '', 'Enseignant', 'Suppression', '2021-03-25', 0),
(121, 'Anthony Chicoine', '325', '350', 'Cours', 'Modification', '2021-03-26', 0),
(122, 'Anthony Chicoine', '1', '0', 'Programme', 'Modification', '2021-03-26', 0),
(123, 'Anthony Chicoine', '0', '1', 'Programme', 'Modification', '2021-03-26', 0),
(124, 'Anthony Chicoine', '1', '0', 'Cours', 'Modification', '2021-03-26', 0),
(125, 'Anthony Chicoine', '0', '1', 'Cours', 'Modification', '2021-03-26', 0),
(126, 'Anthony Chicoine', 'wqe', '', 'Cours', 'Suppression', '2021-03-26', 0),
(127, 'Anthony Chicoine', '1', '0', 'Cours', 'Modification', '2021-03-26', 0),
(128, 'Anthony Chicoine', 'wqe', '', 'Cours', 'Suppression', '2021-03-26', 0),
(129, 'Anthony Chicoine', '175', '1750', 'Frais', 'Modification', '2021-03-26', 0),
(130, 'Anthony Chicoine', '1750', '0', 'Frais', 'Modification', '2021-03-26', 0),
(131, 'Anthony Chicoine', '0', '-1', 'Frais', 'Modification', '2021-03-26', 0),
(132, 'Anthony Chicoine', '53', '', 'Frais', 'Suppression', '2021-03-26', 0),
(134, 'Anthony Chicoine', '0', '-2', 'Resultat', 'Modification', '2021-03-26', 0),
(139, 'Anthony Chicoine', '0', '-2', 'Resultat', 'Modification', '2021-03-26', 0),
(140, 'Anthony Chicoine', '4', '', 'Resultat', 'Suppression', '2021-03-26', 0),
(141, 'Anthony Chicoine', '-2', '0', 'Resultat', 'Modification', '2021-03-26', 0),
(142, 'Anthony Chicoine', '2', '', 'Resultat', 'Suppression', '2021-03-26', 0),
(143, 'Anthony Chicoine', '0', '100', 'Resultat', 'Modification', '2021-03-26', 0),
(144, 'Anthony Chicoine', '2', '', 'Resultat', 'Suppression', '2021-03-26', 0),
(145, 'Anthony Chicoine', '100', '-2', 'Resultat', 'Modification', '2021-03-26', 0),
(146, 'Anthony Chicoine', '40', '', 'Resultat', 'Suppression', '2021-03-26', 0),
(147, 'Anthony Chicoine', '0', '-2', 'Resultat', 'Modification', '2021-03-26', 0),
(148, 'Anthony Chicoine', '37 7', '', 'Resultat', 'Suppression', '2021-03-26', 0),
(149, 'Jean-Marc Robert', 'Créer un site internet avec HTML/CSS. Veuillez commenter votre code(15 points)', 'Creer un site internet en HTML/CSS', 'CoursDetails', 'Modification', '2021-03-26', 0),
(150, 'Jean-Marc Robert', 'Creer un site internet en HTML/CSS', 'Creer un site internet en HTML/CSS. Veuillez commenter votre code(15 points)', 'CoursDetails', 'Modification', '2021-03-26', 0),
(151, 'Jean-Marc Robert', '1', '0', 'Cours', 'Modification', '2021-03-28', 0),
(154, 'Jean-Marc Robert', '36', '', 'CoursDetails', 'Suppression', '2021-03-28', 0),
(155, 'Anthony Chicoine', 'Bases de la création d’un site web. Emploi d’un langage de balisage conçu pour représenter les pages web (HTML). Feuilles de style en cascade (CSS). Création d’applications Web. Utilisation de scripts pour valider l’intégrité des données. Règles d’ergonom', 'Bases de la création d’un site web. Emploi d’un langage de balisage con?u pour représenter les pages web (HTML). Feuilles de style en cascade (CSS). Création d’applications Web. Utilisation de scripts pour valider l’intégrité des données. R?gles d’ergonom', 'Cours', 'Modification', '2021-03-28', 0),
(156, 'Anthony Chicoine', '0', '1', 'Cours', 'Modification', '2021-03-28', 0),
(157, 'Anthony Chicoine', 'Développement web 1', '', 'Cours', 'Suppression', '2021-03-28', 0),
(158, 'Jean-Marc Robert', '37', '', 'CoursDetails', 'Suppression', '2021-03-28', 0),
(159, 'Anthony Chicoine', 'sdadasd', '', 'Administration', 'Suppression', '2021-03-29', 0),
(160, 'Anthony Chicoine', 'sdadasd', '', 'Enseignant', 'Suppression', '2021-03-29', 0),
(161, 'Anthony Chicoine', 'test', '', 'Administration', 'Suppression', '2021-03-29', 0),
(162, 'Anthony Chicoine', 'test', '', 'Enseignant', 'Suppression', '2021-03-29', 0),
(163, 'Anthony Chicoine', '-1', '1', 'Cours', 'Modification', '2021-03-29', 0),
(164, 'Anthony Chicoine', 'Profession de gestionnaire en résautique', '', 'Cours', 'Suppression', '2021-03-29', 0),
(165, 'Anthony Chicoine', '0', '31', 'Resultat', 'Modification', '2021-03-29', 0),
(166, 'Anthony Chicoine', '44 21', '', 'Resultat', 'Suppression', '2021-03-29', 0),
(167, 'Anthony Chicoine', '44 21', '', 'Resultat', 'Suppression', '2021-03-29', 0),
(168, 'Anthony Chicoine', '31', '84', 'Resultat', 'Modification', '2021-03-29', 0),
(169, 'Anthony Chicoine', '44 21', '', 'Resultat', 'Suppression', '2021-03-29', 0),
(170, 'Anthony Chicoine', 'Tim98', '', 'Etudiant', 'Suppression', '2021-03-29', 0),
(171, 'Anthony Chicoine', '1', '0', 'Cours', 'Modification', '2021-03-30', 0),
(172, 'Anthony Chicoine', '1', '0', 'Cours', 'Suppression', '2021-03-30', 0),
(173, 'Anthony Chicoine', '0', '1', 'Programme', 'Suppression', '2021-03-30', 0),
(174, 'Anthony Chicoine', '1', '0', 'Programme', 'Suppression', '2021-03-30', 0),
(175, 'Anthony Chicoine', '0', '1', 'Programme', 'Suppression', '2021-03-30', 0),
(176, 'Anthony Chicoine', '0', '1', 'Programme', 'Suppression', '2021-03-30', 0),
(177, 'Antho', '1', '0', 'Programme', 'Suppression', '2021-03-30', 0),
(178, 'Anthony Chicoine', '1', '0', 'Programme', 'Suppression', '2021-03-30', 0),
(179, 'Jean-Denis Chicoine', '0', '1', 'Programme', 'Suppression', '2021-03-30', 0),
(180, 'Jean-Denis Chicoine', '1', '0', 'Programme', 'Suppression', '2021-03-30', 0),
(181, 'Anthony Chicoine', '0', '1', 'Programme', 'Suppression', '2021-03-30', 0),
(182, 'Anthony Chicoine', '1', '0', 'Programme', 'Suppression', '2021-03-30', 0),
(183, 'Anthony Chicoine', 'Ame_boyer', 'Ame_boyer1', 'Etudiant', 'Modification', '2021-04-08', 0),
(184, 'Anthony Chicoine', '112 rue du Dollars', '112 rue du Dollard', 'Etudiant', 'Modification', '2021-04-08', 0),
(185, 'Anthony Chicoine', 'Francais', 'English', 'Etudiant', 'Modification', '2021-04-08', 0),
(186, 'Anthony Chicoine', '52', '', 'Frais', 'Suppression', '2021-04-08', 0),
(187, 'Anthony Chicoine', 'MuertosQc', 'Muertos', 'Etudiant', 'Modification', '2021-04-08', 0),
(188, 'Anthony Chicoine', '5148313934', '5148313933', 'Etudiant', 'Modification', '2021-04-08', 0),
(191, 'Anthony Chicoine', 'Muertos', 'MuertosQc', 'Etudiant', 'Modification', '2021-04-08', 0),
(192, 'Anthony Chicoine', '5148313933', '5148313934', 'Etudiant', 'Modification', '2021-04-08', 0),
(193, 'Anthony Chicoine', 'Francais', 'English', 'Etudiant', 'Modification', '2021-04-08', 0),
(194, 'Anthony Chicoine', '57', '', 'Frais', 'Suppression', '2021-04-08', 0),
(195, 'Anthony Chicoine', 'MuertosQc', 'Muertos', 'Etudiant', 'Modification', '2021-04-08', 0),
(196, 'Anthony Chicoine', '5148313934', '5148313933', 'Etudiant', 'Modification', '2021-04-08', 0),
(197, 'Anthony Chicoine', 'English', 'Francais', 'Etudiant', 'Modification', '2021-04-08', 0),
(198, 'Anthony Chicoine', '58', '', 'Frais', 'Suppression', '2021-04-08', 0),
(199, 'Anthony Chicoine', 'Muertos', 'MuertosQc', 'Etudiant', 'Modification', '2021-04-08', 0),
(200, 'Anthony Chicoine', '5148313933', '5148313934', 'Etudiant', 'Modification', '2021-04-08', 0),
(201, 'Anthony Chicoine', '59', '', 'Frais', 'Suppression', '2021-04-08', 0),
(202, 'Anthony Chicoine', '123', 'cf83e1357eefb8bdf1542850d66d80', 'Administration', 'Modification', '2021-04-12', 0),
(203, 'Anthony Chicoine', 'cf83e1357eefb8bdf1542850d66d80', '??.?r=?g?_o.5', 'Administration', 'Modification', '2021-04-13', 0),
(204, 'Anthony Chicoine', '??.?r=?g?_o.5', '123', 'Administration', 'Modification', '2021-04-13', 0),
(205, 'Anthony Chicoine', '123', '??.?r=?g?_o.5', 'Administration', 'Modification', '2021-04-13', 0),
(206, 'Anthony Chicoine', '??.?r=?g?_o.5', '123', 'Administration', 'Modification', '2021-04-13', 0),
(207, 'Anthony Chicoine', '123', '?Մ???L???q???', 'Administration', 'Modification', '2021-04-13', 0),
(208, 'Anthony Chicoine', '?Մ???L???q???', '123', 'Administration', 'Modification', '2021-04-13', 0),
(209, 'Anthony Chicoine', '123', 'uUG5i?N?Ȅ?C?\'', 'Administration', 'Modification', '2021-04-13', 0),
(210, 'Anthony Chicoine', 'uUG5i?N?Ȅ?C?\'', '123', 'Administration', 'Modification', '2021-04-13', 0),
(211, 'Anthony Chicoine', '123', 'uUG5i?N?Ȅ?C?\'', 'Administration', 'Modification', '2021-04-13', 0),
(212, 'Anthony Chicoine', 'uUG5i?N?Ȅ?C?\'', '123', 'Administration', 'Modification', '2021-04-13', 0),
(213, 'Anthony Chicoine', '123', 'uUG5i?N?Ȅ?C?\'', 'Administration', 'Modification', '2021-04-13', 0),
(214, 'Anthony Chicoine', 'uUG5i?N?Ȅ?C?\'', '123', 'Administration', 'Modification', '2021-04-13', 0),
(215, 'Anthony Chicoine', '123', 'uUG5i?N?Ȅ?C?\'', 'Administration', 'Modification', '2021-04-13', 0),
(216, 'Anthony Chicoine', 'uUG5i?N?Ȅ?C?\'', '123', 'Administration', 'Modification', '2021-04-13', 0),
(217, 'Anthony Chicoine', '123', 'uUG5i?N?Ȅ?C?\'', 'Administration', 'Modification', '2021-04-13', 0),
(218, 'Anthony Chicoine', 'uUG5i?N?Ȅ?C?\'', 'Qu???+\Z?(?q?????', 'Administration', 'Modification', '2021-04-13', 0),
(219, 'Anthony Chicoine', 'Qu???+\Z?(?q?????', '??%\"baN?1?\ZG', 'Administration', 'Modification', '2021-04-13', 0),
(220, 'Anthony Chicoine', '??%\"baN?1?\ZG', '#', 'Administration', 'Modification', '2021-04-13', 0),
(221, 'Anthony Chicoine', '#', '\Z4?;d?w:?#9?o?~', 'Administration', 'Modification', '2021-04-13', 0),
(222, 'Anthony Chicoine', '\Z4?;d?w:?#9?o?~', '?Y???9=?yI???nh?', 'Administration', 'Modification', '2021-04-13', 0),
(223, 'Anthony Chicoine', '?Y???9=?yI???nh?', '', 'Administration', 'Modification', '2021-04-14', 0),
(224, 'Anthony Chicoine', '', 'E?Y??ҦW??Y?:	', 'Administration', 'Modification', '2021-04-14', 0),
(225, 'Jean-Denis Chicoine', '123123123', '', 'Administration', 'Suppression', '2021-04-14', 0),
(226, 'Jean-Denis Chicoine', 'qwe', '', 'Administration', 'Suppression', '2021-04-14', 0),
(227, 'Jean-Denis Chicoine', ' sadasdas d', '', 'Administration', 'Suppression', '2021-04-14', 0),
(228, 'Jean-Denis Chicoine', '123', 'E?Y??ҦW??Y?:	', 'Administration', 'Modification', '2021-04-14', 0),
(229, 'Jean-Denis Chicoine', '123', '#E', 'Administration', 'Modification', '2021-04-14', 0),
(230, 'Jean-Denis Chicoine', '123', '#!21#1#', 'Administration', 'Modification', '2021-04-14', 0),
(231, 'Jean-Denis Chicoine', '123', '!1#', 'Administration', 'Modification', '2021-04-14', 0),
(232, 'Jean-Denis Chicoine', 'E?Y??ҦW??Y?:	', '#', 'Administration', 'Modification', '2021-04-14', 0),
(233, 'Jean-Denis Chicoine', '#E', '#', 'Administration', 'Modification', '2021-04-14', 0),
(234, 'Jean-Denis Chicoine', 'eqw', '', 'Administration', 'Suppression', '2021-04-14', 0),
(235, 'Jean-Denis Chicoine', 'adssdadsa', '', 'Administration', 'Suppression', '2021-04-14', 0),
(236, 'Jean-Denis Chicoine', 'Admin_2', '', 'Administration', 'Suppression', '2021-04-14', 0),
(237, 'Jean-Denis Chicoine', '#', 'E?Y??ҦW??Y?:	', 'Administration', 'Modification', '2021-04-14', 0),
(238, 'Jean-Denis Chicoine', '#!21#1#', '*<*??|l]?؃?Պ?', 'Administration', 'Modification', '2021-04-14', 0),
(239, 'Jean-Denis Chicoine', '!1#', '?^?\r???ہ$f??O?', 'Administration', 'Modification', '2021-04-14', 0),
(240, 'Jean-Denis Chicoine', 'E?Y??ҦW??Y?:	', '?#??fy*q?y?6?\n', 'Administration', 'Modification', '2021-04-14', 0),
(241, 'Jean-Denis Chicoine', '*<*??|l]?؃?Պ?', '??$(?}?⅌???$W', 'Administration', 'Modification', '2021-04-14', 0),
(242, 'Jean-Denis Chicoine', '?^?\r???ہ$f??O?', 'xSe\r?pUj???N?', 'Administration', 'Modification', '2021-04-14', 0),
(243, 'Jean-Denis Chicoine', '?#??fy*q?y?6?\n', '4', 'Administration', 'Modification', '2021-04-14', 0),
(244, 'Jean-Denis Chicoine', '4', '?#??fy*q?y?6?\n', 'Administration', 'Modification', '2021-04-14', 0),
(245, 'Jean-Denis Chicoine', '123', 'P?\"?I?$BX ???S?a&\Z?\'???T?d??', 'Administration', 'Modification', '2021-04-14', 0),
(246, 'Anthony Chicoine', 'qwe', '', 'Administration', 'Suppression', '2021-04-14', 0),
(247, 'Anthony Chicoine', 'qwe', '', 'Enseignant', 'Suppression', '2021-04-14', 0),
(248, 'Anthony Chicoine', '123', '(???<?*?0\\???7?R???sc???X<?', 'Administration', 'Modification', '2021-04-14', 1);

-- --------------------------------------------------------

--
-- Structure de la table `programme`
--

CREATE TABLE `programme` (
  `ID` int(11) NOT NULL,
  `Titre` varchar(60) NOT NULL,
  `TitreAnglais` varchar(50) NOT NULL,
  `Temps` varchar(5) NOT NULL,
  `Cout` int(11) NOT NULL,
  `Etat` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `programme`
--

INSERT INTO `programme` (`ID`, `Titre`, `TitreAnglais`, `Temps`, `Cout`, `Etat`) VALUES
(1, 'Programmeur Analiste oriente internet', 'Programmer Analyst / Internet Solutions Developer ', '0', 0, 1),
(2, 'Gestionnaire en réseautique:Spécialiste sécurité', 'Network and internet security specialist', '0', 0, 1),
(4, 'awd', 'wad', '0', 0, 0),
(5, 'test', 'tets', '0', 0, 0);

--
-- Déclencheurs `programme`
--
DELIMITER $$
CREATE TRIGGER `after_update_programme` AFTER UPDATE ON `programme` FOR EACH ROW BEGIN
	declare Utilisateur varchar(50);
   	Select Createur from tableutilisateur into Utilisateur;
 
    IF OLD.Titre <> new.Titre THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Titre, new.Titre, "Programme", "Modification", CURRENT_DATE );    
    END IF;
    
    IF OLD.TitreAnglais <> new.TitreAnglais THEN
        INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.TitreAnglais, new.TitreAnglais, "Programme", "Modification", CURRENT_DATE);    
    END IF;
      IF OLD.Temps <> new.Temps THEN 
      INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Temps, new.Temps, "Cours", "Modification", CURRENT_DATE);    
      END IF;
      IF OLD.Cout <> new.Cout THEN 
      INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Cout, new.Cout, "Programme", "Modification", CURRENT_DATE);    
      END IF;
       IF OLD.Etat <> new.Etat THEN 
       INSERT INTO logupdatedeleteaccount(Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1)
        VALUES (Utilisateur, OLD.Etat, new.Etat, "Programme", "Suppression", CURRENT_DATE);    
      END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `programmecours`
--

CREATE TABLE `programmecours` (
  `IdProgramme` int(11) NOT NULL,
  `IdCours` int(11) NOT NULL,
  `Ordre` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `programmecours`
--

INSERT INTO `programmecours` (`IdProgramme`, `IdCours`, `Ordre`) VALUES
(1, 2, 1),
(1, 3, 2),
(1, 4, 3),
(1, 5, 4),
(1, 7, 5),
(1, 8, 6),
(1, 9, 7),
(1, 10, 8),
(1, 12, 9),
(1, 13, 10),
(1, 15, 11),
(1, 1, 12),
(1, 6, 13),
(1, 11, 14),
(1, 14, 15),
(1, 16, 16),
(1, 17, 17),
(1, 18, 18),
(1, 19, 19),
(1, 20, 20),
(2, 21, 1);

-- --------------------------------------------------------

--
-- Structure de la table `tablecommentaire`
--

CREATE TABLE `tablecommentaire` (
  `IdCommentaire` int(11) NOT NULL,
  `Createur` varchar(50) NOT NULL,
  `ID` int(11) NOT NULL COMMENT 'ID enseignant/etudiant/frais/exam etc',
  `Type` varchar(50) NOT NULL,
  `Commentaire` longtext NOT NULL,
  `Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `tablecommentaire`
--

INSERT INTO `tablecommentaire` (`IdCommentaire`, `Createur`, `ID`, `Type`, `Commentaire`, `Date`) VALUES
(1, 'Anthony Chicoine', 34, 'Etudiant', 'L\'etudiant a quitter l\'ecole', '2021-03-20'),
(2, 'Anthony Chicoine', 38, 'Etudiant', 'test etudiant', '2021-03-22'),
(3, 'Anthony Chicoine', 32, 'Enseignant', 'test enseignant', '2021-03-22'),
(4, 'Anthony Chicoine', 0, 'Programme', 'test Programme', '2021-03-22'),
(5, 'Anthony Chicoine', 23, 'Cours', 'test Cours', '2021-03-22'),
(6, 'Anthony Chicoine', 42, 'Frais', 'test Frais', '2021-03-22'),
(7, 'Anthony Chicoine', 0, 'Resultat', 'test Resultat', '2021-03-22'),
(8, 'Anthony Chicoine', 0, 'Resultat', 'Test Supprimer', '2021-03-22'),
(9, 'Anthony Chicoine', 39, 'Etudiant', 'wad', '2021-03-23'),
(10, 'Anthony Chicoine', 34, 'Enseignant', 'Enseignant test', '2021-03-25'),
(11, 'Anthony Chicoine', 24, 'Cours', 'test', '2021-03-26'),
(12, 'Anthony Chicoine', 24, 'Cours', 'test', '2021-03-26'),
(13, 'Anthony Chicoine', 53, 'Frais', 'mauvaise facture', '2021-03-26'),
(14, 'Anthony Chicoine', 0, 'Resultat', 'test', '2021-03-26'),
(15, 'Anthony Chicoine', 0, 'Resultat', 'test', '2021-03-26'),
(16, 'Anthony Chicoine', 0, 'Resultat', 'test', '2021-03-26'),
(17, 'Anthony Chicoine', 0, 'Resultat', 'test', '2021-03-26'),
(18, 'Jean-Marc Robert', 2, 'Cours', 'examen de pauvre', '2021-03-28'),
(19, 'Jean-Marc Robert', 2, 'Cours', 'examen de pauvre', '2021-03-28'),
(20, 'Jean-Marc Robert', 0, 'Examen', 'QWEWQE', '2021-03-28'),
(21, 'Jean-Marc Robert', 0, 'Examen', 'TEST', '2021-03-28'),
(22, 'Anthony Chicoine', 35, 'Enseignant', 'test', '2021-03-29'),
(23, 'Anthony Chicoine', 36, 'Enseignant', 'test', '2021-03-29'),
(24, 'Anthony Chicoine', 44, 'Etudiant', 'Il a quitte l\'ecole', '2021-03-29'),
(25, 'Anthony Chicoine', 25, 'Cours', 'test after delete', '2021-03-30'),
(26, 'Anthony Chicoine', 26, 'Cours', 'test trigger', '2021-03-30'),
(27, 'Anthony Chicoine', 0, 'Programme', 'test', '2021-03-30'),
(28, 'Anthony Chicoine', 0, 'Programme', 'test', '2021-03-30'),
(29, 'Anthony Chicoine', 0, 'Programme', 'test', '2021-03-30'),
(30, 'Jean-Denis Chicoine', 0, 'Programme', 'test', '2021-03-30'),
(31, 'Anthony Chicoine', 0, 'Programme', 'test', '2021-03-30'),
(32, 'Jean-Denis Chicoine', 37, 'Enseignant', 'qwe', '2021-04-14'),
(33, 'Jean-Denis Chicoine', 39, 'Enseignant', 'qwe', '2021-04-14'),
(34, 'Jean-Denis Chicoine', 39, 'Enseignant', 'qwe', '2021-04-14'),
(35, 'Anthony Chicoine', 43, 'Enseignant', 'test', '2021-04-14');

-- --------------------------------------------------------

--
-- Structure de la table `tablenotification`
--

CREATE TABLE `tablenotification` (
  `IdNotification` int(11) NOT NULL,
  `Auteur` varchar(50) NOT NULL,
  `IdLog` int(11) NOT NULL,
  `IdAdmin` int(11) NOT NULL,
  `Etat` int(11) NOT NULL COMMENT '1 = pas lu / 0 = lu'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `tablenotification`
--

INSERT INTO `tablenotification` (`IdNotification`, `Auteur`, `IdLog`, `IdAdmin`, `Etat`) VALUES
(1, 'Anthony Chicoine', 199, 1, 1),
(2, 'Anthony Chicoine', 200, 1, 1),
(3, 'Anthony Chicoine', 199, 31, 1),
(4, 'Anthony Chicoine', 200, 31, 1),
(5, 'Jean-Denis Chicoine', 201, 1, 1),
(6, 'Jean-Denis Chicoine', 202, 1, 1),
(7, 'Jean-Denis Chicoine', 203, 1, 1),
(8, 'Jean-Denis Chicoine', 204, 1, 1),
(9, 'Jean-Denis Chicoine', 205, 1, 1),
(10, 'Jean-Denis Chicoine', 206, 1, 1),
(11, 'Jean-Denis Chicoine', 207, 1, 1),
(12, 'Jean-Denis Chicoine', 208, 1, 1),
(13, 'Jean-Denis Chicoine', 209, 1, 1),
(14, 'Jean-Denis Chicoine', 210, 1, 1),
(15, 'Jean-Denis Chicoine', 211, 1, 1),
(16, 'Jean-Denis Chicoine', 212, 1, 1),
(17, 'Jean-Denis Chicoine', 213, 1, 1),
(18, 'Jean-Denis Chicoine', 214, 1, 1),
(19, 'Jean-Denis Chicoine', 215, 1, 1),
(20, 'Jean-Denis Chicoine', 216, 1, 1),
(21, 'Jean-Denis Chicoine', 217, 1, 1),
(22, 'Jean-Denis Chicoine', 218, 1, 1),
(23, 'Jean-Denis Chicoine', 219, 1, 1),
(24, 'Jean-Denis Chicoine', 220, 1, 1),
(25, 'Jean-Denis Chicoine', 221, 1, 1),
(26, 'Jean-Denis Chicoine', 222, 1, 1),
(27, 'Jean-Denis Chicoine', 223, 1, 1),
(28, 'Jean-Denis Chicoine', 224, 1, 1),
(29, 'Jean-Denis Chicoine', 225, 1, 1),
(30, 'Jean-Denis Chicoine', 201, 31, 1),
(31, 'Jean-Denis Chicoine', 202, 31, 1),
(32, 'Jean-Denis Chicoine', 203, 31, 1),
(33, 'Jean-Denis Chicoine', 204, 31, 1),
(34, 'Jean-Denis Chicoine', 205, 31, 1),
(35, 'Jean-Denis Chicoine', 206, 31, 1),
(36, 'Jean-Denis Chicoine', 207, 31, 1),
(37, 'Jean-Denis Chicoine', 208, 31, 1),
(38, 'Jean-Denis Chicoine', 209, 31, 1),
(39, 'Jean-Denis Chicoine', 210, 31, 1),
(40, 'Jean-Denis Chicoine', 211, 31, 1),
(41, 'Jean-Denis Chicoine', 212, 31, 1),
(42, 'Jean-Denis Chicoine', 213, 31, 1),
(43, 'Jean-Denis Chicoine', 214, 31, 1),
(44, 'Jean-Denis Chicoine', 215, 31, 1),
(45, 'Jean-Denis Chicoine', 216, 31, 1),
(46, 'Jean-Denis Chicoine', 217, 31, 1),
(47, 'Jean-Denis Chicoine', 218, 31, 1),
(48, 'Jean-Denis Chicoine', 219, 31, 1),
(49, 'Jean-Denis Chicoine', 220, 31, 1),
(50, 'Jean-Denis Chicoine', 221, 31, 1),
(51, 'Jean-Denis Chicoine', 222, 31, 1),
(52, 'Jean-Denis Chicoine', 223, 31, 1),
(53, 'Jean-Denis Chicoine', 224, 31, 1),
(54, 'Jean-Denis Chicoine', 225, 31, 1),
(55, 'Jean-Denis Chicoine', 226, 1, 1),
(56, 'Jean-Denis Chicoine', 226, 31, 1),
(57, 'Jean-Denis Chicoine', 227, 1, 1),
(58, 'Jean-Denis Chicoine', 227, 31, 1),
(59, 'Anthony Chicoine', 228, 1, 1),
(60, 'Anthony Chicoine', 229, 1, 1),
(61, 'Anthony Chicoine', 230, 1, 1),
(62, 'Anthony Chicoine', 231, 1, 1),
(63, 'Anthony Chicoine', 232, 1, 1),
(64, 'Anthony Chicoine', 233, 1, 1),
(65, 'Anthony Chicoine', 234, 1, 1),
(66, 'Anthony Chicoine', 235, 1, 1),
(67, 'Anthony Chicoine', 236, 1, 1),
(68, 'Anthony Chicoine', 237, 1, 1),
(69, 'Anthony Chicoine', 238, 1, 1),
(70, 'Anthony Chicoine', 239, 1, 1),
(71, 'Anthony Chicoine', 240, 1, 1),
(72, 'Anthony Chicoine', 241, 1, 1),
(73, 'Anthony Chicoine', 242, 1, 1),
(74, 'Anthony Chicoine', 243, 1, 1),
(75, 'Anthony Chicoine', 244, 1, 1),
(76, 'Anthony Chicoine', 245, 1, 1),
(77, 'Anthony Chicoine', 246, 1, 1),
(78, 'Anthony Chicoine', 247, 1, 1),
(79, 'Anthony Chicoine', 228, 31, 1),
(80, 'Anthony Chicoine', 229, 31, 1),
(81, 'Anthony Chicoine', 230, 31, 1),
(82, 'Anthony Chicoine', 231, 31, 1),
(83, 'Anthony Chicoine', 232, 31, 1),
(84, 'Anthony Chicoine', 233, 31, 1),
(85, 'Anthony Chicoine', 234, 31, 1),
(86, 'Anthony Chicoine', 235, 31, 1),
(87, 'Anthony Chicoine', 236, 31, 1),
(88, 'Anthony Chicoine', 237, 31, 1),
(89, 'Anthony Chicoine', 238, 31, 1),
(90, 'Anthony Chicoine', 239, 31, 1),
(91, 'Anthony Chicoine', 240, 31, 1),
(92, 'Anthony Chicoine', 241, 31, 1),
(93, 'Anthony Chicoine', 242, 31, 1),
(94, 'Anthony Chicoine', 243, 31, 1),
(95, 'Anthony Chicoine', 244, 31, 1),
(96, 'Anthony Chicoine', 245, 31, 1),
(97, 'Anthony Chicoine', 246, 31, 1),
(98, 'Anthony Chicoine', 247, 31, 1);

-- --------------------------------------------------------

--
-- Structure de la table `tableutilisateur`
--

CREATE TABLE `tableutilisateur` (
  `Createur` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `tableutilisateur`
--

INSERT INTO `tableutilisateur` (`Createur`) VALUES
('Anthony Chicoine');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `administration`
--
ALTER TABLE `administration`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `ancienenseignant`
--
ALTER TABLE `ancienenseignant`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `ancienetudiant`
--
ALTER TABLE `ancienetudiant`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `clavardage`
--
ALTER TABLE `clavardage`
  ADD PRIMARY KEY (`IdMessage`);

--
-- Index pour la table `cour`
--
ALTER TABLE `cour`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `coursdetails`
--
ALTER TABLE `coursdetails`
  ADD PRIMARY KEY (`IdEvalutation`);

--
-- Index pour la table `enseignant`
--
ALTER TABLE `enseignant`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `etudiant`
--
ALTER TABLE `etudiant`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `etudiantcour`
--
ALTER TABLE `etudiantcour`
  ADD PRIMARY KEY (`IdResultat`);

--
-- Index pour la table `frais`
--
ALTER TABLE `frais`
  ADD PRIMARY KEY (`IdFrais`);

--
-- Index pour la table `logcreatedaccount`
--
ALTER TABLE `logcreatedaccount`
  ADD PRIMARY KEY (`Id`);

--
-- Index pour la table `logcreatedprogcours`
--
ALTER TABLE `logcreatedprogcours`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `logupdatedeleteaccount`
--
ALTER TABLE `logupdatedeleteaccount`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `programme`
--
ALTER TABLE `programme`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `tablecommentaire`
--
ALTER TABLE `tablecommentaire`
  ADD PRIMARY KEY (`IdCommentaire`);

--
-- Index pour la table `tablenotification`
--
ALTER TABLE `tablenotification`
  ADD PRIMARY KEY (`IdNotification`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `administration`
--
ALTER TABLE `administration`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT pour la table `ancienenseignant`
--
ALTER TABLE `ancienenseignant`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT pour la table `ancienetudiant`
--
ALTER TABLE `ancienetudiant`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT pour la table `clavardage`
--
ALTER TABLE `clavardage`
  MODIFY `IdMessage` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `cour`
--
ALTER TABLE `cour`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT pour la table `coursdetails`
--
ALTER TABLE `coursdetails`
  MODIFY `IdEvalutation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT pour la table `etudiant`
--
ALTER TABLE `etudiant`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT pour la table `etudiantcour`
--
ALTER TABLE `etudiantcour`
  MODIFY `IdResultat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=145;

--
-- AUTO_INCREMENT pour la table `frais`
--
ALTER TABLE `frais`
  MODIFY `IdFrais` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

--
-- AUTO_INCREMENT pour la table `logcreatedaccount`
--
ALTER TABLE `logcreatedaccount`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT pour la table `logcreatedprogcours`
--
ALTER TABLE `logcreatedprogcours`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `logupdatedeleteaccount`
--
ALTER TABLE `logupdatedeleteaccount`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=249;

--
-- AUTO_INCREMENT pour la table `programme`
--
ALTER TABLE `programme`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `tablecommentaire`
--
ALTER TABLE `tablecommentaire`
  MODIFY `IdCommentaire` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT pour la table `tablenotification`
--
ALTER TABLE `tablenotification`
  MODIFY `IdNotification` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=99;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
