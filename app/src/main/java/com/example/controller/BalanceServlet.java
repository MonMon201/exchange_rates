package com.example.controller;

import com.example.model.Balance;
import com.example.service.BalanceServiceBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/balance/manage")
public class BalanceServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(BalanceServlet.class);

    @Inject
    private BalanceServiceBean balanceService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Balance> balances = balanceService.getBalance();
        request.setAttribute("balances", balances);
        request.getRequestDispatcher("/view/Balance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("addUah".equals(action)) {
                addUah(request);
            } else if ("decreaseUah".equals(action)) {
                decreaseUah(request);
            } else if ("convert".equals(action)) {
                convertCurrency(request);
            }
        } catch (Exception e) {
            logger.error("Error handling action: " + action, e);
            throw new ServletException(e);
        }
        response.sendRedirect(request.getContextPath() + "/balance/manage");
    }

    private void addUah(HttpServletRequest request) {
        double amount = Double.parseDouble(request.getParameter("amount"));
        balanceService.addUah(amount);
    }

    private void decreaseUah(HttpServletRequest request) throws Exception {
        double amount = Double.parseDouble(request.getParameter("amount"));
        balanceService.decreaseUah(amount);
    }

    private void convertCurrency(HttpServletRequest request) throws Exception {
        String fromCurrency = request.getParameter("fromCurrency");
        String toCurrency = request.getParameter("toCurrency");
        double amount = Double.parseDouble(request.getParameter("amount"));
        balanceService.convertCurrency(fromCurrency, toCurrency, amount);
    }
}