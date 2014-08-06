package com.wenin819.easyweb.codegen;

import com.wenin819.easyweb.codegen.util.CodegenConfigUtils;
import com.wenin819.easyweb.codegen.util.DbUtils;
import com.wenin819.easyweb.codegen.util.FreemarkerUtils;
import com.wenin819.easyweb.codegen.vo.TableEntity;

import freemarker.template.Configuration;
import freemarker.template.Template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * 代码生成主类.
 * Created by wenin819@gmail.com on 2014/4/7.
 */
public class CodeGen {

    private static final Logger logger = LoggerFactory.getLogger(CodeGen.class);

    private static final String separator = File.separator;

    public static void main(String[] args) throws IOException {

        // 代码模板配置
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(CodegenConfigUtils.getTplPath());

        // 定义模板变量
        Map<Object, Object> model = CodegenConfigUtils.getConfigs();

        // 生成 Entity
        Template entityTpl = cfg.getTemplate("Entity.ftl");
        Template daoTpl = cfg.getTemplate("EntityDao.ftl");
        Template sqlMapperTpl = cfg.getTemplate("SqlMapper.ftl");
        Template sqlMapperExtTpl = cfg.getTemplate("SqlMapperExt.ftl");

        Collection<TableEntity> tables = DbUtils.getTables(CodegenConfigUtils.getTableSchema(),
                CodegenConfigUtils.getTablePattern());
        if (logger.isInfoEnabled()) {
            logger.info("tables:[{}]", tables.toString());
        }
        for (TableEntity table : tables) {
            model.put("table", table);
            String filePath = CodegenConfigUtils.getJavaBasePath() + separator + "model"
                              + separator + table.getClassName() + ".java";
            String content = FreemarkerUtils.process2String(entityTpl, model);
            writeFile(content, filePath, false);
            logger.info("Entity: {}", filePath);

            filePath = CodegenConfigUtils.getJavaBasePath() + separator + "dao"
                              + separator + table.getClassName() + "Dao.java";
            content = FreemarkerUtils.process2String(daoTpl, model);
            writeFile(content, filePath, false);
            logger.info("Dao: {}", filePath);

            filePath = CodegenConfigUtils.getMapperBasePath() + separator
                       + table.getName() + ".xml";
            content = FreemarkerUtils.process2String(sqlMapperTpl, model);
            writeFile(content, filePath, true);
            logger.info("SqlMapper: {}", filePath);

            filePath = CodegenConfigUtils.getMapperBasePath() + separator
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
            logger.error("写到文件时出错", e);
        }
    }
}
