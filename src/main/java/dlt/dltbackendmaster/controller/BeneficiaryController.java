package dlt.dltbackendmaster.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.Beneficiaries;
import dlt.dltbackendmaster.domain.NumericSequence;
import dlt.dltbackendmaster.service.DAOService;
import dlt.dltbackendmaster.service.SequenceGenerator;

@RestController
@RequestMapping("/api/beneficiaries")
public class BeneficiaryController
{
    private final DAOService service;

    private SequenceGenerator generator;

    public BeneficiaryController(DAOService service) {
        this.service = service;
        this.generator = new SequenceGenerator(service);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Beneficiaries>> get(@RequestParam(name = "profile") Integer profile, @RequestParam(name = "level") String level, @RequestParam(name = "params",
                                                                                                                                                              required = false) @Nullable Integer[] params) {

        try {
            List<Beneficiaries> beneficiaries = null;

            if (level.equals("CENTRAL")) {
                beneficiaries = service.getAll(Beneficiaries.class);
            } else if (level.equals("PROVINCIAL")) {
                beneficiaries = service.GetAllEntityByNamedQuery("Beneficiary.findByProvinces", Arrays.asList(params));
            } else if (level.equals("DISTRITAL")) {
                beneficiaries = service.GetAllEntityByNamedQuery("Beneficiary.findByDistricts", Arrays.asList(params));
            } else {
                beneficiaries = service.GetAllEntityByNamedQuery("Beneficiary.findByLocalities", Arrays.asList(params));
            }

            return new ResponseEntity<List<Beneficiaries>>(beneficiaries, HttpStatus.OK);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Beneficiaries> get(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            Beneficiaries beneficiary = service.find(Beneficiaries.class, id);
            return new ResponseEntity<>(beneficiary, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Beneficiaries> save(@RequestBody Beneficiaries beneficiary) {

        if (beneficiary == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            beneficiary.setDateCreated(new Date());
            String partnerNUI = beneficiary.getPartnerNUI();

            if (partnerNUI != null && partnerNUI != "") {
                Beneficiaries partner = service.GetUniqueEntityByNamedQuery("Beneficiary.findByNui", partnerNUI);
                // TODO: Throw exception for NUI not found
                if (partner != null)
                    beneficiary.setPartnerId(partner.getId());
            }
            NumericSequence nextNUI = generator.getNextNumericSequence();
            beneficiary.setNui(nextNUI.getSequence());
            Integer beneficiaryId = (Integer) service.Save(beneficiary);
            Beneficiaries createdBeneficiary = service.find(Beneficiaries.class, beneficiaryId);
            return new ResponseEntity<>(createdBeneficiary, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Beneficiaries> update(@RequestBody Beneficiaries beneficiary) {

        if (beneficiary == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            beneficiary.setDateUpdated(new Date());
            String partnerNUI = beneficiary.getPartnerNUI();

            if (partnerNUI != null && partnerNUI != "") {
                Beneficiaries partner = service.GetUniqueEntityByNamedQuery("Beneficiary.findByNui", partnerNUI);
                // TODO: Throw exception for NUI not found
                if (partner != null)
                    beneficiary.setPartnerId(partner.getId());
            } else {
				beneficiary.setPartnerId(null);
			}
            Beneficiaries updatedBeneficiary = service.update(beneficiary);
            return new ResponseEntity<>(updatedBeneficiary, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
