package dev.sandrocaseiro.apitemplate.handlers.io;

import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import org.beanio.types.TypeHandler;

import java.math.BigDecimal;

public class BigDecimalTypeHandler implements TypeHandler {
	@Getter @Setter
	private int decimals;

	@Override
	public Object parse(String s) {
		if (Strings.isNullOrEmpty(s))
			return BigDecimal.ZERO;

		BigDecimal valor = BigDecimal.valueOf(Long.parseLong(s));
		if (decimals > 0)
			return valor.movePointLeft(decimals);
		else
			return valor;
	}

	@Override
	public String format(Object o) {
		if (o != null)
			return o.toString();

		return "0";
	}

	@Override
	public Class<?> getType() {
		return BigDecimal.class;
	}
}
