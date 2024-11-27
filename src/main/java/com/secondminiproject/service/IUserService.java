package com.secondminiproject.service;

import java.util.List;

import com.secondminiproject.bindingclasses.LogingForm;
import com.secondminiproject.bindingclasses.SignupForm;
import com.secondminiproject.bindingclasses.UnlockForm;
import com.secondminiproject.entities.User;

public interface IUserService {

	public String login(LogingForm form);

	public boolean signup(SignupForm form);

	public boolean unlockAccount(UnlockForm form);



	public boolean recoverPassword(String email);

}
