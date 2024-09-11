CREATE DATABASE IF NOT EXISTS MONGEUL;

USE MONGEUL;

-- User 테이블 생성
CREATE TABLE IF NOT EXISTS User (
                                    user_id INT NOT NULL auto_increment,
                                    name VARCHAR(255) NOT NULL,
                                    email VARCHAR(255) NOT NULL,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    PRIMARY KEY (user_id)
);

-- User 테이블 더미 데이터
INSERT INTO User (name, email) VALUES
                                   ('김철수', 'chulsoo@example.com'),
                                   ('이영희', 'younghee@example.com'),
                                   ('박지민', 'mmongeul@gmail.com');

-- Baby 테이블 생성
CREATE TABLE IF NOT EXISTS Baby (
                                    baby_id INT NOT NULL AUTO_INCREMENT,
                                    user_id INT NOT NULL,
                                    baby_name VARCHAR(255) NOT NULL,
                                    birth DATETIME NOT NULL,
                                    gender VARCHAR(10) NOT NULL,
                                    PRIMARY KEY (baby_id),
                                    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

-- Baby 테이블 더미 데이터
INSERT INTO Baby ( user_id, baby_name, birth, gender) VALUES
                                                          (1,'김쑥쑥', '2022-05-15 00:00:00', 'M'),
                                                          (2,'이튼튼', '2023-01-10 00:00:00', 'F'),
                                                          (3,'박몽글', '2021-11-20 00:00:00', 'M');

-- Baby_Photo 테이블 생성
CREATE TABLE IF NOT EXISTS Baby_Photo (
                                          baby_photo_id INT NOT NULL AUTO_INCREMENT,
                                          baby_id INT NOT NULL,
                                          file_path VARCHAR(255) NOT NULL,
                                          upload_date DATETIME NOT NULL,
                                          PRIMARY KEY (baby_photo_id),
                                          FOREIGN KEY (baby_id) REFERENCES Baby (baby_id)
);

-- Baby_Photo 테이블 더미 데이터
INSERT INTO Baby_Photo (baby_id, file_path, upload_date)
VALUES
    (1, '/photos/baby1_photo1.jpg', '2024-09-01 10:00:00'),
    (2, '/photos/baby2_photo1.jpg', '2024-09-05 12:30:00'),
    (3, '/photos/baby3_photo1.jpg', '2024-09-10 15:45:00');

-- Book 테이블 생성
CREATE TABLE IF NOT EXISTS Book (
                                    book_id INT NOT NULL auto_increment,
                                    user_id INT NOT NULL,
                                    title VARCHAR(255) NOT NULL,
                                    cover_path VARCHAR(255) NOT NULL,
                                    start_date DATETIME NOT NULL,
                                    end_date DATETIME NOT NULL,
                                    generated_date DATETIME NOT NULL,
                                    PRIMARY KEY (book_id),
                                    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

-- Book 테이블 더미 데이터
INSERT INTO Book ( user_id, title, cover_path, start_date, end_date, generated_date) VALUES
                                                                                         ( 1, '김쑥쑥의 첫 번째 이야기', '/covers/book1_cover.jpg', '2024-01-01 00:00:00', '2024-01-31 23:59:59', '2024-02-01 10:00:00'),
                                                                                         ( 2, '이튼튼의 신나는 모험', '/covers/book2_cover.jpg', '2024-02-01 00:00:00', '2024-02-29 23:59:59', '2024-03-01 10:00:00'),
                                                                                         ( 3, '박몽글의 꿈과 희망', '/covers/book3_cover.jpg', '2024-03-01 00:00:00', '2024-03-31 23:59:59', '2024-04-01 10:00:00');

-- Page 테이블 생성
CREATE TABLE IF NOT EXISTS Page (
                                    page_id INT NOT NULL auto_increment,
                                    book_id INT NOT NULL,
                                    page_num INT NOT NULL,
                                    text VARCHAR(255) NOT NULL,
                                    illust_prompt VARCHAR(255) NOT NULL,
                                    image_path VARCHAR(255) NOT NULL,
                                    PRIMARY KEY (page_id),
                                    FOREIGN KEY (book_id) REFERENCES Book(book_id)
);

-- Page 테이블 더미 데이터
INSERT INTO Page ( book_id, page_num, text, illust_prompt, image_path) VALUES
                                                                           (1, 1, '김쑥쑥이가 처음으로 혼자 앉았어요.', '아기가 혼자 앉아있는 모습, 밝고 따뜻한 분위기', '/images/book1_page1.jpg'),
                                                                           ( 1, 2, '엄마 아빠가 너무 기뻐했답니다.', '기뻐하는 부모님과 아기, 행복한 가족 분위기', '/images/book1_page2.jpg'),
                                                                           (2, 1, '이튼튼이가 첫 걸음마를 떼었어요.', '아기가 첫 걸음마를 떼는 모습, 응원하는 가족들', '/images/book2_page1.jpg'),
                                                                           (2, 2, '온 가족이 박수를 치며 축하했답니다.', '박수치는 가족들과 웃는 아기, 축하 분위기', '/images/book2_page2.jpg'),
                                                                           (3, 1, '박몽글이가 처음으로 "엄마"라고 말했어요.', '아기가 말하는 모습, 놀란 표정의 엄마', '/images/book3_page1.jpg'),
                                                                           (3, 2, '엄마의 눈에서 기쁨의 눈물이 흘렀답니다.', '눈물 흘리는 엄마와 웃는 아기, 감동적인 분위기', '/images/book3_page2.jpg');

-- Today_sum 테이블 생성
CREATE TABLE IF NOT EXISTS Today_sum (
                                         today_id INT NOT NULL auto_increment,
                                         user_id INT NOT NULL,
                                         book_id INT NULL,
                                         content VARCHAR(255) NULL,
                                         start_date DATETIME NOT NULL,
                                         end_date DATETIME NOT NULL,
                                         generated_date DATETIME NOT NULL,
                                         revision_date DATETIME NULL,
                                         PRIMARY KEY (today_id),
                                         FOREIGN KEY (user_id) REFERENCES User (user_id),
                                         FOREIGN KEY (book_id) REFERENCES book (book_id)
);

-- Today_sum 테이블 더미 데이터
INSERT INTO Today_sum (user_id, book_id, content, start_date, end_date, generated_date, revision_date) VALUES
                                                                                                           ( 1, 1, '오늘 아기는 처음으로 혼자 앉았어요...', '2024-03-01 00:00:00', '2024-03-01 23:59:59', '2024-03-01 22:00:00', NULL),
                                                                                                           ( 2, 2, '오늘 아기가 첫 걸음마를 뗐어요...', '2024-03-08 00:00:00', '2024-03-08 23:59:59', '2024-03-08 22:00:00', NULL),
                                                                                                           ( 3, 3, '오늘 아기가 처음으로 "엄마"라고 말했어요...', '2024-03-15 00:00:00', '2024-03-15 23:59:59', '2024-03-15 22:00:00', NULL);

-- Calendar_Photo 테이블 생성
CREATE TABLE IF NOT EXISTS Calendar_Photo (
                                              calendar_photo_id INT NOT NULL auto_increment,
                                              user_id INT NOT NULL,
                                              baby_id INT NOT NULL,
                                              file_path VARCHAR(255) NULL,
                                              date DATETIME NULL,
                                              PRIMARY KEY (calendar_photo_id),
                                              FOREIGN KEY (user_id) REFERENCES User (user_id),
                                              FOREIGN KEY (baby_id) REFERENCES Baby (baby_id)
);

-- Calendar_Photo 테이블 더미 데이터
INSERT INTO Calendar_Photo (user_id, baby_id, file_path, date) VALUES
                                                                   ( 1, 1, '/photos/baby1_photo1.jpg', '2024-05-15 14:45:00'),
                                                                   ( 2, 2, '/photos/baby2_photo1.jpg', '2024-01-10 14:45:00'),
                                                                   ( 3, 3, '/photos/baby3_photo1.jpg', '2024-11-20 14:45:00');

-- Calendar 테이블 생성
CREATE TABLE IF NOT EXISTS Calendar (
                                        calendar_id INT NOT NULL auto_increment,
                                        user_id INT NOT NULL,
                                        baby_id INT NULL,
                                        calendar_photo_id INT NULL,
                                        today_id INT NULL,
                                        book_id INT NULL,
                                        title VARCHAR(255) NULL,
                                        start_time DATETIME NULL,
                                        end_time DATETIME NULL,
                                        location VARCHAR(255) NULL,
                                        PRIMARY KEY (calendar_id),
                                        FOREIGN KEY (user_id) REFERENCES User (user_id),
                                        FOREIGN KEY (baby_id) REFERENCES Baby (baby_id),
                                        FOREIGN KEY (calendar_photo_id) REFERENCES Calendar_Photo (calendar_photo_id),
                                        FOREIGN KEY (today_id) REFERENCES Today_sum (today_id),
                                        FOREIGN KEY (book_id) REFERENCES book (book_id)
);

-- Calendar 테이블 더미 데이터
INSERT INTO Calendar (user_id, baby_id, calendar_photo_id, today_id, book_id, title, start_time, end_time, location) VALUES
                                                                                                                         ( 1, 1, 1, 1, 1, '첫 돌 촬영', '2024-05-15 10:00:00', '2024-05-15 12:00:00', '스튜디오'),
                                                                                                                         ( 2, 2, 2, 2, 2, '백일 잔치', '2024-04-20 11:00:00', '2024-04-20 14:00:00', '자택'),
                                                                                                                         ( 3, 3, 3, 3, 3, '소아과 검진', '2024-03-25 14:00:00', '2024-03-25 15:00:00', '행복 소아과');

-- Memo 테이블 생성
CREATE TABLE IF NOT EXISTS Memo (
                                    memo_id INT NOT NULL auto_increment,
                                    user_id INT NOT NULL,
                                    today_id INT NULL,
                                    book_id INT NULL,
                                    date DATETIME NULL,
                                    content VARCHAR(255) NULL,
                                    PRIMARY KEY (memo_id),
                                    FOREIGN KEY (user_id) REFERENCES User (user_id),
                                    FOREIGN KEY (today_id) REFERENCES Today_sum (today_id),
                                    FOREIGN KEY (book_id) REFERENCES book (book_id)
);

-- Memo 테이블 더미 데이터
INSERT INTO Memo ( user_id, today_id, book_id, date, content) VALUES
                                                                  (1, 1, 1, '2024-03-01 15:30:00', '오늘 아기가 처음으로 혼자 앉았다. 정말 감동적인 순간!'),
                                                                  (2, 2, 2, '2024-03-08 18:45:00', '아기가 첫 걸음마를 뗐다. 너무 자랑스럽다!'),
                                                                  (3, 3, 3, '2024-03-15 20:00:00', '드디어 아기가 "엄마"라고 말했다. 눈물이 날 것 같다.');

-- Alim 테이블 생성
CREATE TABLE IF NOT EXISTS Alim (
                                    alim_id INT NOT NULL auto_increment,
                                    user_id INT NOT NULL,
                                    baby_id INT NOT NULL,
                                    content VARCHAR(255) NULL,
                                    date DATETIME NOT NULL,
                                    PRIMARY KEY (alim_id),
                                    FOREIGN KEY (user_id) REFERENCES User (user_id),
                                    FOREIGN KEY (baby_id) REFERENCES Baby (baby_id)
);

-- Alim 테이블 더미 데이터
INSERT INTO Alim ( user_id, baby_id, content, date) VALUES
                                                        (1, 1, '오늘의 특별한 순간: 아기가 처음으로 혼자 앉았어요!', '2024-03-01 22:00:00'),
                                                        (2, 2, '축하해요! 아기가 첫 걸음마를 뗐어요.', '2024-03-08 22:00:00'),
                                                        (3, 3, '감동의 순간: 아기가 처음으로 "엄마"라고 말했어요.', '2024-03-15 22:00:00');

-- Alim_inf 테이블 생성
CREATE TABLE IF NOT EXISTS Alim_inf (
                                        aliminf_id INT NOT NULL auto_increment,
                                        alim_id INT NOT NULL,
                                        user_id INT NOT NULL,
                                        baby_id INT NOT NULL,
                                        today_id INT NULL,
                                        name VARCHAR(255) NULL,
                                        emotion VARCHAR(255) NULL,
                                        health VARCHAR(255) NULL,
                                        nutrition VARCHAR(255) NULL,
                                        activities VARCHAR(255) NULL,
                                        special VARCHAR(255) NULL,
                                        keywords VARCHAR(255) NULL,
                                        diary VARCHAR(255) NULL,
                                        date datetime NOT NULL,
                                        PRIMARY KEY (aliminf_id),
                                        FOREIGN KEY (alim_id) REFERENCES Alim (alim_id),
                                        FOREIGN KEY (user_id) REFERENCES User (user_id),
                                        FOREIGN KEY (baby_id) REFERENCES Baby (baby_id),
                                        FOREIGN KEY (today_id) REFERENCES Today_sum (today_id)
);

-- Alim_inf 테이블 더미 데이터
INSERT INTO Alim_inf (alim_id, user_id, baby_id, today_id, name, emotion, health, nutrition, activities, special, keywords, diary, date) VALUES
                                                                                                                                             (1, 1, 1, 1, '김쑥쑥', '행복', '건강', '모유 수유', '혼자 앉기', '첫 혼자 앉기 성공', '발달,성장', '오늘 우리 아기가 드디어 혼자 앉았어요. 정말 감동적인 순간이었습니다.', '2024-05-15 10:10:00'),
                                                                                                                                             (2, 2, 2, 2, '이튼튼', '신남', '건강', '이유식 시작', '걸음마', '첫 걸음마', '발달,운동', '우리 아기가 오늘 처음으로 걸음마를 뗐어요. 너무 자랑스럽고 행복합니다.','2024-05-15 10:10:00'),
                                                                                                                                             (3, 3, 3, 3, '박몽글', '기쁨', '건강', '고형식', '말하기', '첫 단어', '언어발달,의사소통', '드디어 우리 아기가 "엄마"라고 말했어요. 눈물이 날 것 같은 감동의 순간이었습니다.','2024-05-15 10:10:00');