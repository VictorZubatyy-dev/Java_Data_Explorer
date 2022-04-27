**Classes**

Main.java
- Used to instantiate the JDBC connection object which is then passed in as an argument to the 
  GUI class.
  
GUI.java
- Used to create the GUI for this database application. It inherits from the JFrame class. 
- All data and functions regarding the database and querying are within the SQL class however,
- the JSwing components such as buttons call these functions.
- This class instantiates the sql object from the SQL class.

SQL.java
- This class is used to query the database tables. It is essentially CRUD without the ability to edit
- data.
- It also has functions specificly made for the Attractions.csv file regarding showing interesting facts about
it.

FileProcesser.java
- This class is used to process the csv file and return a multi-dimensional array dynamically.

EstablishConnection.java
- This class is used to set database values needed for a successful connection to the 
- database.


**Core Functionality**
- The core functions of this application is heavily built within the FileProcessor class. This class
  includes functions that make read the CSV file and parse it into a multi-dimensional array.
- This multidimensional array is then used within the SQL class to create, insert (update), 
  and drop tables.
- The SQL class's object is instantiated within the GUI class, this enables the core functionality of
  creating and retrieiving values from the database to be inacted by the user from within the GUI
  class (the GUI interface itself).
- The GUI class implements the action listeners for the buttons, textfields and the file chooser.
- The EstablishConnection class just recieves the Users localhost database destails and creates (returns)
  a Connection object which is used within the Main Class (main method).
- The main class just instantiates the GUI class with the Connection object as a parameter.

Sequence of events: Main -> EstablishConnection -> GUI -> FileProcessor -> SQL

**Optional Functionality**
- The optional functionality I included within this application is the ability to insert any CSV file
  into this application and have it dynamically create a table with whatever name you choose in the 
  setter function for the table name.
- This functionality is done within the FileProcessor class and the SQL class.
- The FileProcessor class returns a two-dimensional array loaded in dynamically into the SQL class 
  that then itself creates a table and inserts the values from the two-dimensional array dynamically,
  given that the user enters a table name that is not already within the database itself. There is a button
  specifically made to drop the table automatically if the user click it. The button is called 
  'Clear table and close'.

**If I had more time**
- I would have implemented: 
- a better FileChooser that did not freeze and enabled the user to continue adding multiple 
  csv files without closing the application.
- better error handling.
  more interesting GUI in regards to the CSV file I had choosen, the Attractions in Ireland
  can really only show the attractions and thats about it.
  
**Youtube URL**

https://www.youtube.com/watch?v=mXHu4lLGSsI&ab_channel=JustSmileBack



