use tft_calendar;

CREATE TABLE summoner (
    summoner_no INT UNSIGNED AUTO_INCREMENT NOT NULL,
    summoner_id CHAR(63) NOT NULL,
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
    match_id VARCHAR(255) NOT NULL,
    game_type_id SMALLINT NOT NULL,
    played_at TIMESTAMP NOT NULL,
    PRIMARY KEY (match_no),
    UNIQUE (match_id)
);

CREATE TABLE tft_match_result (
    match_result_no VARCHAR(255) NOT NULL,
    match_no VARCHAR(255) NOT NULL,
    summoner_no INT UNSIGNED NOT NULL,
    placement TINYINT UNSIGNED NOT NULL,
    playtime_in_seconds SMALLINT UNSIGNED NOT NULL,
    PRIMARY KEY (match_result_no),
    UNIQUE match_no_summoner_no (match_no, summoner_no),
    FOREIGN KEY (match_no) REFERENCES tft_match(match_no),
    FOREIGN KEY (summoner_no) REFERENCES summoner(summoner_no)
);