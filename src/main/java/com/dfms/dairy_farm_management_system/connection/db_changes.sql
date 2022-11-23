use dairyfarm;

# change address type in employee table
ALTER TABLE employees
    MODIFY address VARCHAR(255) NOT NULL;

# add quantity and availability columns to stock table
ALTER TABLE stocks
    ADD quantity INT NOT NULL;
ALTER TABLE stocks
    ADD availability ENUM ('available', 'out of stock') NOT NULL;