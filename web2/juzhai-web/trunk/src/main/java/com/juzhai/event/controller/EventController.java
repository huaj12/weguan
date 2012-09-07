package com.juzhai.event.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("event")
public class EventController {

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(Model model) {
		return "web/event/index";
	}
}
