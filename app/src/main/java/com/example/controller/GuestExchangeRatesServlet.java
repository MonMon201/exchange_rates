package com.example.controller;

import com.example.repository.ExchangeRateRepository;
import com.example.model.ExchangeRate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/guest/exchangeRates")
public class GuestExchangeRatesServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(GuestExchangeRatesServlet.class);
    private ExchangeRateRepository exchangeRateRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        exchangeRateRepository = new ExchangeRateRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ExchangeRate> exchangeRates = exchangeRateRepository.getAllExchangeRates();
        logger.info("Exchange rates: {}", exchangeRates);
        request.setAttribute("exchangeRates", exchangeRates);
        request.getRequestDispatcher("/view/GuestExchangeRatesView.jsp").forward(request, response);
    }
}
