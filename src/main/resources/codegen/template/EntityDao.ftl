/**
* There are <a href="https://github.com/wenin819/easyweb">EasyWeb</a> code generation
*/
package ${basePackageName}.dao;

import com.wenin819.easyweb.core.db.BaseDao;
import ${basePackageName}.model.${table.className};

import org.springframework.stereotype.Repository;

/**
 * @author ${author}
 */
@Repository
public interface ${table.className}Dao extends BaseDao<${table.className}> {

}
