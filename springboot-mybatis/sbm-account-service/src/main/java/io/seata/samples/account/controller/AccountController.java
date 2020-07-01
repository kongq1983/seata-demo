package io.seata.samples.account.controller;

import io.seata.core.context.RootContext;
import io.seata.samples.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping
    public void debit(@RequestParam String userId, @RequestParam BigDecimal orderMoney, HttpServletRequest request) {
        System.out.println("account XID " + RootContext.getXID());

        String requestHeaderXID = request.getHeader(RootContext.KEY_XID);
        System.out.println("account requestHeaderXID " + requestHeaderXID);

        accountService.debit(userId, orderMoney);
    }

}
