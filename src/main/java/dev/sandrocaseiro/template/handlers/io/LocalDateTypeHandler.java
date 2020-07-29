package dev.sandrocaseiro.template.handlers.io;

import dev.sandrocaseiro.template.utils.DateUtil;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import org.beanio.types.ConfigurableTypeHandler;
import org.beanio.types.TypeHandler;

import java.time.LocalDate;
import java.util.Properties;

public class LocalDateTypeHandler implements ConfigurableTypeHandler, Cloneable {
    @Getter @Setter
    private String format = "ddMMyyyy";

    @Override
    public Object parse(String s) {
        if (Strings.isNullOrEmpty(s))
            return null;

        return DateUtil.toDate(s, format);
    }

    @Override
    public String format(Object o) {
        if (o != null)
            return DateUtil.toString((LocalDate) o, format);

        return null;
    }

    @Override
    public Class<?> getType() {
        return LocalDate.class;
    }

    @Override
    public TypeHandler newInstance(Properties properties) {
        String customFormat = properties.getProperty("format");
        if (Strings.isNullOrEmpty(customFormat))
            return this;

        try {
            LocalDateTypeHandler handler = (LocalDateTypeHandler) this.clone();
            handler.setFormat(customFormat);

            return handler;
        }
        catch (CloneNotSupportedException e) {
            throw new IllegalArgumentException("Invalid date format pattern '" + customFormat + "': " + e.getMessage(), e);
        }
    }
}
