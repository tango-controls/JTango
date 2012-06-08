//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  Java code to define Polled Ring bubber.
//				Command result or attribute values are stored in this
//				buffer manages as a ring buffer.
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 3.4  2005/12/02 09:55:46  pascal_verdier
// java import have been optimized.
//
// Revision 3.3  2005/02/02 08:54:05  pascal_verdier
// Idem for get_cmd_history().
//
// Revision 3.2  2005/02/02 08:51:40  pascal_verdier
// Bug on get_attr_history() fixed.
//
// Revision 3.1  2004/05/14 13:47:58  pascal_verdier
// Compatibility with Tango-2.2.0 cpp
// (polling commands and attibites).
//
//
// Copyleft 2000 by European Synchrotron Radiation Facility, Grenoble, France
//-======================================================================


package fr.esrf.TangoDs;

/**
 * Java code to define Polled Ring buffer.
 *	Command result or attribute values are stored in this
 *	buffer manages as a ring buffer.
 *
 * @author	$Author$
 * @version	$Revision$
 */

import fr.esrf.Tango.*;
import org.omg.CORBA.Any;

import java.util.Date;
import java.util.Vector;

public class PollRing extends Vector implements TangoConst
{
	
	private int		max_elt;

	
//==========================================================================
/**
 *	Constructor for the PollRing class.
 *	It does not take any argument and construct a black box
 *	with the default depth.
 */
//==========================================================================
	PollRing()
	{
		super();
		max_elt = Tango_DefaultPollRingDepth;
	}
//==========================================================================
/**
 *	Constructor for the PollRing class.
 *	It creates a black box with a depth defined by input argument.
 *
 * @param	max_size	The black box depth
 */
//==========================================================================
	PollRing(int max_size)
	{
		super();
		if (max_size<=0)
			max_elt = Tango_DefaultPollRingDepth;
		else
			max_elt = max_size;
	}
//==========================================================================
/**
 *	This method insert a new element in the ring buffer
 *	when its real data
 *
 * @param	any	The Any returned by the command
 * @param	t	the date
 */
//==========================================================================
	void insert_data(Any any, TimeVal t)
	{
		if (size()>=max_elt)
			remove(0);
		add(new RingElt(any, t));
	}

//==========================================================================
/**
 *	This method insert a new element in the ring buffer
 *	when its real data
 *
 * @param	attr_val	the attribute returned by read_attribute.
 * @param	t	the date
 */
//==========================================================================
	void insert_data(AttributeValue attr_val, TimeVal t)
	{
		if (size()>=max_elt)
			remove(0);
		add(new RingElt(attr_val, t));
		/*
		for (int i=0 ; i<size() ; i++)
			System.out.println(elementAt(i).toString());
		*/
	}

//==========================================================================
/**
 *	This method insert a new element in the ring buffer
 *	when it is an exception.
 *
 * @param	ex	The exception to be stored
 * @param	t	the date
 */
//==========================================================================
	void insert_except(DevFailed ex, TimeVal t)
	{
		if (size()>=max_elt)
			remove(0);
		add(new RingElt(ex, t));
	}
//==========================================================================
/**
 *	This method computes the delta time between records
 *	in the ring buffer
 *
 *	@param	nb	The number of delta t to be computed
 */
//==========================================================================
	double[] get_delta_t(int nb) throws DevFailed
	{
		// Throw exception if nothing in ring
		if (size() < 2)
		{
			Except.throw_exception("API_PollRingBufferEmpty",
				        "Not enough data stored yet in polling ring buffer",
				        "PollRing.get_delta_t");
		}

		// Compute how many delta can be computed
		if (nb >= size())
			nb = size() - 1;
		double[]	result = new double[nb];
		int	start = size() - nb - 1;
		if (start<0)
			start = 0;

		// The delta t computing loop
		for (int i=start, idx=0 ; i<size()-1 ; i++, idx++)
		{
			//	Get concerned  elements
			RingElt	ref = (RingElt)elementAt(i+1);
			RingElt	prev = (RingElt)elementAt(i);

			//	Get tim,e as double
			double t_ref  = (double)ref.when.tv_sec  +
						((double)ref.when.tv_usec / 1000000);
			double t_prev = (double)prev.when.tv_sec +
						((double)prev.when.tv_usec / 1000000);

			//	Get difference
			result[idx] = t_ref - t_prev;
		}
		return result;
	}
//==========================================================================
/**
 *	This method returns the date of the last insert in the
 *	ring buffer
 */
//==========================================================================
	TimeVal get_last_insert_date()
	{
		return get_last_element().when;
	}
//==========================================================================
/**
 *	This method returns a boolean set to true if the last
 *	data recorded into the ring buffer was an exception
 */
//==========================================================================
	boolean is_last_an_error()
	{
		return (get_last_element().except != null);
	}
//==========================================================================
/**
 *	This method returns the exception recently stored in
 *	the ring buffer.
 */
//==========================================================================
	DevFailed get_last_except()
	{
		return get_last_element().except;
	}
//==========================================================================
/**
 *	This method returns the command result recently stored in
 *	the ring buffer.
 */
//==========================================================================
	Any get_last_cmd_result() throws DevFailed
	{
		RingElt	record = get_last_element();
		if (record.except == null)
			return record.cmd_result;//.extract_any();
		else
			throw record.except;
	}
//==========================================================================
/**
 *	This method returns the read attribute result recently stored in
 *	the ring buffer.
 */
//==========================================================================
	AttributeValue get_last_attr_value() throws DevFailed
	{
		RingElt	record = get_last_element();
		if (record.except == null)
			return record.attr_value;
		else
			throw record.except;
	}
//==========================================================================
/**
 *	This method get command history from the ring buffer
 *
 *	@param	nb	record number
 *	@return the sequence where command result is stored
 */
//==========================================================================
	DevCmdHistory[] get_cmd_history(int nb)
	{
		DevCmdHistory[]	histo = new DevCmdHistory[nb];
		int	start = size() - nb;
		for (int i=start ; i<size() ; i++)
		{
			RingElt	record = (RingElt)elementAt(i);
			TimeVal time = record.when;
			boolean cmd_failed;
			Any value;
			DevError[] errors;
			value  = record.cmd_result;
			if (record.except==null)
			{
				cmd_failed = false;
				errors = new DevError[0];
			}
			else
			{
				cmd_failed = true;
				errors = record.except.errors;
			}
			histo[i-start] = new DevCmdHistory(time, cmd_failed, value, errors);
		}
		return histo;
	}
//==========================================================================
/**
 *	This method get attribute history from the ring buffer
 *
 *	@param	nb		Record number
 *	@param	type	The attribute data type
 *	@return the sequence where attribute result is stored
 */
//==========================================================================
	DevAttrHistory[] get_attr_history(int nb, int type)
	{
		DevAttrHistory[]	histo = new DevAttrHistory[nb];
		int	start = size() - nb;
		for(int i=start ; i<size() ; i++)
		{
			RingElt	record = (RingElt)elementAt(i);
			boolean attr_failed;
			AttributeValue value;
			DevError[] errors;
			value  = record.attr_value;
			if (record.except==null)
			{
				attr_failed = false;
				errors = new DevError[0];
			}
			else
			{
				attr_failed = true;
				errors = record.except.errors;
			}
			histo[i-start] = new DevAttrHistory(attr_failed, value, errors);
		}
		return histo;
	}

	//===============================================================
	//===============================================================
	private RingElt get_last_element()
	{
			return (RingElt)lastElement();
	}
	//===============================================================
	//===============================================================
	boolean is_empty()
	{
		return (size() == 0);
	}


//=============================================================================
//	The ring element definition class
//=============================================================================
	class RingElt
	{
		Any			cmd_result;
		DevFailed	except;
		TimeVal		when;
		AttributeValue	attr_value;
		//==========================================================================
		/**
		 *	Constructor for the RingElt class.
		 *	This constructor simply set the internal value to their default
		 */
		//==========================================================================
		public RingElt()
		{
			when = new TimeVal(0,0,0);;
			cmd_result = null;
			attr_value = null;
			except = null;
		}
		//==========================================================================
		/**
		 *	Constructor for the RingElt class with command.
		 * @param	any	The Any returned by the command
		 * @param	t	the date
		 */
		//==========================================================================
		public RingElt(Any any, TimeVal t)
		{
			when = t;
			when.tv_sec += Tango_DELTA_T;
			cmd_result = any;
			attr_value = null;
			except = null;
		}
		//==========================================================================
		/**
		 *	Constructor for the RingElt class with attribute value.
		 * @param	attr_val	the attribute returned by read_attribute.
		 * @param	t	the date
		 */
		//==========================================================================
		public RingElt(AttributeValue attr_val, TimeVal t)
		{
			when = t;
			when.tv_sec += Tango_DELTA_T;
			cmd_result = null;
			attr_value = attr_val;
			attr_value.time = when;
			except = null;
		}
		//==========================================================================
		/**
		 *	Constructor for the RingElt class with attribute value.
		 * @param	ex	The exception to be stored
		 * @param	t	the date
		 */
		//==========================================================================
		public RingElt(DevFailed ex, TimeVal t)
		{
			when = t;
			when.tv_sec += Tango_DELTA_T;
			cmd_result = null;
			try {
				cmd_result = fr.esrf.TangoApi.ApiUtil.get_orb().create_any();
			}
			catch(DevFailed e) {}
			attr_value = new AttributeValue(
						cmd_result, AttrQuality.ATTR_INVALID, when, "", 0, 0);
			except = ex;
		}
		//==========================================================================
		//==========================================================================
		public String toString()
		{
			long	t0 = (long)(when.tv_sec + Tango_DELTA_T)*1000 + when.tv_usec/1000;
			Date	date = new Date(t0);
			return "	" + date.toString();
		}
	}
}
