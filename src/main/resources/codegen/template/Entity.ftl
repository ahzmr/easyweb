/**
* There are <a href="https://github.com/wenin819/easyweb">EasyWeb</a> code generation
*/
package ${basePackageName}.model;

import com.wenin819.easyweb.core.persistence.BaseEntity;
import com.wenin819.easyweb.core.persistence.IFiledEnum;
import com.wenin819.easyweb.core.utils.ConfigUtils;
<#list table.importTypeList as importType >
import ${importType};
</#list>

/**
 * ${table.remarks}
 * @author ${author}
 */
public class ${table.className} extends BaseEntity<${table.className}.TE> {
<#macro field classType name remarks>
    /**
     * ${remarks}
     */
    private ${classType} ${name};
</#macro>
<#macro getSetMethod classType name upperName remarks>
    public ${classType} get${upperName}() {
        return ${name};
    }

    public void set${upperName}(${classType} ${name}) {
        this.${name} = ${name};
    }
</#macro>

<#list table.fieldList as tf>
        <@field classType="${tf.javaTypeName}" name="${tf.name}" remarks="${tf.remarks}" />
</#list>

<#list table.fieldList as tf>
    <@getSetMethod classType="${tf.javaTypeName}" name="${tf.name}"
        upperName="${tf.methodName}" remarks="${tf.remarks}" />
</#list>

    public static enum TE implements IFiledEnum {
<#macro enumField name collumnName remarks>
        ${name}("${collumnName}"),  // ${remarks}
</#macro>
<#list table.fieldList as field>
    <@enumField name="${field.name}" collumnName="${field.collumnName}" remarks="${field.remarks}" />
</#list>
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
            return "${table.name}";
        }
        @Override
        public String getTableSchema() {
            return ConfigUtils.get().getValue("${table.schemaPropName}");
        }
        @Override
        public String toString() {
            return this.filedName;
        }
    }
}
