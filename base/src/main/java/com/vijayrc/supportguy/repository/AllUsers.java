package com.vijayrc.supportguy.repository;

import com.vijayrc.supportguy.domain.User;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Scope("single")
@Log4j
public class AllUsers {
    private List<User> users = new ArrayList<User>();

    public AllUsers add(User user){
        users.add(user);
        return this;
    }

    public User getFor(String name){
        for (User user : users)
            if(user.nameIs(name))
                return user;
        return null;
    }
}
