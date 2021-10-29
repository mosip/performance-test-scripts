Contains:-

This folder contains performance test scripts for Preregistration module.

How to run JMeter Test script:-

(1) We have one test script named 'Prereg_Test_script.jmx' in the scripts folder which is used for our test execution of the Preregistration UI end to end flow.

(2) It consists of one thread group named 'PreReg UI (Execution)' which consists of all the transactions involved for the Preregistration UI end to end flow.

(3) We have a test element named 'User Defined Variables' in the test script where the server IP, server port & protocol all these are parameterized & can be changed based on our requirements which will further reflect in the entire script.

(4) Also for viewing the results or output of our test we have added certain listener test elements at the end of our test script which are - View Results Tree, Aggregate Report, Active Threads Over Time graph, Response Times Percentiles graph, Response Times vs Threads graph & Transaction Throughput vs Threads graph.

How to run JMeter DB script:-

(1) The JMeter DB script named 'Prereg Get PreIDs From DB.jmx' is used for getting the prereg id's from the DB which were created through our test script.

(2) It contains one thread group named 'Select PreRegIds From DB' (for getting the prereg id's from the DB).

(3) Set the parameters of the environment database like dbHost,dbPort,dbName,dbUser & dbPassword in the test element named 'User Defined Variables'-

dbHost -- host name of the database
dbPort -- port number of the database
dbName -- name of the database
dbUser -- user name of the database
dbPassword -- password of the database