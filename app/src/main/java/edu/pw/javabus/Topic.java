package edu.pw.javabus;

/**
 * Identification of specific area in which consumers are registering their methods.
 * <p>
 * Classes implementing this interface shall also correctly implement {@link Object#equals(Object)} and {@link Object#hashCode()} methods.
 */
public interface Topic {

    /**
     * If a topic has a parent (higher-order topic) it shall return its instance here, null otherwise.
     * @return a parent topic or null if it is a top-level topic
     */
    Topic getParent();

}
