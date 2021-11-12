## This folder contains performance test scripts and test data of Kernel module.

### Environment Required:-
* Websub
* Kernel notification service
* Kernel audit service
* Kernel authmanager service
* Kernel keymanager service
* Kernel ridgenerator service
* Kernel idgenerator service
* Kernel pridgenerator service
* Kernel otpmanager service

### Data prequisite:-
* Center & Machine ID details are required for the RID Generation through ridgenerator service. These values can be updated based on the environment from [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/kernel/support-files/Center-MachineIDValues.csv).
* Audit manager request variable details are stored [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/kernel/support-files/auditManagerRequestDetails.csv) and can be be modified based on requirements.
* Auth manager request client id & secret key details are stored [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/kernel/support-files/authManagerClientIdSecretKeyDetails.csv) and can be be modified based on environment.
* Auth manager request user id & password details are stored [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/kernel/support-files/authManagerUserIdPasswordDetails.csv) and can be be modified based on environment.
* Message & number details for notification manager sms api request are stored [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/kernel/support-files/messageAndNumberDetails.csv) and can be be modified based on requirements.
* Email details for notification manager email api request are stored [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/kernel/support-files/emailDetails.csv) and can be be modified based on requirements.
* Encrypt data request variable details for Keymanager api's are stored [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/kernel/support-files/encryptDataRequestVariations.csv)and can be modified based on the requirements.

### How to run JMeter scripts:-
* We need to take care of the prerequisites first for which we a helper script [Kernel_Helper_Script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/commons/kernel/scripts/Kernel_Helper_Script.jmx).
* The helper script has one thread group for the creation of authorization token which we will further use in our execution.
* All the creation tasks which will happen that will automatically save the tokens created to a file in the bin folder of JMeter which will be used further by our test script for execution.
* For the test execution part we have total 8 test scripts which will do all the execution tasks for all the kernel services.
* In the test scripts we have preparation thread group for some of the api's & execution thread groups for all the api's, where in preparation group the data preparation part will happen & in the execution group the main test execution will take place.
* The Kernel module test scripts which we are considering here are below-
   1. Kernel notification service
   2. Kernel audit service
   3. Kernel authmanager service
   4. Kernel keymanager service
   5. Kernel ridgenerator service
   6. Kernel idgenerator service
   7. Kernel pridgenerator service
   8. Kernel otpmanager service
  All these scripts can be found [here](https://github.com/mosip/mosip-performance-tests-mt/tree/1.1.5/commons/kernel/scripts).
* All the thread groups will run in a sequential manner & if we don't want to run all of them we can disable the one which we don't want to run.
* Also for viewing the results or output of our test we have added certain listener test elements at the end of our 8 test scripts which are - View Results Tree, Aggregate Report, Active Threads Over Time graph, Response Times Percentiles graph, Response Times vs Threads graph & Transaction Throughput vs Threads graph.
* We have a test element named 'User Defined Variables' in both the helper & test scripts where the server IP, server port, protocol, clientId, secretKey & appId all these are parameterized & can be changed based on our requirements which will further reflect in the entire script.