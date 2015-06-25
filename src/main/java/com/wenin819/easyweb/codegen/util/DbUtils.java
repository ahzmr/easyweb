package com.wenin819.easyweb.codegen.util;

import com.wenin819.easyweb.codegen.vo.TableEntity;
import com.wenin819.easyweb.codegen.vo.TableField;
import com.wenin819.easyweb.core.persistence.IFiledEnum;
import com.wenin819.easyweb.core.utils.ConfigUtils;
import com.wenin819.easyweb.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.*;
import java.util.*;

/**
 * 代码生成方面的数据库工具类.
 * Created by wenin819@gmail.com on 2014/4/9.
 */
public class DbUtils {

    private static final Logger logger = LoggerFactory.getLogger(DbUtils.class);

    public static Connection getConnect() {
        try {
            Class.forName(ConfigUtils.get().getValue("jdbc.driver"));
            Properties tmpProp = System.getProperties();
            tmpProp.setProperty("user", ConfigUtils.get().getValue("jdbc.username"));
            tmpProp.setProperty("password", ConfigUtils.get().getValue("jdbc.password"));
            tmpProp.setProperty("remarksReporting", "true");
            tmpProp.setProperty("useInformationSchema", "true");
            return DriverManager.getConnection(ConfigUtils.get().getValue("jdbc.url"), tmpProp);
        } catch (Exception e) {
            logger.error("获取和数据库的连接失败", e);
            throw new RuntimeException("获取和数据库的连接失败", e);
        }
    }

    public static Collection<TableEntity> getTables(String schema, String tablePattern) {
        Set<TableEntity> tableCollection = new HashSet<TableEntity>();
        if (StringUtils.isBlank(tablePattern)) {
            return tableCollection;
        }
        String[] tablePatterns = tablePattern.split(",");
        ResultSet rs = null;
        try {
            DatabaseMetaData metaData = getConnect().getMetaData();
            for (String tablePatternStr : tablePatterns) {
                rs = metaData.getTables(null, schema, tablePatternStr, new String[]{"TABLE"});
                tableCollection.addAll(parseTableEntity4ResultSet(rs));
                closeResultSet(rs);
            }
            for (TableEntity entity : tableCollection) {
                rs = metaData.getColumns(null, entity.getSchema(), entity.getName(), null);
                entity.setFieldList(parseTableField4ResultSet(rs));
                closeResultSet(rs);
                rs = metaData.getPrimaryKeys(null, entity.getSchema(), entity.getName());
                while (rs.next()) {
                    String columnName = rs.getString("COLUMN_NAME"); //$NON-NLS-1$
                    entity.addPrimaryKeyColumn(columnName);
                }
                closeResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error("获取数据库表信息失败", e);
            throw new RuntimeException("获取数据库信息失败", e);
        } finally {
            closeResultSet(rs);
        }
        return tableCollection;
    }

    private static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    private static List<TableField> parseTableField4ResultSet(ResultSet rs) throws SQLException {
        List<TableField> list = new ArrayList<TableField>();
        if (null == rs) {
            return null;
        }
        while (rs.next()) {
            TableField field = new TableField();
            field.setCollumnName(rs.getString("COLUMN_NAME"));
            field.setJdbcType(rs.getInt("DATA_TYPE"));
            field.setLength(rs.getInt("COLUMN_SIZE"));
            field.setNullable(rs.getInt("NULLABLE") > 0);
            field.setRemarks(rs.getString("REMARKS"));
            field.setScale(rs.getInt("DECIMAL_DIGITS"));
            list.add(field);
        }
        return list;
    }

    private static List<TableEntity> parseTableEntity4ResultSet(ResultSet rs) throws SQLException {
        List<TableEntity> list = new ArrayList<TableEntity>();
        if (null == rs) {
            return null;
        }
        while (rs.next()) {
            TableEntity table = new TableEntity();
            table.setSchema(rs.getString("TABLE_SCHEM"));
            if (null == table.getSchema()) {
                table.setSchema(rs.getString("TABLE_CAT"));
            }
            table.setName(rs.getString("TABLE_NAME"));
            table.setRemarks(rs.getString("REMARKS"));
            list.add(table);
        }
        return list;
    }

    public static String getTableId(IFiledEnum filedEnum) {
        String tableSchema = filedEnum.getTableSchema();
        if (null != tableSchema && !tableSchema.isEmpty()) {
            return tableSchema + '.' + filedEnum.getTableName();
        } else {
            return filedEnum.getTableName();
        }
    }

    public static void printResultSet(ResultSet rs) {
        printResultSet(rs, System.out);
    }

    public static void printResultSet(ResultSet rs, PrintStream out) {
        out.printf(resultSet2String(rs));
        out.flush();
    }

    public static String resultSet2String(ResultSet rs) {
        if (null == rs) {
            return "";
        }
        StringBuilder s = new StringBuilder();
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            s.append("Name:[\t");
            for (int i = 1; i <= columnCount; i++) {
                s.append(metaData.getColumnName(i));
                s.append("\t");
            }
            s.append("]\n");
            s.append("ColumnType:[\t");
            for (int i = 1; i <= columnCount; i++) {
                s.append(metaData.getColumnType(i));
                s.append("\t");
            }
            s.append("]\n");

            while (rs.next()) {
                s.append("Row:[\t");
                for (int i = 1; i <= columnCount; i++) {
                    s.append(rs.getString(i));
                    s.append("\t");
                }
                s.append("]\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s.toString();
    }
}
