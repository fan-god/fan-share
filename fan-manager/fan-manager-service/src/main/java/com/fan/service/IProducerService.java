package com.fan.service;

import org.springframework.stereotype.Service;

public interface IProducerService {

	void sendMessage(String message);
}
