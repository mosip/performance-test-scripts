
### Contains
* This folder contains performance helper script and test script of below API endpoint categories.
    1. Management API Endpoints
    2. UI API Endpoints
    3. OIDC API Endpoints 
    4. Wallet Binding Endpoints 
    5. Sign Up Services Endpoints

* Open source Tools used,
    1. [Apache JMeter](https://jmeter.apache.org/)

### How to run performance scripts using Apache JMeter tool
* Download Apache JMeter from https://jmeter.apache.org/download_jmeter.cgi
* Download scripts for the required module.
* Start JMeter by running the jmeter.bat file for Windows or jmeter file for Unix. 
* Validate the scripts for one user.
* Execute a dry run for 10 min.
* Execute performance run with various loads in order to achieve targeted NFR's.

### Setup points for Execution

* Create Identities in MOSIP Authentication System (Setup) : This thread contains the authorization api's for regproc and idrepo from which the auth token will be generated. There is set of 4 api's generate RID, generate UIN, add identity and add VID. From here we will get the VID which can be further used as individual id. These 4 api's are present in the loop controller where we can define the number of samples for creating identities in which "addIdentitySetup" is used as a variable. 

* Create Identities in MOSIP Authentication System - Password Based Auth (Setup) : This thread contains the authorization api's for regproc and idrepo from which the auth token will be generated. There is set of 3 api's generate UIN, generate hash for password and add identity. From here we will get the identifier value which can be further used as an individual id or we can say as phone number in pwd based authentication. These 3 api's are present in the loop controller where we can define the number of samples for creating identities in which "addIdentitySetup" is used as a variable.

* Create OIDC Client in MOSIP Authentication System (Setup) : This thread contains a JSR223 sampler(Generate Key Pair) from which will get a public-private key pair. The public key generated will be used in the OIDC client api to generate client id's  which will be registered for both IDA and eSignet. The private key generated from the sampler will be used in another JSR223 sampler(Generate Client Assertion) present in the OIDC Token (Execution). Generated client id's and there respective private key will be stored in a file which will be used further in the required api's.

* In the above Create OIDC Client in MOSIP Authentication System (Setup) check for the Policy name and Auth partner id for the particular env in which we are executing the scripts. The policy name provided must be associated with the correct Auth partner id. 

* Auth Token Generation (Setup) : This thread conatins Auth manager authentication API which will generate auth token value for PMS and mobile client. 

* Store Successful Credentials To File - Password Based Auth (Setup) : In this thread we will pass the file created from Create Identities in MOSIP Authentication System - Password Based Auth (Setup). Here, we will create a csv file which will store successful credentials into the file. 

* For execution purpose neeed to check for the mentioned properties: 
   * eSignet default properties: Update the value for the properties according to the execution setup. Perform the execution for eSignet api's with redis setup. So check for the redis setup accordingly.
          mosip.esignet.cache.size - Enabled while not using the redis setup. Can keep the cache size around more than 100k.
          mosip.esignet.cache.expire-in-seconds - 86400
          mosip.esignet.access-token-expire-seconds - 86400
          mosip.esignet.id-token-expire-seconds - 86400
          spring.cache.type=redis - check for this property and enable the redis.
   * application default properties: Update the value for the below property.
          mosip.kernel.otp.expiry-time - 86400
   * id-authentication default properties: Update the value for the below properties.
          otp.request.flooding.max-count - 100000
          kyc.token.expire.time.adjustment.seconds - 86400 
   
   * signup default properties : Update the value for the properties according to the execution setup.
         mosip.signup.unauthenticated.txn.timeout=86400
         mosip.signup.verified.txn.timeout=86400
         mosip.signup.status-check.txn.timeout=86400

* We need some jar files which needs to be added in lib folder of jmeter, PFA dependency links for your reference : 

   * bcprov-jdk15on-1.66.jar
      * <!-- https://mvnrepository.com/artifact/org.bouncycastle/bcprov-jdk15on -->
<dependency>
    <groupId>org.bouncycastle</groupId>
    <artifactId>bcprov-jdk15on</artifactId>
    <version>1.66</version>
</dependency>

   * jjwt-api-0.11.2.jar
      * <!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.2</version>
</dependency>

   * jjwt-impl-0.11.2.jar
       * <!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-impl -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.2</version>
    <scope>runtime</scope>
</dependency>

   * jjwt-jackson-0.11.2.jar
       * <!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-jackson -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.11.2</version>
    <scope>runtime</scope>
</dependency>

   * nimbus-jose-jwt-9.25.6.jar  
       * <!-- https://mvnrepository.com/artifact/com.nimbusds/nimbus-jose-jwt -->
<dependency>
    <groupId>com.nimbusds</groupId>
    <artifactId>nimbus-jose-jwt</artifactId>
    <version>9.25.6</version>
</dependency>

### Execution points for eSignet Management API's
* Management - Create OIDC Client (Execution) : This thread group will directly execute in which we are using a counter which will generate unique client id. Because we can't generate same duplicate cliend id.
* Management - Update OIDC Client : 
   * Management Update OIDC Client (Preparation) - In this the above mentioned Create OIDC Client API will be used to generate a large number of OIDC client id samples which will get stored in a file and will be used in the execution.
   * Management Update OIDC Client (Execution) - Thread will use the client id file generated in the preparation part. We can reuse the file for multiple runs and the number of preparation samples should be greater or equal to the number of execution samples.


### Execution points for eSignet UI API's
*  UI - OAuth Details : 
   * OAuth Details (Execution) - Client id created from Create OIDC Client in MOSIP Authentication System (Setup) will be loaded. Total samples created during execution can be higher in number as compared to the samples present in the file.

*  UI - Send OTP :
   * Send OTP (Preparation) - OAuth Details api will be used here in which created client id would be used.
   * Send OTP (Execution) - Same execution scenario applied as of OAuth details i.e. preparation samples should be greater or equal to the number of execution samples. than preparation. Transaction id will be used which is created in the preparation part. Registered individual id also need to be passed in the body for which we have separately added the setup thread group for creating identity. The files created from preparation part can be used for multiple executions.

*  UI - Authentication :
   * Authentication (Preparation) - In this thread group we are authenticating with 2 types of auth factor i.e. "PWD" and "OTP". So we have added an If controller where according to the type of auth factor the controller will execute. In the If controller we are using a variable which is defined in the user defined variables as "authFactorType. When the authFactorType will be "PWD" the first If controller will be executed and which contains OAuth details endpoint. When the authFactorType will be "OTP" the second If controller will be executed which contains 2 api's OAuth Details and Send OTP endpoints. We cant use the preparation file for multiple runs.
   * Authentication (Execution) - For the execution also there are 2 If controller in which we have authentication endpoint for PWD and OTP respectively. The total preparation samples must be equal or higher in number.

   * UI - Authentication Complete Flow (Execution) - In this thread we have kept all the ednpoints of the auth flow in a single thread group. Thread contains 2 types of auth factor i.e. "PWD" and "OTP" flow endpoints.

*  UI - Authorization Code : 
   * Authorization Code (Preparation) - There will be 3 api's included in the preparation i.e. OAuth Details, Send OTP, Authentication Endpoint - OTP from which we will get the transaction id, OTP which will be used in the execution.  We cant use the preparation file for multiple runs.
   * Authorization Code (Execution) - The total number of samples for preparation should be equal or higher in number as compared to execution. Have to pass the Transaction id generated from the preparation.

*  UI - Send OTP Linked Auth :
   * Send OTP Linked Auth (Preparation) - For the preparation we need 3 api's OAuth Details, Generate Link Code, Link Transaction api from which a link transaction ID will be generated and will be used for the execution.
   * Send OTP Linked Auth (Execution) - Link transaction id will be used which is created in the preparation part. Registered individual id also need to be passed in the body for which we have separately added the setup thread group for creating identity. The files created from preparation part can't be used for multiple executions transaction has a expiry.

* UI - Linked Authentication :
   * Linked Authentication (Preparation) - For the preparation we need 4 api's OAuth Details, Generate Link Code, Link Transaction and Send OTP Linked Auth api from which we will get the transaction id. We cant use the preparation file for multiple runs as OTP will not be valid.
   * Linked Authentication (Execution) - For the execution the total preparation samples must be equal or higher in number. The link transaction id received from link-transaction endpoint api will be used. Registered individual id also need to be passed in the body.

* UI - Linked Consent :
   * Linked Consent (Preparation) - For the preparation we need 5 api's OAuth Details, Generate Link Code, Link Transaction, Send OTP Linked Auth and linked authenication api from which we will get the transaction id.
   * Linked Consent (Execution) - The link transaction id received from link-transaction endpoint api will be used.  We cant use the preparation file for multiple runs.

* UI - Link Authorization Code :
   * Link Authorization Code (Preparation) - This thread includes 6 api's OAuth Details, Generate Link Code, Link Transaction, Send OTP Linked Auth, linked authenication and linked consent api. Transaction id and linked code must be same as the one received from oauth-details and generate link code api respectively.
   * Link Authorization Code (Execution) - Transaction id and linked code will be used from the preparation part.

### Execution points for eSignet OIDC API's
*  OIDC - Authorization : Its a GET API with no preparations and application will do a browser redirect to this endpoint with all required details passed as query parameters.

*  OIDC - Token :
   * OIDC Token (Preparation) - This includes the OAuth Details, Send OTP, Authentication, Authorization Code API's in the preparation part. From Authorization Code api will get the code value which will be used in the execution. Client id and individual id will be read from a file. The code generated can only be used once so the preparation samples must be equal or higher to execution.
   * OIDC Token (Execution) - Code created in the preparation will be used only once and a signed JWT key value is also required for which we are using a JSR223 Sampler. The sampler(Generate Client Assertion) will generate a signed JWT token value using the client id and its private key from the file created in Create OIDC Client in MOSIP Authentication System (Setup). An access token will be generated in the response body.

*  OIDC - UserInfo :
   * OIDC UserInfo (Preparation) - For the preparation we need 5 api's OAuth Details, Send OTP, Authentication, Authorization Code and Token Endpoint api from which a access token will be generated. Access token will be used for the execution.
   * OIDC UserInfo (Execution) - For execution the generated access token from the token end point api is used. Till the token is not expired it can be used for multiple samples.

*  OIDC - Configuration (Execution) : Open ID Connect dynamic provider discovery is not supported currently, this endpoint is only for facilitating the OIDC provider details in a standard way.

*  OIDC - JSON Web Key Set (Execution) : Endpoint to fetch all the public keys of the eSignet server.Returns public key set in the JWKS format.

### Execution points for eSignet Wallet Binding API's

*  Wallet Binding - Send Binding OTP (Execution) : This thread group will send the otp for the individual id passed in the request body.  For Registered individual id we have separately added the setup thread group for creating identities.

*  Wallet Binding : 
   * Wallet Binding (Preparation) - This preparation thread group contains binding otp api from which we will save the passed individual id into a file and will use that same file in the execution.

   * Wallet Binding (Execution) - In this thread will pass the auth factor type as "WLA". Also, a JWT format binding public key which will be generated from a code written in JSR223 preprocessor. Will use the file generated from the preparation and it can't be used multiple times. 

### Execution points for eSignet Sign Up Services API's

* Sign Up Service - Setting (Execution) : This thread only contains a Setting endpoint API which is a GET API. 

* Sign Up Service - Generate Challenge (Execution) : This thread contains Generate Challenge endpoint API. We need to pass an identifier value which is nothing but a 8-10 digit phone number with country code as the prefix. We are using a preprocessor from which we are getting the random generated phone number.

* Sign Up Service - Verify Challenge (Preparation) : In this thread we have generate challenge API from which we will get the value of identifier and from response headers will get the transaction id which will be stored in a csv file.

* Sign Up Service - Verify Challenge (Execution) : This thread contains verify challenge API in which we will pass the value of identifier i.e. phone number and transaction id in the headers which will get from the csv file generated in preparation. The file generated can't be used for multiple iterations. We need to increase the expiry time of the transaction id we are getting from the preparation thread group so for that we need to update the mentioned property mosip.signup.unauthenticated.txn.timeout in signup default properties.

* Sign Up Service - Register (Preparation) : This thread contains 2 API's i.e. generate challenge and verify challenge. Will save the value of identifier which will be passed in both the API's in a csv file. Will also get a verified transaction id in the response header of verified challenge endpoint and will save the transaction id in the same csv file and will use that file in the execution.

* Sign Up Service - Register (Execution) : This thread is for register API endpoint and will use a csv file to pass the value of identifier and verified transaction id. Will use the file generated from the preparation and it can't be used multiple times. We need to increase the expiry time of the transaction id we are getting from the preparation thread group so for that we need to update the mentioned property mosip.signup.verified.txn.timeout in signup default properties.

* Sign Up Service - Registration Status (Preparation) : This thread contains 3 API's i.e. generate challenge, verify challenge and register API endpoints. Will save the transaction id generated from the response headers of verify challenge endpoint in a csv file and will use that in the execution.

* Sign Up Service - Registration Status (Execution) : This thread contains Registration Status API endpoint. Will use the file generated from the preparation to pass the transaction id and it can be used multiple times as it will only give the latest status for the transaction id we are passing. The transaction id used has a expiry time which can be configured with the mentioned property mosip.signup.status-check.txn.timeout available in mosip config signup default properties. We need to increase the expiry time of the transaction id we are getting from the preparation thread group so for that we need to update the mentioned property mosip.signup.status-check.txn.timeout in signup default properties.
