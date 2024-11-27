package com.secondminiproject.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.secondminiproject.bindingclasses.LogingForm;
import com.secondminiproject.bindingclasses.SignupForm;
import com.secondminiproject.bindingclasses.UnlockForm;
import com.secondminiproject.constants.AppConstants;
import com.secondminiproject.entities.User;
import com.secondminiproject.repositories.UserRepository;
import com.secondminiproject.utils.EmailUtil;
import com.secondminiproject.utils.PwdUtil;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private HttpSession session;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailUtil emailUtil;

	@Override
	public String login(LogingForm form) {
		User user = this.userRepository.findByEmailAndPwd(form.getUsername(), form.getPassword());

		if (user == null) {
			return AppConstants.STR_INVALID_CREDENTIALS;
		}

		if (user.getAccStatus().equals(AppConstants.STR_LOCKED)) {
			return AppConstants.STR_YOUR_ACC_LOCKED_UNLOCK_THROUGH_EMAIL;
		}
		
		//login success we storing the user information in session
		session.setAttribute(AppConstants.STR_USERID, user.getUserId());

		return AppConstants.STR_SUCCESS;
	}

	@Override
	public boolean signup(SignupForm form) {

		User user1 = this.userRepository.findByEmail(form.getEmail());
		if (user1 != null) {
			return true;
		}
		// todo : map signup form data into user object
		User user = new User();
		BeanUtils.copyProperties(form, user);

//		todo: generate random password in java and set to object
		String tempPwd = PwdUtil.generateRadomPwd();
		user.setPwd(tempPwd);

		// todo: set user status as a unlock
		user.setAccStatus(AppConstants.STR_LOCKED);

		// todo: save the user object in the db
		this.userRepository.save(user);

		// todo: sent mail to user for unlock the account
		String to = form.getEmail();
		String subject = AppConstants.STR_UNLOCK_YOUR_ACC;
		StringBuilder body = new StringBuilder("");

		body.append("<h1> Use below temporary password to unlock your account<h2>");

		body.append("Temporary passsword : " + tempPwd);

		body.append("<br>");

		body.append("<a href=\"http://localhost:8888/unlock?email=" + to + "\">Cliic here to unlock your account");

		boolean sendUnlockLink = this.emailUtil.sendUnlockLink(to, body.toString(), subject);

		return sendUnlockLink;
	}

	@Override
	public boolean unlockAccount(UnlockForm form) {
		User user = this.userRepository.findByEmail(form.getEmail());

		if (user.getPwd().equals(form.getTempPassword())) {

			user.setPwd(form.getConfirmPassword());
			user.setAccStatus(AppConstants.STR_UNLOCKED);
			this.userRepository.save(user);
			return true;
		}
		return false;
	}

	@Override
	public boolean recoverPassword(String email) {
		// we need to check email is exists in database or not
		User user = this.userRepository.findByEmail(email);

		// check the user data if null return fasle
		if (user == null) {
			return false;
		}

		// user is not null send password to email
		String subject = AppConstants.STR_PASS_RECOVERY;
		String body = AppConstants.STR_YOUR_PASS + user.getPwd();
		this.emailUtil.sendUnlockLink(email, body, subject);

		return true;
	}

}
