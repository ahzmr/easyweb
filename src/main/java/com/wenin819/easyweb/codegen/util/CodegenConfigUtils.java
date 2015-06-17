package com.wenin819.easyweb.codegen.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * 代码生成配置管理工具类
 */
public class CodegenConfigUtils {

    private static Logger logger = LoggerFactory.getLogger(CodegenConfigUtils.class);

    private static final String[] NOT_NULL_PROPS = new String[]{
            "author", "basePackageName", "tablePattern"
    };

    private static Properties props = genConfigProperties();

    private CodegenConfigUtils() {}

    /**
     * 读取并编译配置文件
     * @return
     */
    private static Properties genConfigProperties() {
        try {
            Properties sysProps = new Properties(System.getProperties());
            PropertiesLoaderUtils.fillProperties(sysProps,
                    new EncodedResource(new ClassPathResource(
                            "codegen/codegen_config.properties"), "UTF-8"));
            checkConfig(sysProps);
            compileConfig(sysProps);
            return sysProps;
        } catch (IOException e) {
            logger.error("加载数据库配置文件失败失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 校验参数配置
     * @param props
     */
    private static void checkConfig(Properties props) {
        for (String notNullProp : NOT_NULL_PROPS) {
            if(!StringUtils.hasText(props.getProperty(notNullProp))) {
                logger.error("缺少关键系数配置：[{}]", notNullProp);
            }
        }
    }

    /**
     * 编译配置文件，解决路径，增加自动变量
     * @param props
     */
    private static void compileConfig(Properties props) {
        props.setProperty("dateStr", DateFormatUtils.format(new Date(), "yyyy-mm-dd"));
        String moduleName = props.getProperty("moduleName");
        if(StringUtils.hasText(moduleName)) {
            props.setProperty("basePackageName", props.getProperty("basePackageName") + "." + moduleName);
            props.setProperty("sqlmapperPath", props.getProperty("sqlmapperPath") + "." + moduleName);
        }
    }

    /**
     * 得到工程目录
     * @return
     */
    public static File getProjectPath() {
        String projectPathFile = "projectPathFile";
        File file = (File) props.get(projectPathFile);
        if(null != file) {
            return file;
        }
        String path = props.getProperty("projectPath");
        try {
            if(!StringUtils.hasText(path)) {
                file = new DefaultResourceLoader().getResource("").getFile();
            } else {
                file = ResourceUtils.getFile(path);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        props.put(projectPathFile, file);
        return file;
    }

    /**
     * class名转文件路径格式
     * @param path
     * @return
     */
    private static String replacePath(String path) {
        return StringUtils.replace(path, ".", "/");
    }

    /**
     * 得到模板位置
     * @return
     */
    public static File getTplPath() {
        try {
            return new File(new DefaultResourceLoader().getResource("").getFile(),
                    "codegen/template");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得Java基本路径
     * @return
     */
    public static File getJavaBasePath() {
        String tplPath = props.getProperty("basePackageName");
        return new File(getProjectPath() + "/src/main/java", replacePath(tplPath));
    }

    /**
     * 获得Sql Mapper基本路径
     * @return
     */
    public static File getMapperBasePath() {
        String tplPath = props.getProperty("sqlmapperPath");
        return new File(getProjectPath() + "/src/main/resources", replacePath(tplPath));
    }

    /**
     * 获得数据库名
     * @return
     */
    public static String getTableSchema() {
        return props.getProperty("tableSchema");
    }

    /**
     * 获得表匹配模式
     * @return
     */
    public static String getTablePattern() {
        return props.getProperty("tablePattern");
    }

    /**
     * 得到代码生成配置信息
     * @return
     */
    public static Map<Object, Object> getConfigs() {
        return props;
    }

    public static void main(String[] args) {
        System.out.println(genConfigProperties());
    }

}
