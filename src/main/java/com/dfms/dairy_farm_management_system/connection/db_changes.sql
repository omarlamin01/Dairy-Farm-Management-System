use dairyfarm;

# change address type in employee table
ALTER TABLE employees
    MODIFY address TEXT;

# add quantity and availability columns to stock table
ALTER TABLE stocks
    ADD quantity INT NOT NULL;
ALTER TABLE stocks
    ADD availability TINYINT;

# change availability column type to boolean
ALTER TABLE stocks
    MODIFY availability VARCHAR(20);

# change type column data type in stocks table
ALTER TABLE stocks
    MODIFY type ENUM ('Machine', 'Vaccine', 'Feed', 'Drug') NOT NULL;

# remove added date
ALTER TABLE stocks
    DROP added_date;