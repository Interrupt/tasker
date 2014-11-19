package com.interrupt.twilio.commands;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Account;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SendTextCommand extends HystrixCommand<Boolean> {

    // TODO: Put these API keys in a properties file
    // TODO: Get these via dependency injection?
    private static TwilioRestClient client =
            new TwilioRestClient("AC3c19e7ddc19aa7f5d2d38d732d258fc0", "7302f064596c0c2b5aeee47d2439ed8a");

    final Logger logger = Logger.getLogger(SendTextCommand.class.getName());
    private String number;
    private String message;

    public SendTextCommand(String number, String message) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("Twilio")).
                andCommandPropertiesDefaults(HystrixCommandProperties.Setter().
                        withExecutionIsolationThreadTimeoutInMilliseconds(5000)));

        this.number = number;
        this.message = message;
    }

    @Override
    protected Boolean run() throws Exception {
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
            return false;
        }

        return true;
    }
}
