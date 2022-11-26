# change clients, suppliers email column data type to varchar
ALTER TABLE `clients`
    MODIFY email varchar(50);
ALTER TABLE `suppliers`
    MODIFY email varchar(50);