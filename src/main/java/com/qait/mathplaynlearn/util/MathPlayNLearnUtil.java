package com.qait.mathplaynlearn.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.relayrides.pushy.apns.ApnsEnvironment;
import com.relayrides.pushy.apns.PushManager;
import com.relayrides.pushy.apns.PushManagerConfiguration;
import com.relayrides.pushy.apns.util.ApnsPayloadBuilder;
import com.relayrides.pushy.apns.util.MalformedTokenStringException;
import com.relayrides.pushy.apns.util.SSLContextUtil;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import com.relayrides.pushy.apns.util.TokenUtil;

public class MathPlayNLearnUtil {

	private static final Logger logger = Logger.getLogger(MathPlayNLearnUtil.class);

	/*
	 * Method returns the stack trace of exception in string format. Used for
	 * logging of exception.
	 */
	public static String getExceptionDescriptionString(Exception e) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		return stringWriter.toString();
	}
	
	/**
	 * Method to send push notifications
	 * @param certificateFilePath
	 * @param password
	 * @param deviceToken
	 * @param alertMessage
	 */
	public static void sendNotification(String certificateFilePath, String password, String deviceToken, String alertMessage)
			throws UnrecoverableKeyException, KeyManagementException,
			KeyStoreException, NoSuchAlgorithmException, CertificateException,
			IOException, MalformedTokenStringException, InterruptedException {
		
		final PushManager<SimpleApnsPushNotification> pushManager = new PushManager<SimpleApnsPushNotification>(
				ApnsEnvironment.getSandboxEnvironment(),
				SSLContextUtil.createDefaultSSLContext(certificateFilePath, password), null, null, null,
				new PushManagerConfiguration(), "Friend Request");

		pushManager.start();

		final byte[] token = TokenUtil.tokenStringToByteArray(deviceToken);

		final ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();
		payloadBuilder.setAlertBody(alertMessage);

		final String payload = payloadBuilder.buildWithDefaultMaximumLength();
		pushManager.getQueue().put(new SimpleApnsPushNotification(token, payload));

		pushManager.shutdown();
	}
}
