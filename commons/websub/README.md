This folder contains performance test scripts and test data of Websub module.

### Environment Required:-
***Below modules should be running in kubernetes setup***

* Websub

### Data prequisite:-
* We need to start the [Websub Perf Utility](https://github.com/mosip/mosip-performance-tests-mt/tree/1.2.0/utilities/websub-perf-utility).

### How to run JMeter script:-
* This consist of a test script [Websub_Test_Script.jmx](https://github.com/mosip/mosip-performance-tests-mt/blob/1.2.0/commons/websub/script/Websub_Test_Script.jmx) which will do all the execution tasks.
* The test script consists of 6 thread groups which are listed below - 
   1. Register Topics : This is basically used for registering a new topic to the websub and has to be run based on the number of topics we want to register & this has to be run before the other thread groups.
   2. Subscribe To Topics : This is used for subscribing to the registered topic by using subscription id's which we have handled in our script using a counter named 'Subscription ID Counter' & this thread group we have to run based on the number of topics & subscribers we are using .
   3. Publish Messages : This is used for publishing messages to the websub which further will traverse to the subscribers.
   4. Delay - Time Wait For Message Delivery : This is used for making the script to wait till all the messages are delivered to websub utility and then the results are collected from the websub utility.
   5. Get Results : This is used for getting the results like total messages received by each subscriber, average turn around time in (ms), ninetieth percentile in (ms) and the subscription id's. Also we will get the total average of the turn around time & ninetieth percentile in (ms) for all the subscribers used.
   6. Reset Results : This is used for resetting the previous results as there is no specific DB to store websub results so it's better to clear the results once we are done with our test.
      
* All the thread groups will run in a sequential manner & if we don't want to run all of them we can disable the one which we don't want to run.
* Also for viewing the results or output of our test we have added certain listener test elements at the end of our test script which are - View Results Tree, Aggregate Report, Active Threads Over Time graph, Response Times Percentiles graph, Response Times vs Threads graph & Transaction Throughput vs Threads graph.
* We have a test element named 'User Defined Variables' in the test script where we have the below variables - 
   1. server IP
   2. server port
   3. protocol
   4. websubPerfUtilityServerIP
   5. websubPerfUtilityServerPortNo
   6. websubPerfUtilityProtocol
   7. delay - (This is used to update the delay value in milliseconds which we need to give for the Delay thread group)
   8. totalSubscribers - (This is used to update the number of subscribers which we want to use)
   9. publisherConcurrency - (This is used to update the number of threads/users for the Publishing Message thread group)
   10. publisherIterations - (This is used to update the number of iterations for the Publishing Message thread group)
   11. uniqueTopicCount - (This is used to update the number of unique topic names we are using in the 'uniqueTopicNames.csv' file)
   
   **(Note - Total messages published to the websub = publisherConcurrency * publisherIterations)**
   
  ***All these are parameterized & can be changed based on our requirements which will further reflect in the entire script***
