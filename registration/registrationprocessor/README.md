## This folder contains performance test scripts & test data for Registration Processor Module.

### Environment Required:-
* Kernel Auth manager
* Kernel Syncdata
* Kernel Notification Manager
* Kernel Key manager
* Kernel Master data
* Kernel Audit manager
* All regproc & dmzregproc services

### Data prerequisite:-
* Context details needs to be updated based on the environment which we are using. It can be updated from [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/registration/registrationprocessor/support-files/contextDetails.csv).
* Sample 2 MB document used for increasing the packet size. This can be found [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/registration/registrationprocessor/support-files/document.pdf).
* Document path can be updated from [here](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/registration/registrationprocessor/support-files/documentPath.txt).

### How to create test data:-

* We need to run the packet generation utility. You can refer to the Part A section of [Packet Generation utility](https://mosip.atlassian.net/wiki/spaces/R1/pages/330825775/Automation+release+notes+and+deliverables). 

* We have a JMeter script [Packet Generation Script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/registration/registrationprocessor/scripts/Packet%20Generation%20Script.jmx) which is used to create packets.

* This script basically has two thread groups - Packet Generation (Preparation) & Packet Generation (Execution).

* In the preparation thread group we will basically create the context with the help of existing center id's, machine id's & user id's present in our current environment & we will read them through a file named [contextDetails.csv](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/registration/registrationprocessor/support-files/contextDetails.csv).

* Once the contexts are created we will use the same in the execution thread group where basically the packet generation happens & then the RID's created gets stored in a file of the bin folder of JMeter.

* A sample document is also added to our packet through a file named [docPath.txt](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/registration/registrationprocessor/support-files/documentPath.txt) in order to increase the size of the packet to around 2 MB.

* We have a test element named 'User Defined Variables' in the script where the server IP, server port, protocol, packet utility port & packet utility server IP all these are parameterized & can be changed based on our requirements which will further reflect in the entire script.

* Create the encrypted data for the packets & for that we have an utility [RegProcessorpacketGenUtil](https://github.com/mosip/mosip-performance-tests-mt/tree/1.1.5/utilities/regprocessorpacketgenutil) which will basically create a file with the encrypted data's for all the packets created.

* Check below property in config.properties file located in src/main/resources of [RegProcessorpacketGenUtil](https://github.com/mosip/mosip-performance-tests-mt/tree/1.1.5/utilities/regprocessorpacketgenutil)-
   1. NUMBER_OF_TEST_PACKETS=100 (number of packets)
   2. ENVIRONMENT= environment name
   3. BASE_URL= environment url

* Run below utility to create encrypted data for generated packets
   1. sync_data - To create encrypted data for generated packets(test data to registration processor sync API)

### How to run JMeter Helper & Test scripts:-

* We need to take care of the prerequisites before running our tests. So for that we have a helper script [Regproc_Helper_script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/registration/registrationprocessor/scripts/Regproc_Helper_Script.jmx).

* In the helper script we have one thread group for the creation of authorization token which we will further use in our execution.

* The token created will be saved to a file in the bin folder of JMeter which will be used further by our test script for execution.

* Once all the prerequisites are taken care we will jump to the test script where our actual execution will take place for all the Regproc api's. The script is [Regproc_Test_script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/registration/registrationprocessor/scripts/Regproc_Test_Script.jmx).

* In the test script we have total 4 thread groups where only for GET Packet Status api we have both preparation & execution thread groups.

* The Regrpoc module api's which we are targetting in this test script are - Sync Registration Packet Details api, Upload Registration Packet api & GET Packet Status api.

* For both Sync Registration Packet Details api & Upload Registration Packet api we are reading the encrypted data & packet path from a file which is created through the [RegProcessorpacketGenUtil](https://github.com/mosip/mosip-performance-tests-mt/tree/1.1.5/utilities/regprocessorpacketgenutil) .

* For GET Packet status api we are first creating the request body based on the number of RID's we want to search at once in the preparation group & store it in a file which gets further used by the execution group for the execution of the test.

* We have a test element named 'User Defined Variables' in both the helper & test scripts where the server IP, server port, protocol, clientId, secretKey, appId & packetStatusReqBodyRidCount (creating the packet status request body based on the number of RID's we want to search at once) all these are parameterized & can be changed based on our requirements which will further reflect in the entire script.

* All the thread groups will run in a sequential manner & if we don't want to run all of them we can disable the one which we don't want to run.

* Also for viewing the results or output of our test we have added certain listener test elements at the end of our test script which are - View Results Tree, Aggregate Report, Active Threads Over Time graph, Response Times Percentiles graph, Response Times vs Threads graph & Transaction Throughput vs Threads graph.

### How to run JMeter DB script:-

* The JMeter DB script [Regproc Packets Processing Details From DB.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.1.5/registration/registrationprocessor/scripts/Regproc%20Packets%20Processing%20Details%20From%20DB.jmx) is used for getting the packet processing status of the packets uploaded to the packet receiver.

* It contains two thread groups 'RegProc PacketProcessing Status From DB' (for getting packet status) & 'RegProc ProcessedPackets Details' (All details of the packets uploaded).

* Set the parameters of the environment database like dbHost,dbPort,dbName,dbUser,dbPassword and also the values for start_time_throughput & delay in the test element named 'User Defined Variables'-
  1. dbHost -- host name of the registration processor database
  2. dbPort -- port number of the registration processor database
  3. dbName -- name of the registration processor database
  4. dbUser -- user name of the registration processor database
  5. dbPassword -- password of the registration processor database
  6. start_time_throughput-- the 'cr_dtimes' for the first packet to reach packet receiver
  7. delay-- delay value in milliseconds between each packets processing

* Execute the script for desired number of packets uploaded.

* Calculate the transaction times by running the [RegProcTransactionDataUtil](https://github.com/mosip/mosip-performance-tests-mt/tree/1.1.5/utilities/regproc_transactiondata_util_v2.2).

* Check below property in config.properties file located in src/main/resources of [RegProcTransactionDataUtil](https://github.com/mosip/mosip-performance-tests-mt/tree/1.1.5/utilities/regproc_transactiondata_util_v2.2)-
  1. ENVIRONMENT= environment name
  2. REGID_LOG_FILE= C:\\MOSIP_PT\\test1\\kafka_softHSM\\regid_file1.csv (Provide generated regids in regid_file.csv)
  3. EXCEL_FILE = C:\\MOSIP_PT\\test1\\kafka_softHSM\\regid_transaction_data.xlsx (Once above java utility is executed ,It will generate the transaction_times.xlsx which has all the transaction times of each stages of each packets)
