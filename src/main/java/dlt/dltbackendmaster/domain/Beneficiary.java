package dlt.dltbackendmaster.domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dlt.dltbackendmaster.domain.watermelondb.BeneficiarySyncModel;
import dlt.dltbackendmaster.serializers.NeighborhoodSerializer;
import dlt.dltbackendmaster.serializers.PartnersSerializer;

@SuppressWarnings("serial")
@Entity
@Table(name = "beneficiaries", catalog = "dreams_db")
@NamedQueries({ @NamedQuery(name = "Beneficiary.findAll", query = "SELECT b FROM Beneficiary b"),
                @NamedQuery(name = "Beneficiary.findByDateCreated",
                            query = "select b from Beneficiary b where b.dateUpdated is null and b.dateCreated > :lastpulledat"),
                @NamedQuery(name = "Beneficiary.findByDateUpdated",
                            query = "select b from Beneficiary b where (b.dateUpdated >= :lastpulledat) or (b.dateUpdated >= :lastpulledat and b.dateCreated = b.dateUpdated)") })
public class Beneficiary extends BasicLifeCycle implements Serializable
{
    private String nui;
    private String surname;
    private String nickName;
    private Partners organization;
    private Date dateOfBirth;
    private Character gender;
    private String address;
    private String phoneNumber;
    private String email;
    private LivesWith livesWith;
    private Boolean isOrphan;
    private Boolean via;
    private Beneficiary partnerId;
    private Boolean isStudent;
    private Integer grade;
    private String schoolName;
    private Boolean isDeficient;
    private DeficiencyType deficiencyType;
    private String entryPoint;
    private Neighborhood neighborhood;
    private Integer usId;
    private Partners partner;
    private Set<BeneficiaryIntervention> interventions;
    private Set<BeneficiaryVulnerability> vulnerabilities;
    private String offlineId;

    public Beneficiary() {}

    public Beneficiary(Integer id, String nui, String surname, String name, String nickName, Date dateOfBirth,
                       Character gender, String address, String phoneNumber, String email, LivesWith livesWith,
                       Boolean isOrphan, Boolean via, Beneficiary partner, Boolean isStudent, Integer grade,
                       String schoolName, Boolean isDeficient, DeficiencyType deficiencyType, String entryPoint,
                       Neighborhood neigbourhoodId, Integer usId, Integer status, Users createdBy, Date dateCreated,
                       Users updatedBy, Date dateUpdated) {
        super(id, name, status, 
        		dateCreated, dateUpdated, createdBy, updatedBy);

        this.nui = nui;
        this.surname = surname;
        this.nickName = nickName;
        this.organization = organization;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.livesWith = livesWith;
        this.isOrphan = isOrphan;
        this.via = via;
        this.partnerId = partner;
        this.isStudent = isStudent;
        this.grade = grade;
        this.schoolName = schoolName;
        this.isDeficient = isDeficient;
        this.deficiencyType = deficiencyType;
        this.entryPoint = entryPoint;
        this.neighborhood = neigbourhoodId;
        this.usId = usId;
    }

    public Beneficiary(BeneficiarySyncModel model, String timestamp) {
        super(model.getName(), Integer.valueOf(model.getStatus()));
        Long t = Long.valueOf(timestamp);
        Date regDate = new Date(t);
        this.nui = model.getNui();
        this.surname = model.getSurname();
        this.nickName = model.getNick_name();
        this.organization = new Partners(model.getOrganization_id());
        this.dateOfBirth = model.getDate_of_birth();
        this.gender = model.getGender();
        this.address = model.getAddress();
        this.phoneNumber = model.getPhone_number();
        this.email = model.getE_mail();
        this.livesWith = model.getLives_with();
        this.isOrphan = model.getIs_orphan();
        this.via = model.getVia();
        this.partnerId = new Beneficiary(model.getPartner_id());
        this.isStudent = model.getIs_student();
        this.grade = model.getGrade();
        this.schoolName = model.getSchool_name();
        this.isDeficient = model.getIs_deficient();
        this.deficiencyType = model.getDeficiency_type();
        this.entryPoint = model.getEntry_point();
        this.neighborhood = new Neighborhood(model.getNeighbourhood_id());
        this.usId = model.getUs_id();
        this.offlineId = model.getId();
        this.dateCreated = regDate;
        this.dateUpdated = regDate;
    }

    public Beneficiary(Integer id) {
        this.id = id;
    }

    @Column(name = "nui", unique = true, nullable = false)
    public String getNui() {
        return nui;
    }

    public void setNui(String nui) {
        this.nui = nui;
    }

    @Column(name = "surname", nullable = false)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "nick_name")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    @JsonProperty("organization")
    @JsonSerialize(using = PartnersSerializer.class)
    public Partners getOrganization() {
        return organization;
    }

    public void setOrganization(Partners organization) {
        this.organization = organization;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_of_birth")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Column(name = "gender")
    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "e_mail")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "lives_with")
    public LivesWith getLivesWith() {
        return livesWith;
    }

    public void setLivesWith(LivesWith livesWith) {
        this.livesWith = livesWith;
    }

    @Column(name = "is_orphan")
    public Boolean getIsOrphan() {
        return isOrphan;
    }

    public void setIsOrphan(Boolean isOrphan) {
        this.isOrphan = isOrphan;
    }

    @Column(name = "via")
    public Boolean getVia() {
        return via;
    }

    public void setVia(Boolean via) {
        this.via = via;
    }

    @OneToOne
    @JoinColumn(name = "partner_id")
    public Beneficiary getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Beneficiary partner) {
        this.partnerId = partner;
    }

    @Column(name = "is_student")
    public Boolean getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(Boolean isStudent) {
        this.isStudent = isStudent;
    }

    @Column(name = "grade")
    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Column(name = "school_name")
    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Column(name = "is_deficient")
    public Boolean getIsDeficient() {
        return isDeficient;
    }

    public void setIsDeficient(Boolean isDeficient) {
        this.isDeficient = isDeficient;
    }

    @Column(name = "deficiency_type")
    public DeficiencyType getDeficiencyType() {
        return deficiencyType;
    }

    public void setDeficiencyType(DeficiencyType deficiencyType) {
        this.deficiencyType = deficiencyType;
    }

    @Column(name = "entry_point")
    public String getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(String entryPoint) {
        this.entryPoint = entryPoint;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "neighbourhood_id")
    @JsonProperty("neighborhood")
    @JsonSerialize(using = NeighborhoodSerializer.class)
    public Neighborhood getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }

    @Column(name = "us_id")
    public Integer getUsId() {
        return usId;
    }

    public void setUsId(Integer usId) {
        this.usId = usId;
    }
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partner")
    @JsonProperty("partner")
    public Partners getPartner() {
		return partner;
	}

	public void setPartner(Partners partner) {
		this.partner = partner;
	}

	@OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "beneficiary_id")
    public Set<BeneficiaryIntervention> getInterventions() {
        return interventions;
    }

    public void setInterventions(Set<BeneficiaryIntervention> interventions) {
        this.interventions = interventions;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "beneficiary_id")
    public Set<BeneficiaryVulnerability> getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(Set<BeneficiaryVulnerability> vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }

    @Column(name = "offline_id", nullable = true, length = 45)
    public String getOfflineId() {
        return offlineId;
    }

    public void setOfflineId(String offlineId) {
        this.offlineId = offlineId;
    }

    public ObjectNode toObjectNode() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode beneficiary = mapper.createObjectNode();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (offlineId != null) {
            beneficiary.put("id", offlineId);
        } else {
            beneficiary.put("id", id);
        }

        if (dateUpdated == null || dateUpdated.after(dateCreated)) {
            beneficiary.put("id", id);
            beneficiary.put("nui", nui);
            beneficiary.put("name", name);
            beneficiary.put("surname", surname);
            beneficiary.put("nickname", nickName);
            beneficiary.put("organization_id", organization.getId());
            beneficiary.put("date_of_birth", dateFormat.format(dateOfBirth));
            beneficiary.put("gender", String.valueOf(gender));
            beneficiary.put("adress", address);
            beneficiary.put("phone_number", phoneNumber);
            beneficiary.put("email", email);
            beneficiary.put("livesWith", livesWith.toString());
            beneficiary.put("is_orphan", isOrphan);
            beneficiary.put("via", via);
            beneficiary.put("partner_id", partnerId == null ? null : partnerId.getId());
            beneficiary.put("is_student", isStudent);
            beneficiary.put("grade", grade);
            beneficiary.put("schoolName", schoolName);
            beneficiary.put("is_deficient", isDeficient);
            beneficiary.put("deficiencyType", deficiencyType == null ? null : deficiencyType.toString());
            beneficiary.put("entry_point", entryPoint);
            beneficiary.put("neighborhood_id", neighborhood.getId());
            beneficiary.put("us_id", usId);
            beneficiary.put("online_id", id); // flag to control if entity is synchronized with the backend
        } else { // ensure online_id is updated first
            beneficiary.put("online_id", id);
        }
        return beneficiary;
    }

    public void update(BeneficiarySyncModel model, String timestamp) {
        Long t = Long.valueOf(timestamp);
        this.offlineId = model.getId();
        this.dateUpdated = new Date(t);
        this.nui = model.getNui();
        this.name = model.getName();
        this.surname = model.getSurname();
        this.nickName = model.getNick_name();
        this.organization.setId(model.getOrganization_id());
        this.dateOfBirth = model.getDate_of_birth();
        this.gender = model.getGender();
        this.address = model.getAddress();
        this.phoneNumber = model.getPhone_number();
        this.email = model.getE_mail();
        this.livesWith = model.getLives_with();
        this.isOrphan = model.getIs_orphan();
        this.via = model.getVia();
        this.partner.setId(model.getPartner_id());
        this.isStudent = model.getIs_student();
        this.grade = model.getGrade();
        this.schoolName = model.getSchool_name();
        this.isDeficient = model.getIs_deficient();
        this.deficiencyType = model.getDeficiency_type();
        this.entryPoint = model.getEntry_point();
        this.neighborhood.setId(model.getNeighbourhood_id());
        this.usId = model.getUs_id();
        this.status = Integer.valueOf(model.getStatus());
    }
}
