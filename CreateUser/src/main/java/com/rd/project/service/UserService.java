package com.rd.project.service;


import com.rd.project.dao.UserDAO;
import com.rd.project.model.User;

public class UserService {

    private static final UserDAO userDAO = new UserDAO();

    public Integer createUser(User user){
        int userId = userDAO.createUser(user);
        return userId;
    }
}
