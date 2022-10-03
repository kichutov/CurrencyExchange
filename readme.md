# Currency Exchange

### Сollects exchange rates, calculate the conversion amounts and show history.  
The service collects exchange rates from the `https://www.cbr.ru/scripts/XML_daily.asp`,  
after that you can calculate the conversion amounts.  
The application displays the conversion history for each user.  

To start using the service, you need to register.  
Then login to the app with your username and password.  

The database can be started with the command:  
docker-compose -f ./docker/docker-compose.yml up -d  

![](https://github.com/kichutov/exchange/blob/main/exchange1.jpg)
![](https://github.com/kichutov/exchange/blob/main/exchange2.jpg)

