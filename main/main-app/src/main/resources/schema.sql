DROP TABLE IF EXISTS comments_history;
DROP TABLE IF EXISTS comments;
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

CREATE TABLE IF NOT EXISTS comments
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    content   TEXT                        NOT NULL,
    created   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    author_id BIGINT REFERENCES users (id) ON DELETE CASCADE,
    event_id  BIGINT REFERENCES events (id) ON DELETE CASCADE,
    parent_id BIGINT,
    status    VARCHAR                     NOT NULL
);

CREATE TABLE IF NOT EXISTS comments_history
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    comment_id  BIGINT REFERENCES comments (id) ON DELETE CASCADE,
    modified_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    old_content TEXT                        NOT NULL,
    new_content TEXT                        NOT NULL,
    old_status  TEXT                        NOT NULL,
    new_status  TEXT                        NOT NULL
);

CREATE OR REPLACE FUNCTION log_comment_history()
    RETURNS TRIGGER AS
'
    BEGIN
        IF NEW.content <> OLD.content OR NEW.status <> OLD.status THEN
            INSERT INTO comments_history(comment_id, modified_at, old_content, new_content, old_status, new_status)
            VALUES (NEW.id, now(), OLD.content, NEW.content, OLD.status, NEW.status);
        end if;

        RETURN NEW;
    END; '
    LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER comment_changes
    BEFORE UPDATE
    ON comments
    FOR EACH ROW
EXECUTE PROCEDURE log_comment_history();