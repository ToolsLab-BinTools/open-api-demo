package cn.bintools.cloud.openapi.model;

import java.io.Serializable;
import java.util.Set;

/***
 * 部门信息
 * @author <a href="jian.huang@bintools.cn">Turbo</a>
 * @version 1.0.0 2021-06-2021/6/23-15:36
 */

public class DepartmentInfo implements Serializable {

    /**
     * 部门名称
     */
    private String name;

    private String principal;
    /**
     * 父级部门
     */
    private Set<String> parents;

    /**
     * 部门描述
     */
    private String description;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public Set<String> getParents() {
        return parents;
    }

    public void setParents(Set<String> parents) {
        this.parents = parents;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DepartmentInfo(String name, String principal, Set<String> parents, String description) {
        this.name = name;
        this.principal = principal;
        this.parents = parents;
        this.description = description;
    }

    public DepartmentInfo() {
    }

    @Override
    public String toString() {
        return "DepartmentInfo{" +
                "name='" + name + '\'' +
                ", principal='" + principal + '\'' +
                ", parents=" + parents +
                ", description='" + description + '\'' +
                '}';
    }
}
