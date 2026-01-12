INSERT INTO users (username, active) VALUES ('andrea', true)
    ON CONFLICT (username) DO NOTHING;

INSERT INTO users (username, active) VALUES ('mario', true)
    ON CONFLICT (username) DO NOTHING;
