package com.juzhai.act.service;

import com.juzhai.act.exception.AddRawActException;
import com.juzhai.act.model.RawAct;

public interface IRawActService {
	RawAct addRawAct(RawAct rawAct) throws AddRawActException;
}
