package com.wenin819.easyweb.codegen;

import com.wenin819.easyweb.codegen.util.CodegenConfigUtils;
import com.wenin819.easyweb.codegen.util.DbUtils;
import com.wenin819.easyweb.codegen.util.FreemarkerUtils;
import com.wenin819.easyweb.codegen.vo.TableEntity;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Collection;
import java.util.Properties;

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
        cfg.setDefaultEncoding("UTF-8");

        // 定义模板变量
        Properties model = CodegenConfigUtils.getConfigs();


        // 生成 Entity
        Template entityTpl = cfg.getTemplate("Entity.ftl");
        Template daoTpl = cfg.getTemplate("EntityDao.ftl");
        Template serviceTpl = cfg.getTemplate("EntityService.ftl");
        Template controllerTpl = cfg.getTemplate("EntityController.ftl");
        Template sqlMapperTpl = cfg.getTemplate("SqlMapper.ftl");
        Template sqlMapperExtTpl = cfg.getTemplate("SqlMapperExt.ftl");
        Template formTpl = cfg.getTemplate("viewForm.ftl");
        Template listTpl = cfg.getTemplate("viewList.ftl");

        Collection<TableEntity> tables = DbUtils.getTables(CodegenConfigUtils.getTableSchema(),
                CodegenConfigUtils.getTablePattern());
        if (logger.isTraceEnabled()) {
            logger.trace("tables:[{}]", tables.toString());
        }

        String genFileType = model.getProperty("genFileType", null);
        if(null != genFileType) {
            genFileType = genFileType.toUpperCase();
        }
        for (TableEntity table : tables) {
            model.put("table", table);
            String filePath = null;
            String content = null;

            if(null == genFileType || genFileType.contains("M")) {
                filePath = CodegenConfigUtils.getJavaBasePath() + separator + "model"
                              + separator + table.getClassName() + ".java";
                content = FreemarkerUtils.process2String(entityTpl, model);
            writeFile(content, filePath, false);
            logger.info("Entity: {}", filePath);

            filePath = CodegenConfigUtils.getMapperMysqlPath() + separator
                       + table.getName() + ".xml";
            content = FreemarkerUtils.process2String(sqlMapperTpl, model);
            writeFile(content, filePath, true);
            logger.info("SqlMapper: {}", filePath);

            filePath = CodegenConfigUtils.getMapperMysqlPath() + separator
                       + table.getName() + "_ext.xml";
            content = FreemarkerUtils.process2String(sqlMapperExtTpl, model);
            writeFile(content, filePath, false);
            logger.info("SqlMapperExt: {}", filePath);
            }

            if(null == genFileType || genFileType.contains("D")) {
                filePath = CodegenConfigUtils.getJavaBasePath() + separator + "dao"
                        + separator + table.getClassName() + "Dao.java";
                content = FreemarkerUtils.process2String(daoTpl, model);
                writeFile(content, filePath, false);
                logger.info("Dao: {}", filePath);
            }

            if(null == genFileType || genFileType.contains("S")) {
                filePath = CodegenConfigUtils.getJavaBasePath() + separator + "service"
                        + separator + table.getClassName() + "Service.java";
                content = FreemarkerUtils.process2String(serviceTpl, model);
                writeFile(content, filePath, false);
                logger.info("Service: {}", filePath);
            }

            if(null == genFileType || genFileType.contains("C")) {
                filePath = CodegenConfigUtils.getJavaBasePath() + separator + "controller"
                        + separator + table.getClassName() + "Controller.java";
                content = FreemarkerUtils.process2String(controllerTpl, model);
                writeFile(content, filePath, false);
                logger.info("Controller: {}", filePath);
            }

            if(null == genFileType || genFileType.contains("J")) {
                filePath = CodegenConfigUtils.getViewBasePath() + separator
                        + table.getClassName() + "List.jsp";
                content = FreemarkerUtils.process2String(listTpl, model);
                writeFile(content, filePath, false);
                logger.info("viewList: {}", filePath);

                filePath = CodegenConfigUtils.getViewBasePath() + separator
                        + table.getClassName() + "Form.jsp";
                content = FreemarkerUtils.process2String(formTpl, model);
                writeFile(content, filePath, false);
                logger.info("viewForm: {}", filePath);
            }

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
        if(null == content || null == filePath) {
            return;
        }
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (cover || !file.exists()) {
                bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8"));
                bufferedWriter.write(content);
            } else {
                logger.info("文件[{}]已存在，直接跳过！", filePath);
            }
        } catch (Exception e) {
            logger.error("写到文件时出错", e);
        } finally {
            if(null != bufferedWriter) {
                try {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } catch (IOException e) {
                    logger.error("关闭文件时出错", e);
                }
            }
        }
    }


}
