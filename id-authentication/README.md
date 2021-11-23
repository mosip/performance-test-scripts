This folder contains performance test scripts and test data for ID Authentication module.

### Environment Required:-
***Below utilities should be configured and run in Jmeter test setup***

* Websub
* Kernel notification service
* Kernel audit service
* Kernel authmanager service
* Biosdk service
* Kernel OTP manager
* ida auth service
* ida kyc service
* ida otp service
****Below utilities should be configured and run in Jmeter test setup****
* We need to start the [Partner Demo/IDA utility](https://mosip.atlassian.net/wiki/spaces/QT/pages/670597144/Auth+using+the+New+Partner+Demo+service+with+external+Certificate)

### Data prerequisite:-
* List of RID's as per environment which is valid in idrepo and IDA and we can edit this list from [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/id-authentication/support-files/RID_list.txt)

### How to run JMeter scripts:-

* We need to take care of the prerequisites before running our tests. So for that we have a helper script by the name of [IDA_Helper_script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/id-authentication/scripts/IDA_Helper_script.jmx).

* In the helper script we have three thread groups which we will run in a sequential manner & if we don't want to run all the three we can disable the one which we don't want to run.

* The first thread group is for the creation of authorization token which we will further use in our execution.

* The second thread group is for the creation of UIN by using existing RID's for which a text file is there in the support files folder [RID_list.txt](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/id-authentication/support-files/RID_list.txt).

* The third thread group is basically for setting up the third party certificates which are required for running the IDA API scripts & also for creating and publishing policy & then creating various partner certificates based on the policy created.

* All the creation tasks which will happen that will automatically save the tokens and id's created to a file in the bin folder of JMeter which will be used further by our test script for execution.

* Once all the prerequisites are taken care we will jump to the test script where our actual execution will take place for all the IDA API's. It is saved by the name of [IDA_Test_script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/id-authentication/scripts/IDA_Test_script.jmx).

* In the test script we have total 10 thread groups which contains one preparation & execution thread group for all the IDA module API's for which performance testing has to be done.

* The preparation thread group takes care of the data preparation of the API for which we want to do our test & then saves the data to a file in the bin folder of JMeter which is further being used by our execution group.

* The execution group is the group where the actual test execution will take place for the API which needs performance testing.

* The IDA module API's which we are targetting in this test script are - Send OTP API, Auth with OTP API, Ekyc with OTP API, Auth with Biometrics API & Ekyc with Biometrics API.

* All the thread groups will run in a sequential manner & if we don't want to run all of them we can disable the one which we don't want to run.

* Also for viewing the results or output of our test we have added certain listener test elements at the end of our test script which are - View Results Tree, Aggregate Report, Active Threads Over Time graph, Response Times Percentiles graph, Response Times vs Threads graph & Transaction Throughput vs Threads graph.

* We have a test element named 'User Defined Variables' in both the helper & test scripts where the server IP, server port, protocol, ida utility port, ida utility server IP, clientId, secretKey & appId all these are parameterized & can be changed based on our requirements which will further reflect in the entire script.

