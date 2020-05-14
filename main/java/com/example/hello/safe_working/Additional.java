package com.example.hello.safe_working;

public class Additional {
    public Additional(String compalintType, String nameAccused, String date1, String witness, String description, String evidence, String name, String id, String complaintno, String approved, String uid, String evidence_url) {
        CompalintType = compalintType;
        NameAccused = nameAccused;
        this.date1 = date1;
        Witness = witness;
        this.description = description;
        this.evidence = evidence;
        Name = name;
        this.id = id;
        this.complaintno = complaintno;
        this.approved = approved;
        this.uid = uid;
        this.evidence_url = evidence_url;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getEvidence_url() {
        return evidence_url;
    }

    public void setEvidence_url(String evidence_url) {
        this.evidence_url = evidence_url;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public Additional() {
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComplaintno() {
        return complaintno;
    }

    public void setComplaintno(String complaintno) {
        this.complaintno = complaintno;
    }

    public String getApproved() {
        return approved;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getEvidence() {
        return evidence;
    }
    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }
    public String getCompalintType() {
        return CompalintType;
    }

    public void setCompalintType(String compalintType) {
        CompalintType = compalintType;
    }

    public String getNameAccused() {
        return NameAccused;
    }

    public void setNameAccused(String nameAccused) {
        NameAccused = nameAccused;
    }

    public String getDate() {
        return date1;
    }

    public void setDate(String date) {
        this.date1 = date;
    }

    public String getWitness() {
        return Witness;
    }

    public void setWitness(String witness) {

        if(Witness==null) {
            Witness = witness;
        }
        else
        {
            Witness+=witness;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String CompalintType,NameAccused,date1="",Witness="",description,evidence="No",Name,id,complaintno,approved,uid,evidence_url;

}
