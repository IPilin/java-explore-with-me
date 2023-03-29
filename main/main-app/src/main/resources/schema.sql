DROP TABLE IF EXISTS compilations_events;
DROP TABLE IF EXISTS compilations;
DROP TABLE IF EXISTS requests;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email VARCHAR UNIQUE NOT NULL,
    name  VARCHAR        NOT NULL
);

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS locations
(
    id  BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    lat REAL,
    lon REAL
);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title              VARCHAR(120)                NOT NULL,
    annotation         VARCHAR(2000)               NOT NULL,
    description        VARCHAR(7000)               NOT NULL,
    created            TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    event_date         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    location_id        BIGINT REFERENCES locations (id) ON DELETE CASCADE,
    category_id        BIGINT REFERENCES categories (id) ON DELETE CASCADE,
    paid               BOOLEAN                     NOT NULL,
    participant_limit  INTEGER                     NOT NULL,
    request_moderation BOOLEAN                     NOT NULL,
    initiator_id       BIGINT REFERENCES users (id) ON DELETE CASCADE,
    state              VARCHAR(64)                 NOT NULL,
    published          TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    created      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    event_id     BIGINT REFERENCES events (id) ON DELETE CASCADE,
    requester_id BIGINT REFERENCES users (id) ON DELETE CASCADE,
    status       VARCHAR(16)                 NOT NULL
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title  VARCHAR UNIQUE NOT NULL,
    pinned BOOLEAN        NOT NULL
);

CREATE TABLE IF NOT EXISTS compilations_events
(
    compilation_id BIGINT REFERENCES compilations (id) ON DELETE CASCADE,
    event_id       BIGINT REFERENCES events (id) ON DELETE CASCADE,
    PRIMARY KEY (compilation_id, event_id)
);