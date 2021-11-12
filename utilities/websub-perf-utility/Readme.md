
## websub-perf-utility



This utility can be used to act as multiple subscribers, get total turn around time for a subid,message count and 90th percentile  .



To use this utility
 1. Create a jar using following command in the directory where pom is present
 
	```
	mvn clean install
	```	
 2.	Run the utility
	 ```
	 java -jar websub-perf-utility-1.2.0-SNAPSHOT.jar
	 ```
 	
	### Usage 
1. When you subscribe callback url should be of the format 
	```
	/callback/{subid}
	```
2. You can use **/result/{subID}** enpoint to get the result for that subID after a run.
3. Since everything is in a local cache it is essential to hit **/reset** url after a run
	


