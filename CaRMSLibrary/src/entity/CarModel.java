/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Darie
 */
@Entity
public class CarModel implements Serializable {



    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long modelId;
    @Column(nullable = false, length = 32)
    @NotNull
//    @Size(min = 8, max = 32)
    private String carMake;
    @Column(nullable = false, length = 32)
    @NotNull
//    @Size(min = 8, max = 32)
    private String modelName;
    @Column(nullable = false)
    @NotNull
    private boolean modelIsDisabled;
    
//    @OneToMany
//    private List<Car> cars;
//    
//    @ManyToOne(optional = false)
//    @JoinColumn(nullable = false)
//    private CarCategory carCategory;
    
    

    public CarModel() {
        modelIsDisabled = false;
    }

    public CarModel(String carMake, String modelName, boolean modelIsDisabled) {
        this.carMake = carMake;
        this.modelName = modelName;
        this.modelIsDisabled = modelIsDisabled;
    }
    

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }
       
    /**
     * @return the carMake
     */
    public String getCarMake() {
        return carMake;
    }

    /**
     * @param carMake the carMake to set
     */
    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    /**
     * @return the modelName
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * @param modelName the modelName to set
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    /**
     * @return the modelIsDisabled
     */
    public boolean isModelIsDisabled() {
        return modelIsDisabled;
    }

    /**
     * @param modelIsDisabled the modelIsDisabled to set
     */
    public void setModelIsDisabled(boolean modelIsDisabled) {
        this.modelIsDisabled = modelIsDisabled;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (modelId != null ? modelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the modelId fields are not set
        if (!(object instanceof CarModel)) {
            return false;
        }
        CarModel other = (CarModel) object;
        if ((this.modelId == null && other.modelId != null) || (this.modelId != null && !this.modelId.equals(other.modelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CarModel[ id=" + modelId + " ]";
    }
    
}
