This folder contains performance test scripts and test data of Keymanager module.

### Environment Required:-
***Below modules should be running in kubernetes setup***

* Kernel audit service
* Kernel authmanager service
* Kernel keymanager service

### Data prequisite:-
* Encrypt data request variable details for Keymanager API's are stored [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.2.0/commons/keymanager/support-files/encryptDataRequestVariations.csv) and can be modified based on the requirements.

### How to run JMeter scripts:-
* We need to take care of the prerequisites first for which we a helper script [Keymanager_Helper_Script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.2.0/commons/keymanager/scripts/Keymanager_Helper_Script.jmx).
* The helper script has one thread group for the creation of authorization token which we will further use in our execution.
* All the creation tasks which will happen that will automatically save the tokens created to a file in the bin folder of JMeter which will be used further by our test script for execution.
* For the test execution part we have a test script [Keymanager_Test_Script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.2.0/commons/keymanager/scripts/Keymanager_Test_Script.jmx) which will take care of the execution tasks for the keymanager api's.
* In the test scripts we have preparation thread group for some of the API's & execution thread groups for all the API's, where in preparation group the data preparation part will happen & in the execution group the main test execution will take place.
* Below are the api's included in our test script-
   1. Encrypt Data
   2. Decrypt Data
   3. Encrypt Data With Pin
   4. Decrypt Data With Pin
   5. Generate JWT Signature
   6. Verify JWT Signature
* All the thread groups will run in a sequential manner & if we don't want to run all of them we can disable the one which we don't want to run.
* Also for viewing the results or output of our test we have added certain listener test elements at the end of our test script which are - View Results Tree, Aggregate Report, Active Threads Over Time graph, Response Times Percentiles graph, Response Times vs Threads graph & Transaction Throughput vs Threads graph.
* We have a test element named 'User Defined Variables' in both the helper & test scripts where the server IP, server port, protocol, clientId, secretKey & appId all these are parameterized & can be changed based on our requirements which will further reflect in the entire script.
