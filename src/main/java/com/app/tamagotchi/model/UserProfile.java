package com.app.tamagotchi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class UserProfile {

  @SerializedName("sub")
  @Expose
  private String sub;
  @SerializedName("nickname")
  @Expose
  private String nickname;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("picture")
  @Expose
  private String picture;
  @SerializedName("updated_at")
  @Expose
  private String updatedAt;
  @SerializedName("email")
  @Expose
  private String email;
  @SerializedName("email_verified")
  @Expose
  private Boolean emailVerified;
}
