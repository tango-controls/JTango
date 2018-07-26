/*
 * Created on 18 Jan 2007
 * with Eclipse
 */
package org.tango.script.evalution;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

/**
 * @author HARDION
 * @version
 *
 *          Evaluate a boolean expression (result can be only true or false)
 *
 */
public final class BooleanExpressionValidator {

    private static Logger logger = LoggerFactory.getLogger(BooleanExpressionValidator.class);

    private static final String RESULT_VARIABLE = "context";

    private final List<IContextVariable> variables;

    private final String contextCondition;

    public BooleanExpressionValidator(final String contextCondition, final List<IContextVariable> variables) {
        this.contextCondition = contextCondition;
        this.variables = new ArrayList<IContextVariable>(variables);
    }

    public boolean isExpressionTrue() throws DevFailed {
        boolean context = true;
        if (variables.size() > 0) {
            try {
                // IF ATTRIBUTES ARE DEFINED
                context = false;

                // Open a context and create a script with the current value of
                // variables to
                // check if all conditions is OK for a valid security context
                final Context cx = Context.enter();
                final Scriptable scope = cx.initStandardObjects();

                // Create the variable of the script
                for (final IContextVariable variable : variables) {
                    final Object value = variable.getValue();
                    final Object m = Context.javaToJS(value, scope);
                    ScriptableObject.putProperty(scope, variable.getName(), m);
                    logger.debug("put value {} for {}", value, variable);
                }
                // Create the variable context to get the result of the script
                final Object sc = Context.javaToJS(context, scope);
                ScriptableObject.putProperty(scope, RESULT_VARIABLE, sc);

                // Make the script (in javascript)
                final String scriptText = RESULT_VARIABLE + " = " + contextCondition + ";";
                logger.debug("script: {} ", scriptText);
                // Evaluate and get the result
                cx.evaluateString(scope, scriptText, "test", 1, null);
                context = (Boolean) ScriptableObject.getProperty(scope, RESULT_VARIABLE);
                logger.debug("result is {}", context);
            } catch (final EcmaError e) {
                throw DevFailedUtils.newDevFailed("EVALUATION_ERROR", e.details());
            } finally {
                Context.exit();
            }
        }
        return context;
    }

    @Override
    public String toString() {
        final ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        sb.append("contextCondition", contextCondition);
        sb.append("variables", variables);
        return sb.toString();
    }

}
