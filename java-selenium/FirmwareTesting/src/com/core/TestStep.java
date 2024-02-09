package com.core;


public class TestStep {
    private String id;
    private UserActions action;
    private String fieldId;
    private String fieldType;
    private String fieldValue;
    private String Result;
    private String fileDescription;

    public TestStep() {
        // empty param constructor
    }

    public TestStep(final String id, final UserActions action, final String fieldId,
                    final String fieldType, final String filedValue, final String Result) {
        this.id = id;
        this.action = action;
        this.fieldId = fieldId;
        this.fieldType = fieldType;
        this.fieldValue = filedValue;
        this.Result = Result;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public UserActions getAction() {
        return action;
    }

    public void setAction(final UserActions action) {
        this.action = action;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(final String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(final String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(final String filedValue) {
        this.fieldValue = filedValue;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(final String Result) {
        this.Result = Result;
    }

    @Override
    public String toString() {
        System.out.println();
        return "TestStep{" + "id='" + id + '\'' + ", action=" + action + ", fieldId='" + fieldId +
                '\'' + ", fieldType='" + fieldType + '\'' + ", filedValue='" + fieldValue + '\'' +
                ", Result='" + Result + '\'' + '}';
    }
}