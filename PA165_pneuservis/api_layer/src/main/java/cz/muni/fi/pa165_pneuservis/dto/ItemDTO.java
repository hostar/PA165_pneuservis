/*
 * Team project for course PA165 - Enterprise Applications in Java
 * For more informations see file README.md
 */
package cz.muni.fi.pa165_pneuservis.dto;

import java.math.BigDecimal;
import javax.validation.constraints.*;

/**
 * @author Jozef Sumaj <374029@mail.muni.cz>
 */
public abstract class ItemDTO {
    
    private Long id;
    
    @NotNull(message = "Can't be empty")
    @Size(min = 2, max = 64, message="Tire name must be between 2 and 64 characters")
    private String name;
    
    @NotNull(message = "Can't be empty")
    @Size(min = 2, max = 2048, message="Tire description must be between 2 and 2048 characters")
    private String description;
    
    @NotNull(message = "Can't be empty")
    private BigDecimal price;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        
        if (obj == null)
            return false;
        
        if (! (obj instanceof ItemDTO))
            return false;
        
        ItemDTO other = (ItemDTO) obj;
        
        if (id == null) {
            if (other.getId() != null)
                return false;
        } else if (!id.equals(other.getId()))
            return false;
        
        if (name == null) {
            if (other.getName()!= null)
                return false;
        } else if (!name.equals(other.getName()))
            return false;
        
        if (description == null) {
            if (other.getDescription()!= null)
                return false;
        } else if (!description.equals(other.getDescription()))
            return false;
        
        if (price == null) {
            if (other.getPrice()!= null)
                return false;
        } else if (!price.equals(other.getPrice()))
            return false;
        
        return true;
    }
}