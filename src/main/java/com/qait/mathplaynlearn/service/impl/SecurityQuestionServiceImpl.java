package com.qait.mathplaynlearn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qait.mathplaynlearn.dao.SecurityQuestionDao;
import com.qait.mathplaynlearn.domain.SecurityQuestion;
import com.qait.mathplaynlearn.service.SecurityQuestionService;

@Service("securityQuestionService")
public class SecurityQuestionServiceImpl implements SecurityQuestionService {
	
	@Autowired
	private SecurityQuestionDao questionDao;

	public List<SecurityQuestion> getAllQuestions() {
		return questionDao.getAllQuestions();
	}

	public SecurityQuestionDao getQuestionDao() {
		return questionDao;
	}

	public void setQuestionDao(SecurityQuestionDao questionDao) {
		this.questionDao = questionDao;
	}
	
	public SecurityQuestion getQuestionById(Long questionId) {
		return questionDao.getQuestionById(questionId);
	}
}
