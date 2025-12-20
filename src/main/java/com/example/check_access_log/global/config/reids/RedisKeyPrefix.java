package com.example.check_access_log.global.config.reids;

public enum RedisKeyPrefix {

    BOARD_DETAIL("board_detail:v1:"),
    ITEM_DETAIL("item_detail:v1:"),
    DATE_DETAIL("date_detail:v1:");

    private final String prefix;

    RedisKeyPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
