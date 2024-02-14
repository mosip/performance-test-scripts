### Contains
* This folder contains performance test script of Resident Service Endpoints.
    1. Authorize​ Admin​ Validate Token 
    2. Login​ Redirect URI 
    3. VID Policy
    4. Generate VID
    5. Get VIDs of the Resident
    6. Service History
    7. Service History Download
    8. Auth Lock Unlock
    9. Auth Lock Status 
    10. Request Card VID 
    11. Revoke VID 
    12. Identity Info Schema Type
    13. Identity Mapping
    14. Masterdata Idschema
    15. Download Personalized Card
    16. Auth Proxy Partner Type
    17. UI Schema
    18. Masterdata Getting Templates
    19. Share Credential
    20. Login
    21. Registration Center List Nearest Location
    22. Registration Center List Location Hierarchy
    23. Download Supporting Docs
    24. Menu Bar Bell Update Time
    25. Menu Bar Unread Notification Count
    26. Menu Bar Bell Notification Click
    27. Validate OTP 
    28. Track Service Request
    29. Download Service Request
    30. Menu Bar Notifications Language Code 
    31. Profile 
    32. Channel Verification Status 
    33. Download Card Event 
    34. Masterdata Get Coordinates
    35. Masterdata Location Info 
    36. Masterdata Registration Centers
    37. Masterdata Valid Documents 
    38. Masterdata Registration Centers Page 
    39. Masterdata Location Immediate Children
    40. Masterdata Applicant Type

 
    


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

* Create Identities in MOSIP Authentication System (Setup) : This thread contains the authorization api's for regproc and idrepo from which the auth token will be generated. There is set of 4 api's generate RID, generate UIN, add identity and add VID. From here we will get the VID's which can be further used as individual id for required Resident Service endpoints. These 4 api's are present in the loop controller where we can define the number of samples for creating identities in which "freshIdentityCreationCount" is used as a variable. In whichever environment we are running the scripts we should have atleast few hundred VID's available handy and if not we can use this setup to create the identities as required. 

* Resident Id Access Token Creation (Setup) : This thread contains 4 esignet api's Oauth details, Send OTP, Authentication, Authorization Code and 1 reisdent Login​ Redirect URI api.  After the login is successful, the resident will be redirected to the resident portal’s logged-in page. From Login​ Redirect URI api will get the id and access token which will be used further in the headers for most of the resident service api's. So till the time id and access token are valid and not expired we can re-use it for the resident service api's. And as per the expiration time once it is not valid and expired, we need to re-run the setup as required.

* For execution purpose need to modify the below mentioned properties: For Performance testing we require a specific amount of data which needs to be used further for the resident service api's and it should be valid till the time of execution. So, We have modified the below properties to increase the expiry time, so that the data prepared to be used for execution is valid until the execution is completed.

   * esignet default properties: Update the value for the properties according to the execution setup. Perform the execution for esignet api's with redis setup. So check for the redis setup accordingly.
          mosip.esignet.cache.size - Enabled while not using the redis setup. Can keep the cache size around more than 100k.
          mosip.esignet.cache.expire-in-seconds (authcodegenerated) - 21600
          mosip.esignet.access-token-expire-seconds - 86400
          mosip.esignet.id-token-expire-seconds - 86400
          spring.cache.type=redis - check for this property and enable the redis.
   * application default properties: Update the value for the below property.
          mosip.kernel.otp.expiry-time - 86400
   * id-authentication default properties: Update the value for the below properties.
          otp.request.flooding.max-count - 100000

          

### Data prerequisite

* List of VID's as per environment which is valid and will be prepared from the above mentioned create identities setup.

### Execution points for Resident Service endpoints

* The id and access token generated from the Login​ Redirect URI api will be stored in a file and will be used in the headers of below mentioned api's :

    * Authorize​ Admin​ Validate Token 
    * Login​ Redirect URI
    * VID Policy
    * Generate VID
    * Get VIDs of the Resident
    * Service History 
    * Service History Download
    * Auth Lock Unlock 
    * Auth Lock Status 
    * Request Card VID  
    * Revoke VID
    * Identity Info Schema Type
    * Identity Mapping
    * Masterdata Idschema
    * Download Personalized Card
    * Auth Proxy Partner Type
    * UI Schema
    * Masterdata Getting Templates
    * Share Credential
    * Menu Bar Bell Update Time
    * Menu Bar Unread Notification Count
    * Menu Bar Bell Notification Click
    * Download Supporting Docs
    * Registration Center List Nearest Location
    * Registration Center List Location Hierarchy
    * Track Service Request
    * Download Service Request
    * Menu Bar Notifications Language Code 
    * Profile 
    * Download Card Event



* Authorize​ Admin​ Validate Token (Execution) - The id and access token generated above in the setup will be used in the headers of this api and can be re-used until they are not expired.

* Login​ Redirect URI (Preparation) - This thread contains set of 4 esignet api's Oauth details, Send OTP, Authentication, Authorization Code. The code generated will be stored in a file and can't be used for multiple runs as code can only be used once. 
* Login​ Redirect URI (Execution) - In this api will use the code generated from the above esignet api. The total preparation samples must be equal or higher in number. We cannot re-use the preparation as the generated code can only be used once.

* VID Policy - This API is to get the VID policy. The id and access token can be re-used until they are not expired.

* Generate VID - This API generates the VID for the given VID type. The id and access token will be used in the headers of this api and can be re-used until they are not expired.

* Get VIDs of the Resident - This API will retrieve the list of active VIDs of the VID of the logged in session. The id and access token can be re-used until they are not expired.

* Service History - This API is to get the service history of one or more service types. The id and access token can be re-used until they are not expired.

* Service History Download - This API is to download the View History Tables as a PDF. The id and access token can be re-used until they are not expired.

* Auth Lock Unlock - Resident service to store the Auth type lock/unlock status with status_comment containing summary of what is locked /unlocked. The id and access token can be re-used until they are not expired.

* Auth Lock Status - This API returns the lock/unlock status of Auth Types for a given VID. The id and access token can be re-used until they are not expired. The id and access token can be re-used until they are not expired.

* Request Card VID - This API is to request the PDF card generation for a specific VID of the logged in user. This will give back a event ID for tracking purpose and to use it to download the VID card from the notifications. The id and access token can be re-used until they are not expired.

* Revoke VID (Preparation) - This thread contains Generate VID api from which we will get a VID which needs to be revoked.
* Revoke VID (Execution) - The generated VID can only be used once to be revoked so the samples created in the preparation must be equal or higher in number.

* Identity Info Schema Type (Execution) - This API is to get the list of ID Attributes of the logged-in user to pre-populate in the UI. A variable is used as "schemaType" to pass the Type of schema in this API from a file named as schema_type. The id and access token will be used here and can be used multiple times until its valid and not expired.

* Identity Mapping (Execution) - This API is to get the identity mapping Json. The id and access token will be passed in the headers and can be used multiple times until valid and not expired.

* Masterdata Idschema (Execution) - This API is used to return the Id-schema. The id and access token will be used here and can be used multiple times until its valid and not expired.

* Download Personalized Card (Execution) - This API is to download the personalized PDF card. The id and access token will be used here and can be used multiple times until its valid and not expired.

* Auth Proxy Partner Type (Execution) - This API is to get the list of partner types from PMS. The id and access token will be used here and can be used multiple times until its valid and not expired.

* UI Schema (Execution) - API to return the UI Spec (UI Schema) for the given schemaType which is one of share-credential/update-demographics/personalized-card. A variable is used as "schemaTypeUI" to pass the Type of schema in this API from the same file used above named as schema_type. The id and access token will be used here and can be used multiple times until its valid and not expired.

* Masterdata Getting Templates (Execution) - This API is to return terms and conditions and will be invoked by UI for the specific language. This is a Proxy API of master data service for getting templates for template type code and language code. Template type code will be passed in this API from a file named as template_type and the variable used is "templateTypeCode". The id and access token will be used here and can be used multiple times until its valid and not expired.

* Share Credential (Execution) - This API is to share the user specified attributes to selected partner in the selected formats along with a purpose. The id and access token will be used here and can be used multiple times until its valid and not expired.

* Login (Execution) - Using this API, the resident portal will redirect to the Esignet URL where the validation is performed and login happens.

* Menu Bar Unread Notification Count (Execution) - API to return the number of unread service notification list. The id and access token will be used here and can be used multiple times until its valid and not expired.

* Menu Bar Bell Notification Click (Execution) - This API is to get the latest date time whenever user clicked on the bell notification icon. The id and access token will be used here and can be used multiple times until its valid and not expired.

* Menu Bar Bell Update Time (Execution) - This API is to update latest date-time when user clicked on the bell notification. The id and access token will be used here and can be used multiple times until its valid and not expired.

* Validate OTP (Preparartion) - This thread contains Request OTP API to generate OTP which will be used in the execution part.
* Validate OTP (Execution) - This API will be used to validate OTP when the resident is trying to verify his phone number or email Id. The generated OTP from the preparation can only be used once, so the samples created in the preparation must be equal or higher in number.

* Download Supporting Docs (Execution) - This API is to convert the List of supporting documents request as a downloadable PDF. The id and access token will be used here and can be used multiple times until its valid and not expired.

* Registration Center List Nearest Location (Execution) - This API will download a PDF of nearest Registration Centers. The Longitude, Latitude and Proximity distance will be passed in this API from a file named as coordinates and the variables used are "longitude", "latitude" and "proximityDistance" respectivily. The id and access token will be used here and can be used multiple times until its valid and not expired.

* Registration Center List Location Hierarchy (Execution)  - This API will download a PDF of Registration Centers list. The Hierarchy level will be passed in this API along with its name from a file named as hirarchy_level and the variables used are "hierarchyLevel" and "name". The id and access token will be used here and can be used multiple times until its valid and not expired. Hierarchy level are followed as 0:Country, 1:Region, 2:Province, 3:City, 4:Zone, 5:Postal Code.

* Track Service Request (Preparation) - This thread contains Auth Lock Unlock API which will generate Event ID in the response and will be used in the execution part. The Event ID will be stored in a file named as eventid_service_request.txt and can be used for multiple runs.

* Track Service Request (Execution) -  This API is to get the details of status for a given Event ID and including its status. The Event ID generated in the preparation will passed from the file with the variable name as "eventId". The id and access token will be used here and can be used multiple times until its valid and not expired.

* Download Service Request (Execution) - This API is to convert the acknowledgement of any service request Event ID as a downloadable PDF. The Event ID stored in the file eventid_service_request.txt can be used here to download the PDF. The id and access token will be used here and can be used multiple times until its valid and not expired.

* Menu Bar Notifications Language Code (Execution) - This API is to get notifictions to the asyncrhonous service requests in a paginated way. The id and access token will be used here and can be used multiple times until its valid and not expired.

* Profile (Execution) - This API is to get User details for the current session. The id and access token will be used here and can be used multiple times until its valid and not expired.

* Channel Verification Status (Preparation) - This thread contains two APIs i.e Request OTP and Validate OTP which are added in a loop controller because we are running both the APIs for otp channel type as "EMAIL" and "PHONE". So will save the individual id and type of channel in a file named as channel_verification_list.txt. Loop count for the loop controller is defined with the variable name "otpChannelTypeLoopCount" which is currenlty set to 2. If required, we can add the channel type to the file and as per the count of otp channel type we can modify the variable defined in the script.

* Channel Verification Status (Execution) - This API is to check if OTP is verified for a channel type for an individual VID. The individual id and channel type will be passed in this API from a file named as channel_verification_list.txt and the variable used are "individualIdVerification" and "channelVerification". 

* Download Card Event (Preparation) - In this thread we are using Request Card VID API from which will get an Event id which is stored in a file named as event_id_download_card.txt. The id and access token will be used here and can be used multiple times until its valid and not expired. In this thread we have also added a constant timer with the variable as delay which is defined in user defined variables. The value for delay is 30 sec.

* Download Card Event (Execution) - This API is to download the UIN card. An Event id is passed from the file generated in the preparation part. The same Event id can't be used for multiple times so the samples created in preparation must be higher in number. The id and access token will be used here and can be used multiple times until its valid and not expired. In this thread also we are using the same delay which is defined in user defined variables.

* Masterdata Get Coordinates (Execution) - This API is to get the longitude, latitude and proximity distance. A file named coordinates is been used to pass the data.

* Masterdata Location Info (Execution) - This API is to get the information about location. We don't have any preparation for this thread. We are passing the location code from a text file regarding which will get the information. 

* Masterdata Registration Centers (Execution) - This API is to get details about a registration center. A file named hierarchy_level is used to pass the hierarchy level and name in the API.

* Masterdata Valid Documents (Execution) - This API is to get the list of supporting documents for a particular document type.

* Masterdata Registration Centers Page (Execution) - This API is to get the details about a registration center. The same file which is used above named hierarchy_level is used to pass the hierarchy level and name in the API.

* Masterdata Location Immediate Children (Execution) - This API is to get the immediate children for a location.

* Masterdata Applicant Type (Execution) - This API is to get the details of the uploaded document based on applicant Id. A file named applicant_id is used to pass the applicant id in the API.
