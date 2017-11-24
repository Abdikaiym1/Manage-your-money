package com.example.asusx555l.projecttoolbar;

import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.example.asusx555l.projecttoolbar.ui.activities.MainActivity;
import com.example.asusx555l.projecttoolbar.ui.fragmets.StatisticsPage;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class XMLParser extends AsyncTask<Object, Valute, Integer> {

    private String URLDate = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";
    public Valute[] valute = {null, null};

    @Override
    protected Integer doInBackground(Object[] params) {
        XmlPullParser receivedDate = tryDownloadingXmlDate();
        int recordsFound = tryParsingXmlDate(receivedDate);
        return recordsFound;
    }

    private int tryParsingXmlDate(XmlPullParser receivedDate) {
        if (receivedDate != null) {
            try {
                return processRecivedDate(receivedDate);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    private int processRecivedDate(XmlPullParser xmlDate) throws IOException, XmlPullParserException {
        int i = -1;
        int count = -1;
        int recordsFound = 0;

        String text = "";
        Valute curValute = null;
        while (i != XmlResourceParser.END_DOCUMENT) {
            String tagName = xmlDate.getName();

            switch (i) {
                case XmlResourceParser.START_TAG:
                    if (tagName.equals("Valute") && (Objects.equals(xmlDate.getAttributeValue(null, "ID"), "R01235") ||
                            Objects.equals(xmlDate.getAttributeValue(null, "ID"), "R01239"))) {
                        count = 1;
                        curValute = new Valute();
                    }
                    break;
                case XmlResourceParser.TEXT:
                    text = xmlDate.getText();
                    break;
                case XmlPullParser.END_TAG:
                    if (tagName.equalsIgnoreCase("CharCode") && count == 1) {
                        curValute.setCharCode(text);
                        count = 1;
                    } else if (tagName.equalsIgnoreCase("Value") && count == 1) {
                        curValute.setValue(text);
                        count = 0;
                    }
                    if (tagName.equals("Valute") && curValute != null && count == 0) {
                        recordsFound++;
                        publishProgress(curValute);
                        count = -1;
                    }
                    break;
            }
            i = xmlDate.next();
        }
        if (recordsFound == 0) {
            publishProgress();
        }
        return recordsFound;
    }

    private XmlPullParser tryDownloadingXmlDate() {
        try {
            URL xmlUrl = new URL(URLDate);
            XmlPullParser receivedDate = XmlPullParserFactory.newInstance().newPullParser();
            receivedDate.setInput(xmlUrl.openStream(), null);
            return receivedDate;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Valute... values) {
        super.onProgressUpdate(values);

        if (values.length == 1) {
            if (Objects.equals(values[0].getCharCode(), "USD")) {
                valute[0] = values[0];
            } else {
                valute[1] = values[0];
            }
        }
    }

    private SendResult sendResult;

    public interface SendResult {
        void send(Valute[] valute);
    }

    public XMLParser(SendResult sendResult, String date) {
        this.sendResult = sendResult;
        URLDate = URLDate + date;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        sendResult.send(valute);
    }
}
