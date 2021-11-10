## This folder contains performance test scripts and test data of Masterdata module.

### Environment Required:-
* Websub
* Kernel notification service
* Kernel audit service
* Kernel authmanager service
* Kernel masterdata service

### Data prequisite:-
* Template request details are required for the creation of new templates. This values can be updated based on the environment from [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/masterdata/support-files/templates.csv).
* Dynamic fields request details are required for the creation of new dynamic fields. This values can be updated based on the environment from [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/masterdata/support-files/dynamicFields.csv).
* Registration center request details are required for the creation of new registration centers. This values can be updated based on the environment from [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/masterdata/support-files/regCenters.csv).
* User ID values required for getting the user details can be updated from [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/masterdata/support-files/userIDValues.txt) based on the environment.

### How to run JMeter scripts:-
* We need to take care of the prerequisites first for which we a helper script [Masterdata_Helper_Script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/masterdata/scripts/Masterdata_Helper_Script.jmx).
* The helper script has one thread group for the creation of authorization token which we will further use in our execution.
* All the creation tasks which will happen that will automatically save the tokens created to a file in the bin folder of JMeter which will be used further by our test script for execution.
* For the test execution part we have a script [Masterdata_Test_Script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/masterdata/scripts/Masterdata_Test_Script.jmx) which will do all the execution tasks.
* In the test script we have preparation & execution thread groups for each of the api's, such that in preparation group the data preparation part will happen & in the execution group the main test execution will take place.
* The ID Repo module api's which we are considering here are - Get Templates api, Get Templates By Lang & Temp Code api, Get Latest ID Schema api, Get Dynamic Fields api, Get Registration Centers api and Get Users api.
* All the thread groups will run in a sequential manner & if we don't want to run all of them we can disable the one which we don't want to run.
* Also for viewing the results or output of our test we have added certain listener test elements at the end of our test script which are - View Results Tree, Aggregate Report, Active Threads Over Time graph, Response Times Percentiles graph, Response Times vs Threads graph & Transaction Throughput vs Threads graph.
* We have a test element named 'User Defined Variables' in both the helper & test scripts where the server IP, server port, protocol, clientId, secretKey & appId all these are parameterized & can be changed based on our requirements which will further reflect in the entire script.