use dairyfarm;

# change address type in employee table
ALTER TABLE employees
    MODIFY address TEXT;

# add quantity and availability columns to stock table
ALTER TABLE stocks
    ADD quantity INT NOT NULL;
ALTER TABLE stocks
    ADD availability VARCHAR(20);

# change type column data type in stocks table
ALTER TABLE stocks
    MODIFY type ENUM ('Machine', 'Vaccine', 'Feed', 'Drug') NOT NULL;

# remove added date
ALTER TABLE stocks
    DROP added_date;

# remove unique constraint from name column in stocks table
ALTER TABLE stocks
    DROP INDEX name;

# update the password type in users table
ALTER TABLE users
    MODIFY password VARCHAR(255) NOT NULL;

#change recruitment date column name in employees table
ALTER TABLE employees
    CHANGE recruitment_date hire_date DATE NOT NULL;


#change id column  in milk_sales table to be auto-increment
ALTER TABLE milk_sales
    MODIFY id INTEGER NOT NULL AUTO_INCREMENT;


#change quantity column  in milk_sales table to be float
ALTER TABLE milk_sales
    MODIFY quantity Float NOT NULL;

# change clients, suppliers email column data type to varchar
ALTER TABLE `clients`
    MODIFY email varchar(50);

ALTER TABLE `suppliers`
    MODIFY email varchar(50);

# change phone data type to varchar in clients and suppliers table
ALTER TABLE `clients`
    MODIFY phone varchar(20);

ALTER TABLE `suppliers`
    MODIFY phone varchar(20);

#change name data type to varchar in races table
ALTER TABLE `races`
    MODIFY name varchar(50);

#change note data type in routines table
ALTER TABLE `routines`
    MODIFY note TEXT;