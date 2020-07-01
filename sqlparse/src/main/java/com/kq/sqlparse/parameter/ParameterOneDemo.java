package com.kq.sqlparse.parameter;

import com.alibaba.druid.sql.visitor.ParameterizedOutputVisitorUtils;
import com.alibaba.druid.util.JdbcConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kq
 * @date 2020-07-01 16:30
 * @since 2020-0630
 */
public class ParameterOneDemo {

//    psql=SELECT *
//    FROM user
//    WHERE id = ?
//    AND age = ?
//    OR name = ?
//    outParameters=[101, 102, king]

    public static void main(String[] args) {
        final String dbType = JdbcConstants.MYSQL;

        // 参数化SQL是输出的参数保存在这个List中
        List<Object> outParameters = new ArrayList<Object>();

        String sql = "select * from user where id = 101 and age = 102 or name = 'king'";
        String psql = ParameterizedOutputVisitorUtils.parameterize(sql, dbType, outParameters);

        System.out.println("psql="+psql);
        System.out.println("outParameters="+outParameters);

    }

}
