CREATE DATABASE IF NOT EXISTS MONGEUL;

USE MONGEUL;

CREATE TABLE IF NOT EXISTS `User` (
                                      `user_id` INT NOT NULL auto_increment,
                                      `name` VARCHAR(255) NOT NULL,
                                      `email` VARCHAR(255) NOT NULL,
                                      PRIMARY KEY (`user_id`)
);

CREATE TABLE IF NOT EXISTS `Baby` (
                                      `baby_id` INT NOT NULL auto_increment,
                                      `user_id` INT NOT NULL,
                                      `baby_name` VARCHAR(255) NOT NULL,
                                      `baby_birthday` DATETIME NOT NULL,
                                      PRIMARY KEY (`baby_id`),
                                      FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`)
);

CREATE TABLE IF NOT EXISTS `Calendar_Photo` (
                                                `calendar_photo_id` INT NOT NULL auto_increment,
                                                `user_id` INT NOT NULL,
                                                `baby_id` INT NOT NULL,
                                                `file_path` VARCHAR(255) NULL,
                                                `date` DATETIME NULL,
                                                PRIMARY KEY (`calendar_photo_id`),
                                                FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`),
                                                FOREIGN KEY (`baby_id`) REFERENCES `Baby` (`baby_id`)
);

CREATE TABLE IF NOT EXISTS `Book` (
                                      book_id INT NOT NULL auto_increment,
                                      user_id INT NOT NULL,
                                      title VARCHAR(255) NOT NULL,
                                      cover_path VARCHAR(255) NOT NULL,
                                      start_date DATETIME NOT NULL,
                                      end_date DATETIME NOT NULL,
                                      generated_date DATETIME NOT NULL,
                                      PRIMARY KEY (book_id)
);

CREATE TABLE IF NOT EXISTS `Page` (
                                      page_id INT NOT NULL auto_increment,
                                      book_id INT NOT NULL,
                                      page_num INT NOT NULL,
                                      text VARCHAR(255) NOT NULL,
                                      illust_prompt VARCHAR(255) NOT NULL,
                                      image_path VARCHAR(255) NOT NULL,
                                      PRIMARY KEY (page_id),
                                      FOREIGN KEY (book_id) REFERENCES Book(book_id)
);

CREATE TABLE IF NOT EXISTS `Today_sum` (
                                           `today_id` INT NOT NULL auto_increment,
                                           `user_id` INT NOT NULL,
                                           `book_id` INT NULL,
                                           `content` VARCHAR(255) NULL,
                                           `start_date` DATETIME NOT NULL,
                                           `end_date` DATETIME NOT NULL,
                                           `generated_date` DATETIME NOT NULL,
                                           `revision_date` DATETIME NULL,
                                           PRIMARY KEY (`today_id`),
                                           FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`),
                                           FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`)
);

CREATE TABLE IF NOT EXISTS `Calendar` (
                                          `calendar_id` INT NOT NULL auto_increment,
                                          `user_id` INT NOT NULL,
                                          `baby_id` INT NULL,
                                          `calendar_photo_id` INT NULL,
                                          `today_id` INT NULL,
                                          `book_id` INT NULL,
                                          `title` VARCHAR(255) NULL,
                                          `start_time` DATETIME NULL,
                                          `end_time` DATETIME NULL,
                                          `location` VARCHAR(255) NULL,
                                          PRIMARY KEY (`calendar_id`),
                                          FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`),
                                          FOREIGN KEY (`baby_id`) REFERENCES `Baby` (`baby_id`),
                                          FOREIGN KEY (`calendar_photo_id`) REFERENCES `Calendar_Photo` (`calendar_photo_id`),
                                          FOREIGN KEY (`today_id`) REFERENCES `Today_sum` (`today_id`),
                                          FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`)
);

CREATE TABLE IF NOT EXISTS `Memo` (
                                      `memo_id` INT NOT NULL auto_increment,
                                      `user_id` INT NOT NULL,
                                      `today_id` INT NULL,
                                      `book_id` INT NULL,
                                      `date` DATETIME NULL,
                                      `content` VARCHAR(255) NULL,
                                      PRIMARY KEY (`memo_id`),
                                      FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`),
                                      FOREIGN KEY (`today_id`) REFERENCES `Today_sum` (`today_id`),
                                      FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`)
);

CREATE TABLE IF NOT EXISTS `Alim` (
                                      `alim_id` INT NOT NULL auto_increment,
                                      `user_id` INT NOT NULL,
                                      `baby_id` INT NOT NULL,
                                      `content` VARCHAR(255) NULL,
                                      `date` DATETIME NOT NULL,
                                      PRIMARY KEY (`alim_id`),
                                      FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`),
                                      FOREIGN KEY (`baby_id`) REFERENCES `Baby` (`baby_id`)
);

CREATE TABLE IF NOT EXISTS `Alim_inf` (
                                          `aliminf_id` INT NOT NULL auto_increment,
                                          `alim_id` INT NOT NULL,
                                          `user_id` INT NOT NULL,
                                          `baby_id` INT NOT NULL,
                                          `today_id` INT NULL,
                                          `name` VARCHAR(255) NULL,
                                          `emotion` VARCHAR(255) NULL,
                                          `health` VARCHAR(255) NULL,
                                          `nutrition` VARCHAR(255) NULL,
                                          `activities` VARCHAR(255) NULL,
                                          `special` VARCHAR(255) NULL,
                                          `keywords` VARCHAR(255) NULL,
                                          `diary` VARCHAR(255) NULL,
                                          `date` datetime NOT NULL,
                                          PRIMARY KEY (`aliminf_id`),
                                          FOREIGN KEY (`alim_id`) REFERENCES `Alim` (`alim_id`),
                                          FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`),
                                          FOREIGN KEY (`baby_id`) REFERENCES `Baby` (`baby_id`),
                                          FOREIGN KEY (`today_id`) REFERENCES `Today_sum` (`today_id`)
);

-- 더미 데이터 삽입
-- User 테이블 더미 데이터
INSERT INTO `User` (`name`, `email`) VALUES
                                         ('김철수', 'chulsoo@example.com'),
                                         ('이영희', 'younghee@example.com'),
                                         ('박지민', 'jimin@example.com');

-- Baby 테이블 더미 데이터
INSERT INTO `Baby` (`user_id`, `baby_name`, `baby_birthday`) VALUES
                                                                 (1, '김쑥쑥', '2022-05-15 00:00:00'),
                                                                 (2, '이튼튼', '2023-01-10 00:00:00'),
                                                                 (3, '박몽글', '2021-11-20 00:00:00');

-- Calendar_Photo 테이블 더미 데이터
INSERT INTO `Calendar_Photo` (`user_id`, `baby_id`, `file_path`, `date`) VALUES
                                                                             (1, 1, '/photos/baby1_photo1.jpg', '2024-05-15 14:45:00'),
                                                                             (2, 2, '/photos/baby2_photo1.jpg', '2024-01-10 14:45:00'),
                                                                             (3, 3, '/photos/baby3_photo1.jpg', '2024-11-20 14:45:00');

-- Book 테이블 더미 데이터
INSERT INTO `Book` (`user_id`, `title`, `cover_path`, `start_date`, `end_date`, `generated_date`) VALUES
                                                                                                      (1, '김쑥쑥의 첫 번째 이야기', '/covers/book1_cover.jpg', '2024-01-01 00:00:00', '2024-01-31 23:59:59', '2024-02-01 10:00:00'),
                                                                                                      (2, '이튼튼의 신나는 모험', '/covers/book2_cover.jpg', '2024-02-01 00:00:00', '2024-02-29 23:59:59', '2024-03-01 10:00:00'),
                                                                                                      (3, '박몽글의 꿈과 희망', '/covers/book3_cover.jpg', '2024-03-01 00:00:00', '2024-03-31 23:59:59', '2024-04-01 10:00:00');

-- Page 테이블 더미 데이터
INSERT INTO `Page` (`book_id`, `page_num`, `text`, `illust_prompt`, `image_path`) VALUES
                                                                                      (1, 1, '김쑥쑥이가 처음으로 혼자 앉았어요.', '아기가 혼자 앉아있는 모습, 밝고 따뜻한 분위기', '/images/book1_page1.jpg'),
                                                                                      (1, 2, '엄마 아빠가 너무 기뻐했답니다.', '기뻐하는 부모님과 아기, 행복한 가족 분위기', '/images/book1_page2.jpg'),
                                                                                      (2, 1, '이튼튼이가 첫 걸음마를 떼었어요.', '아기가 첫 걸음마를 떼는 모습, 응원하는 가족들', '/images/book2_page1.jpg'),
                                                                                      (2, 2, '온 가족이 박수를 치며 축하했답니다.', '박수치는 가족들과 웃는 아기, 축하 분위기', '/images/book2_page2.jpg'),
                                                                                      (3, 1, '박몽글이가 처음으로 "엄마"라고 말했어요.', '아기가 말하는 모습, 놀란 표정의 엄마', '/images/book3_page1.jpg'),
                                                                                      (3, 2, '엄마의 눈에서 기쁨의 눈물이 흘렀답니다.', '눈물 흘리는 엄마와 웃는 아기, 감동적인 분위기', '/images/book1.jpg');

