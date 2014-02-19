package fr.soleil.tango.clientapi.util;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.type.TypeReference;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoDs.TangoConst;

public final class DevStateToObjectConverter implements IConverter {
    @Override
    public Object convert(final ConversionContext context, final Object sourceObject,
	    final TypeReference<?> destinationType) throws ConverterException {
	if (sourceObject == null) {
	    if (destinationType.isPrimitive()) {
		throw new ConverterException("Cannot convert null to primitive number");
	    } else {
		return null;
	    }
	}
	Object result;
	final DevState state = (DevState) sourceObject;
	if (destinationType.isType(String.class)) {
	    result = TangoConst.Tango_DevStateName[state.value()];
	} else {
	    final Transmorph transmorph = new Transmorph(new DefaultConverters());
	    result = transmorph.convert(state.value(), destinationType);
	}
	return result;
    }

    @Override
    public boolean canHandle(final ConversionContext context, final Object sourceObject,
	    final TypeReference<?> destinationType) {
	boolean canHandle = false;
	if (sourceObject instanceof DevState && (destinationType.isType(String.class) || destinationType.isNumber())) {
	    canHandle = true;
	}
	return canHandle;
    }
}
