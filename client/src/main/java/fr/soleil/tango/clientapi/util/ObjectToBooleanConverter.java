package fr.soleil.tango.clientapi.util;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.type.TypeReference;

public final class ObjectToBooleanConverter implements IConverter {

    @Override
    public Object convert(final ConversionContext context, final Object sourceObject,
	    final TypeReference<?> destinationType) throws ConverterException {

	Object result = true;
	final String stringVal = sourceObject.toString();
	if (stringVal.equalsIgnoreCase("true")) {
	    result = true;
	} else if (stringVal.equalsIgnoreCase("false")) {
	    result = false;
	} else if (stringVal.isEmpty()) {
	    result = false;
	} else {
	    double value = 0;
	    try {
		value = Double.valueOf(sourceObject.toString());
		if (value == 0) {
		    result = false;
		}
	    } catch (final NumberFormatException e) {
		result = false;
	    }

	}
	return result;
    }

    @Override
    public boolean canHandle(final ConversionContext context, final Object sourceObject,
	    final TypeReference<?> destinationType) {
	return (destinationType.isType(Boolean.class) || destinationType.isType(boolean.class))
		&& (Number.class.isAssignableFrom(sourceObject.getClass()) || String.class
			.isAssignableFrom(sourceObject.getClass()));
    }

}
