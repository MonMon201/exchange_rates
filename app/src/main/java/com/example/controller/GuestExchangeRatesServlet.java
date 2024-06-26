package com.example.controller;

import com.example.model.ExchangeRate;
import com.example.service.ExchangeRateServiceBean;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/guest/exchangeRates")
public class GuestExchangeRatesServlet extends HttpServlet {

    @Inject
    private ExchangeRateServiceBean exchangeRateService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ExchangeRate> exchangeRates = exchangeRateService.getAllExchangeRates();
        request.setAttribute("exchangeRates", exchangeRates);
        request.setAttribute("role", "guest");
        request.getRequestDispatcher("/view/ExchangeRates.jsp").forward(request, response);
    }
}
