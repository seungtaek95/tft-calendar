use tft_calendar;

CREATE TABLE summoner (
    summoner_no INT UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    riot_summoner_id CHAR(63) NOT NULL,
    account_id CHAR(56) NOT NULL,
    puuid CHAR(78) NOT NULL,
    name VARCHAR(255) NOT NULL,
    profile_icon_id SMALLINT UNSIGNED NOT NULL,
    level SMALLINT UNSIGNED NOT NULL,
    last_fetched_at TIMESTAMP,
    PRIMARY KEY (summoner_no),
    UNIQUE (puuid),
    UNIQUE (name)
);

CREATE TABLE tft_match (
    match_no VARCHAR(255) NOT NULL,
    riot_match_id VARCHAR(255) NOT NULL,
    game_type SMALLINT NOT NULL,
    played_at TIMESTAMP NOT NULL,
    PRIMARY KEY (match_no),
    UNIQUE (riot_match_id)
);

CREATE TABLE tft_match_result (
    match_result_no VARCHAR(255) NOT NULL,
    match_no VARCHAR(255) NOT NULL,
    summoner_no INT UNSIGNED NOT NULL,
    position TINYINT UNSIGNED NOT NULL,
    playtime_in_seconds SMALLINT UNSIGNED NOT NULL,
    PRIMARY KEY (match_result_no),
    UNIQUE match_no_summoner_no (match_no, summoner_no),
    FOREIGN KEY (match_no) REFERENCES tft_match(match_no),
    FOREIGN KEY (summoner_no) REFERENCES summoner(summoner_no)
);