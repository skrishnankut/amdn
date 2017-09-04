package io.pivotal.microservices.pact.consumer;

public class User {

    private int value;

    public User() {
    }

    public User(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (value != user.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
