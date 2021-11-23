This folder contains performance test scripts and test data for Resident Services module

### Environment Required:-
***Below modules should be running in kubernetes setup***

* Websub
* ida-internal-service
* idrepo-identity-service
* idrepo-vid-service
* Credential request generator and credential service
* Kernel notification service
* Kernel audit service
* Kernel authmanager service
* Kernel keymanager service
* Biosdk service
* Kernel OTP manager service
* resident service

### Data prerequisite:-
* List of RID's as per environment which is valid in idrepo and IDA and we can edit this list from [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/resident-services/support-files/RID_list.txt)

### How to run JMeter scripts:-

* We need to take care of the prerequisites before running our tests. So for that we have a helper script by the name of [Resident_Helper_script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/resident-services/scripts/Resident_Helper_script.jmx).

* In the helper script we have two thread groups which we will run in a sequential manner & if we don't want to run all two we can disable the one which we don't want to run.

* The first thread group is for the creation of authorization token which we will further use in our execution.

* The second thread group is for the creation of UIN by using existing RID's for which a text file is there in the support files folder [RID_list.txt](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/resident-services/support-files/RID_list.txt).

* All the creation tasks which will happen that will automatically save the tokens and id's created to a file in the bin folder of JMeter which will be used further by our test script for execution.

* Once all the prerequisites are taken care we will jump to the test script where our actual execution will take place for all the Resident API's. It is saved by the name of [Resident_Test_script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/resident-services/scripts/Resident_Test_script.jmx).

* In the test script we have total 15 thread groups which contains one preparation & execution thread group for all the Resident module API's for which performance testing has to be done.

* The preparation thread group takes care of the data preparation of the API for which we want to do our test & then saves the data to a file in the bin folder of JMeter which is further being used by our execution group.

* The execution group is the group where the actual test execution will take place for the API which needs performance testing.

* Only for Revoke VID API we have two preparation groups where in the first preparation thread group VID's will get generated & saved to a file in bin folder of JMeter which will be taken further by the second preparation thread group which will basically send otp as type VID & store the the VID's along with the transaction ID's used to a file which will finally be used by our execution group.

* There will be some delay between the two preparation groups & the value of the delay we have to give in the test element 'User Defined Variables' in milliseconds.

***NOTE: The delay value entirely depends on how much time the credential request generator takes to make all the VID's generated to ISSUED state from NEW and this can be checked in credential_transaction table from the DB.***

* The Resident module API's which we are targetting in this test script are - Revoke VID API, Request OTP API, RID Check Status API, Auth Lock API, Auth Unlock API, Request Credential API, Generate VID API & Auth History API.

* All the thread groups will run in a sequential manner & if we don't want to run all of them we can disable the one which we don't want to run.

* Also for viewing the results or output of our test we have added certain listener test elements at the end of our test script which are - View Results Tree, Aggregate Report, Active Threads Over Time graph, Response Times Percentiles graph, Response Times vs Threads graph & Transaction Throughput vs Threads graph.

* We have a test element named 'User Defined Variables' in both the helper & test scripts where the server IP, server port, protocol, delay (delay between revoke vid preparation groups), clientId, secretKey & appId all these are parameterized & can be changed based on our requirements which will further reflect in the entire script.
