package com.graphql.xymatic.model;

import com.graphql.xymatic.enums.RoleEnums;
import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "xt_roles")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RoleModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "author", nullable = false, updatable = false)
  @EqualsAndHashCode.Include
  private UserModel author;

  @Enumerated(EnumType.STRING)
  @Column(name = "name")
  @EqualsAndHashCode.Include
  private RoleEnums name;
}
