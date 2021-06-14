package com.app.tamagotchi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AccessToken {

  @SerializedName("access_token")
  @Expose
  private String accessToken;
  @SerializedName("id_token")
  @Expose
  @JsonIgnore
  private String idToken;
  @SerializedName("scope")
  @Expose
  private String scope;
  @SerializedName("expires_in")
  @Expose
  private Integer expiresIn;
  @SerializedName("token_type")
  @Expose
  private String tokenType;

}
