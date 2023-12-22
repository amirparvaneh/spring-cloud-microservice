## docker run -d -p 3306:3306 --name customer -e MYSQL_ROOT_PASSWORD=PASSWORD -e MYSQL_DATABASE=customer -e MYSQL_USER=micro -e MYSQL_PASSWORD=PASSWORD mysql

## docker run -d -p 3307:3306 --name deposit -e MYSQL_ROOT_PASSWORD=PASSWORD -e MYSQL_DATABASE=transaction -e MYSQL_USER=micro2 -e MYSQL_PASSWORD=PASSWORD mysql

## docker run -d -p 3308:3306 --name transaction -e MYSQL_ROOT_PASSWORD=PASSWORD -e MYSQL_DATABASE=transaction -e MYSQL_USER=micro3 -e MYSQL_PASSWORD=PASSWORD mysql
