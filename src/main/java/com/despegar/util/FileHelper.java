package com.despegar.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class FileHelper {

	@Autowired
	private static String location = "C:\\Users\\emi\\git\\despegar-logs\\src\\main\\resources\\";
	
	private static Log log = LogFactory.getLog(FileHelper.class);

	public static BufferedReader getReader(String file) {
		BufferedReader bf = null;
		try {
			FileInputStream fis = new FileInputStream(location + file);
			bf = new BufferedReader(new InputStreamReader(fis));
		} catch (FileNotFoundException e) {
			log.error("Archivo no encontrado");
		}
		return bf;
	}
}
