package com.blog.enteties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserDetail implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false)
    private String email;
    private String password;
    private String about;
    @OneToMany(mappedBy = "userDetail", cascade = CascadeType.ALL)
    private List<Post> posts;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId"))
    private List<Role> roles;

    //Setting the roles in UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorites = this.roles.stream().map((role) -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
        return authorites;
    }

    //Giving Spring Security the email/username
    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
