package com.example.controller;

import com.example.dao.ExchangeRateRepository;
import com.example.model.ExchangeRateEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/manage-new-rates")
public class AdminRatesServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(AdminRatesServlet.class);
    private ExchangeRateRepository exchangeRateRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        exchangeRateRepository = new ExchangeRateRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("AdminRatesServlet doGet");
        List<ExchangeRateEntity> exchangeRates = exchangeRateRepository.getAllExchangeRates();
        request.setAttribute("exchangeRates", exchangeRates);
        request.getRequestDispatcher("/jsps/manageExchangeRates.jsp").forward(request, response);
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
        response.sendRedirect(request.getContextPath() + "/admin/manage-new-rates");
    }

    private void addExchangeRate(HttpServletRequest request) {
        String currency = request.getParameter("currency");
        String date = request.getParameter("date");
        double buyingRate = Double.parseDouble(request.getParameter("buyingRate"));
        double sellingRate = Double.parseDouble(request.getParameter("sellingRate"));

        exchangeRateRepository.addExchangeRate(new ExchangeRateEntity(null, currency, date, buyingRate, sellingRate));
    }

    private void updateExchangeRate(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        String currency = request.getParameter("currency");
        String date = request.getParameter("date");
        double buyingRate = Double.parseDouble(request.getParameter("buyingRate"));
        double sellingRate = Double.parseDouble(request.getParameter("sellingRate"));

        exchangeRateRepository.updateExchangeRate(new ExchangeRateEntity(id, currency, date, buyingRate, sellingRate));
    }

    private void deleteExchangeRate(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));

        exchangeRateRepository.deleteExchangeRate(id);
    }
}
