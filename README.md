cs3704-virt-stock-mkt

# Virtual Stock Market Game

Authors: Jordan Deang (jdeang), Mihir Kamani (mihirk93), Kevin Koehncke (koehncke), Philip Whitcomb (philipwh)

What Is It?
---------------------

The Virtual Stock Market Game is an application that allows users to sign up for different virtual stock market leagues, simulate stock transactions with fake money, and compete against other users who have signed up in the league by each user's investment performances.

The Latest Version
---------------------


Documentation
---------------------


In order to run the Virtual Stock Market Application, one must do the following:

- Have MongoDB installed 

- Open command prompt and navigate to the bin/ folder, and type the command: 
  mongod --dbpath <path to folder where you want MongoDB data to be saved>

  (This sets the MongoDB instance to run locally and all the data will be saved in the mongoData folder)

- ./gradlew clean followed by ./gradlew bootRun
