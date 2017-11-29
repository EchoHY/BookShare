package com.lq.entity;

import java.io.Serializable;

public class ServiceView implements Serializable {
    private static final long serialVersionUID = -2909733442648785569L;
    private ServiceId id;
    public ServiceView() {
        super();
    }
    public ServiceId getId() {
        return id;
    }
    public void setId(ServiceId id) {
        this.id = id;
    }
}
