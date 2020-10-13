package com.kq.sqlparse.demo;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;

import java.sql.SQLSyntaxErrorException;
import java.util.List;

/**
 * @author kq
 * @date 2020-07-01 15:36
 * @since 2020-0630
 */
public class SelectParserDemo {

    public static void main(String[] args) throws SQLSyntaxErrorException {
        String sql = "select  id,name,username from account where id=1";
        String dbType = "mysql";
        System.out.println("原始SQL 为 ： "+sql);
        SQLSelectStatement statement = (SQLSelectStatement) parser(sql, dbType);
        SQLSelect select = statement.getSelect();
        SQLSelectQueryBlock query = (SQLSelectQueryBlock) select.getQuery();
        SQLExprTableSource tableSource = (SQLExprTableSource) query.getFrom();
        String tableName = tableSource.getExpr().toString();
        System.out.println("获取的表名为  tableName ：" + tableName);
        //修改表名为acct_1
        tableSource.setExpr("t_account");
        System.out.println("修改表名后的SQL 为 ： [" + statement.toString() +"]");
    }

    public static SQLStatement parser(String sql, String dbType) throws SQLSyntaxErrorException {
        List<SQLStatement> list = SQLUtils.parseStatements(sql, dbType);
        if (list.size() > 1) {
            throw new SQLSyntaxErrorException("MultiQueries is not supported,use single query instead ");
        }
        return list.get(0);
    }


}