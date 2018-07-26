package fr.soleil.tango.clientapi.factory;

import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.TangoConst;
import fr.soleil.tango.clientapi.attribute.AttributeValueType;
import fr.soleil.tango.clientapi.attribute.IExtractor;
import fr.soleil.tango.clientapi.attribute.IInserter;
import fr.soleil.tango.clientapi.command.CommandType;
import fr.soleil.tango.clientapi.command.ICommandExtractor;
import fr.soleil.tango.clientapi.command.ICommandInserter;

public final class InsertExtractFactory {

    private static final String TANGO_WRONG_DATA_ERROR = "TANGO_WRONG_DATA_ERROR";

    private InsertExtractFactory() {
    }

    public static IExtractor getAttributeExtractor(final int dataType) throws DevFailed {
	IExtractor extractor = AttributeValueType.getExtractorFromDataType(dataType);
	if (extractor == null) {
	    // send an extractor anyway to be able to extract errors.
	    extractor = AttributeValueType.getExtractorFromDataType(TangoConst.Tango_DEV_DOUBLE);
	    // throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR,
	    // "attribute type not supported " + dataType);
	}
	return extractor;
    }

    public static IInserter getAttributeInserter(final int dataType) throws DevFailed {
	IInserter inserter = AttributeValueType.getInserterFromDataType(dataType);
	if (inserter == null) {
	    // send an extractor anyway to be able to extract errors.
	    inserter = AttributeValueType.getInserterFromDataType(TangoConst.Tango_DEV_DOUBLE);
	    // throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR,
	    // "attribute type not supported " + dataType);
	}
	return inserter;
    }

    public static ICommandExtractor getCommandExtractor(final int dataType) throws DevFailed {
	final ICommandExtractor extractor = CommandType.getExtractorFromDataType(dataType);
	if (extractor == null) {
	    throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, "command type not supported " + dataType);
	}
	return extractor;
    }

    public static ICommandInserter getCommandInserter(final int dataType) throws DevFailed {
	final ICommandInserter inserter = CommandType.getInserterFromDataType(dataType);
	if (inserter == null) {
	    throw DevFailedUtils.newDevFailed(TANGO_WRONG_DATA_ERROR, "command type not supported " + dataType);
	}
	return inserter;
    }
}
