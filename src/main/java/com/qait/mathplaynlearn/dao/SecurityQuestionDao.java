package com.qait.mathplaynlearn.dao;

import java.util.List;

import com.qait.mathplaynlearn.domain.SecurityQuestion;

public interface SecurityQuestionDao extends GenericDao<SecurityQuestion, Long>{

	public List<SecurityQuestion> getAllQuestions();
	
	public SecurityQuestion getQuestionById(Long questionId);
}
