package com.juzhai.act.service;

import com.juzhai.act.model.RawAct;
import com.juzhai.core.exception.AddRawActException;

public interface IRawActService {
	RawAct addRawAct(RawAct rawAct) throws AddRawActException;
}
