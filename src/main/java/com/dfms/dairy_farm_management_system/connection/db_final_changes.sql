-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 25 nov. 2022 à 12:25
-- Version du serveur : 10.4.25-MariaDB
-- Version de PHP : 8.1.10

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
-- Structure de la table `animals`
--

CREATE TABLE `animals` (
  `id` varchar(30) NOT NULL,
  `birth_date` date NOT NULL,
  `purchase_date` date DEFAULT NULL,
  `routine` int(11) NOT NULL,
  `race` int(11) NOT NULL,
  `type` enum('cow','bull','calf') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `animals_sales`
--

CREATE TABLE `animals_sales` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `animal_id` varchar(20) DEFAULT NULL,
  `price` float NOT NULL,
  `sale_date` date DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `clients`
--

CREATE TABLE `clients` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `type` enum('person','company') DEFAULT NULL,
  `phone` int(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `consuming`
--

CREATE TABLE `consuming` (
  `id` int(11) NOT NULL,
  `stock_id` int(11) NOT NULL,
  `quantity` float NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `employees`
--

CREATE TABLE `employees` (
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `gender` enum('F','M') NOT NULL,
  `cin` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `address` varchar(30) NOT NULL,
  `salary` float NOT NULL,
  `hire_date` date NOT NULL,
  `contract_type` enum('CDI','CDD','CTT') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `health_status`
--

CREATE TABLE `health_status` (
  `id` int(11) NOT NULL,
  `animal_id` varchar(20) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `breathing` int(11) DEFAULT NULL,
  `health_score` enum('excellent','good','not bad','bad') NOT NULL,
  `control_date` date NOT NULL,
  `notes` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `milk_collections`
--

CREATE TABLE `milk_collections` (
  `id` int(11) NOT NULL,
  `cow_id` varchar(20) NOT NULL,
  `quantity` float NOT NULL,
  `period` enum('morning','evening') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `milk_sales`
--

CREATE TABLE `milk_sales` (
  `id` int(11) NOT NULL DEFAULT 0,
  `client_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` float NOT NULL,
  `sale_date` date DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `pregnancies`
--

CREATE TABLE `pregnancies` (
  `id` int(11) NOT NULL,
  `cow_id` varchar(20) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `delivery_date` date DEFAULT NULL,
  `pregnancy_type` enum('Natural Service','By Collecting Semen') DEFAULT NULL,
  `pregnancy_status` enum('pending','finished','failed') NOT NULL,
  `notes` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `purchases`
--

CREATE TABLE `purchases` (
  `id` int(11) NOT NULL,
  `supplier_id` int(11) NOT NULL,
  `stock_id` int(11) NOT NULL,
  `price` float NOT NULL,
  `purchase_date` date DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `races`
--

CREATE TABLE `races` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `routines`
--

CREATE TABLE `routines` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `note` varchar(50) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `routine_has_feeds`
--

CREATE TABLE `routine_has_feeds` (
  `id` int(11) NOT NULL,
  `stock_id` int(11) NOT NULL,
  `routine_id` int(11) NOT NULL,
  `quantity` float NOT NULL,
  `feeding_time` enum('Morning','Evening') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `stocks`
--

CREATE TABLE `stocks` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `type` enum('Machine','Vaccine','feed','Drug') DEFAULT NULL,
  `unit` varchar(10) DEFAULT NULL,
  `added_date` date DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `suppliers`
--

CREATE TABLE `suppliers` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `type` enum('person','company') DEFAULT NULL,
  `phone` int(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `password` varchar(20) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `first_name` varchar(20) DEFAULT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  `gender` enum('M','F') NOT NULL,
  `cin` varchar(20) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `salary` float NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `vaccination`
--

CREATE TABLE `vaccination` (
  `id` int(11) NOT NULL,
  `animal_id` varchar(30) NOT NULL,
  `responsible_id` int(11) NOT NULL,
  `vaccine_id` int(11) NOT NULL,
  `vaccination_date` date NOT NULL,
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `animals`
--
ALTER TABLE `animals`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_animal_id_r` (`race`),
  ADD KEY `fk_animal_id_routine` (`routine`);

--
-- Index pour la table `animals_sales`
--
ALTER TABLE `animals_sales`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_animal_sale_id_client` (`client_id`);

--
-- Index pour la table `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`,`phone`,`email`);

--
-- Index pour la table `consuming`
--
ALTER TABLE `consuming`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_consuming_id_stock` (`stock_id`);

--
-- Index pour la table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`cin`),
  ADD UNIQUE KEY `cin` (`cin`,`email`,`phone`);

--
-- Index pour la table `health_status`
--
ALTER TABLE `health_status`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_health_status_id_animal` (`animal_id`);

--
-- Index pour la table `milk_collections`
--
ALTER TABLE `milk_collections`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_milk_collection_id_cow` (`cow_id`);

--
-- Index pour la table `milk_sales`
--
ALTER TABLE `milk_sales`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_milk_sale_id_client` (`client_id`);

--
-- Index pour la table `pregnancies`
--
ALTER TABLE `pregnancies`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_pregnancy_id_cow` (`cow_id`);

--
-- Index pour la table `purchases`
--
ALTER TABLE `purchases`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_purchase_id_stock` (`stock_id`),
  ADD KEY `fk_purchase_id_supplier` (`supplier_id`);

--
-- Index pour la table `races`
--
ALTER TABLE `races`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Index pour la table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Index pour la table `routines`
--
ALTER TABLE `routines`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Index pour la table `routine_has_feeds`
--
ALTER TABLE `routine_has_feeds`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_routine_has_feeds_id_routine` (`routine_id`),
  ADD KEY `fk_routine_has_feeds_id_stock` (`stock_id`);

--
-- Index pour la table `stocks`
--
ALTER TABLE `stocks`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Index pour la table `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`,`phone`,`email`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `cin` (`cin`,`phone`,`email`),
  ADD KEY `fk_user_id_role` (`role_id`);

--
-- Index pour la table `vaccination`
--
ALTER TABLE `vaccination`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_vacc_has_animal` (`animal_id`),
  ADD KEY `fk_vacc_has_respo` (`responsible_id`),
  ADD KEY `fk_vacc_has_stock` (`vaccine_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `animals_sales`
--
ALTER TABLE `animals_sales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `clients`
--
ALTER TABLE `clients`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `consuming`
--
ALTER TABLE `consuming`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `health_status`
--
ALTER TABLE `health_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `milk_collections`
--
ALTER TABLE `milk_collections`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `pregnancies`
--
ALTER TABLE `pregnancies`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `purchases`
--
ALTER TABLE `purchases`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `races`
--
ALTER TABLE `races`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `routines`
--
ALTER TABLE `routines`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `routine_has_feeds`
--
ALTER TABLE `routine_has_feeds`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `stocks`
--
ALTER TABLE `stocks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `vaccination`
--
ALTER TABLE `vaccination`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `vaccination`
--
ALTER TABLE `vaccination`
  ADD CONSTRAINT `fk_vacc_has_animal` FOREIGN KEY (`animal_id`) REFERENCES `animals` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_vacc_has_respo` FOREIGN KEY (`responsible_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_vacc_has_stock` FOREIGN KEY (`vaccine_id`) REFERENCES `stocks` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
