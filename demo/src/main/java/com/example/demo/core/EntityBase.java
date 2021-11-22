package com.example.demo.core;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Type;

@MappedSuperclass
public @NoArgsConstructor @Getter @Setter abstract class EntityBase {
    
    @Id
    @Type (type="uuid-char")
    private UUID id;

    @Override
    public boolean equals(Object obj)    {
        if (!(obj instanceof EntityBase)) {
            return false;
        }
        EntityBase tmp = (EntityBase)obj;
        return tmp.id.equals(this.id);
    }
   
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
