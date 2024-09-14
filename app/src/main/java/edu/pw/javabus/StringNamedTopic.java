package edu.pw.javabus;

import java.util.Objects;

public class StringNamedTopic implements Topic {

    private final String topicName;
    private final Topic parent;

    public StringNamedTopic(String topicName) {
        this(topicName, null);
    }

    public StringNamedTopic(String topicName, Topic parent) {
        this.topicName = topicName;
        this.parent = parent;
    }

    @Override
    public Topic getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringNamedTopic that = (StringNamedTopic) o;
        return Objects.equals(topicName, that.topicName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(topicName);
    }
}
