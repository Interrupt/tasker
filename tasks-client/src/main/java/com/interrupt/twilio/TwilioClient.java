package com.interrupt.twilio;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Account;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TwilioClient {
    // TODO: Get API keys out of a properties file, instead of hard coding them
    final TwilioRestClient client = new TwilioRestClient("AC3c19e7ddc19aa7f5d2d38d732d258fc0", "7302f064596c0c2b5aeee47d2439ed8a");
    final Logger logger = Logger.getLogger(TwilioClient.class.getName());

    /**
     * Sends a text message to the given number
     * @param number
     * @param message
     */
    public void sendText(String number, String message) {
        final Account mainAccount = client.getAccount();
        final SmsFactory messageFactory = mainAccount.getSmsFactory();

        final List<NameValuePair> messageParams = new ArrayList<>();
        messageParams.add(new BasicNameValuePair("To", number));
        messageParams.add(new BasicNameValuePair("From", "(201) 335-8694"));
        messageParams.add(new BasicNameValuePair("Body", message));

        try {
            messageFactory.create(messageParams);
        } catch (TwilioRestException e) {
            logger.severe(e.getErrorMessage());
        }
    }
}
