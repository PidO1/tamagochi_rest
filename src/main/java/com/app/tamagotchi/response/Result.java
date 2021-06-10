package com.app.tamagotchi.response;

import lombok.Data;

import java.util.List;

public interface Result<T> {
  T getResult();
  List<ResultMessage> getMessages();
}
