package nl.rotterdamminsk.checksumsdemo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Attachment implements Serializable {
    long version;
    String type;
    List<Role> roles;
    byte[] data;

    public Attachment(long version, String type, List<Role> roles, byte[] data) {
        this.version = version;
        this.type = type;
        this.roles = roles;
        this.data = data;
    }

    /**
     * Optimized isSame method with lazy checksum calculation
     */
    public boolean isSame(Attachment other) {
        if (other == null) return false;
        if (this == other) return true;

        // Quick field comparison before expensive checksum calculation
        if (this.version != other.version) return false;
        if (!Objects.equals(this.type, other.type)) return false;
        if (!Objects.equals(this.roles, other.roles)) return false;
        return Arrays.equals(this.data, other.data);
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Role> getRoles() {
        return roles == null ? null : new ArrayList<>(roles);
    }

    public void setRoles(List<Role> roles) {
        if (roles == null) {
            this.roles = null;
        } else {
            this.roles = new ArrayList<>(roles); // Shallow copy of list, deep copy of Role objects if needed
        }
    }

    public byte[] getData() {
        return data == null ? null : data.clone();
    }

    public void setData(byte[] data) {
        this.data = data == null ? null : data.clone();
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "version=" + version +
                ", type='" + type + '\'' +
                ", roles=" + roles +
                ", dataSize=" + (data != null ? data.length : 0) +
                '}';
    }
}
