package com.wenin819.easyweb.codegen.util;

import com.wenin819.easyweb.codegen.vo.TableField;

import java.math.BigDecimal;
import java.sql.JDBCType;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenin819@gmail.com on 2014/4/13.
 */
public class JavaTypeResolverUtil {

    private static final Map<JDBCType, Class> typeMap = new HashMap<>();

    static {
        typeMap.put(JDBCType.ARRAY, Object.class);
        typeMap.put(JDBCType.BIGINT, Long.class);
        typeMap.put(JDBCType.BIT, Boolean.class);
        typeMap.put(JDBCType.BOOLEAN, Boolean.class);
        typeMap.put(JDBCType.CHAR, String.class);
        typeMap.put(JDBCType.CLOB, String.class);
        typeMap.put(JDBCType.DATALINK, Object.class);
        typeMap.put(JDBCType.DATE, Date.class);
        typeMap.put(JDBCType.DISTINCT, Object.class);
        typeMap.put(JDBCType.DOUBLE, Double.class);
        typeMap.put(JDBCType.FLOAT, Double.class);
        typeMap.put(JDBCType.INTEGER, Integer.class);
        typeMap.put(JDBCType.JAVA_OBJECT, Object.class);
        typeMap.put(JDBCType.LONGVARCHAR, String.class);
        typeMap.put(JDBCType.NULL, Object.class);
        typeMap.put(JDBCType.OTHER, Object.class);
        typeMap.put(JDBCType.REAL, Float.class);
        typeMap.put(JDBCType.REF, Object.class);
        typeMap.put(JDBCType.SMALLINT, Short.class);
        typeMap.put(JDBCType.STRUCT, Object.class);
        typeMap.put(JDBCType.TIME, Date.class);
        typeMap.put(JDBCType.TIMESTAMP, Date.class);
        typeMap.put(JDBCType.TINYINT, Byte.class);
        typeMap.put(JDBCType.VARCHAR, String.class);
    }

    /**
     * 计算JDBC对应的Java类型
     */
    public static Class calculateJavaType(TableField field) {
        JDBCType jdbcType = field.getJdbcType();
        Class javaType = typeMap.get(jdbcType);
        if (null == javaType) {
            switch (jdbcType) {
                case DECIMAL:
                case NUMERIC:
                    if (field.getScale() > 0 || field.getLength() > 18) {
                        javaType = BigDecimal.class;
                    } else if (field.getLength() > 9) {
                        javaType = Long.class;
                    } else if (field.getLength() > 4) {
                        javaType = Integer.class;
                    } else {
                        javaType = Short.class;
                    }
                    break;
                default:
                    javaType = null;
                    break;
            }
        }
        return javaType;
    }
}
