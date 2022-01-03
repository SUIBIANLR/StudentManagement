package com.wqf.studentmanagement.DataStructure;

public class Grade {
    private String subject="";
    private int score=0;
    private int term=2001;//学期
    private String note="";//备注

    public void setTerm(int term) {
        this.term = term;
    }

    public Grade(String subject, int score, int term, String note) {
        this.subject = subject;
        this.score = score;
        this.term = term;
        this.note = note;
    }

    public Grade(){

    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTerm() {
        return term;
    }

    public String getNote() {
        return note;
    }



    public Grade(String subject,int score){
        this.subject=subject;
        this.score=score;
    }

    public String getSubject(){
        return subject;
    }

    public int getScore(){
        return score;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public void setScore(int score){
        this.score = score;
    }
}
