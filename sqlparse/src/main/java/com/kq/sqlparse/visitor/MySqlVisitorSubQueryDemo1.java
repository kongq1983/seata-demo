package com.kq.sqlparse.visitor;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.mysql.ast.clause.MySqlSelectIntoStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlASTVisitorAdapter;
import com.alibaba.druid.util.JdbcConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kq
 * @date 2020-07-01 16:16
 * @since 2020-0630
 */
public class MySqlVisitorSubQueryDemo1 {


    public static void main(String[] args) {
        String dbType = JdbcConstants.MYSQL;
//        String sql = "select * from mytable a where a.id = 3";
        String sql = "select id,name,createtime from user u where id in (select userid from account a where age = 18) order by createtime desc,name";
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);

        ExportTableAliasVisitor visitor = new ExportTableAliasVisitor();
        for (SQLStatement stmt : stmtList) {
            stmt.accept(visitor);
        }

        SQLTableSource tableSource = visitor.getAliasMap().get("a");
        System.out.println(tableSource);

        System.out.println(visitor);
    }

    public static class ExportTableAliasVisitor extends MySqlASTVisitorAdapter {
        private Map<String, SQLTableSource> aliasMap = new HashMap<String, SQLTableSource>();

        @Override
        public boolean visit(SQLExprTableSource x) {
            String alias = x.getAlias();
            aliasMap.put(alias, x);
            return true;
        }

        public Map<String, SQLTableSource> getAliasMap() {
            return aliasMap;
        }
    }





}
