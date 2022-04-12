package dlt.dltbackendmaster.domain;
// Generated Jan 25, 2022, 4:05:43 PM by Hibernate Tools 5.2.12.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Profiles generated by hbm2java
 */
@Entity
@Table(name = "profiles", catalog = "dreams_db")
@NamedQueries({
    @NamedQuery(name = "Profiles.findAll", query = "SELECT c FROM Profiles c"),
    @NamedQuery(name = "Profiles.findByDateCreated", query = "SELECT c FROM Profiles c WHERE c.dateCreated = :lastpulledat"),
    @NamedQuery(name = "Profiles.findByDateUpdated", query = "SELECT c FROM Profiles c WHERE c.dateUpdated = :lastpulledat")})
public class Profiles implements java.io.Serializable {

	private Integer id;
	private String name;
	private String description;
	private int createdBy;
	private Date dateCreated;
	private Integer updatedBy;
	private Date dateUpdated;
	private Set<Users> userses = new HashSet<Users>(0);

	public Profiles() {
	}

	public Profiles(String name) {
		this.name = name;
	}
	
	public Profiles(Integer id) {
		this.id = id;
	}

	public Profiles(String name, String description, Set<Users> userses) {
		this.name = name;
		this.description = description;
		this.userses = userses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 150)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", length = 250)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "created_by", nullable = false)
	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created", nullable = false, length = 19)
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Column(name = "updated_by")
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_updated", length = 19)
	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "profiles")
	public Set<Users> getUserses() {
		return this.userses;
	}

	public void setUserses(Set<Users> userses) {
		this.userses = userses;
	}
	
	public ObjectNode toObjectNode() {
		ObjectMapper mapper = new ObjectMapper();
		
		ObjectNode profile = mapper.createObjectNode();
		profile.put("id", id);
		profile.put("name", name);
		profile.put("description", description);
		profile.put("online_id", id); // flag to control if entity is synchronized with the backend
		return profile;
	} 

}
