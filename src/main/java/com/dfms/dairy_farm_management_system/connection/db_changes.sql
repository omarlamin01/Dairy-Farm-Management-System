use dairyfarm;

/*change the size of email in employee table */
ALTER TABLE employee MODIFY email VARCHAR(50) NOT NULL;

/*change phone to varchar in employee table */
ALTER TABLE employee MODIFY phone VARCHAR(20) NOT NULL;

/*add new columns to user table */
ALTER TABLE user ADD COLUMN first_name VARCHAR(20);
ALTER TABLE user ADD COLUMN last_name VARCHAR(20);
ALTER TABLE user ADD COLUMN gender ENUM('M','F') NOT NULL;
ALTER TABLE user ADD COLUMN cin VARCHAR(20) NOT NULL;
ALTER TABLE user ADD COLUMN phone VARCHAR(20) NOT NULL;
ALTER TABLE user ADD COLUMN salary FLOAT NOT NULL;
ALTER TABLE user ADD COLUMN email VARCHAR(50);
ALTER TABLE user ADD COLUMN address VARCHAR(100);

/*change the type of animal_id */

/*insert roles */
INSERT INTO role (name) VALUES ('Admin');
INSERT INTO role (name) VALUES ('HR');
INSERT INTO role (name) VALUES ('Sales agent');
INSERT INTO role (name) VALUES ('Production manager');
INSERT INTO role (name) VALUES ('Veterinary');

