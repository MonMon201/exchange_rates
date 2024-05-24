package com.example.controller;

import com.example.dao.ExchangeRateDAO;
import com.example.model.ExchangeRate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/manage-rates")
public class AdminServlet extends HttpServlet {
    private ExchangeRateDAO exchangeRateDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        exchangeRateDAO = new ExchangeRateDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ExchangeRate> exchangeRates = exchangeRateDAO.getAllExchangeRates();
        request.setAttribute("exchangeRates", exchangeRates);
        request.getRequestDispatcher("/jsps/adminRates.jsp").forward(request, response);
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
        response.sendRedirect(request.getContextPath() + "/admin/manage-rates");
    }

    private void addExchangeRate(HttpServletRequest request) {
        String currency = request.getParameter("currency");
        String date = request.getParameter("date");
        double buyingRate = Double.parseDouble(request.getParameter("buyingRate"));
        double sellingRate = Double.parseDouble(request.getParameter("sellingRate"));

        exchangeRateDAO.addExchangeRate(new ExchangeRate(currency, date, buyingRate, sellingRate));
    }

    private void updateExchangeRate(HttpServletRequest request) {
        String currency = request.getParameter("currency");
        String date = request.getParameter("date");
        double buyingRate = Double.parseDouble(request.getParameter("buyingRate"));
        double sellingRate = Double.parseDouble(request.getParameter("sellingRate"));

        exchangeRateDAO.updateExchangeRate(new ExchangeRate(currency, date, buyingRate, sellingRate));
    }

    private void deleteExchangeRate(HttpServletRequest request) {
        String currency = request.getParameter("currency");
        String date = request.getParameter("date");

        exchangeRateDAO.deleteExchangeRate(currency, date);
    }
}
