Contains:-

This folder contains performance test scripts and test data for ID Authentication module

How to run JMeter scripts:-

(1) First we need to start the Partner Demo/IDA utility - You can refer the the below link for reference:
https://mosip.atlassian.net/wiki/spaces/QT/pages/670597144/Auth+using+the+New+Partner+Demo+service+with+external+Certificate

(2) Once the Partner Demo/IDA Utility is started we need to take care of the prerequisites before running our tests. So for that we have a helper script by the name of 'IDA_Helper_script.jmx' in the scripts folder.

(3) In the helper script we have three thread groups which we will run in a sequential manner & if we don't want to run all the three we can disable the one which we don't want to run.

(4) The first thread group is for the creation of authorization token which we will further use in our execution.

(5) The second thread group is for the creation of UIN by using existing RID's for which a text file is there in the support files folder by the name of 'RID_list.txt'.

(6) The third thread group is basically for setting up the third party certificates which are required for running the IDA api scripts & also for creating and publishing policy & then creating various partner certificates based on the policy created.

(7) All the creation tasks which will happen that will automatically save the tokens and id's created to a file in the bin folder of JMeter which will be used further by our test script for execution.

(8) Once all the prerequisites are taken care we will jump to the test script where our actual execution will take place for all the IDA api's. It is saved by the name of 'IDA_Test_script.jmx' in the scripts folder.

(9) In the test script we have total 10 thread groups which contains one preparation & execution thread group for all the IDA module api's for which performance testing has to be done.

(10) The preparation thread group takes care of the data preparation of the api for which we want to do our test & then saves the data to a file in the bin folder of JMeter which is further being used by our execution group.

(11) The execution group is the group where the actual test execution will take place for the api which needs performance testing.

(12) The IDA module api's which we are targetting in this test script are - Send OTP api, Auth with OTP api, Ekyc with OTP api, Auth with Biometrics api & Ekyc with Biometrics api.

(13) All the thread groups will run in a sequential manner & if we don't want to run all of them we can disable the one which we don't want to run.

(14) Also for viewing the results or output of our test we have added certain listener test elements at the end of our test script which are - View Results Tree, Aggregate Report, Active Threads Over Time graph, Response Times Percentiles graph, Response Times vs Threads graph & Transaction Throughput vs Threads graph.

(15) We have a test element named 'User Defined Variables' in both the helper & test scripts where the server IP, server port, protocol, ida utility port & ida utility server IP all these are parameterized & can be changed based on our requirements which will further reflect in the entire script.

