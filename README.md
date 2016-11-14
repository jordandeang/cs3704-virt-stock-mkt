# cs3704-virt-stock-mkt

In order to run the Virtual Stock Market Application, one must do the following:

- Open command prompt and navigate to the bin/ folder, and type the command: 
  mongod --dbpath C:\Users\Kevin\IdeaProjects\virtualSM\mongoData

  (This sets the MongoDB instance to run locally and all the data will be saved in the mongoData folder)

- ./gradlew clean followed by ./gradlew bootRun