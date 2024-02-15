This folder contains performance test scripts and test data of Authmanager module.

### Environment Required:-
***Below modules should be running in kubernetes setup***

* Kernel audit service
* Kernel authmanager service

### Data prequisite:-
* Auth manager request client id & secret key details are stored [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.2.0/commons/authmanager/support-files/authManagerClientIdSecretKeyDetails.csv) and can be be modified based on environment.
* Auth manager request user id & password details are stored [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.2.0/commons/authmanager/support-files/authManagerUserIdPasswordDetails.csv) and can be be modified based on environment.

### How to run JMeter script:-
* For the test execution part we have a test script [Authmanager_Test_Script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.2.0/commons/authmanager/scripts/Authmanager_Test_Script.jmx) which will take care of the execution tasks for the all the Authmanager api's.
* In the test script we have only one preparation thread group for Validate token api & have three execution thread groups for all the api's, where in preparation group the data preparation part will happen & in the execution group the main test execution will take place.
* Below are the api's which are included in the test script-
   1. Authenticate Using Client Id - Secret Key
   2. Authenticate Using User Id Pwd
   3. Validate Token
* All the thread groups will run in a sequential manner & if we don't want to run all of them we can disable the one which we don't want to run.
* Also for viewing the results or output of our test we have added certain listener test elements at the end of our test script which are - View Results Tree, Aggregate Report, Active Threads Over Time graph, Response Times Percentiles graph, Response Times vs Threads graph & Transaction Throughput vs Threads graph.
* We have a test element named 'User Defined Variables' in the test script where the server IP, server port & protocol details are parameterized & can be changed based on our requirements which will further reflect in the entire script.
