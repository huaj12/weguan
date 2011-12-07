package com.juzhai.home.controller.website;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.juzhai.core.controller.BaseController;

@Controller
public class UserHomeController extends BaseController {

	@RequestMapping
	public String myActs(HttpServletRequest request, Model model) {

		return null;
	}

	@RequestMapping
	public String myInterests(HttpServletRequest request, Model model) {
		return null;
	}

	@RequestMapping
	public String myDatings(HttpServletRequest request, Model model) {
		return null;
	}

	@RequestMapping
	public String myBeInterested(HttpServletRequest request, Model model) {
		return null;
	}

	@RequestMapping
	public String myBeDated(HttpServletRequest request, Model model) {
		return null;
	}
}
