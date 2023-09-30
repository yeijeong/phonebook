package com.nasa.phonebook.service;

import com.nasa.phonebook.dao.PhoneBookDao;

public class PhoneBookService {
	PhoneBookDao phonebookDao;
	
//	아이디 중복 확인
	public int idCheck(String loginid) throws Exception {
		int cnt = phonebookDao.idCheck(loginid);
		System.out.println(cnt);
		return cnt;
	}
}
