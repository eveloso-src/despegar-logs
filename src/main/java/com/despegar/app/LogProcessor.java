package com.despegar.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.RecursiveAction;

import org.slf4j.Logger;

import com.despegar.model.LogLine;
import com.despegar.util.FileHelper;

public class LogProcessor extends RecursiveAction {

	Logger log = org.slf4j.LoggerFactory.getLogger(LogProcessor.class);

	Integer rows;

	private String file;

	public LogProcessor(String f, Integer rowCount) {
		file = f;
		rows = rowCount;
	}

	public void setFile(String f) {
		file = f;
	}

	@Override
	protected void compute() {

		List<LogLine> lines = new ArrayList<LogLine>();
		LogReader logReader = new LogReader();
		logReader.setFile2Read(file);

		BufferedReader buffReader = FileHelper.getReader(file);
		String line = "";
		LogBuilder builder = new LogBuilder();
		try {
			line = buffReader.readLine();
			LogLine logLine;
			while (line != null) {
				while ((line != null) && rows > lines.size()) {
					try {
						logLine = logReader.createLogFromInput(line);
						lines.add(logLine);

					} catch (ParseException pe) {
						log.error("Linea descartada: " + line);
					} finally {
						line = buffReader.readLine();
					}
				}
				log.info("lineas leidas: " + lines.size());
				lines = builder.printJSONTree("null", lines);
				log.info("lineas remanentes: " + lines.size());
				lines = expireLines(lines);
				log.info("lineas depuradas: " + lines.size());
			}
			buffReader.close();
		} catch (IOException e) {
			log.error("Error en lectura: " + line);
		}
	}

	private List<LogLine> expireLines(List<LogLine> lines) {

		Calendar expiration = Calendar.getInstance();
		expiration.add(Calendar.SECOND, -30 );
		List<LogLine> linesAux = new ArrayList<>();
		for (LogLine logLine : lines) {
			if (expiration.getTime().after(logLine.getStart())) {
				linesAux.add(logLine);
			}
		}
		lines.removeAll(linesAux);
		return lines;
	}
}
