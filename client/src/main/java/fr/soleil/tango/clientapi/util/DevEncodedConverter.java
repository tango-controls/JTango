package fr.soleil.tango.clientapi.util;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.type.TypeReference;
import fr.esrf.Tango.DevEncoded;

public final class DevEncodedConverter implements IConverter {

    @Override
    public Object convert(final ConversionContext context, final Object sourceObject,
	    final TypeReference<?> destinationType) throws ConverterException {
	Object result;
	if (sourceObject instanceof DevEncoded) {
	    result = sourceObject;
	} else {
	    final Transmorph transmorph = new Transmorph(new DefaultConverters());
	    if (sourceObject.getClass().isArray()) {
		result = new DevEncoded("unknown", transmorph.convert(sourceObject, byte[].class));
	    } else {
		result = new DevEncoded("unknown", new byte[] { transmorph.convert(sourceObject, byte.class) });
	    }

	}
	return result;
    }

    @Override
    public boolean canHandle(final ConversionContext context, final Object sourceObject,
	    final TypeReference<?> destinationType) {
	boolean canHandle = false;
	if (destinationType.isType(DevEncoded.class)) {
	    canHandle = true;
	}
	return canHandle;
    }

}
