package com.fan.interfaces;

import org.springframework.stereotype.Service;

public interface IProducerService {

	void sendMessage(String message);
}
