use dairyfarm;

# Rename some tables, fields & fix some problems
ALTER TABLE `clients`
    CHANGE `phone` `phone` VARCHAR(20) NULL,
    CHANGE `email` `email` VARCHAR(50) NULL;
SAVEPOINT D;

ALTER TABLE `employees`
    CHANGE `address` `address` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
    CHANGE `recruitment_date` `hire_date` DATE NOT NULL;
SAVEPOINT E;

ALTER TABLE `pregnancies`
    DROP `pregnancy_type`;
SAVEPOINT I;

ALTER TABLE `pregnancies`
    CHANGE `pregnancy_status` `pregnancy_status` ENUM ('Ongoing','finished','failed') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;
SAVEPOINT J;

ALTER TABLE `routines`
    CHANGE `note` `note` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL;
SAVEPOINT K;

ALTER TABLE `suppliers`
    CHANGE `phone` `phone` VARCHAR(20) NOT NULL,
    CHANGE `email` `email` VARCHAR(50) NOT NULL;
SAVEPOINT L;

ALTER TABLE `users`
    CHANGE `role_id` `role` INT(11) NOT NULL,
    CHANGE `password` `password` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;
SAVEPOINT M;

ALTER TABLE `users`
    CHANGE `created_at` `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `address`;
ALTER TABLE `users`
    CHANGE `first_name` `first_name` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `id`;
ALTER TABLE `users`
    CHANGE `last_name` `last_name` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `first_name`;
ALTER TABLE `users`
    CHANGE `cin` `cin` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL AFTER `last_name`;
ALTER TABLE `users`
    CHANGE `email` `email` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `cin`;
ALTER TABLE `users`
    CHANGE `password` `password` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL AFTER `email`;
ALTER TABLE `users`
    CHANGE `updated_at` `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `address`;
ALTER TABLE `users`
    CHANGE `gender` `gender` ENUM ('M','F') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL AFTER `password`;
ALTER TABLE `users`
    CHANGE `phone` `phone` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL AFTER `gender`;
ALTER TABLE `users`
    CHANGE `salary` `salary` FLOAT NOT NULL AFTER `phone`;
ALTER TABLE `users`
    CHANGE `address` `address` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `salary`;
COMMIT;


