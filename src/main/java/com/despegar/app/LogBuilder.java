package com.despegar.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.despegar.model.LogLine;
import com.despegar.model.Root;
import com.despegar.model.Trace;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LogBuilder {

	Logger log = org.slf4j.LoggerFactory.getLogger(LogBuilder.class);

	private List<LogLine> tracesLeft;

	public List<LogLine> printJSONTree(String source, List<LogLine> traces) {

		List<LogLine> rootLogList = traces.stream().filter(tr -> tr.getSource().equals(source))
				.collect(Collectors.toList());

		traces.remove(rootLogList.get(0));
		tracesLeft = traces;

		List<Root> list = new ArrayList<Root>();
		for (LogLine logl : rootLogList) {
			Root root = new Root();
			root.setId(logl.getTraceId());

			Trace traceLog = new Trace();
			traceLog.setStart(logl.getStart());
			traceLog.setEnd(logl.getEnd());
			traceLog.setService(logl.getService());
			traceLog.setSpan(logl.getSpan());

			List<Trace> calls = getCalls(logl.getDestination(), traces);
			traceLog.setCalls(calls);

			root.setRoot(traceLog);
			list.add(root);
		}

		ObjectMapper mapper = new ObjectMapper();
		for (Root trace2 : list) {
			try {
				log.info(mapper.writeValueAsString(trace2));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

		return traces;
	}

	private List<Trace> getCalls(String source, List<LogLine> logs) {
		List<Trace> calls = new ArrayList<Trace>();

		List<LogLine> matchSourceList = logs.parallelStream().filter(tr -> tr.getSource().equals(source))
				.collect(Collectors.toList());

		List<LogLine> logLines = new ArrayList<LogLine>();

		for (LogLine logLine : matchSourceList) {
			logLines.add(logLine);
			List<Trace> callsMade = getCalls(logLine.getDestination(), logs);
			Trace newTrace = new Trace();
			newTrace.setStart(logLine.getStart());
			newTrace.setEnd(logLine.getEnd());
			newTrace.setService(logLine.getService());
			newTrace.setSpan(logLine.getSpan());
			newTrace.setCalls(callsMade);
			calls.add(newTrace);
		}
		tracesLeft.removeAll(logLines);
		return calls;
	}

}
