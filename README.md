Access the application via http://localhost:4200/

## MySQL commands:
CREATE SCHEMA `giphy` ;

CREATE TABLE `giphy`.`userdetails` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userid` INT NULL,
  `imageid` VARCHAR(200) NULL,
  `title` VARCHAR(200) NULL,
  `url` VARCHAR(500) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = big5;

## Steps:

### To search Giphy images
1. Search Giphy images via the Keywords, Rating, Number of Returned Images and Offset input fields
2. Click on the 'Search Giphy images' button
3. See search result below the button. Pagination saved at Component (but not displayed on webpage).

### To add image to Favourite
1. Enter your userid via first 'Enter your userid' input field
2. Click on the 'Login' button
3. Click on the image link from the search result to save as Favourite
4. To add another image or view user details, please click on the 'Back' button of the browser

### To view user details
1. Enter your userid via second 'Enter your userid' input field.
2. Click on the 'View your Favourite Images' button
3. The Favourite list of the user will be displayed
4. To remove image from Favourite list, please click on the 'Remove image' link



















