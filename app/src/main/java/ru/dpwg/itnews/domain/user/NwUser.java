package ru.dpwg.itnews.domain.user;

import java.util.List;

public class NwUser {

    public int id;
    public String email;
    public String fullName;
    public String avatar;
    public List<NwAuthority> authorities;
}
