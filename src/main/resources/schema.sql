CREATE TABLE users (
                           id SERIAL PRIMARY KEY,
                           username VARCHAR(255) NOT NULL UNIQUE,
                           email VARCHAR(255) NOT NULL UNIQUE,
                           password VARCHAR(255) NOT NULL
    );


CREATE TABLE posts (
                       id SERIAL PRIMARY KEY,
                       user_id INTEGER NOT NULL,
                       title VARCHAR(255) NOT NULL,
                       content TEXT,
                       CONSTRAINT fk_user
                           FOREIGN KEY(user_id)
                               REFERENCES users(id)
                               ON DELETE CASCADE
);
INSERT INTO users (username, email, password) VALUES
                                                  ('user1', 'user1@example.com', 'password123'),
                                                  ('user2', 'user2@example.com', 'password321');

INSERT INTO posts (user_id, title, content) VALUES
                                                (3, 'First Post', 'Content of the first post'),
                                                (4, 'Second Post', 'Content of the second post');



