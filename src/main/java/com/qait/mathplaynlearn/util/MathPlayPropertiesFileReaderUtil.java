package com.qait.mathplaynlearn.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.qait.mathplaynlearn.exception.MathPlayNLearnException;



/**
 * This class defines a utility to read the property file of constants.
 */
public class MathPlayPropertiesFileReaderUtil {

	private static final Logger logger = Logger
			.getLogger(MathPlayPropertiesFileReaderUtil.class);
	private static Properties properties = System.getProperties();

	
	public static String getPropertyValue(String key) {
		try {
			properties.load(MathPlayPropertiesFileReaderUtil.class.getClassLoader()
					.getResourceAsStream("properties/message.properties"));
		} catch (FileNotFoundException e) {
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
			throw new MathPlayNLearnException();
		} catch (IOException e) {
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
			throw new MathPlayNLearnException();
		}
		return properties.getProperty(key) != null ? properties
				.getProperty(key).trim() : "";
	}



}
