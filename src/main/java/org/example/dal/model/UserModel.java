package org.example.dal.model;

public class UserModel {

    private int id;
    private String nickname;
    private String email;
    private byte[] avatar;

    public UserModel(int id, String nickname, String email, byte[] avatar) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.avatar = avatar;
    }

    public UserModel(String nickname, String email, byte[] avatar) {
        this.nickname = nickname;
        this.email = email;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getAvatar() {
        return avatar;
    }
}
