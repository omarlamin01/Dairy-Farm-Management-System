-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost
-- Généré le : lun. 07 nov. 2022 à 11:37
-- Version du serveur : 8.0.28
-- Version de PHP : 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `dairyfarm`
--

-- --------------------------------------------------------

--
-- Structure de la table `animal`
--

CREATE TABLE `animal` (
  `id` int NOT NULL,
  `birth_date` date NOT NULL,
  `purchase_date` date DEFAULT NULL,
  `routine_id` int NOT NULL,
  `race_id` int NOT NULL,
  `type` enum('cow','bull','calf') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `animalsale`
--

CREATE TABLE `animal_sale` (
  `id` int NOT NULL,
  `client_id` int NOT NULL,
  `animal_id` int DEFAULT NULL,
  `price` float NOT NULL,
  `sale_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `id` int NOT NULL,
  `name` varchar(50) NOT NULL,
  `type` enum('person','company') DEFAULT NULL,
  `phone` int NOT NULL,
  `email` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `consuming`
--

CREATE TABLE `consuming` (
  `id` int NOT NULL,
  `stock_id` int NOT NULL,
  `quantity` float NOT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `employee`
--

CREATE TABLE `employee` (
  `id` int NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `gender` enum('F','M') NOT NULL,
  `cin` varchar(20) NOT NULL,
  `email` varchar(20) NOT NULL,
  `phone` int NOT NULL,
  `address` varchar(30) NOT NULL,
  `salary` float NOT NULL,
  `recruitment_date` date NOT NULL,
  `contract_type` enum('CDI','CDD','CTT') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `healthstatus`
--

CREATE TABLE `health_status` (
  `id` int NOT NULL,
  `animal_id` int DEFAULT NULL,
  `vaccine_id` int NOT NULL,
  `weight` float DEFAULT NULL,
  `breading` float DEFAULT NULL,
  `age` float DEFAULT NULL,
  `date_control` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `milkcollection`
--

CREATE TABLE `milk_collection` (
  `id` int NOT NULL,
  `cow_id` int NOT NULL,
  `quantity` float NOT NULL,
  `period` enum('morning','evening') NOT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `milksale`
--

CREATE TABLE `milk_sale` (
  `id` int NOT NULL DEFAULT '0',
  `client_id` int NOT NULL,
  `quantity` int DEFAULT NULL,
  `price` float NOT NULL,
  `sale_date` date DEFAULT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `pregnancy`
--

CREATE TABLE `pregnancy` (
  `id` int NOT NULL,
  `cow_id` int DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `delivery_date` date DEFAULT NULL,
  `pregnancy_type` enum('Natural Service','By Collecting Semen') DEFAULT NULL,
  `pregnancy_status` enum('pending','finished','failed') NOT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `purchase`
--

CREATE TABLE `purchase` (
  `id` int NOT NULL,
  `supplier_id` int NOT NULL,
  `stock_id` int NOT NULL,
  `price` float NOT NULL,
  `purchase_date` date DEFAULT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `race`
--

CREATE TABLE `race` (
  `id` int NOT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `role`
--

CREATE TABLE `role` (
  `id` int NOT NULL,
  `name` varchar(20) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `routine`
--

CREATE TABLE `routine` (
  `id` int NOT NULL,
  `name` varchar(50) NOT NULL,
  `note` varchar(50) DEFAULT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `routine_has_feeds`
--

CREATE TABLE `routine_has_feeds` (
  `id` int NOT NULL,
  `id_stock` int NOT NULL,
  `feeding_time` date NOT NULL,
  `id_routine` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `stock`
--

CREATE TABLE `stock` (
  `id_stock` int NOT NULL,
  `name` varchar(30) NOT NULL,
  `type` enum('Machine','Vaccin','feed','Drug') DEFAULT NULL,
  `unit` varchar(10) DEFAULT NULL,
  `addedDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `supplier`
--

CREATE TABLE `supplier` (
  `id_supplier` int NOT NULL,
  `name` varchar(50) NOT NULL,
  `type` enum('person','company') DEFAULT NULL,
  `phone` int NOT NULL,
  `email` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id_user` int NOT NULL,
  `password` varchar(20) NOT NULL,
  `id_role` int NOT NULL,
  `id_employee` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `vaccin`
--

CREATE TABLE `vaccin` (
  `id_vaccin` int NOT NULL,
  `name` varchar(20) NOT NULL,
  `dose` float NOT NULL,
  `note` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `animal`
--
ALTER TABLE `animal`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_animal_id_r` (`race_id`),
  ADD KEY `fk_animal_id_routine` (`routine_id`);

--
-- Index pour la table `animalsale`
--
ALTER TABLE `animal_sale`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_animalsale_id_animal` (`animal_id`),
  ADD KEY `fk_animalsale_id_client` (`client_id`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `consuming`
--
ALTER TABLE `consuming`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_consuming_id_stock` (`stock_id`);

--
-- Index pour la table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `healthstatus`
--
ALTER TABLE `health_status`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_healthsatus_id_animal` (`animal_id`),
  ADD KEY `fk_healthsatus_id_vaccin` (`vaccine_id`);

--
-- Index pour la table `milkcollection`
--
ALTER TABLE `milk_collection`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_milkcollection_id_cow` (`cow_id`);

--
-- Index pour la table `milksale`
--
ALTER TABLE `milk_sale`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_milksale_id_client` (`client_id`);

--
-- Index pour la table `pregnancy`
--
ALTER TABLE `pregnancy`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_pregnancy_id_cow` (`cow_id`);

--
-- Index pour la table `purchase`
--
ALTER TABLE `purchase`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_purchase_id_stock` (`stock_id`),
  ADD KEY `fk_purchase_id_supplier` (`supplier_id`);

--
-- Index pour la table `race`
--
ALTER TABLE `race`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `routine`
--
ALTER TABLE `routine`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `routine_has_feeds`
--
ALTER TABLE `routine_has_feeds`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_routine_has_feeds_id_routine` (`id_routine`),
  ADD KEY `fk_routine_has_feeds_id_stock` (`id_stock`);

--
-- Index pour la table `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`id_stock`);

--
-- Index pour la table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id_supplier`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`),
  ADD KEY `fk_user_id_role` (`id_role`),
  ADD KEY `fk_user_id_employee` (`id_employee`);

--
-- Index pour la table `vaccin`
--
ALTER TABLE `vaccin`
  ADD PRIMARY KEY (`id_vaccin`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `animal`
--
ALTER TABLE `animal`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `animalsale`
--
ALTER TABLE `animal_sale`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `consuming`
--
ALTER TABLE `consuming`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `healthstatus`
--
ALTER TABLE `health_status`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `milkcollection`
--
ALTER TABLE `milk_collection`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `pregnancy`
--
ALTER TABLE `pregnancy`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `purchase`
--
ALTER TABLE `purchase`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `race`
--
ALTER TABLE `race`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `role`
--
ALTER TABLE `role`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `routine`
--
ALTER TABLE `routine`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `routine_has_feeds`
--
ALTER TABLE `routine_has_feeds`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `stock`
--
ALTER TABLE `stock`
  MODIFY `id_stock` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `id_supplier` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `vaccin`
--
ALTER TABLE `vaccin`
  MODIFY `id_vaccin` int NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `animal`
--
ALTER TABLE `animal`
  ADD CONSTRAINT `fk_animal_id_r` FOREIGN KEY (`race_id`) REFERENCES `race` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_animal_id_routine` FOREIGN KEY (`routine_id`) REFERENCES `routine` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `animalsale`
--
ALTER TABLE `animal_sale`
  ADD CONSTRAINT `fk_animalsale_id_animal` FOREIGN KEY (`animal_id`) REFERENCES `animal` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_animalsale_id_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `consuming`
--
ALTER TABLE `consuming`
  ADD CONSTRAINT `fk_consuming_id_stock` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id_stock`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `healthstatus`
--
ALTER TABLE `health_status`
  ADD CONSTRAINT `fk_healthsatus_id_animal` FOREIGN KEY (`animal_id`) REFERENCES `animal` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_healthsatus_id_vaccin` FOREIGN KEY (`vaccine_id`) REFERENCES `vaccin` (`id_vaccin`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `milkcollection`
--
ALTER TABLE `milk_collection`
  ADD CONSTRAINT `fk_milkcollection_id_cow` FOREIGN KEY (`cow_id`) REFERENCES `animal` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `milksale`
--
ALTER TABLE `milk_sale`
  ADD CONSTRAINT `fk_milksale_id_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `pregnancy`
--
ALTER TABLE `pregnancy`
  ADD CONSTRAINT `fk_pregnancy_id_cow` FOREIGN KEY (`cow_id`) REFERENCES `animal` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `purchase`
--
ALTER TABLE `purchase`
  ADD CONSTRAINT `fk_purchase_id_stock` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id_stock`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_purchase_id_supplier` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id_supplier`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `routine_has_feeds`
--
ALTER TABLE `routine_has_feeds`
  ADD CONSTRAINT `fk_routine_has_feeds_id_routine` FOREIGN KEY (`id_routine`) REFERENCES `routine` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_routine_has_feeds_id_stock` FOREIGN KEY (`id_stock`) REFERENCES `stock` (`id_stock`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_user_id_employee` FOREIGN KEY (`id_employee`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_user_id_role` FOREIGN KEY (`id_role`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
