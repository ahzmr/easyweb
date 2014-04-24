package com.wenin819.easyweb.core.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by wenin819@gmail.com on 2014/4/5.
 */
public class DbUtils {

    private static final Logger logger = LoggerFactory.getLogger(DbUtils.class);

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
