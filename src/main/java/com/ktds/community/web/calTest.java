package com.ktds.community.web;

import java.util.Calendar;

public class calTest {
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		System.out.println(currentYear);
	}
}
