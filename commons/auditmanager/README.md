This folder contains performance test scripts and test data of Auditmanager module.

### Environment Required:-
***Below modules should be running in kubernetes setup***

* Kernel audit service
* Kernel authmanager service

### Data prequisite:-
* Audit manager request variable details are stored [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.2.0/commons/auditmanager/support-files/auditManagerRequestDetails.csv) and can be be modified based on requirements.

### How to run JMeter scripts:-
* We need to take care of the prerequisites first for which we a helper script [Auditmanager_Helper_Script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.2.0/commons/auditmanager/scripts/Auditmanager_Helper_Script.jmx).
* The helper script has one thread group for the creation of authorization token which we will further use in our execution.
* All the creation tasks which will happen that will automatically save the tokens created to a file in the bin folder of JMeter which will be used further by our test script for execution.
* For the test execution part we have a test script [Auditmanager_Test_Script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.2.0/commons/auditmanager/scripts/Auditmanager_Test_Script.jmx) which will take care of the execution tasks for the Auditmanager api.
* In the test script we have only one execution thread group for Add audits api which will take care of the main test execution.
* All the thread groups will run in a sequential manner & if we don't want to run all of them we can disable the one which we don't want to run.
* Also for viewing the results or output of our test we have added certain listener test elements at the end of our test script which are - View Results Tree, Aggregate Report, Active Threads Over Time graph, Response Times Percentiles graph, Response Times vs Threads graph & Transaction Throughput vs Threads graph.
* We have a test element named 'User Defined Variables' in both the helper & test scripts where the server IP, server port, protocol, clientId, secretKey & appId all these are parameterized & can be changed based on our requirements which will further reflect in the entire script.
