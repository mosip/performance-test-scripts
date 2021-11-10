## This folder contains performance test scripts and test data of Packet manager module.

### Environment Required:-
* Websub
* Kernel notification service
* Kernel audit service
* Kernel authmanager service
* Packetmanager service
* All regproc & dmzregproc services

### Data prequisite:-
* Reg ID's, process and source request values are stored [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/packetmanager/support-files/ridProcess%26Source.txt) and can be updated based on the environment.
* Sample document names are stored [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/packetmanager/support-files/documentNames.txt) and can be updated based on the requirement.
* Sample field names are stored [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/packetmanager/support-files/fieldNames.txt) and can be updated based on the requirement.
* Sample modality values are stored [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/packetmanager/support-files/modalityValues.txt) and can be updated based on the environment.
*Sample field values are stored [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/packetmanager/support-files/searchFieldsRequestBody.csv) and can be updated based on the environment.

### How to run JMeter scripts:-
* We need to take care of the prerequisites first for which we a helper script [PacketManager_Helper_Script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/packetmanager/scripts/PacketManager_Helper_Script.jmx).
* The helper script has one thread group for the creation of authorization token which we will further use in our execution.
* All the creation tasks which will happen that will automatically save the tokens created to a file in the bin folder of JMeter which will be used further by our test script for execution.
* For the test execution part we have a script [PacketManager_Test_Script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/packetmanager/scripts/PacketManager_Test_Script.jmx) which will do all the execution tasks.
* In the test script we have 7 execution thread groups for all the api's, where the main test execution will take place.
* The Packetmanager module api's which we are considering here are - Get Documents api, Validate Packet api, Search Field api, Search Fields api, Get Biometrics, Get Audits api and Get MetaInfo api.
* All the thread groups will run in a sequential manner & if we don't want to run all of them we can disable the one which we don't want to run.
* Also for viewing the results or output of our test we have added certain listener test elements at the end of our test script which are - View Results Tree, Aggregate Report, Active Threads Over Time graph, Response Times Percentiles graph, Response Times vs Threads graph & Transaction Throughput vs Threads graph.
* We have a test element named 'User Defined Variables' in both the helper & test scripts where the server IP, server port, protocol, clientId, secretKey & appId all these are parameterized & can be changed based on our requirements which will further reflect in the entire script.