package com.wenin819.easyweb.system.service;

import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.core.persistence.mybatis.MybatisBaseDao;
import com.wenin819.easyweb.core.service.mybatis.MybatisBaseService;
import com.wenin819.easyweb.core.utils.ConfigEnum;
import com.wenin819.easyweb.core.utils.ConfigUtils;
import com.wenin819.easyweb.system.dao.SysDictDao;
import com.wenin819.easyweb.system.model.SysDict;
import com.wenin819.easyweb.utils.SysDictUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wenin819@gmail.com
 */
@Service
public class SysDictService extends MybatisBaseService<SysDict> {

    @Resource
    private SysDictDao sysDictDao;

    @Override
    public String getTableName() {
        return ConfigUtils.get().getValue("schema.configPlat") + ".sys_dict";
    }

    @Override
    public String getIdKey() {
        return "id";
    }

    @Override
    public MybatisBaseDao<SysDict> getDao() {
        return sysDictDao;
    }

    /**
     * 查询总的数据字典类型
     * @return
     */
    public List<String> queryTypeList(){
        CriteriaQuery query = new CriteriaQuery();
        query.setDistinct(true);
        query.addSelect(SysDict.TE.type);
        List<SysDict> sysDicts = getDao().queryByCriteria(query);
        if(null != sysDicts && !sysDicts.isEmpty()) {
            List<String> typeList = new ArrayList<String>(sysDicts.size());
            for (SysDict sysDict : sysDicts) {
                typeList.add(sysDict.getType());
            }
            return typeList;
        } else {
            return new ArrayList<String>(0);
        }
    }

    @Override
    public int save(SysDict params) {
        SysDictUtils.removeDictTypeCache(params.getType());
        return super.save(params);
    }

    @Override
    public int delete(Object params) {
        SysDict sysDict;
        if(params instanceof SysDict) {
            sysDict = (SysDict) params;
        } else {
            sysDict = queryById(params);
        }
        SysDictUtils.removeDictTypeCache(sysDict.getType());
        return super.delete(params);
    }
}
