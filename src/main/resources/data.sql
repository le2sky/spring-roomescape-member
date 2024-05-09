INSERT INTO reservation_time (start_at)
VALUES ('10:00'),
       ('11:00'),
       ('13:00');

INSERT INTO theme (name, description, thumbnail)
VALUES ('레벨1 탈출',
        '우테코 레벨1를 탈출하는 내용입니다.',
        'https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg'),
       ('레벨2 탈출',
        '우테코 레벨2를 탈출하는 내용입니다.',
        'https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg'),
       ('레벨3 탈출',
        '우테코 레벨3를 탈출하는 내용입니다.',
        'https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg'),
       ('레벨4 탈출',
        '우테코 레벨4를 탈출하는 내용입니다.',
        'https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg'),
       ('레벨5 탈출',
        '우테코 레벨5를 탈출하는 내용입니다.',
        'https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg'),
       ('레벨6 탈출',
        '우테코 레벨6를 탈출하는 내용입니다.',
        'https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg'),
       ('레벨7 탈출',
        '우테코 레벨7를 탈출하는 내용입니다.',
        'https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg'),
       ('레벨8 탈출',
        '우테코 레벨8를 탈출하는 내용입니다.',
        'https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg'),
       ('레벨9 탈출',
        '우테코 레벨9를 탈출하는 내용입니다.',
        'https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg'),
       ('레벨10 탈출',
        '우테코 레벨10를 탈출하는 내용입니다.',
        'https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg'),
       ('레벨11 탈출',
        '우테코 레벨11를 탈출하는 내용입니다.',
        'https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg');

INSERT INTO reservation (name, date, time_id, theme_id)
VALUES ('브라운', DATEADD(DAY, -7, current_date), 1, 8),
       ('엘라', DATEADD(DAY, -6, current_date), 2, 7),
       ('릴리', DATEADD(DAY, -7, current_date), 3, 7),
       ('브라운', current_date, 1, 7),
       ('엘라', current_date, 2, 7),
       ('릴리', DATEADD(DAY, -1, current_date), 2, 7),
       ('브라운', DATEADD(DAY, -1, current_date), 1, 7),
       ('엘라', DATEADD(DAY, -1, current_date), 3, 7),
       ('릴리', DATEADD(DAY, -1, current_date), 2, 6),
       ('브라운', DATEADD(DAY, -1, current_date), 1, 6),
       ('엘라', DATEADD(DAY, -1, current_date), 3, 6),
       ('릴리', DATEADD(DAY, -7, current_date), 2, 6),
       ('브라운', DATEADD(DAY, -7, current_date), 1, 6),
       ('엘라', DATEADD(DAY, -7, current_date), 3, 6),
       ('릴리', DATEADD(DAY, -7, current_date), 2, 5),
       ('브라운', DATEADD(DAY, -7, current_date), 1, 5),
       ('엘라', DATEADD(DAY, -7, current_date), 3, 5),
       ('릴리', DATEADD(DAY, -6, current_date), 2, 5),
       ('브라운', DATEADD(DAY, -6, current_date), 1, 5),
       ('엘라', DATEADD(DAY, -6, current_date), 1, 4),
       ('릴리', DATEADD(DAY, -6, current_date), 2, 4),
       ('브라운', DATEADD(DAY, -1, current_date), 1, 4),
       ('엘라', DATEADD(DAY, -1, current_date), 2, 4),
       ('릴리', DATEADD(DAY, -1, current_date), 2, 3),
       ('브라운', DATEADD(DAY, -1, current_date), 1, 3),
       ('엘라', DATEADD(DAY, -1, current_date), 3, 3),
       ('릴리', DATEADD(DAY, -1, current_date), 2, 1);

INSERT INTO member (name, email, password)
VALUES ('어드민', 'admin@test.com', 'password');
