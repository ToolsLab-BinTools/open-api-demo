# open-api-demo
This project is a example project for CloudQuery Community, which can assist community users in code development such as organizational structure import, and is also the recommended implementation of the current CloudQuery open API. Welcome all community users with development capabilities to join us and learn about code technology.

## Use Guide
 1. After downloading the code package or cloning the code, use IDEA or other Java IDE tools to open it. exist
``` open-api-demo/src/main/java/cn/bintools/cloud/openapi/ad/ ```
Find ` AdDomainServer ` in the directory .
 2. Replace the ` APPID ` and ` SECRET ` in the code with the appId and secret applied by the CloudQuery platform
 3. Replace ` HOST ` in the code with the IP of the CloudQuery server
 4.Replace the ` adminName ` in the ` connectionAdDomain() ` method with the AD server account
 5. Replace ` adminPassword ` with the corresponding password of the account
 6. Replace ` ldapURL ` with the domain name and port number of the AD server
 7. Replace ` SECURITY_AUTHENTICATION ` with the corresponding authentication level of the AD server ("none", "simple", "strong")
 8. Run the ` main ` method in the class

## TODO 
Permission module and audit module will continue to be opened in the future
 - Permission module: export and import permissions under connection
 - Audit module: filter and export audit records
 
## Learn More
Click here to learn about [CloudQuery](https://cloudquery.club/) </br>
Click here to learn about [Bintools](https://bintools.cn/)
