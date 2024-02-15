This folder contains performance test scripts and test data of Kernel module.

### Environment Required:-
***Below modules should be running in kubernetes setup***

* Kernel notification service
* Kernel audit service
* Kernel authmanager service
* Kernel keymanager service
* Kernel ridgenerator service
* Kernel idgenerator service
* Kernel pridgenerator service
* Kernel otpmanager service

### Data prequisite:-
* Center & Machine ID details are required for the RID Generation through ridgenerator service. These values can be updated based on the environment from [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.2.0/commons/kernel/support-files/Center-MachineIDValues.csv).
* Message & number details for notification manager sms API request are stored [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.2.0/commons/kernel/support-files/messageAndNumberDetails.csv) and can be be modified based on requirements.
* Email details for notification manager email API request are stored [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.2.0/commons/kernel/support-files/emailDetails.csv) and can be be modified based on requirements.

### How to run JMeter scripts:-
* We need to take care of the prerequisites first for which we a helper script [Kernel_Helper_Script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.2.0/commons/kernel/scripts/Kernel_Helper_Script.jmx).
* The helper script has one thread group for the creation of authorization token which we will further use in our execution.
* All the creation tasks which will happen that will automatically save the tokens created to a file in the bin folder of JMeter which will be used further by our test script for execution.
* For the test execution part we have a test script [Kernel_Test_Script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.2.0/commons/kernel/scripts/Kernel_Test_Script.jmx) which will take care of the execution tasks for all the kernel services.
* In the test script we have a preparation thread group for only OTP Manager - Validate OTP api & execution thread groups for all the other API's, where in preparation group the data preparation part will happen & in the execution group the main test execution will take place.
* The Kernel module test script consists of the following api's-
   1. OTP Manager- Generate OTP
   2. OTP Manager- Validate OTP
   3. Notification Manager- SMS
   4. Notification Manager- EMAIL
   5. IDGenerator- Generate UIN
   6. IDGenerator- Generate VID
   7. Generate RID
   8. Generate PRID
  
  ***All these scripts can be found [here](https://github.com/mosip/mosip-performance-tests-mt/tree/1.2.0/commons/kernel/scripts).***
* All the thread groups will run in a sequential manner & if we don't want to run all of them we can disable the one which we don't want to run.
* Also for viewing the results or output of our test we have added certain listener test elements at the end of our test script which are - View Results Tree, Aggregate Report, Active Threads Over Time graph, Response Times Percentiles graph, Response Times vs Threads graph & Transaction Throughput vs Threads graph.
* We have a test element named 'User Defined Variables' in both the helper & test scripts where the server IP, server port, protocol, clientId, secretKey & appId all these are parameterized & can be changed based on our requirements which will further reflect in the entire script.
