package Ensak.Blanat.Blanat.entities;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import Ensak.Blanat.Blanat.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "users")
public class UserApp implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  
  String userName;
  //String lastName;

  @Column(unique = true)
  String email;

  String password;
  @Enumerated(EnumType.STRING)
  Role role;

  LocalDateTime createdAt;

  LocalDateTime updatedAt;

  @OneToMany(mappedBy = "user")
  private Collection<Comment> comments;

  @OneToMany(mappedBy = "user")
  private Collection<SavedDeals> savedDeals;

  @OneToMany(mappedBy = "dealCreator")
  private Collection<Deal> deals;

  @Getter
  @OneToMany(mappedBy ="createur")
  private List<Discussion> discussions;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
      return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getUsername() {
      // our "username" for security is the email field
      return email;
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

  public void setDiscussions(List<Discussion> discussions) {
    this.discussions = discussions;
  }
}
