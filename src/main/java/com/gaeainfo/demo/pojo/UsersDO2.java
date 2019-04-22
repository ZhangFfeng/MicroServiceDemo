package com.gaeainfo.demo.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 实体类users2
 *
 * @author 张丰
 * @version v1.0
 */
@SuppressWarnings("ALL")
@Entity
@Table(name = "users2")
public class UsersDO2 implements Serializable {

    private static final long serialVersionUID = -3946734305303957850L;
    @Id
    @Column(name = "pid")
    private String pid;

    @Column(name = "lid")
    private String lid;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    public UsersDO2() {
    }


    @Override
    public String toString() {
        return pid + " ," + lid + " ," + name + "," + age;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
