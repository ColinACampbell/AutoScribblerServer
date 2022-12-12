package tech.eazley.AutoScribbler.Models.HttpModels;

import tech.eazley.AutoScribbler.Models.Database.User;

public class UserAuthBody
{
    private String token;
    private User user;
    public UserAuthBody(String token, User user)
    {
        this.token = token;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
