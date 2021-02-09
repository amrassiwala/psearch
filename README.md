# psearch
Steps to deploy Product Search Application :

1. Make sure you have docker and docker-compose installed on your system.

2. Clone the code from github 
```
git clone https://github.com/amrassiwala/psearch.git
```

3. From the psearch folder, start docker
```
docker-compose up -d
```

4. Give a minute for elastic search to start, then verify from browser using the following url
```
http://localhost:9200 
```
If elasticsearch is up you will get a json with the tagline You Know, for Search"

5. See if tomcat is up at the following url :
```
http://localhost:8080 
```
You will see the search page.

6. Import products from json, hit the following url :
```
http://localhost:8080/import
```
Once this is done, you can start searching the products in the search box and try different filters

7. To stop the application, use the following commad:
```
docker-compose down
```
8. To re-compile after making any changes in the code, you will need JDK and Maven installed on your local machine, 
Re-compilation can be done using the following command :
```
mvn war:war
```
