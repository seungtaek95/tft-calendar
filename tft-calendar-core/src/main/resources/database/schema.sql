use tft_calendar;

CREATE TABLE summoner (
    id INT UNSIGNED AUTO_INCREMENT NOT NULL,
    account_id VARCHAR(255) NOT NULL,
    puuid VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    profile_icon_id VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE (puuid)
);

CREATE TABLE tft_match (
    id VARCHAR(255) NOT NULL,
    match_id VARCHAR(255) NOT NULL,
    game_type VARCHAR(255) NOT NULL,
    played_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (match_id)
);