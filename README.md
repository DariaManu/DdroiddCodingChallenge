# DdroiddCodingChallenge

## Implementation Details
The solution to the problem is divided into 4 main packages (model, repo, service and ui) 
based on a layered architecture as follows:<br>
- <i><b>model</b></i> package includes classes from the problem's domain (Item class contains details about
a product in the catalog, ShippingRate contains details about the shipping rates applied
to each country that ships products);
- <i><b>repo</b></i> package includes InMemoryRepo class that manages access to the data
structure containing the catalog of products and their corresponding shipping rates;
- <i><b>service</b></i> package includes Service class which use the persistence layer and
implement the business rules of the application;
- <i><b>ui</b></i> package includes UI class which manages the input commands from the user
and displays the results of the commands

## How to run the program
In order to start the application, run the <i>main</i> method in class Main<br>
<br>
After starting, the program displays the available commands.
![](.\images\ddcc1.png)
In order to view the products in the catalog type "items".
![](.images\ddcc2.png)
In order to add a new product to the shopping cart first type "add".
![](.\ddcc3.png)
(in this step, the available offers will also be displayed)<br>
Then, type the name of the product as it appears in the catalog.<br>
The added product will appear in the shopping cart.
![](.\images\ddcc4.png)
Repeat this step until you have added all the wanted products in the shopping cart.
![](.\images\ddcc5.png)
To check out and see the final invoice with all the details type "checkout"
![](.\images\ddcc6.png)
To exit the program type "exit"
