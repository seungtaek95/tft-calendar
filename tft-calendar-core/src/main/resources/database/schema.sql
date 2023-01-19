use tft_calendar;

CREATE TABLE summoner (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    summoner_id CHAR(63) NOT NULL,
    account_id CHAR(56) NOT NULL,
    puuid CHAR(78) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL UNIQUE,
    profile_icon_id SMALLINT UNSIGNED NOT NULL,
    level SMALLINT UNSIGNED NOT NULL,
    last_fetched_match_id VARCHAR(255),
    last_fetched_at TIMESTAMP
);

CREATE TABLE tft_match (
    id VARCHAR(255) NOT NULL,
    match_id VARCHAR(255) NOT NULL,
    game_type VARCHAR(255) NOT NULL,
    played_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (match_id)
);