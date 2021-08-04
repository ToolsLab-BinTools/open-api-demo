package cn.bintools.cloud.openapi.ad;

import cn.bintools.cloud.openapi.model.DepartmentInfo;
import cn.bintools.cloud.openapi.model.DepartmentRequest;
import cn.bintools.cloud.openapi.model.UserInfo;
import cn.bintools.cloud.openapi.model.UserRequest;
import cn.bintools.cloud.openapi.util.HttpCommonUtils;
import cn.bintools.cloud.openapi.util.SignatureUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.*;

/***
 * ad域服务层
 * @author <a href="jian.huang@bintools.cn">Turbo</a>
 * @version 1.0.0 2021-06-2021/6/24-11:51
 */
public class AdDomainServer {

    /**
     * 请根据文档获取 App ID、Secret，并设置 HOST 为 CloudQuery 平台访问地址
     * https://cloudquery.club/docs/install/open-api
     */
    private static final String APPID = "ryca9fwJ";
    private static final String SECRET = "2bcfb70fbe5cc975632e599787f67cf11f3dc4c6";
    private static final String HOST = "http://localhost:9098/openapi/user";

    public static void main(String[] args) throws NamingException {
        LdapContext ldapContext = connectionAdDomain();
        try {
            Set<DepartmentInfo> adDepartment = getAdDepartment(ldapContext);
            syncDepartment(adDepartment);
            List<UserInfo> adUserInfo = getAdUserInfo(ldapContext);
            syncUsers(adUserInfo);
        } catch (NamingException e) {
            throw new IllegalArgumentException("读取部门信息异常");
        } finally {
            ldapContext.close();
        }
    }

    private static void syncUsers(List<UserInfo> adUserInfo) {
        //构造请求地址
        String url = HOST + "/user/info";
        Long currentTime = System.currentTimeMillis();
        UserRequest request = new UserRequest(APPID, "AD域", currentTime);
        //构造请求参数
        String status = createUserStatus(request);
        request.setStatus(status);
        request.setUserInfos(adUserInfo);
        String s = JSON.toJSONString(request);
        String response = HttpCommonUtils.postRequest(url, s);
        System.out.println(response);
    }

    private static List<UserInfo> getAdUserInfo(LdapContext ldapContext) {
        List<UserInfo> userInfos = new ArrayList<>();
        try {
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String searchFilter = "(objectClass=user)";
            String searchBase = "DC=bintools,DC=cn";
            String returnedAtts[] = {"url", "employeeID", "mail", "sex",
                    "name", "userPrincipalName", "physicalDeliveryOfficeName",
                    "departmentNumber", "telephoneNumber", "homePhone",
                    "mobile", "department", "sAMAccountName", "whenChanged"};
            searchCtls.setReturningAttributes(returnedAtts);
            NamingEnumeration<SearchResult> answer = ldapContext.search(searchBase, searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                UserInfo user = new UserInfo();
                if (!StringUtils.isEmpty(sr.getAttributes().get("mail"))) {
                    user.setEmail(sr.getAttributes().get("mail").get().toString());
                }
                if (!StringUtils.isEmpty(sr.getAttributes().get("name"))) {
                    user.setUserName(sr.getAttributes().get("name").get().toString());
                }
                if (!StringUtils.isEmpty(sr.getAttributes().get("userPrincipalName"))) {
                    user.setUserId(sr.getAttributes().get("userPrincipalName").get().toString());
                }
                if (!StringUtils.isEmpty(sr.getAttributes().get("telephoneNumber"))) {
                    user.setTelephone(sr.getAttributes().get("telephoneNumber").get().toString());
                }
                userInfos.add(user);
            }
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Problem searching directory: " + e);
        }
        return userInfos;
    }

    private static void syncDepartment(Set<DepartmentInfo> adDepartment) {
        //构造请求地址
        String url = HOST + "/dept";
        Long currentTime = System.currentTimeMillis();
        DepartmentRequest request = new DepartmentRequest(APPID, "AD域", currentTime);
        //构造请求参数
        String status = createStatus(request);
        request.setStatus(status);
        request.setDepartmentInfos(adDepartment);
        String s = JSON.toJSONString(request);
        String response = HttpCommonUtils.postRequest(url, s);
        System.out.println(response);
    }

    private static String createStatus(DepartmentRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("currentTime", request.getCurrentTime());
        map.put("source", request.getSource());
        map.put("appId", request.getAppId());
        try {
            return SignatureUtil.getSignature(map, SECRET);
        } catch (Exception e) {
            throw new IllegalArgumentException("加密异常");
        }
    }

    private static String createUserStatus(UserRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("currentTime", request.getCurrentTime());
        map.put("source", request.getSource());
        map.put("appId", request.getAppId());
        try {
            return SignatureUtil.getSignature(map, SECRET);
        } catch (Exception e) {
            throw new IllegalArgumentException("加密异常");
        }
    }

    private static LdapContext connectionAdDomain() {
        try {
            Properties env = new Properties();
            String adminName = "administrator@bintools.cn";//username@domain
            String adminPassword = "Hello123$";//password
            String ldapURL = "LDAP://ad.bintools.cn:389";//ip:port
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.SECURITY_AUTHENTICATION, "simple");//"none","simple","strong"
            env.put(Context.SECURITY_PRINCIPAL, adminName);
            env.put(Context.SECURITY_CREDENTIALS, adminPassword);
            env.put(Context.PROVIDER_URL, ldapURL);
            LdapContext ctx = new InitialLdapContext(env, null);
            return ctx;
        } catch (NamingException ex) {
            throw new IllegalArgumentException("ad域链接认证失败");
        }

    }

    public static Set<DepartmentInfo> getAdDepartment(LdapContext ctx) throws NamingException {
        //LDAP搜索过滤器类，此处只获取AD域用户，所以条件为用户user或者person均可
        String searchFilter = "(ou>='')";
        //AD域节点结构
        String searchBase = "DC=bintools,DC=cn";
        NamingEnumeration<SearchResult> answer = getSearchResult(ctx, searchFilter, searchBase);
        Set<DepartmentInfo> adDepartments = new HashSet<DepartmentInfo>();
        while (answer.hasMoreElements()) {
            SearchResult sr = answer.next();
            List<String> strings = Arrays.asList(sr.getName().split(","));
            DepartmentInfo dept = new DepartmentInfo();
            for (String result : strings) {
                if (strings.indexOf(result) == 0) {
                    dept.setName(result.replace("OU=", ""));
                } else {
                    Set<String> parent = new HashSet<String>();
                    parent.add(result.replace("OU=", ""));
                    dept.setParents(parent);
                }
                adDepartments.add(dept);
            }

        }
        return adDepartments;
    }

    public static NamingEnumeration<SearchResult> getSearchResult(LdapContext ctx, String searchFilter, String searchBase) throws NamingException {
        //搜索控制器
        SearchControls searchCtls = new SearchControls();
        //创建搜索控制器
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String returnedAtts[] = {"canonicalName", "distinguishedName", "id",
                "name", "userPrincipalName", "departmentNumber", "telephoneNumber", "homePhone",
                "mobile", "department", "sAMAccountName", "whenChanged", "departmentDescription"}; // 定制返回属性
        searchCtls.setReturningAttributes(returnedAtts);
        NamingEnumeration<SearchResult> answer = ctx.search(searchBase, searchFilter, searchCtls);
        return answer;
    }
}
