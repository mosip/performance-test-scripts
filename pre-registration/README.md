## This folder contains performance test scripts for Preregistration module.

### Environment Required:-
* Kernel Otp manager
* Kernel Syncdata
* Kernel Notification Manager
* Kernel Key manager
* Kernel Master data
* Kernel Audit manager
* All pre reg services

### Data prerequisite:-
* Sample documents are required for uploading in the upload document section of Prereg UI booking appointment slot end to end flow. The documents can be found & edited from [here](https://github.com/mosip/mosip-performance-tests-mt/tree/1.1.5/pre-registration/support-files)

### How to run JMeter Test script:-

* We have a test script [Prereg_Test_script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/pre-registration/scripts/Prereg_Test_Script.jmx) which is used for our test execution of the Preregistration UI end to end flow.

* It consists of one thread group named 'PreReg UI (Execution)' which consists of all the transactions involved for the Preregistration UI end to end flow.

* We have a test element named 'User Defined Variables' in the test script where the server IP, server port & protocol all these are parameterized & can be changed based on our requirements which will further reflect in the entire script.

* Also for viewing the results or output of our test we have added certain listener test elements at the end of our test script which are - View Results Tree, Aggregate Report, Active Threads Over Time graph, Response Times Percentiles graph, Response Times vs Threads graph & Transaction Throughput vs Threads graph.

### How to run JMeter DB script:-

* The JMeter DB script named [Prereg Get PreIDs From DB.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/pre-registration/scripts/Prereg%20Get%20PreIDs%20From%20DB.jmx) is used for getting the prereg id's from the DB which were created through our test script.

* It contains one thread group named 'Select PreRegIds From DB' (for getting the prereg id's from the DB).

* Set the parameters of the environment database like dbHost, dbPort, dbName, dbUser & dbPassword in the test element named 'User Defined Variables'-
    1. dbHost -- host name of the database
    2. dbPort -- port number of the database
    3. dbName -- name of the database
    4. dbUser -- user name of the database
    5. dbPassword -- password of the database
