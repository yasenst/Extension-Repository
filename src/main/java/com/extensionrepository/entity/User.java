package com.extensionrepository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;


    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "fullName", nullable = false)
    @JsonIgnore
    private String fullName;

    @Column(name = "enabled")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @JsonIgnore
    private boolean enabled;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",

            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnore
    private Set<Role> roles;


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Extension> extensions;

    @Transient
    @JsonIgnore
    public boolean isAdmin() {
        return this.getRoles()
                .stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
    }

    @Transient
    public boolean isOwner(Extension extension) {
        return Objects.equals(this.getId(),
                    extension.getUser().getId());
    }

    public User(){
        this.extensions = new ArrayList<>();
        this.roles = new HashSet<>();
    }

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.enabled = true;

        this.extensions = new ArrayList<>();
        this.roles = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) { this.roles.add(role); }

    public List<Extension> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
    }
}