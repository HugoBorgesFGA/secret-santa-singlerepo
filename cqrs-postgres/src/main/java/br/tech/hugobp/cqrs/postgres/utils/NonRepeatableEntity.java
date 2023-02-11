package br.tech.hugobp.cqrs.postgres.utils;

public abstract class NonRepeatableEntity {

    private String nonRepeatableId;

    public String getNonRepeatableId() {
        return nonRepeatableId;
    }

    public void setNonRepeatableId(String nonRepeatableId) {
        this.nonRepeatableId = nonRepeatableId;
    }
}
