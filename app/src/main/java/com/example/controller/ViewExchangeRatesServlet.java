package com.example.controller;

import com.example.dao.ExchangeRateDAO;
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

@WebServlet("/view-all-rates")
public class ViewExchangeRatesServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ViewExchangeRatesServlet.class);
    private ExchangeRateDAO exchangeRateDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        exchangeRateDAO = new ExchangeRateDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ExchangeRate> exchangeRates = exchangeRateDAO.getAllExchangeRates();
        logger.info("Exchange rates: {}", exchangeRates);
        request.setAttribute("exchangeRates", exchangeRates);
        request.getRequestDispatcher("/jsps/viewAllRates.jsp").forward(request, response);
    }
}
