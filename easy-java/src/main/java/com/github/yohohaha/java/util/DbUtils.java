package com.github.yohohaha.java.util;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * created at 2020/10/21 11:37:58
 *
 * @author Yohohaha
 */
public class DbUtils {

    /**
     * split multi sql's string to sql list using separator ';'
     *
     * @param sqlString
     *
     * @return sql list
     */
    public static List<String> splitSqls(String sqlString) {
        if (sqlString == null) {
            // null return empty
            return Collections.emptyList();
        }
        List<String> sqlList = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder(128);
        boolean inQuotation = false;
        for (int i = 0; i < sqlString.length(); i++) {
            char c = sqlString.charAt(i);
            if (Character.isHighSurrogate(c)) {
                sqlBuilder.append(c).append(sqlString.charAt(i + 1));
                i++;
            } else {
                switch (c) {
                    case ';':
                        if (inQuotation) {
                            continue;
                        }
                        sqlBuilder.append(c);
                        // get one complete sql
                        String sql = sqlBuilder.toString().trim();
                        if (!sql.trim().isEmpty()) {
                            sqlList.add(sql);
                        }
                        // clear string builder
                        sqlBuilder.setLength(0);
                        break;
                    case '\'':
                    case '"':
                        inQuotation = !inQuotation;
                        sqlBuilder.append(c);
                        break;
                    default:
                        sqlBuilder.append(c);
                        break;
                }
            }
        }
        // get one complete sql
        String sql = sqlBuilder.toString();
        if (!sql.trim().isEmpty()) {
            sqlList.add(sql);
        }
        return sqlList;
    }
}
