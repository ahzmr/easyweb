<#assign serviceName = table.className?uncap_first + "Service" />
package ${basePackageName}.controller;

import com.wenin819.easyweb.core.persistence.mybatis.Criteria;
import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.core.service.mybatis.MybatisBaseService;
import com.wenin819.easyweb.core.web.ActionType;
import com.wenin819.easyweb.core.web.BaseCrudController;
import ${basePackageName}.model.${table.className};
import ${basePackageName}.service.${table.className}Service;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author ${author}
 */
@Controller
@RequestMapping(${table.className}Controller.BASE_URL)
public class ${table.className}Controller extends BaseCrudController<${table.className}> {

    public static final String BASE_URL = "/${moduleName}/${table.className}/";
    private static final String BASE_PATH = "modules/${moduleName}/${table.className}";

    @Resource
    private ${table.className}Service ${serviceName};

    @Override
    protected String getBaseUrl() {
        return BASE_URL;
    }

    @Override
    protected String getBasePagePath() {
        return BASE_PATH;
    }

    @Override
    protected String getBasePermission() {
        return "${moduleName}:${table.className}";
    }

    @Override
    protected MybatisBaseService<${table.className}> getService() {
        return ${serviceName};
    }
}
