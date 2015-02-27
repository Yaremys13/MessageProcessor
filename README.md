# MessageProcessor
Test for HR Currency Fair made by Yaremys Luces

To use this app, you have two options:

1) You can upload a file with all messages to be processed, which must have the following syntax
  {"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP", "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471, "timePlaced" : "14-JAN-15 10:27:44", "originatingCountry" : "FR"}
  
  and if there are more than one must be separated by  a comma (,) as follow:
  
  {"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP", "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471, "timePlaced" : "14-JAN-15 10:27:44", "originatingCountry" : "FR"},
  {"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP", "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471, "timePlaced" : "14-JAN-15 10:27:44", "originatingCountry" : "FR"},
  {"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP", "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471, "timePlaced" : "14-JAN-15 10:27:44", "originatingCountry" : "FR"}
  
2) Or you can load messages one by one, using button "You can also load message by message" and completing the form that shows up down it

Once you have chosen your option, press Load & Process button (case one) or Load 1 by 1 (case two).

Immediately you can see results below.

You can repeat the process many times as you need, and  is going to automatically update the results.


Project Structure:

Project was design as a Java Dynamic Web Project. In this repository you can find the files without an specific structure, just for code revision, if you want to instal the application, you can ask for .war file.

Developing environment:

Dynamic Web Project 2.5
JRE 1.7
Apache Tomcat 6.0

Additional Libraries used (directly installed on Apache):

commons-fileupload-1.3.1.jar
commons-io-2.4.jar
javax.json-1.0.2.jar
json-simple-1.1.1.jar
