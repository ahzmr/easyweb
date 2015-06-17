/**
* There are <a href="https://github.com/wenin819/easyweb">EasyWeb</a> code generation
*/
package com.wenin819.easyweb.modules.contacts.model;

import com.wenin819.easyweb.core.persistence.BaseEntity;
import com.wenin819.easyweb.core.persistence.IFiledEnum;

/**
 * 通讯录
 * @author wenin819@gmail.com
 */
public class TxContacts extends BaseEntity<TxContacts.TE> {

    /**
     * 学号
     */
    private String id = "07070110";
    /**
     * 名称
     */
    private String name;
    /**
     * 常用联系方式
     */
    private String cellphone;
    /**
     * 其它联系方式
     */
    private String phone;
    /**
     * 学历
     */
    private String education = "本科";
    /**
     * 学校
     */
    private String university = "合肥学院";
    /**
     * 院系
     */
    private String department = "数理系";
    /**
     * 专业
     */
    private String profession = "信息与计算科学";
    /**
     * 常住地点
     */
    private String address;
    /**
     * 工作地点
     */
    private String workAddr;
    /**
     * 职业
     */
    private String job;
    /**
     * 公司
     */
    private String company;
    /**
     * 互助信息
     */
    private String myMsg;
    /**
     * 备注
     */
    private String memo;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getWorkAddr() {
        return workAddr;
    }

    public void setWorkAddr(String workAddr) {
        this.workAddr = workAddr;
    }
    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    public String getMyMsg() {
        return myMsg;
    }

    public void setMyMsg(String myMsg) {
        this.myMsg = myMsg;
    }
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public static enum TE implements IFiledEnum {
        id("ID"),  // 学号
        name("name"),  // 名称
        cellphone("cellphone"),  // 常用联系方式
        phone("phone"),  // 其它联系方式
        education("education"),  // 学历
        university("university"),  // 学校
        department("department"),  // 院系
        profession("profession"),  // 专业
        address("address"),  // 常住地点
        workAddr("work_addr"),  // 工作地点
        job("job"),  // 职业
        company("company"),  // 公司
        myMsg("my_msg"),  // 互助信息
        memo("memo"),  // 备注
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
            return "tx_contacts";
        }
        @Override
        public String getTableSchema() {
            return "easyweb";
        }

        @Override
        public String toString() {
            return this.filedName;
        }
    }
}
