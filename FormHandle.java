package com.quanglinhit;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormHandle {

	public HashMap<String, String> validate(String birthday, String phone) {
		HashMap<String, String> erros = new HashMap<>();

		String ptBirthday = "\\d{4}\\/\\d{2}\\/\\d{2}";
		Pattern pattern1 = Pattern.compile(ptBirthday);
		Matcher matcher1 = pattern1.matcher(birthday);
		if (!matcher1.matches()) {
			erros.put("Birthday", "Ngày sinh ko đúng định dạng!");
		}

		String ptPhone = "(\\+84|0)\\d{9,10}";
		Pattern pattern2 = Pattern.compile(ptPhone);
		Matcher matcher2 = pattern2.matcher(phone);
		if (!matcher2.matches()) {
			erros.put("Phone", "SĐT ko đúng định dạng!");
		}

		return erros;
	}
}
