CREATE TABLE users (
                           user_id SERIAL PRIMARY KEY,
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
                           FOREIGN KEY(id)
                               REFERENCES users(user_id)
                               ON DELETE CASCADE
);
INSERT INTO users (username, email, password) VALUES
                                                  ('user1', 'user1@example.com', 'password123'),
                                                  ('user2', 'user2@example.com', 'password321'),
                                                  ('user3', 'user3@example.com', 'password321'),
                                                  ('user4', 'user4@example.com', 'password3232');


INSERT INTO posts (user_id, title, content) VALUES
                                                (1, 'First Post', 'Content of the first post'),
                                                (1, 'Second Post', 'Content of the second post'),
                                                (2, 'Third Post', 'Content of the third person');



