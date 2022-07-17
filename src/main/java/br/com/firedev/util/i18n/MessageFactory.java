package br.com.firedev.util.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import br.com.firedev.util.ContextUtil;

public class MessageFactory {

	private static MessageSource messageSource;

	private MessageFactory() {
		throw new IllegalStateException("Utility class");
	}

	private static MessageSource getMessageSource() {
		if (messageSource == null)
			messageSource = ContextUtil.getBean("messageSource", MessageSource.class);
		return messageSource;
	}

	public static String getMessage(String message, String... args) {
		try {
			return getMessageSource().getMessage(message, args, LocaleContextHolder.getLocale());
		} catch (NoSuchMessageException e) {
			return message;
		}
	}

	public static String getMessage(Messages message, String... args) {
		return getMessage(message.toString(), args);
	}

	public static String getLabel(String label, String... args) {
		return getMessage(label, args);
	}
}
