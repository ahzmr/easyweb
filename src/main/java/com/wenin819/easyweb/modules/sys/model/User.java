package com.wenin819.easyweb.modules.sys.model;

import com.wenin819.easyweb.core.db.BaseEntity;
import com.wenin819.easyweb.core.db.IFiledEnum;

/**
 * Created by wenin819@gmail.com on 2014/4/7.
 */
public class User extends BaseEntity<User.TE> {

    private String id;
    private String name;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static enum TE implements IFiledEnum {
        id("id"),
        name("name"),
        email("email")
        ;
        private String filedName;
        private TE(String filedName) {
            this.filedName = filedName;
        }
        @Override
        public String getFiledName() {
            return this.filedName;
        }

        @Override
        public String getTableName() {
            return "sys_user";
        }

        @Override
        public String getTableSchema() {
            return null;
        }
    }
}
