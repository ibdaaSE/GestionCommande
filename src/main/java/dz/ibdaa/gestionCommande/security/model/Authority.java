package dz.ibdaa.gestionCommande.security.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "authorityprevileges",
            joinColumns = {@JoinColumn(name = "authorityId", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "previlegeId", referencedColumnName = "id")})
    private List<Previlege> previleges;
    
    
    @Enumerated(EnumType.STRING)
    private AuthorityName name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthorityName getName() {
        return name;
    }

    public void setName(AuthorityName name) {
        this.name = name;
    }

	public List<Previlege> getPrevileges() {
		return previleges;
	}

	public void setPrevileges(List<Previlege> previleges) {
		this.previleges = previleges;
	}

    
    
}