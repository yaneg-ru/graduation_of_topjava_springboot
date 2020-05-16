package ru.yaneg.graduation_of_topjava_springboot.io.entitiy;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EATERY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EateryEntity extends AbstractNamedEntity {

    public EateryEntity() {
    }

    public EateryEntity(String name) {
        super(name);
    }

    public EateryEntity(int id, String name) {
        super(id, name);
    }

    public EateryEntity(EateryEntity eateryEntity) {
        super(eateryEntity.getId(), eateryEntity.getName());
    }
}
