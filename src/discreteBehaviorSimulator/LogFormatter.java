package discreteBehaviorSimulator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * <p>This class is used to format a log record in a more human readable way</p>
 * @author Flavien Vernier
 * @see Formatter
 */
public class LogFormatter extends Formatter {

	/**
	 * <p>Constructor of class {@link LogFormatter}</p>
	 * @param rec log record to format
	 * @return string formatted log record as a {@link StringBuffer}
	 */
	public String format(LogRecord rec) {
		StringBuffer buf = new StringBuffer();
		
		buf.append(calcDate(rec.getMillis()));
		buf.append(": ");
		buf.append(rec.getLevel());
		buf.append(System.getProperty("line.separator"));
		buf.append(formatMessage(rec));
		buf.append(System.getProperty("line.separator"));
		
		return buf.toString();
	}

	/**
	 * Transform timestamp into a more meaningful string date
	 * @param millisecs timestamp in milliseconds
	 * @return string formatted date
	 */
	private String calcDate(long millisecs) {
	    SimpleDateFormat date_format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SS");
	    Date resultdate = new Date(millisecs);
	    return date_format.format(resultdate);
	  }

	  /**
	   * <p>Return the header string for a set of formatted records</p>
	   * @see Formatter#getHead(Handler)
	   */
	  public String getHead(Handler h) {
		  return "";
	  }
	  
	  /**
	   * <p>Return the tail string for a set of formatted records</p>
	   * @see Formatter#getTail(Handler)
	   */
	  public String getTail(Handler h) {
	    return "";
	  }

}
