package com.secondminiproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.secondminiproject.bindingclasses.LogingForm;
import com.secondminiproject.bindingclasses.SignupForm;
import com.secondminiproject.bindingclasses.UnlockForm;
import com.secondminiproject.constants.AppConstants;
import com.secondminiproject.service.IUserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	private IUserService iUserService;

	@Autowired
	private HttpSession session;

	@Autowired
	public UserController(IUserService iUserService, HttpSession session) {
		super();
		this.iUserService = iUserService;
	}

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@PostMapping("/signup")
	public String register(@ModelAttribute("user") SignupForm form, Model model) {
		boolean signup = this.iUserService.signup(form);

		if (signup) {
			model.addAttribute("succmsg", "Your Account is created, Send link to your emil for unlock your account");

		} else {
			model.addAttribute("errmsg", "Please enter a unquie email id ");

		}

		return "signup";

	}

	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("user", new SignupForm());
		return "signup";

	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("login", new LogingForm());
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("login") LogingForm form, Model model) {
		String login = this.iUserService.login(form);
		if (login.equals(AppConstants.STR_SUCCESS)) {
			return "redirect:/dashboard";
		} else {
			model.addAttribute("errmsg", login);
		}
		return "login";
	}

	@PostMapping("/unlock")
	public String unlockAccount(@ModelAttribute("unlock") UnlockForm form, Model model) {

		if (form.getConfirmPassword().equals(form.getNewPassword())) {
			boolean unlockAccount = this.iUserService.unlockAccount(form);

			if (unlockAccount) {
				model.addAttribute("succmsg", AppConstants.STR_ACC_ACTIVATED_SUCCESSFULY);
			} else {
				model.addAttribute("errmsg", AppConstants.STR_ENTER_CORRECT_TEMP_PASSWORD);
			}

		} else {
			model.addAttribute("errmsg", AppConstants.STR_NEW_PASSWRD_AND_CONFIRM_PASSWORD_NOT_SAME);
		}
		return null;
	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {
		UnlockForm form = new UnlockForm();
		form.setEmail(email);
		model.addAttribute("unlock", form);
		return "unlock";
	}

	@GetMapping("/forgotpwd")
	public String forgotPwdPage() {
		return "forgotPwd";
	}

	@PostMapping("/forgotpwd")
	public String recoverPassword(@RequestParam("mail") String email, Model model) {

		System.out.println(email);
		boolean recoverPassword = this.iUserService.recoverPassword(email);
		if (recoverPassword) {
			model.addAttribute("succmsg", "Password sent to your email id ");

		} else {
			model.addAttribute("errmsg", "Enter a valid email id");
		}

		return "forgotpwd";

	}

}
