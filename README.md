# spark-project-postman
 This application is created with the intention of exporting large/small sized `.csv` file(s) and dump it into the database(postgres configured for this app). 

## Required

  - Scala 2.12.12
  - Spark 3.0.1
  - sbt  0.13.18
  - postgres 13.2
  
There is a large file for this application in `resource` directory of this application which is default source for file, if user wants to send they can send multiple files through commnd line. 

`Driver.scala` is the main class for this application and also entry for this applcation.

## Tables

| Table | Details |
| ------ | ------ |
| [sku_lookup](https://github.com/amitprasad119/spark-project-postman/blob/master/src/main/sql/sku_lookup.sql) | This table maintains the unique id(sku_id) for each sku, also null sku is replaced as `Unknown` and has sku_id too|
| [product](https://github.com/amitprasad119/spark-project-postman/blob/master/src/main/sql/product.sql) | This table contains the details of product with reference to sku_id from  `sku_lookup` table and has name,and description columns as well |
| [product_aggregated](https://github.com/amitprasad119/spark-project-postman/blob/master/src/main/sql/product_aggregated.sql) | This table contains the product count based on name |

Table script are in under sql folder of this project.

ER Diagram for above table is below.
![alt text](https://github.com/amitprasad119/spark-project-postman/blob/master/ER_diagram.png) 

## Points to achieve
   ### Done
  1, 2,4,5 are achieved.
   ### Yet to be done
      Non blocking is yet to be achieved efficiently, but for now it can read multiple files and import it into database.

## How it works 

There are three columns in `csv` file `name`,`sku`,`description` which are comma seprated.

This app will read the file and create the `sku_lookup` table based on the `sku` and when sku_table(sku_id) are created

Next `sku_id` will be referenced to `product` table and will have details with `sku_id`,`name`,`description`.

Additionally is has created_at metadata also.

Also there is a table `product_aggregated` which has `name` and `total` number of item sfor that name.

## Steps to Run 

 1. ```sbt docker:publishLocal  ```
  2. `docker run <jar file> <files as arguments>`

## Table contents for all three are below 
![sku_lookup](https://github.com/amitprasad119/spark-project-postman/blob/master/ER_diagram.png)
![product](https://github.com/amitprasad119/spark-project-postman/blob/master/ER_diagram.png) 
![product_aggregated](https://github.com/amitprasad119/spark-project-postman/blob/master/ER_diagram.png) 
 

