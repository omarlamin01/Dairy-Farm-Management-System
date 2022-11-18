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
    DROP `id`;
COMMIT;

# Unicity of some fields
ALTER TABLE `client` ADD UNIQUE(`name`, `phone`, `email`);
SAVEPOINT A;

ALTER TABLE `employee` ADD UNIQUE(`cin`, `email`, `phone`);
SAVEPOINT B;

ALTER TABLE `race` ADD UNIQUE(`name`);
SAVEPOINT C;

ALTER TABLE `role` ADD UNIQUE(`name`);
SAVEPOINT D;

ALTER TABLE `routine` ADD UNIQUE(`name`);
SAVEPOINT E;

ALTER TABLE `stock` ADD UNIQUE(`name`);
SAVEPOINT F;

ALTER TABLE `supplier` ADD UNIQUE(`name`, `phone`, `email`);
SAVEPOINT G;

ALTER TABLE `user` ADD UNIQUE(`cin`, `phone`, `email`);
COMMIT;

