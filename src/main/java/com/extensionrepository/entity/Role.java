package com.extensionrepository.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany()
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    public Role(){
    }

    public Role(String name, Set<User> users) {
        this.name = name;
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
