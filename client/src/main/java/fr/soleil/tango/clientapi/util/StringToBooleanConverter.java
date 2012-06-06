package fr.soleil.tango.clientapi.util;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.type.TypeReference;

/**
 * Convert "0" or "1" to a boolean
 * 
 * @author ABEILLE
 * 
 */
public final class StringToBooleanConverter implements IConverter {

    @Override
    public Object convert(final ConversionContext context, final Object sourceObject,
	    final TypeReference<?> destinationType) throws ConverterException {
	Object result;
	final String string = (String) sourceObject;
	if (string.equalsIgnoreCase("0")) {
	    result = false;
	} else {
	    result = true;
	}
	return result;
    }

    @Override
    public boolean canHandle(final ConversionContext context, final Object sourceObject,
	    final TypeReference<?> destinationType) {
	return (destinationType.isType(Boolean.class) || destinationType.isType(boolean.class))
		&& sourceObject instanceof String;
    }

}
