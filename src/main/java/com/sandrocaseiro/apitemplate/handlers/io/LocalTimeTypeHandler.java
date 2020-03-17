package com.sandrocaseiro.apitemplate.handlers.io;

import com.sandrocaseiro.apitemplate.utils.DateUtil;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import org.beanio.types.ConfigurableTypeHandler;
import org.beanio.types.TypeHandler;

import java.time.LocalTime;
import java.util.Properties;

public class LocalTimeTypeHandler implements ConfigurableTypeHandler, Cloneable {
	@Getter @Setter
	private String format = "ddMMyyyy";

	@Override
	public Object parse(String s) {
		if (Strings.isNullOrEmpty(s))
			return null;

		return DateUtil.toTime(s, format);
	}

	@Override
	public String format(Object o) {
		if (o != null)
			return DateUtil.toString((LocalTime)o, format);

		return null;
	}

	@Override
	public Class<?> getType() {
		return LocalTime.class;
	}

	@Override
	public TypeHandler newInstance(Properties properties) {
		String customFormat = properties.getProperty("format");
		if (Strings.isNullOrEmpty(customFormat))
			return this;

		try {
			LocalTimeTypeHandler handler = (LocalTimeTypeHandler)this.clone();
			handler.setFormat(customFormat);

			return handler;
		}
		catch (CloneNotSupportedException e) {
			throw new IllegalArgumentException("Invalid time format pattern '" + customFormat + "': " + e.getMessage(), e);
		}
	}
}
