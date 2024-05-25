package com.example.controller;

import com.example.model.ExchangeRate;
import com.example.service.ExchangeRateServiceBean;
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

@WebServlet("/admin/exchangeRates")
public class AdminExchangeRatesServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(AdminExchangeRatesServlet.class);

    @Inject
    private ExchangeRateServiceBean exchangeRateService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currency = request.getParameter("currency");
        List<ExchangeRate> exchangeRates;
        if (currency != null && !currency.isEmpty()) {
            exchangeRates = exchangeRateService.findByCurrency(currency);
        } else {
            exchangeRates = exchangeRateService.getAllExchangeRates();
        }
        log.info("Exchange rates: {}", exchangeRates);
        request.setAttribute("exchangeRates", exchangeRates);
        request.setAttribute("role", "admin");
        request.getRequestDispatcher("/view/ExchangeRates.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("_method");
        if (method == null || method.equalsIgnoreCase("POST")) {
            addExchangeRate(request);
        } else if (method.equalsIgnoreCase("PUT")) {
            updateExchangeRate(request);
        } else if (method.equalsIgnoreCase("DELETE")) {
            deleteExchangeRate(request);
        }
        response.sendRedirect(request.getContextPath() + "/admin/exchangeRates?role=admin");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateExchangeRate(request);
        response.sendRedirect(request.getContextPath() + "/admin/exchangeRates?role=admin");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        deleteExchangeRate(request);
        response.sendRedirect(request.getContextPath() + "/admin/exchangeRates?role=admin");
    }

    private void addExchangeRate(HttpServletRequest request) {
        String currency = request.getParameter("currency");
        String date = request.getParameter("date");
        double buyingRate = Double.parseDouble(request.getParameter("buyingRate"));
        double sellingRate = Double.parseDouble(request.getParameter("sellingRate"));

        exchangeRateService.addExchangeRate(new ExchangeRate(null, currency, date, buyingRate, sellingRate));
    }

    private void updateExchangeRate(HttpServletRequest request) throws ServletException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            String currency = request.getParameter("currency");
            String date = request.getParameter("date");
            double buyingRate = Double.parseDouble(request.getParameter("buyingRate"));
            double sellingRate = Double.parseDouble(request.getParameter("sellingRate"));

            exchangeRateService.updateExchangeRate(new ExchangeRate(id, currency, date, buyingRate, sellingRate));
        } catch (NumberFormatException e) {
            log.error("Invalid ID format", e);
            throw new ServletException("Invalid ID format", e);
        }
    }

    private void deleteExchangeRate(HttpServletRequest request) throws ServletException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            exchangeRateService.deleteExchangeRate(id);
        } catch (NumberFormatException e) {
            log.error("Invalid ID format", e);
            throw new ServletException("Invalid ID format", e);
        }
    }
}
