package facebookapp.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Reply extends Post{

    private Boolean isPublic;
}
