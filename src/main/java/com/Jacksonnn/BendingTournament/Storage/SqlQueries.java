package com.Jacksonnn.BendingTournament.Storage;

public enum SqlQueries {
    CREATE_TOURNAMENTS("CREATE TABLE IF NOT EXISTS `tournaments` ("
            + "`id` INT NOT NULL AUTO_INCREMENT,"
            + "`startedBy` TEXT NOT NULL,"
            + "`name` TEXT,"
            + "`winner` TEXT"
            + "PRIMARY KEY (`id`)"
            + ");", "CREATE TABLE IF NOT EXISTS tournaments ("
            + "id integer PRIMARY KEY AUTOINCREMENT,"
            + "startedBy text,"
            + "name text,"
            + "winner text"
            + ");"),
    CREATE_USERS("CREATE TABLE IF NOT EXISTS `users` ("
            + "`id` INT NOT NULL AUTO_INCREMENT,"
            + "`uuid` TEXT NOT NULL,"
            + "`tournament` TEXT,"
            + "`tournament_element` TEXT,"
            + "PRIMARY KEY (`id`)"
            + ");", "CREATE TABLE IF NOT EXISTS users ("
            + "id integer PRIMARY KEY AUTOINCREMENT,"
            + "uuid text,"
            + "tournament text,"
            + "tournament_element text"
            + ");"),
    CREATE_TOURNAMENT(
            "INSERT INTO `tournaments` (startedBy, name) VALUES (?, ?)",
            "INSERT INTO tournaments (startedBy, name) VALUES (?, ?)"),
    CREATE_USER(
            "INSERT INTO `users` (uuid) VALUES (?)",
            "INSERT INTO users (uuid) VALUES (?)"),
    GET_USERS(
            "SELECT * FROM `users`",
            "SELECT * FROM users"),
    GET_TOURNAMENTS(
            "SELECT * FROM `tournaments`",
            "SELECT * FROM tournaments"),
    GET_USER(
            "SELECT * FROM `users` WHERE uuid=?",
            "SELECT * FROM users WHERE uuid=?"),
    SET_WINNER(
            "UPDATE `tournaments` SET winner=? WHERE name=?",
            "UPDATE tournaments SET winner=? WHERE name=?"),
    JOIN_TOURNAMENT(
            "UPDATE `users` SET tournament=?, tournament_element=? WHERE uuid=?",
            "UPDATE users SET tournament=?, tournament_element=? WHERE uuid=?"),
    GET_TOURNAMENT_INFO(
            "SELECT * FROM `tournaments` WHERE name=?",
            "SELECT * FROM tournaments WHERE name=?"),
    GET_TOURNAMENT_USERS(
            "SELECT * FROM `users` WHERE tournament=?",
            "SELECT * FROM users WHERE tournament=?"),
    GET_TOURNAMENT_KEY(
            "SELECT `id` FROM `tournaments` WHERE name=?",
            "SELECT id FROM tournaments WHERE name=?"),
    LEAVE_TOURNAMENT(
            "UPDATE `users` SET tournament=?, tournament_element=? WHERE uuid=? AND tournament=?",
            "UPDATE users SET tournament=?, tournament_element=? WHERE uuid=? AND tournament=?"),
    DELETE_TOURNAMENT(
            "DELETE FROM `tournaments` WHERE tournament=?",
            "DELETE FROM tournaments WHERE tournament=?");

    private String mysqlQuery;
    private String sqliteQuery;

    SqlQueries(String mysqlQuery, String sqliteQuery) {
        this.mysqlQuery = mysqlQuery;
        this.sqliteQuery = sqliteQuery;
    }


    public String getMysqlQuery() {
        return mysqlQuery;
    }

    public String getSqliteQuery() {
        return sqliteQuery;
    }
}
