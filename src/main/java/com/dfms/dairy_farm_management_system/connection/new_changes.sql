use dairyfarm;

ALTER TABLE `routine_has_feeds` CHANGE `feeding_time` `feeding_time` ENUM('Morning','Evening') NOT NULL;

ALTER TABLE `routine_has_feeds` ADD `quantity` FLOAT NOT NULL AFTER `routine_id`;