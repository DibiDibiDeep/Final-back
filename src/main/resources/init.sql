CREATE DATABASE IF NOT EXISTS MONGEUL;

USE MONGEUL;

CREATE TABLE IF NOT EXISTS `User` (
    `user_id`	INT	NOT NULL auto_increment,
    `name`	VARCHAR(255)	NULL,
    `email`	VARCHAR(255)	NULL,
    PRIMARY KEY (`user_id`)
    );

-- User 테이블 더미 데이터
INSERT INTO `User` (`user_id`, `name`, `email`) VALUES
    (1, 'John Doe', 'john@example.com'),
    (2, 'Jane Smith', 'jane@example.com');

CREATE TABLE IF NOT EXISTS `baby` (
                                      `baby_id`	INT	NOT NULL auto_increment,
                                      `user_id`	INT	NOT NULL,
                                      `baby_name`	VARCHAR(255)	NULL,
    `baby_birthday`	DATETIME	NULL,
    PRIMARY KEY (`baby_id`),
    FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`)
    );

-- baby 테이블 더미 데이터
INSERT INTO `baby` (`baby_id`, `user_id`, `baby_name`, `baby_birthday`) VALUES
                                                                            (1, 1, 'Baby John', '2022-01-15'),
                                                                            (2, 2, 'Baby Jane', '2021-12-20');

CREATE TABLE IF NOT EXISTS `Calendar_Photo` (
                                                `calendar_photo_id`    INT    NOT NULL auto_increment,
                                                `user_id`    INT   NULL,
                                                `baby_id`    int   NULL,
                                                `file_path`    VARCHAR(255)    NULL,
    `taken_at`    VARCHAR(5)    NULL,  -- DATETIME에서 MM-dd 형식으로 변경
    PRIMARY KEY (`calendar_photo_id`),
    FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`),
    FOREIGN KEY (`baby_id`) REFERENCES `baby` (`baby_id`)
    );

-- Calendar_Photo 테이블 더미 데이터 수정
INSERT INTO `Calendar_Photo` (`calendar_photo_id`, `user_id`, `baby_id`, `file_path`, `taken_at`) VALUES
                                                                                                      (1, 1, 1, '/photos/calendar1.jpg', '09-01'),
                                                                                                      (2, 2, 2, '/photos/calendar2.jpg', '09-02');

-- Calendar 테이블 구조 수정
CREATE TABLE IF NOT EXISTS `Calendar` (
                                          `calendar_id`    INT    NOT NULL auto_increment,
                                          `user_id`    INT NULL,
                                          `calendar_photo_id`    INT    NULL,
                                          `baby_id`    int NULL,
                                          `title`    VARCHAR(255)    NULL,
    `description`    TEXT    NULL,
    `date`    VARCHAR(5)    NULL,  -- MM-dd 형식으로 변경
    `location`    VARCHAR(255)    NULL,
    PRIMARY KEY (`calendar_id`),
    FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`),
    FOREIGN KEY (`calendar_photo_id`) REFERENCES `Calendar_Photo` (`calendar_photo_id`),
    FOREIGN KEY (`baby_id`) REFERENCES `baby` (`baby_id`)
    );

-- Calendar 테이블 더미 데이터 수정
INSERT INTO `Calendar` (`calendar_id`, `user_id`, `calendar_photo_id`, `baby_id`, `title`, `description`, `date`, `location`) VALUES
                                                                                                                                  (1, 1, 1, 1, 'Birthday Party', 'Baby John\'s first birthday party', '09-01', 'John\'s House'),
                                                                                                                                  (2, 2, 2, 2, 'Playdate', 'Baby Jane\'s playdate with friends', '09-02', 'Jane\'s House');

-- Today_sum 테이블 구조 수정
CREATE TABLE IF NOT EXISTS `Today_sum` (
                                           `today_id`    INT    NOT NULL auto_increment,
                                           `user_id`    INT    NOT NULL,
                                           `calendar_id`    INT    NULL,
                                           `generated_date`    VARCHAR(5)    NULL,  -- MM-dd 형식으로 변경
    `content`    TEXT    NULL,
    PRIMARY KEY (`today_id`),
    FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`),
    FOREIGN KEY (`calendar_id`) REFERENCES `Calendar` (`calendar_id`)
    );

-- Today_sum 테이블 더미 데이터 수정
INSERT INTO `Today_sum` (`today_id`, `user_id`, `calendar_id`, `generated_date`, `content`) VALUES
                                                                                                (1, 1, 1, '09-01', 'It was a great day at the party.'),
                                                                                                (2, 2, 2, '09-02', 'A fun day with friends.');

CREATE TABLE IF NOT EXISTS `Memo` (
                                      `memo_id`    INT    NOT NULL auto_increment,
                                      `today_id`    INT    NULL,
                                      `user_id`    INT    NOT NULL,
                                      `calendar_id`    INT    NULL,
                                      `created_at`    VARCHAR(5)    NULL,  -- MM-dd 형식으로 변경
    `content`    TEXT    NULL,
    PRIMARY KEY (`memo_id`),
    FOREIGN KEY (`today_id`) REFERENCES `Today_sum` (`today_id`),
    FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`),
    FOREIGN KEY (`calendar_id`) REFERENCES `Calendar` (`calendar_id`)
    );

-- Memo 테이블 더미 데이터 수정
INSERT INTO `Memo` (`memo_id`, `today_id`, `user_id`, `calendar_id`, `created_at`, `content`) VALUES
    (1, 1, 1, 1, '09-01', 'Memo about John\'s birthday party'),
    (2, 2, 2, 2, '09-02', 'Memo about Jane\'s playdate');

CREATE TABLE IF NOT EXISTS `Alim` (
                                      `alim_id`    INT    NOT NULL auto_increment,
                                      `baby_id`    INT    NOT NULL,
                                      `content`    TEXT    NULL,  -- file_path 대신 content로 변경
                                      `created_at`    VARCHAR(5)    NULL,  -- taken_at을 created_at으로 변경하고 MM-dd 형식으로 수정
    PRIMARY KEY (`alim_id`),
    FOREIGN KEY (`baby_id`) REFERENCES `baby` (`baby_id`)
    );

-- Alim 테이블 더미 데이터
INSERT INTO `Alim` (`alim_id`, `baby_id`, `content`, `created_at`) VALUES
    (1, 1, 'John\'s first steps!', '09-01'),
    (2, 2, 'Jane\'s first words: "Mama"', '09-02');

-- Alim_inf 테이블의 외래 키 참조 수정
CREATE TABLE IF NOT EXISTS `Alim_inf` (
                                          `aliminf_id`    INT    NOT NULL auto_increment,
                                          `alim_id`    INT    NULL,
                                          `today_id`    INT    NULL,
                                          `Field`    VARCHAR(255)    NULL,
    PRIMARY KEY (`aliminf_id`),
    FOREIGN KEY (`alim_id`) REFERENCES `Alim` (`alim_id`),
    FOREIGN KEY (`today_id`) REFERENCES `Today_sum` (`today_id`)
    );

-- Alim_inf 테이블 더미 데이터는 변경 없음
INSERT INTO `Alim_inf` (`aliminf_id`, `alim_id`, `today_id`, `Field`) VALUES
    (1, 1, 1, 'John\'s Alim information'),
(2, 2, 2, 'Jane\'s Alim information');
