package dz.ibdaa.gestionCommande.security.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the previleges database table.
 * 
 */
@Entity
@Table(name="previleges")
@NamedQuery(name="Previlege.findAll", query="SELECT p FROM Previlege p")
public class Previlege implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	public Previlege() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}