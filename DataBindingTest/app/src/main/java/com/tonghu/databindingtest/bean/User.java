package com.tonghu.databindingtest.bean;

/**
 * @author york
 * @date 10/1/15
 * @since 1.0.0
 */
public class User {
    public final String firstName;
    public final String lastName;
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public final boolean isFriend() {
        return true;
    }

    public boolean isAult() {
        return false;
    }

}
