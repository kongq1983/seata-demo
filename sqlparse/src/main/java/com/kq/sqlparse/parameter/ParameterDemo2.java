package com.kq.sqlparse.parameter;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlExportParameterVisitor;
import com.alibaba.druid.sql.visitor.ExportParameterVisitor;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;

/**
 * @author kq
 * @date 2020-07-01 16:39
 * @since 2020-0630
 */
public class ParameterDemo2 {

    public static void main(String[] args) {
//        String sql = "select * from t where id = 3 and name = 'abc'";
        String sql = "select id,name,createtime from user u where id in (select userid from account where age = 18) order by createtime desc,name";

        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);

        StringBuilder out = new StringBuilder();
        ExportParameterVisitor visitor = new MySqlExportParameterVisitor(out);
        for (SQLStatement stmt : stmtList) {
            stmt.accept(visitor);
        }

        String paramteredSql = out.toString();
        System.out.println("paramteredSql="+paramteredSql);

        List<Object> paramters = visitor.getParameters(); // [3, "abc"]
        for (Object param : paramters) {
            System.out.println("param="+param);
        }
    }

}
