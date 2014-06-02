package com.wenin819.easyweb.codegen;

import com.wenin819.easyweb.codegen.util.DbUtils;
import com.wenin819.easyweb.codegen.util.FreemarkerUtils;
import com.wenin819.easyweb.codegen.vo.TableEntity;

import freemarker.template.Configuration;
import freemarker.template.Template;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成主类.
 * Created by wenin819@gmail.com on 2014/4/7.
 */
public class CodeGen {

    private static final Logger logger = LoggerFactory.getLogger(CodeGen.class);

    public static void main(String[] args) throws IOException {

        // ========== ↓↓↓↓↓↓ 执行前请修改参数，谨慎执行。↓↓↓↓↓↓ ====================

        // 主要提供基本功能模块代码生成。

        String basePackageName = "com.wenin819.easyweb.modules";
        String moduleName = "sys";            // 模块名，例：sys
        String author = "wenin819@gmail.com";        // 类作者，例：wenin819@gmail.com
        String tableSchema = "easyweb";             // 数据库名
        String tablePattern = "sys_user";            // 表名表达式，可以用逗号分隔，也可以用'%'匹配

        // 是否启用生成工具
        Boolean isEnable = true;

        // ========== ↑↑↑↑↑↑ 执行前请修改参数，谨慎执行。↑↑↑↑↑↑ ====================

        if (!isEnable) {
            logger.error("请启用代码生成工具，设置参数：isEnable = true");
            return;
        }

        if (StringUtils.isEmpty(moduleName) || StringUtils.isEmpty(moduleName)
            || StringUtils.isEmpty(tableSchema) || StringUtils.isEmpty(tablePattern)) {
            logger.error("参数设置错误：包名、模块名、数据库名、表名表达式不能为空。");
            return;
        }

        // 获取文件分隔符
        String separator = File.separator;
        basePackageName = basePackageName.toLowerCase();

        // 获取工程路径
        File projectPath = new DefaultResourceLoader().getResource("").getFile();
        while (!new File(projectPath.getPath() + separator + "src" + separator + "main").exists()) {
            projectPath = projectPath.getParentFile();
        }
        logger.info("Project Path: {}", projectPath);

        // 模板文件路径
        String
            tplPath =
            StringUtils
                .replace(projectPath + "/src/main/java/com/wenin819/easyweb/codegen/template", "/",
                         separator);
        logger.info("Template Path: {}", tplPath);

        // Java文件路径
        String javaPath = StringUtils.replaceEach(projectPath + "/src/main/java/" + basePackageName,
                                                  new String[]{"/", "."},
                                                  new String[]{separator, separator});
        logger.info("Java Path: {}", javaPath);
        String sqlMapperPath = StringUtils.replaceEach(projectPath + "/src/main/resources/sqlmapper/mysql",
                                                       new String[]{"/", "."},
                                                       new String[]{separator, separator});
        logger.info("SqlMapper Path: {}", sqlMapperPath);

        // 代码模板配置
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(tplPath));

        // 定义模板变量
        Map<String, Object> model = new HashMap<>();
        model.put("basePackageName", StringUtils.lowerCase(basePackageName));
        model.put("moduleName", StringUtils.lowerCase(moduleName));
        model.put("author", author);

        // 生成 Entity
        Template entityTpl = cfg.getTemplate("Entity.ftl");
        Template daoTpl = cfg.getTemplate("EntityDao.ftl");
        Template sqlMapperTpl = cfg.getTemplate("SqlMapper.ftl");
        Template sqlMapperExtTpl = cfg.getTemplate("SqlMapperExt.ftl");

        Collection<TableEntity> tables = DbUtils.getTables("easyweb", tablePattern);
        if (logger.isInfoEnabled()) {
            logger.info("tables:[{}]", tables.toString());
        }
        for (TableEntity table : tables) {
            model.put("table", table);
            String filePath = javaPath + separator + model.get("moduleName") + separator + "model"
                              + separator + table.getClassName() + ".java";
            String content = FreemarkerUtils.process2String(entityTpl, model);
            writeFile(content, filePath, false);
            logger.info("Entity: {}", filePath);

            filePath = javaPath + separator + model.get("moduleName") + separator + "dao"
                              + separator + table.getClassName() + "Dao.java";
            content = FreemarkerUtils.process2String(daoTpl, model);
            writeFile(content, filePath, false);
            logger.info("Dao: {}", filePath);

            filePath = sqlMapperPath + separator + model.get("moduleName") + separator
                       + table.getName() + ".xml";
            content = FreemarkerUtils.process2String(sqlMapperTpl, model);
            writeFile(content, filePath, true);
            logger.info("SqlMapper: {}", filePath);

            filePath = sqlMapperPath + separator + model.get("moduleName") + separator
                       + table.getName() + "_ext.xml";
            content = FreemarkerUtils.process2String(sqlMapperExtTpl, model);
            writeFile(content, filePath, false);
            logger.info("SqlMapperExt: {}", filePath);
        }

        logger.info("Generate Success.");
    }

    /**
     * 将内容写入文件
     *
     * @param content  文件内容
     * @param filePath 路径
     * @param cover 是否覆盖已有文件
     */
    public static void writeFile(String content, String filePath, boolean cover) {
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (cover || !file.exists()) {
                FileWriter fileWriter = new FileWriter(filePath, false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(content);
                bufferedWriter.flush();
                bufferedWriter.close();
                fileWriter.close();
            } else {
                logger.info("文件[{}]已存在，直接跳过！", filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
