package com.example.dao;

import com.example.model.ExchangeRate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateDAO {
    private static final Logger logger = LogManager.getLogger(ExchangeRateDAO.class);
    private static final String FILE_PATH = "/usr/local/tomcat/webapps/app/rates/exchange_rates.txt";
    private List<ExchangeRate> exchangeRates;

    public ExchangeRateDAO() {
        this.exchangeRates = new ArrayList<>();
    }

    public List<ExchangeRate> getAllExchangeRates() {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        ensureFileExists();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                logger.info("line: " + line);
                String[] parts = line.split(",");
                String currency = parts[0];
                String date = parts[1];
                double buyingRate = Double.parseDouble(parts[2]);
                double sellingRate = Double.parseDouble(parts[3]);
                exchangeRates.add(new ExchangeRate(currency, date, buyingRate, sellingRate));
            }
        } catch (IOException e) {
            logger.error("Error reading exchange rates from file", e);
        }
        logger.info(exchangeRates);
        return exchangeRates;
    }

    public void saveExchangeRates(List<ExchangeRate> exchangeRates) {
        ensureFileExists();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (ExchangeRate rate : exchangeRates) {
                bw.write(rate.getTargetCurrency() + "," + rate.getDate() + "," + rate.getBuyingRate() + "," + rate.getSellingRate());
                bw.newLine();
            }
        } catch (IOException e) {
            logger.error("Error writing exchange rates to file", e);
        }
    }

    private void ensureFileExists() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                logger.info("File does not exist: " + file.getAbsolutePath());
                File parentDir = file.getParentFile();
                if (!parentDir.exists()) {
                    parentDir.mkdirs();
                }
                file.createNewFile();
                String filePath = file.getAbsolutePath();
                logger.info(filePath+" file path");
            } else {
                logger.info("File exists: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            logger.error("Error ensuring file exists", e);
        }
    }

    public void addExchangeRate(ExchangeRate exchangeRate) {
        List<ExchangeRate> exchangeRates = getAllExchangeRates();
        exchangeRates.add(exchangeRate);
        saveExchangeRates(exchangeRates);
    }

    public void updateExchangeRate(ExchangeRate exchangeRate) {
        List<ExchangeRate> exchangeRates = getAllExchangeRates();
        for (ExchangeRate rate : exchangeRates) {
            if (rate.getTargetCurrency().equals(exchangeRate.getTargetCurrency()) && rate.getDate().equals(exchangeRate.getDate())) {
                rate.setBuyingRate(exchangeRate.getBuyingRate());
                rate.setSellingRate(exchangeRate.getSellingRate());
                break;
            }
        }
        saveExchangeRates(exchangeRates);
    }

    public void deleteExchangeRate(String currency, String date) {
        List<ExchangeRate> exchangeRates = getAllExchangeRates();
        exchangeRates.removeIf(rate -> rate.getTargetCurrency().equals(currency) && rate.getDate().equals(date));
        saveExchangeRates(exchangeRates);
    }
}
