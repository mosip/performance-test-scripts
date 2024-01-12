
### Contains
* This folder contains performance helper script and test script of below API endpoint categories.
    1. Management API Endpoints
    2. UI API Endpoints
    3. OIDC API Endpoints 
    4. VCI API Endpoint

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

* Create OIDC Client in MOSIP Authentication System (Setup) : This thread contains a JSR223 sampler(Generate Key Pair) from which will get a public-private key pair. The public key generated will be used in the OIDC client api to generate client id's  which will be registered for both IDA and Esignet. The private key generated from the sampler will be used in another JSR223 sampler(Generate Client Assertion) present in the OIDC Token (Execution). Generated client id's and there respective private key will be stored in a file which will be used further in the required api's.

* In the above Create OIDC Client in MOSIP Authentication System (Setup) check for the Policy name and Auth partner id for the particular env in which we are executing the scripts. The policy name provided must be associated with the correct Auth partner id.

* For execution purpose neeed to check for the mentioned properties: 
   * esignet default properties: Update the value for the properties according to the execution setup. Perform the execution for Esignet api's with redis setup. So check for the redis setup accordingly.
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

### Execution points for Esignet Management API's
* Management - Create OIDC Client (Execution) : This thread group will directly execute in which we are using a counter which will generate unique client id. Because we can't generate same duplicate cliend id.
* Management - Update OIDC Client : 
   * Management Update OIDC Client (Preparation) - In this the above mentioned Create OIDC Client API will be used to generate a large number of OIDC client id samples which will get stored in a file and will be used in the execution.
   * Management Update OIDC Client (Execution) - Thread will use the client id file generated in the preparation part. We can reuse the file for multiple runs and the number of preparation samples should be greater or equal to the number of execution samples.


### Execution points for Esignet UI API's
*  UI - OAuth Details : 
   * OAuth Details (Execution) - Client id created from Create OIDC Client in MOSIP Authentication System (Setup) will be loaded. Total samples created during execution can be higher in number as compared to the samples present in the file.

*  UI - Send OTP :
   * Send OTP (Preparation) - OAuth Details api will be used here in which created client id would be used.
   * Send OTP (Execution) - Same execution scenario applied as of OAuth details i.e. preparation samples should be greater or equal to the number of execution samples. than preparation. Transaction id will be used which is created in the preparation part. Registered individual id also need to be passed in the body for which we have separately added the setup thread group for creating identity. The files created from preparation part can be used for multiple executions.

*  UI - Authentication :
   * Authentication - OTP (Preparation) - For the preparation we need 2 api's OAuth Details and Send OTP from which we will get the transaction id and required OTP respectively. We cant use the preparation file for multiple runs as OTP will not be valid.
   * Authentication - OTP (Execution) - For the execution the total preparation samples must be equal or higher in number. Transaction id will be used which is created from OAuth details api. Registered individual id also need to be passed in the body along with the OTP.

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

### Execution points for Esignet OIDC API's
*  OIDC - Authorization : Its a GET API with no preparations and application will do a browser redirect to this endpoint with all required details passed as query parameters.

*  OIDC - Token :
   * OIDC Token (Preparation) - This includes the OAuth Details, Send OTP, Authentication, Authorization Code API's in the preparation part. From Authorization Code api will get the code value which will be used in the execution. Client id and individual id will be read from a file. The code generated can only be used once so the preparation samples must be equal or higher to execution.
   * OIDC Token (Execution) - Code created in the preparation will be used only once and a signed JWT key value is also required for which we are using a JSR223 Sampler. The sampler(Generate Client Assertion) will generate a signed JWT token value using the client id and its private key from the file created in Create OIDC Client in MOSIP Authentication System (Setup). An access token will be generated in the response body.

*  OIDC - UserInfo :
   * OIDC UserInfo (Preparation) - For the preparation we need 5 api's OAuth Details, Send OTP, Authentication, Authorization Code and Token Endpoint api from which a access token will be generated. Access token will be used for the execution.
   * OIDC UserInfo (Execution) - For execution the generated access token from the token end point api is used. Till the token is not expired it can be used for multiple samples.

*  OIDC - Configuration (Execution) : Open ID Connect dynamic provider discovery is not supported currently, this endpoint is only for facilitating the OIDC provider details in a standard way.

*  OIDC - JSON Web Key Set (Execution) : Endpoint to fetch all the public keys of the Esignet server.Returns public key set in the JWKS format. 

### Execution points for Esignet VCI API 
* VCI - Get Credential (Praparation) - For the preparation we need 5 api's OAuth Details, Send OTP, Authentication, Authorization Code and Token Endpoint api from which a access token will be generated. Will also use a JSR223 Post processor in which we are having a groovy code which is generating a proof jwt value which will be used in the execution. Both access token and proof jwt values will be stored in a text file which will be used for execution.

* VCI - Get Credential (Execution) - In this thread group will have the get credential endpoint API for execution which will use the generated access token and proof jwt values from the preparation part. We cant use the preparation file for multiple runs so the total number of samples generated from preparation should be equal or higher in number as compared to execution.
