package ru.yaneg.graduation_of_topjava_springboot.io.entitiy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="roles")
public class RoleEntity extends AbstractNamedEntity implements Serializable {

    private static final long serialVersionUID = 5605260522147928803L;

    @ManyToMany(mappedBy="roles")
    private Collection<UserEntity> users;

    public RoleEntity() {}

    public RoleEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Collection<UserEntity> users) {
        this.users = users;
    }


}
