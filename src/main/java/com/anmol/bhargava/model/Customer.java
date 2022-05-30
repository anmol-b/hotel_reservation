package com.anmol.bhargava.model;

public class Customer {
    
    private String firstName;
    private String lastName;
    private String email_id;
    private int customerId; // This is not used as 

    public Customer(String firstName, String lastName, String email_id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email_id = email_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail_id() {
        return email_id;
    }
    
    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        
        return "Customer{ "
                + "firstName: " + this.firstName + ", "
                + "lastName: " + this.lastName + ", "
                + "email: " + this.email_id + " }";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Customer)) {
            return false;
        }

        Customer c = (Customer) obj;

        return this.firstName.equals(c.getFirstName())
                && this.lastName.equals(c.getLastName())
                && this.email_id.equals(c.getEmail_id());
    }
}
