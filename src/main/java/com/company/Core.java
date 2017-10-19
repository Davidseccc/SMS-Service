package com.company;

import com.company.modules.SmsService.model.PDU;
import com.company.modules.SmsService.model.SMS;
import com.company.modules.SmsService.model.SMS_Constants;
import com.company.modules.SmsService.utils.*;
import com.company.modules.SmsService.SmsEvent;
import com.company.modules.SmsService.SmsService;
import com.company.modules.SmsService.iface.SmsEventListener;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Honza on 12. 4. 2017.
 */
public class Core {

    XMLConfiguration xml;
    XMLConfiguration localeXml;
    SmsService smsService;
    ClassLoader classloader;

    /**
     * Creates new Core instance. Constructor makes all surrounding modules instances and prepares all routes
     */
    public Core() {
        try {
            classloader = Thread.currentThread().getContextClassLoader();
            URL cfg = classloader.getResource("config.xml");
            Configurations config = new Configurations();
            xml = config.xml(cfg);
            System.out.println("Config ready");
            createInstances();
            createListeners();

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prepares all necessary instances for modules
     */
    private void createInstances() {

        System.out.println("Creating instances");
        String port = xml.getString("modem.comport");
        String[] numbers = xml.getStringArray("nummers.number");
        SmsService smsService = new SmsService(port);
        smsService.setNumbers(numbers);
        smsService.setDevicePort(port);
        String stringLocale = xml.getString("language.localeFile");

        if (classloader != null) {
            classloader = Thread.currentThread().getContextClassLoader();
            URL messageStrings = classloader.getResource(stringLocale);
            Configurations config = new Configurations();
            try {
                localeXml = config.xml(messageStrings);
            } catch (ConfigurationException e) {
                e.printStackTrace();
            }

            MessageStrings.strings.put("MESSAGE_SEND", localeXml.getString("sms-message.received"));
            MessageStrings.strings.put("MESSAGE_RECEIVED", localeXml.getString("sms-message.send"));
            MessageStrings.strings.put("TEST", localeXml.getString("sms-message.TEST"));

            SmsService event = new SmsService(port);
            SmsEvent e = new SmsEvent(SmsEvent.SmsEventType.OPEN_PORT,null, null);
            event.propagateEvent(e);
            List<SMS> smsList = new ArrayList<>();
            smsList.add(new SMS(numbers[0], MessageStrings.strings.get("TEST")));  // now send SMS only to first number
            e = new SmsEvent(SmsEvent.SmsEventType.MESSAGE_SEND,"SEND_SMS", smsList);
            event.propagateEvent(e);
            System.out.println("SMS SENDED");
            //e = new SmsEvent(SmsEvent.SmsEventType.LIST_SMS,"LIST SMS", SMS_Constants.STAT_ALL);
            //event.propagateEvent(e);
            e = new SmsEvent(SmsEvent.SmsEventType.CLOSE_PORT,null, null);
            //event.propagateEvent(e);
        }
    }

    /**
     * Prepares all listeners for instantiated modules
     */
    private void createListeners() {
        if (smsService != null) {
            smsService.addEventListener(new SmsEventListener() {
                @Override
                public void onSmsEvent(SmsEvent e) {
                    if (e.getEventType() == SmsEvent.SmsEventType.MESSAGE_ARRIVED) {
                        //todo: DO something.
                    }
                    if (e.getEventType() == SmsEvent.SmsEventType.CREDIT_LOW) {
                        //todo: DO something else.
                    }
                    if (e.getEventType() == SmsEvent.SmsEventType.NO_SIGNAL) {
                        //todo: DO something else.
                    }
                    if (e.getEventType() == SmsEvent.SmsEventType.SEND_FAILED) {
                        //todo: DO something else.
                    }
                }

                @Override
                public void onMessageReceived(SmsEvent e) {

                }

                @Override
                public void onMessageSent(SmsEvent e) {

                }
            });

        }


    }

}
