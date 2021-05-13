package io.rajatchaudhary.covidtracker.models;

public class Location {

    private String states;
    private String country;
    private int updatedCases;
    private int casesSincePreviousDay;

    public int getCasesSincePreviousDay() {
        return casesSincePreviousDay;
    }

    public void setCasesSincePreviousDay(int casesSincePreviousDay) {
        this.casesSincePreviousDay = casesSincePreviousDay;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getUpdatedCases() {
        return updatedCases;
    }

    public void setUpdatedCases(int updatedCases) {
        this.updatedCases = updatedCases;
    }

    @Override
    public String toString() {
        return "Location{" +
                "states='" + states + '\'' +
                ", country='" + country + '\'' +
                ", updatedCases=" + updatedCases +
                '}';
    }
}
