package com.example.demo.core;

import java.util.Set;
import java.util.UUID;

import com.example.demo.core.exceptions.BadRequestException;
import com.example.demo.core.functionalinterfaces.ExistsByField;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Type;

@MappedSuperclass
public @NoArgsConstructor @Getter @Setter abstract class EntityBase {
    
    @Id
    @Type (type="uuid-char")
    private UUID id;

    public void validate(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<EntityBase>> violations = validator.validate(this);
        if(!violations.isEmpty()){
            BadRequestException badRequestException = new BadRequestException();
            for(ConstraintViolation<EntityBase> violation: violations){
                badRequestException.addException(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw badRequestException;
        }
    }

    public void validate(String key, String value, ExistsByField existsByField){
        this.validate();
        if(existsByField.exists(value)){
            BadRequestException badRequestException = new BadRequestException();
            badRequestException.addException(key, String.format("Value %s for key %s is duplicated.", value, key));
            throw badRequestException;
        }
    }

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
