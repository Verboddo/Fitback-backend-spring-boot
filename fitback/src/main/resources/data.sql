INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

INSERT INTO users (email, password, username) VALUES ('admin@hotmail.com', '$2a$12$je/EqWM9L7cGTDBA59CnGO20FdbnE80gu9hufU2LOovciOTvMbMHu', 'admin');

INSERT INTO user_roles(user_id, role_id) VALUES (1, 2);