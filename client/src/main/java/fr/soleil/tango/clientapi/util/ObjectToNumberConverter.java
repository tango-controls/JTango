package fr.soleil.tango.clientapi.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.type.TypeReference;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * convert an object to any number. Force conversion for all types (eg double to int)
 *
 * @author ABEILLE
 *
 */
public final class ObjectToNumberConverter implements IConverter {

    private static final Class<?>[] BYTE_TYPE = new Class<?>[] { Byte.TYPE, Byte.class };
    private static final Class<?>[] DOUBLE_TYPE = new Class<?>[] { Double.TYPE, Double.class };
    private static final Class<?>[] FLOAT_TYPE = new Class<?>[] { Float.TYPE, Float.class };
    private static final Class<?>[] INT_TYPE = new Class<?>[] { Integer.TYPE, Integer.class };
    private static final Class<?>[] LONG_TYPE = new Class<?>[] { Long.TYPE, Long.class };
    private static final Class<?>[] SHORT_TYPE = new Class<?>[] { Short.TYPE, Short.class };

    private final Logger logger = LoggerFactory.getLogger(ObjectToNumberConverter.class);

    @Override
    public Object convert(final ConversionContext context, final Object sourceObject,
            final TypeReference<?> destinationType) throws ConverterException {
        double value;
        if (Boolean.class.isAssignableFrom(sourceObject.getClass())) {
            value = getDouble((Boolean) sourceObject);
        } else {
            value = getDouble(sourceObject.toString());
        }

        final Object result = getResult(sourceObject, destinationType, value);
        if (result != null) {
            return result;
        } else {
            throw new ConverterException("Could not convert");
        }
    }

    private Object getResult(final Object sourceObject, final TypeReference<?> destinationType, final double value)
            throws ConverterException {
        Object result = null;
        try {
            if (ArrayUtils.contains(BYTE_TYPE, destinationType.getRawType())) {
                result = (byte) value;
            } else if (ArrayUtils.contains(DOUBLE_TYPE, destinationType.getRawType())) {
                result = value;
            } else if (ArrayUtils.contains(FLOAT_TYPE, destinationType.getRawType())) {
                result = new Float(value);
            } else if (ArrayUtils.contains(INT_TYPE, destinationType.getRawType())) {
                result = (int) value;
            } else if (ArrayUtils.contains(LONG_TYPE, destinationType.getRawType())) {
                result = (long) value;
            } else if (ArrayUtils.contains(SHORT_TYPE, destinationType.getRawType())) {
                result = (short) value;
            } else if (destinationType.hasRawType(BigInteger.class)) {
                result = new BigInteger(Integer.toString((int) value));
            } else if (destinationType.hasRawType(BigDecimal.class)) {
                result = new BigDecimal(value);
            }
        } catch (final NumberFormatException e) {
            logger.error("error", e);
            throw new ConverterException(MessageFormat.format(
                    "Could not convert from ''{0}'' to object with type signature ''{1}''", sourceObject,
                    destinationType.toString()), e);
        }
        return result;
    }

    private double getDouble(final Boolean bool) {
        double value;
        if (bool) {
            value = 1;
        } else {
            value = 0;
        }
        return value;
    }

    private double getDouble(final String stringVal) {
        double value;
        if (stringVal.equalsIgnoreCase("true")) {
            value = 1;
        } else if (stringVal.equalsIgnoreCase("false")) {
            value = 0;
        } else if (stringVal.isEmpty()) {
            value = Double.NaN;
        } else {
            value = Double.valueOf(stringVal);
        }
        return value;
    }

    @Override
    public boolean canHandle(final ConversionContext context, final Object sourceObject,
            final TypeReference<?> destinationType) {
        return destinationType.isNumber()
                && (Number.class.isAssignableFrom(sourceObject.getClass())
                        || Boolean.class.isAssignableFrom(sourceObject.getClass()) || String.class
                            .isAssignableFrom(sourceObject.getClass()));
    }
}
