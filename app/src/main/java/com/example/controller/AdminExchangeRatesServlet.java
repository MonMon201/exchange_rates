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
        log.info("AdminRatesServlet doGet");
        List<ExchangeRate> exchangeRates = exchangeRateService.getAllExchangeRates();
        request.setAttribute("exchangeRates", exchangeRates);
        request.getRequestDispatcher("/view/AdminExchangeRatesView.jsp").forward(request, response);
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
        response.sendRedirect(request.getContextPath() + "/admin/exchangeRates");
    }

    private void addExchangeRate(HttpServletRequest request) {
        String currency = request.getParameter("currency");
        String date = request.getParameter("date");
        double buyingRate = Double.parseDouble(request.getParameter("buyingRate"));
        double sellingRate = Double.parseDouble(request.getParameter("sellingRate"));

        exchangeRateService.addExchangeRate(new ExchangeRate(null, currency, date, buyingRate, sellingRate));
    }

    private void updateExchangeRate(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        String currency = request.getParameter("currency");
        String date = request.getParameter("date");
        double buyingRate = Double.parseDouble(request.getParameter("buyingRate"));
        double sellingRate = Double.parseDouble(request.getParameter("sellingRate"));

        exchangeRateService.updateExchangeRate(new ExchangeRate(id, currency, date, buyingRate, sellingRate));
    }

    private void deleteExchangeRate(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));

        exchangeRateService.deleteExchangeRate(id);
    }
}
