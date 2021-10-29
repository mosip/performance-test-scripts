Contains:-

This folder contains performance test scripts & test data for Registration Processor.

How to create test data:-

(1) First we need to run the packet generation utility. You can refer to the Part A section of this https://mosip.atlassian.net/wiki/spaces/R1/pages/330825775/Automation+release+notes+and+deliverables

(1) Next we have a JMeter script by the name of 'Packet Generation Script.jmx' in the scripts folder which is used to create packets.

(2) This script basically has two thread groups - Packet Generation (Preparation) & Packet Generation (Execution).

(3) In the preparation thread group we will basically create the context with the help of existing center id's, machine id's & user id's present in our current environment & we will read them through a file named 'contextDetails.csv' which is also present in the support-files folder.

(4) Once the contexts are created we will use the same in the execution thread group where basically the packet generation happens & then the RID's created gets stored in a file of the bin folder of JMeter.

(5) A sample document is also added to our packet through a file named 'docPath.txt' in order to increase the size of the packet to around 2 MB. Both 'docPath.txt' file & the 2 MB sample document are present in the support-files folder.

(6) We have a test element named 'User Defined Variables' in the script where the server IP, server port, protocol, packet utility port & packet utility server IP all these are parameterized & can be changed based on our requirements which will further reflect in the entire script.

(7) Once the packets are created we need to create the encrypted data for the packets & for that we have an utility by the name of 'RegProcessorpacketGenUtil' https://github.com/mosip/mosip-performance-tests-mt/tree/1.1.5/utilities/regprocessorpacketgenutil which will basically create a file with the encrypted data's for all the packets created.

(8) Check below property in config.properties file located in src/main/resources of 'RegProcessorpacketGenUtil'-

NUMBER_OF_TEST_PACKETS=100 (number of packets)
ENVIRONMENT=<environment name>
BASE_URL=<environment url>
Run below utility to create encrypted data for generated packets

sync_data - To create encrypted data for generated packets(test data to registration processor sync API)

How to run JMeter Helper & Test scripts:-

(1) Once the test data files are created we need to take care of the prerequisites before running our tests. So for that we have a helper script by the name of 'Regproc_Helper_script.jmx' in the scripts folder.

(2) In the helper script we have one thread group for the creation of authorization token which we will further use in our execution.

(3) The token created will be saved to a file in the bin folder of JMeter which will be used further by our test script for execution.

(4) Once all the prerequisites are taken care we will jump to the test script where our actual execution will take place for all the Regproc api's. It is saved by the name of 'Regproc_Test_script.jmx' in the scripts folder.

(5) In the test script we have total 4 thread groups where only for GET Packet Status api we have both preparation & execution thread groups.

(6) The Regrpoc module api's which we are targetting in this test script are - Sync Registration Packet Details api, Upload Registration Packet api & GET Packet Status api.

(7) For both Sync Registration Packet Details api & Upload Registration Packet api we are reading the encrypted data & packet path from file 'packetDetails.csv' which is there in the support-files folder.

(8) For GET Packet status api we are first creating the request body based on the number of RID's we want to search at once in the preparation group & store it in a file which gets further used by the execution group for the execution of the test.

(9) We have a test element named 'User Defined Variables' in both the helper & test scripts where the server IP, server port, protocol & packetStatusReqBodyRidCount (creating the packet status request body based on the number of RID's we want to search at once) all these are parameterized & can be changed based on our requirements which will further reflect in the entire script.

(10) All the thread groups will run in a sequential manner & if we don't want to run all of them we can disable the one which we don't want to run.

(11) Also for viewing the results or output of our test we have added certain listener test elements at the end of our test script which are - View Results Tree, Aggregate Report, Active Threads Over Time graph, Response Times Percentiles graph, Response Times vs Threads graph & Transaction Throughput vs Threads graph.

How to run JMeter DB script:-

(1) The JMeter DB script named 'Regproc Packets Processing Details From DB.jmx' is used for getting the packet processing status of the packets uploaded to the packet receiver.

(2) It contains two thread groups 'RegProc PacketProcessing Status From DB' (for getting packet status) & 'RegProc ProcessedPackets Details' (All details of the packets uploaded).

(3) Set the parameters of the environment database like dbHost,dbPort,dbName,dbUser,dbPassword and also the values for start_time_throughput & delay in the test element named 'User Defined Variables'-

dbHost -- host name of the registration processor database
dbPort -- port number of the registration processor database
dbName -- name of the registration processor database
dbUser -- user name of the registration processor database
dbPassword -- password of the registration processor database
start_time_throughput-- the 'cr_dtimes' for the first packet to reach packet receiver
delay-- delay value in milliseconds between each packets processing

(4) Execute the script for desired number of packets uploaded.

(5) Calculate the transaction times by running the 'RegProcTransactionDataUtil' https://github.com/mosip/mosip-performance-tests-mt/tree/1.1.5/utilities/regproc_transactiondata_util_v2.2.

(6) Check below property in config.properties file located in src/main/resources of 'RegProcTransactionDataUtil'-

ENVIRONMENT=<environment name>
REGID_LOG_FILE= C:\\MOSIP_PT\\test1\\kafka_softHSM\\regid_file1.csv (Provide generated regids in regid_file.csv)
EXCEL_FILE = C:\\MOSIP_PT\\test1\\kafka_softHSM\\regid_transaction_data.xlsx (Once above java utility is executed ,It will generate the transaction_times.xlsx which has all the transaction times of each stages of each packets)
