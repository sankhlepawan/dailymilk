package com.dailyservice.whatsappbot.model.v1;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NamedQueries({
	@NamedQuery(name="findByWhatsappNumber",query="select u from User u where u.whatsappNumber=:whatsappNumber"),
	@NamedQuery(name="enableUser",query="update User u set u.enable=:enable where u.id=:userId"),
//	@NamedQuery(name="deleteUser",query="update User u set u.deleted=:deleted where u.id=:id"),
})

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wb_user")
public class User implements Serializable{
   
   private static final long serialVersionUID = 1L;

   
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;
   
   @Column(name = "first_name")
   private String firstName;
   
   @Column(name = "last_name")
   private String lastName;
   
   @Column(name = "isWhatsappNumber")
   private boolean whatsappNumber = false;
   
   @Column(name = "mobile")
   private String mobile;
   
   @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
   @JoinColumn(name = "address_id")
   private Address address;
   
   @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
   @JoinColumn(name = "preference_id")
   private UserPreference userPreference;
   
   @Column(name = "enable")
   private boolean enable = false;
   
   @Column(name = "deleted")
   private boolean deleted = false;
   
//   @OneToMany
//   @JoinTable(name = "user_role",
//   joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
//   inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
//   
   
   @ManyToMany(targetEntity=Role.class,fetch=FetchType.EAGER)
   @JoinTable(name="user_role", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="role_id"))
   private Set<Role> roles;
}

