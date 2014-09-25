package com.qait.mathplaynlearn.service;

import java.util.List;

import com.qait.mathplaynlearn.domain.SecurityQuestion;

public interface SecurityQuestionService {

	public List<SecurityQuestion> getAllQuestions();
	
	public SecurityQuestion getQuestionById(Long questionId);
}
