package com.epam.audiospot.entity;

import java.util.Arrays;
import java.util.List;

public enum Role {
    CLIENT("client", Arrays.asList("addComment","buyTracks","cancelOrder","orderTrack","payOrder"
            ,"showPlaylist","submitComment","submitPayment","changeLang","home","logout")),
    ADMIN("admin",Arrays.asList("addTrack","changeClientStatus","showClients"
            ,"submitTrack","changeLang","home","logout"));

    private String value;
    private List<String> permissions;

    Role(String value, List <String> permissions) {
        this.value = value;
        this.permissions = permissions;
    }

    public String getValue() {
        return value;
    }

    public List <String> getPermissions() {
        return permissions;
    }
}
