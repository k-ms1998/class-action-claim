package com.proejct.ClassActionClaim.domain.baseEntities;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public abstract class IdEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

}
