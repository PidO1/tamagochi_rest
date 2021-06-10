package com.app.tamagotchi.interfaces;

import com.app.tamagotchi.response.ResultMessage;

import java.util.List;

public interface Result<T> {
  T getResult();
  List<ResultMessage> getMessages();
}
