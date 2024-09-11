package com.maddev.coozy;

import com.maddev.coozy.model.User;
import com.maddev.coozy.model.UserDAO;

public class PopulateDB {
    static UserDAO dao= new UserDAO();
    static User user =new User("martin", "martin@gmail", "martinius", "1", "1234");

    public static void main(String[] args) {
        dao.insert(user);
    }
}
