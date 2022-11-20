use dairyfarm;

ALTER TABLE `health_status` ADD `notes` TEXT NULL AFTER `control_date`;

ALTER TABLE `pregnancies` ADD `notes` TEXT NULL AFTER `pregnancy_status`;

