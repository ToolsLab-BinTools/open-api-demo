# open-api-demo
This project is an example for CloudQuery Community, which can assist community users in code development such as organizational structure import, and is also the recommended implementation of the current CloudQuery open API. Welcome all community users with development capabilities to join us and learn about code technology.
</br>
</br>
本项目为 CloudQuery 社区的示例项目，可以帮助社区用户进行组织架构导入等代码开发，也是目前 CloudQuery OpenAPI 的推荐实现。欢迎各位有开发能力的社区用户加入我们，切磋代码技术。


## Use Guide
 1. After downloading the code package or cloning the code, use IDEA or other Java IDE tools to open it. exist
``` open-api-demo/src/main/java/cn/bintools/cloud/openapi/ad/ ```
Find ` AdDomainServer ` in the directory ;
 2. Replace the ` APPID ` and ` SECRET ` in the code with the appId and secret applied by the CloudQuery platform ;
 3. Replace ` HOST ` in the code with the IP of the CloudQuery server ;
 4. Replace the ` adminName ` in the ` connectionAdDomain() ` method with the AD server account ;
 5. Replace ` adminPassword ` with the corresponding password of the account ;
 6. Replace ` ldapURL ` with the domain name and port number of the AD server ;
 7. Replace ` SECURITY_AUTHENTICATION ` with the corresponding authentication level of the AD server ("none", "simple", "strong") ;
 8. Run the ` main ` method in the class ;
</br>

 1. 下载代码包或克隆代码后，使用IDEA或其他Java IDE工具打开。在 ``` open-api-demo/src/main/java/cn/bintools/cloud/openapi/ad/ ```目录下找到 ` AdDomainServer ` ;
 2. 将代码中的 ` APPID ` 与` SECRET ` 替换为 CloudQuery 平台申请的 ` appId `和 ` secret `;
 3. 将代码中的 ` HOST ` 替换为 CloudQuery 服务器所在ip ;
 4. 将 ` connectionAdDomain() ` 方法中的 ` adminName ` 替换为AD服务器账户;
 5. 将 ` adminPassword ` 替换为账户对应密码;
 6. 将 ` ldapURL ` 替换为AD服务器域名以及端口号;
 7. 将 ` SECURITY_AUTHENTICATION ` 替换为AD服务器对应验证等级 （"none","simple","strong"）;
 8. 运行类中的` main `方法;


## TODO 
Permission module and audit module will continue to be opened in the future
 - Permission module: export and import permissions under connection
 - Audit module: filter and export audit records
</br>

后续会持续开放 权限模块、审计模块 
 - 权限模块：连接下权限导出、导入
 - 审计模块：审计记录筛选、导出

 
## Details
Refer to the link for details of interface use [here](https://cloudquery.club/docs/install/open-api)
</br>
</br>
接口调用详情请参考[这里](https://cloudquery.club/docs/install/open-api)

## Thanks
Special thanks：
 - Dear community users
 - Dear bugs
 
## Learn More
Click here to learn about [CloudQuery](https://cloudquery.club/) </br>
Click here to learn about [Bintools](https://bintools.cn/)
</br>
</br>
点击了解 [CloudQuery](https://cloudquery.club/) </br>
点击了解 [Bintools](https://bintools.cn/)
