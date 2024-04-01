CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255)
);


CREATE TABLE posts (
                       id SERIAL PRIMARY KEY,
                       user_id INTEGER NOT NULL,
                       title VARCHAR(255) NOT NULL,
                       content TEXT,
                       CONSTRAINT fk_user
                           FOREIGN KEY(user_id)
                               REFERENCES users(user_id)
                               ON DELETE CASCADE
);

-- Вставка начальных данных в таблицу users.
INSERT INTO users (user_id, username, email, password) VALUES
                                                  ('1', 'user1', 'user1@example.com', 'password123'),
                                                  ('2', 'user2', 'user2@example.com', 'password321');
-- Вставка начальных данных в таблицу posts.
INSERT INTO posts (user_id, title, content) VALUES
                                                (1, 'First Post', 'Content of the first post'),
                                                (2, 'Second Post', 'Content of the second post');



-- Если у вас есть сущность Tag, связь ManyToMany обычно реализуется через дополнительную таблицу.
-- CREATE TABLE tags (
--     tag_id SERIAL PRIMARY KEY,
--     name VARCHAR(255) UNIQUE NOT NULL
-- );
--
-- CREATE TABLE post_tags (
--     post_id INT NOT NULL,
--     tag_id INT NOT NULL,
--     CONSTRAINT pk_post_tags PRIMARY KEY (post_id, tag_id),
--     CONSTRAINT fk_post
--         FOREIGN KEY(post_id)
--         REFERENCES posts(post_id)
--         ON DELETE CASCADE,
--     CONSTRAINT fk_tag
--         FOREIGN KEY(tag_id)
--         REFERENCES tags(tag_id)
--         ON DELETE CASCADE
-- );
