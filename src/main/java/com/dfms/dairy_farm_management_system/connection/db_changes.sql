use dairyfarm;

# Update created_at & updated_at
ALTER TABLE `animal`
    CHANGE `created_at` `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHANGE `updated_at` `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
SAVEPOINT A;

ALTER TABLE `animal_sale`
    CHANGE `created_at` `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHANGE `updated_at` `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
SAVEPOINT B;

ALTER TABLE `client`
    CHANGE `created_at` `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHANGE `updated_at` `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
SAVEPOINT C;

ALTER TABLE `consuming`
    CHANGE `created_at` `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHANGE `updated_at` `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
SAVEPOINT D;

ALTER TABLE `employee`
    CHANGE `created_at` `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHANGE `updated_at` `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
SAVEPOINT E;

ALTER TABLE `health_status`
    CHANGE `created_at` `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHANGE `updated_at` `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
SAVEPOINT F;

ALTER TABLE `milk_collection`
    CHANGE `created_at` `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHANGE `updated_at` `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
SAVEPOINT G;

ALTER TABLE `milk_sale`
    CHANGE `created_at` `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHANGE `updated_at` `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
SAVEPOINT H;

ALTER TABLE `pregnancy`
    CHANGE `created_at` `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHANGE `updated_at` `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
SAVEPOINT I;

ALTER TABLE `purchase`
    CHANGE `created_at` `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHANGE `updated_at` `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
SAVEPOINT J;

ALTER TABLE `race`
    DROP `created_at`,
    DROP `updated_at`;
SAVEPOINT K;

ALTER TABLE `role`
    DROP `created_at`,
    DROP `updated_at`;
SAVEPOINT L;

ALTER TABLE `routine`
    CHANGE `created_at` `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHANGE `updated_at` `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
SAVEPOINT M;

ALTER TABLE `routine_has_feeds`
    CHANGE `created_at` `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHANGE `updated_at` `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
SAVEPOINT N;

ALTER TABLE `stock`
    CHANGE `created_at` `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHANGE `updated_at` `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
SAVEPOINT O;

ALTER TABLE `supplier`
    CHANGE `created_at` `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHANGE `updated_at` `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
SAVEPOINT P;

ALTER TABLE `user`
    CHANGE `created_at` `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHANGE `updated_at` `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
SAVEPOINT Q;

ALTER TABLE `vaccine`
    CHANGE `created_at` `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHANGE `updated_at` `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
COMMIT;

# Update ids
ALTER TABLE `animal`
    CHANGE `id` `id` VARCHAR(20) NOT NULL AUTO_INCREMENT;
SAVEPOINT A;

ALTER TABLE `animal_sale`
    CHANGE `animal_id` `animal_id` VARCHAR(20) NULL DEFAULT NULL;
SAVEPOINT B;

ALTER TABLE `health_status`
    CHANGE `animal_id` `animal_id` VARCHAR(20) NULL DEFAULT NULL;
SAVEPOINT C;

ALTER TABLE `milk_collection`
    CHANGE `cow_id` `cow_id` VARCHAR(20) NOT NULL;
SAVEPOINT D;

ALTER TABLE `pregnancy`
    CHANGE `cow_id` `cow_id` VARCHAR(20) NULL DEFAULT NULL;
SAVEPOINT E;
/*
If this didn't work try delete every user manually.
We're gonna make `cne` the primary key for employees,
this will solve lots of issues.
*/
TRUNCATE `user`;
SAVEPOINT F;

ALTER TABLE `user`
    DROP INDEX `fk_user_id_employee`;
SAVEPOINT G;

ALTER TABLE `user`
    DROP `employee_id`;
SAVEPOINT H;

ALTER TABLE `employee`
    ADD PRIMARY KEY (`cin`);
SAVEPOINT I;

ALTER TABLE `employee`
    DROP `id`;
COMMIT;

# Unicity of some fields
ALTER TABLE `client`
    ADD UNIQUE (`name`, `phone`, `email`);
SAVEPOINT A;

ALTER TABLE `employee`
    ADD UNIQUE (`cin`, `email`, `phone`);
SAVEPOINT B;

ALTER TABLE `race`
    ADD UNIQUE (`name`);
SAVEPOINT C;

ALTER TABLE `role`
    ADD UNIQUE (`name`);
SAVEPOINT D;

ALTER TABLE `routine`
    ADD UNIQUE (`name`);
SAVEPOINT E;

ALTER TABLE `stock`
    ADD UNIQUE (`name`);
SAVEPOINT F;

ALTER TABLE `supplier`
    ADD UNIQUE (`name`, `phone`, `email`);
SAVEPOINT G;

ALTER TABLE `user`
    ADD UNIQUE (`cin`, `phone`, `email`);
COMMIT;

# Rename some tables, fields & fix some problems
DROP TABLE `vaccine`;
ALTER TABLE `animal` RENAME TO `animals`;
ALTER TABLE `animal_sale` RENAME TO `animals_sales`;
ALTER TABLE `client` RENAME TO `clients`;
ALTER TABLE `employee` RENAME TO `employees`;
ALTER TABLE `milk_collection` RENAME TO `milk_collections`;
ALTER TABLE `milk_sale` RENAME TO `milk_sales`;
ALTER TABLE `pregnancy` RENAME TO `pregnancies`;
ALTER TABLE `purchase` RENAME TO `purchases`;
ALTER TABLE `race` RENAME TO `races`;
ALTER TABLE `role` RENAME TO `roles`;
ALTER TABLE `routine` RENAME TO `routines`;
ALTER TABLE `stock` RENAME TO `stocks`;
ALTER TABLE `supplier` RENAME TO `suppliers`;
ALTER TABLE `user` RENAME TO `users`;
SAVEPOINT A;

ALTER TABLE `animals`
    CHANGE `routine_id` `routine` INT(11) NOT NULL,
    CHANGE `race_id` `race` INT(11) NOT NULL;
SAVEPOINT B;

ALTER TABLE `animal_sale`
    CHANGE `client_id` `client` INT(11) NOT NULL,
    CHANGE `animal_id` `animal` INT(11) NULL DEFAULT NULL;
SAVEPOINT C;

ALTER TABLE `client`
    CHANGE `phone` `phone` VARCHAR(20) NULL,
    CHANGE `email` `email` VARCHAR(50) NULL;
SAVEPOINT D;

ALTER TABLE `employee`
    CHANGE `address` `address` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    CHANGE `recruitment_date` `hire_date` DATE NOT NULL;
SAVEPOINT E;

ALTER TABLE `health_status`
    CHANGE `animal_id` `animal` VARCHAR(11) NULL DEFAULT NULL,
    CHANGE `breading` `breathing` INT NULL DEFAULT '0';
SAVEPOINT F;

ALTER TABLE `health_status`
    DROP INDEX `fk_health_status_id_vaccine`;
SAVEPOINT G;

ALTER TABLE `health_status`
    DROP `vaccine_id`;
SAVEPOINT H;

ALTER TABLE `pregnancy`
    DROP `pregnancy_type`;
SAVEPOINT I;

ALTER TABLE `pregnancy`
    CHANGE `pregnancy_status` `pregnancy_status` ENUM ('Ongoing','finished','failed') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;
SAVEPOINT J;

ALTER TABLE `routine`
    CHANGE `note` `note` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL;
SAVEPOINT K;

ALTER TABLE `supplier`
    CHANGE `phone` `phone` VARCHAR(20) NOT NULL,
    CHANGE `email` `email` VARCHAR(50) NOT NULL;
SAVEPOINT L;

ALTER TABLE `user`
    CHANGE `role_id` `role` INT(11) NOT NULL,
    CHANGE `password` `password` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;
SAVEPOINT M;

ALTER TABLE `user`
    CHANGE `created_at` `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `address`;
ALTER TABLE `user`
    CHANGE `first_name` `first_name` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `id`;
ALTER TABLE `user`
    CHANGE `last_name` `last_name` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `first_name`;
ALTER TABLE `user`
    CHANGE `cin` `cin` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL AFTER `last_name`;
ALTER TABLE `user`
    CHANGE `email` `email` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `cin`;
ALTER TABLE `user`
    CHANGE `password` `password` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL AFTER `email`;
ALTER TABLE `user`
    CHANGE `updated_at` `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `address`;
ALTER TABLE `user`
    CHANGE `gender` `gender` ENUM ('M','F') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL AFTER `password`;
ALTER TABLE `user`
    CHANGE `phone` `phone` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL AFTER `gender`;
ALTER TABLE `user`
    CHANGE `salary` `salary` FLOAT NOT NULL AFTER `phone`;
ALTER TABLE `user`
    CHANGE `address` `address` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `salary`;
COMMIT;
