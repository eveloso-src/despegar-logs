package com.despegar.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.despegar.model.LogLine;
import com.despegar.util.Constants;

@Component
public class LogReader {

	Logger log = org.slf4j.LoggerFactory.getLogger(LogReader.class);

	private String file2Read;

	public String getFile2Read() {
		return file2Read;
	}

	public void setFile2Read(String file2Read) {
		this.file2Read = file2Read;
	}



	public LogLine createLogFromInput(String line) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		StringTokenizer st;
		StringTokenizer sourceTok;
		st = new StringTokenizer(line, Constants.BLANK);

		Date start = sdf.parse(st.nextToken());
		Date end = sdf.parse(st.nextToken());
		String traceId = st.nextToken();
		String service = st.nextToken();
		String source = st.nextToken();
		sourceTok = new StringTokenizer(source, Constants.ARROW);
		source = sourceTok.nextToken();
		String destination = sourceTok.nextToken();

		// create lines
		LogLine logLine = new LogLine();
		logLine.setStart(start);
		logLine.setEnd(end);
		logLine.setTraceId(traceId);
		logLine.setService(service);
		logLine.setSource(source);
		logLine.setSpan(destination);
		logLine.setDestination(destination);
		return logLine;
	}

}
