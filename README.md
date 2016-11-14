# cs3704-virt-stock-mkt

In order to run the Virtual Stock Market Application, one must do the following:

- Have MongoDB installed 

- Open command prompt and navigate to the bin/ folder, and type the command: 
  mongod --dbpath <path to folder where you want MongoDB data to be saved>

  (This sets the MongoDB instance to run locally and all the data will be saved in the mongoData folder)

- ./gradlew clean followed by ./gradlew bootRun