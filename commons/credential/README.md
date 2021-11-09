## This folder contains performance test scripts and test data of Credential module.

### Environment Required:-
* Websub
* Kernel notification service
* Kernel audit service
* Kernel authmanager service
* Biosdk service
* IDrepo credential request & credential service
* Datashare service

### Data prequisite:-
* * All the credential request variables like credential id, credential type, issuer etc are needed for generating credential request or for issuing credential service. These values can be modified from [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/credential/support-files/credRequestVariables.csv) based on which environment we are working.

### How to run JMeter scripts:-
* We need to take care of the prerequisites first for which we a helper script [Credential_Helper_Script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/credential/scripts/Credential_Helper_Script.jmx).
* The helper script has two thread groups for the creation of authorization token's for both Credential Request & Credential Service which we will further use in our execution.
* All the creation tasks which will happen that will automatically save the tokens created to a file in the bin folder of JMeter which will be used further by our test script for execution.
* For the test execution part we have a script [Credential_Test_Script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/credential/scripts/Credential_Test_Script.jmx) which will do all the execution tasks.
* In the test script we have two execution thread groups one for Generate Credential Request api & the other one for Issue Credential api which we will use for the main test executions.
* All the thread groups will run in a sequential manner & if we don't want to run all of them we can disable the one which we don't want to run.
* Also for viewing the results or output of our test we have added certain listener test elements at the end of our test script which are - View Results Tree, Aggregate Report, Active Threads Over Time graph, Response Times Percentiles graph, Response Times vs Threads graph & Transaction Throughput vs Threads graph.
* We have a test element named 'User Defined Variables' in the helper script where the below values can be changed based on the environment we are using - 
   1. server IP
   2. server port
   3. protocol
   4. clientIdCredReq
   5. secretKeyCredReq
   6. appIdCredReq
   7. clientIdCredSer
   8. appIdCredSer
   9. secretKeyCredSer