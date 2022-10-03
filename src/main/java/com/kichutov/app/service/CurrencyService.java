package com.kichutov.app.service;

import com.kichutov.app.model.Currency;
import com.kichutov.app.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository){
        this.currencyRepository = currencyRepository;
        updateCurrencyData();
    };

    public void updateCurrencyData() {

        String url = "https://www.cbr.ru/scripts/XML_daily.asp";
        Document document;

        try (InputStream inputStream = new URL(url).openStream()) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.parse(inputStream);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
            return;
        }

        // get node by every currency
        NodeList nodeList = document.getFirstChild().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Currency currency = new Currency();
            String id = nodeList.item(i).getAttributes().getNamedItem("ID").getNodeValue();
            currency.setId(id);

            NodeList nodeList2 = nodeList.item(i).getChildNodes();

            for (int j = 0; j < nodeList2.getLength(); j++) {

                String nodeName = nodeList2.item(j).getNodeName();
                String nodeValue = nodeList2.item(j).getTextContent();

                if (nodeName.equals("NumCode")) {
                    currency.setNumCode(Integer.valueOf(nodeValue));
                } else if (nodeName.equals("CharCode")) {
                    currency.setCharCode(nodeValue);
                } else if (nodeName.equals("Nominal")) {
                    currency.setNominal(new BigDecimal(nodeValue));
                } else if (nodeName.equals("Name")) {
                    currency.setName(nodeValue);
                } else if (nodeName.equals("Value")) {
                    currency.setValue(new BigDecimal(nodeValue.replace(",",".")));
                }

            }
            currency.setUpdateDate(LocalDate.now());
            currencyRepository.save(currency);
        }
    }
}
