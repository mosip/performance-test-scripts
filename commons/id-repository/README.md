This folder contains performance test scripts and test data of ID-Repository module.

### Environment Required:-
***Below modules should be running in kubernetes setup***

* Websub
* Kernel notification service
* Kernel audit service
* Kernel authmanager service
* Biosdk service
* All IDrepo services
* All regproc & dmzregproc services

### Data prequisite:-
* Center & Machine ID details are required for the RID Generation. This values can be updated based on the environment from [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/id-repository/support-files/Center-MachineIDValues.csv).
* Some of the identity variables like firstname, date of birth & gender can be added from [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/id-repository/support-files/addIdentityRequestDetails.csv).
* Some of the identity variables like firstname, date of birth, gender & address can be updated or modified from [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/id-repository/support-files/updateIdentityRequestDetails.csv).

### How to run JMeter scripts:-
* We need to take care of the prerequisites first for which we a helper script [IDRepo_Helper_Script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/id-repository/scripts/IDRepo_Helper_Script.jmx).
* The helper script has one thread group for the creation of authorization token which we will further use in our execution.
* All the creation tasks which will happen that will automatically save the tokens created to a file in the bin folder of JMeter which will be used further by our test script for execution.
* For the test execution part we have a script [IDRepo_Test_Script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/id-repository/scripts/IDRepo_Test_Script.jmx) which will do all the execution tasks.
* In the test script we have a preparation & execution thread group for all the API's. In preparation group the data preparation part will happen & in the execution group the main test execution will take place.
* The ID Repo module API's which we are considering here are - Retrieve Identity using UIN API, Retrieve Identity using VID API, Add Identity API, Update Identity API,Create VID API, Update VID API & Auth type status API.
* All the thread groups will run in a sequential manner & if we don't want to run all of them we can disable the one which we don't want to run.
* Also for viewing the results or output of our test we have added certain listener test elements at the end of our test script which are - View Results Tree, Aggregate Report, Active Threads Over Time graph, Response Times Percentiles graph, Response Times vs Threads graph & Transaction Throughput vs Threads graph.
* We have a test element named 'User Defined Variables' in both the helper & test scripts where the 
  1. server IP
  2. server port
  3. protocol
  4. clientId
  5. secretKey
  6. appId
  7. vidType - (This is specifically used for Create VID API where we can basically set the type of VID we want to use eg: Temporary or Perpetual)
  8. lockStatus - (This is specifically used for Auth type status API where we can basically mention the auth status either it will be locked or unlocked)
  
  ***All these are parameterized & can be changed based on our requirements which will further reflect in the entire script***
