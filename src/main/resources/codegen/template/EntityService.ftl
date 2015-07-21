<#assign daoName = table.className?uncap_first + "Dao" />
package ${basePackageName}.service;

import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.core.persistence.mybatis.MybatisBaseDao;
import com.wenin819.easyweb.core.service.mybatis.MybatisBaseService;
import com.wenin819.easyweb.core.utils.ConfigUtils;
import ${basePackageName}.dao.${table.className}Dao;
import ${basePackageName}.model.${table.className};
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * @author ${author}
 */
@Service
public class ${table.className}Service extends MybatisBaseService<${table.className}> {

    @Resource
    private ${table.className}Dao ${daoName};

    @Override
    public String getTableName() {
        return ConfigUtils.get().getValue("${table.schemaPropName}") + ".${table.name}";
    }

    @Override
    public String getIdKey() {
        return "${table.primaryField.name}";
    }

    @Override
    public MybatisBaseDao<${table.className}> getDao() {
        return ${daoName};
    }

    @Override
    public CriteriaQuery genCriteriaQuery(${table.className} entity) {
        CriteriaQuery query = super.genCriteriaQuery(entity);
        return query;
    }
}
