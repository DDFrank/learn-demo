package io.frank.test_server;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author jinjunliang
 **/
public class Org {
  private String id;
  private Set<String> users;
  private Set<String> devices;

  public Org(String id) {
    this.id = id;
    this.users = new HashSet<>();
    this.devices = new HashSet<>();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Set<String> getUsers() {
    return users;
  }

  public void setUsers(Set<String> users) {
    this.users = users;
  }

  public Set<String> getDevices() {
    return devices;
  }

  public void setDevices(Set<String> devices) {
    this.devices = devices;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Org org = (Org) o;
    return Objects.equals(id, org.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public Org plus(Org that) {
    this.users.addAll(that.getUsers());
    this.devices.addAll(that.getDevices());
    return this;
  }
}
