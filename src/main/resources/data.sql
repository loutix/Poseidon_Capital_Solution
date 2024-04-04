DROP TABLE IF EXISTS BidList;
DROP TABLE IF EXISTS Trade;
DROP TABLE IF EXISTS CurvePoint;
DROP TABLE IF EXISTS Rating;
DROP TABLE IF EXISTS RuleName;
DROP TABLE IF EXISTS Users;



CREATE TABLE BidList
(
    bid_list_id    tinyint(4)  NOT NULL AUTO_INCREMENT,
    account      VARCHAR(30) NOT NULL,
    type         VARCHAR(30) NOT NULL,
    bid_quantity  DOUBLE,
    ask_quantity  DOUBLE,
    bid          DOUBLE,
    ask          DOUBLE,
    benchmark    VARCHAR(125),
    bid_list_date  TIMESTAMP,
    commentary   VARCHAR(125),
    security     VARCHAR(125),
    status       VARCHAR(10),
    trader       VARCHAR(125),
    book         VARCHAR(125),
    creation_name VARCHAR(125),
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    revision_name VARCHAR(125),
    revision_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deal_name     VARCHAR(125),
    deal_type     VARCHAR(125),
    source_list_id VARCHAR(125),
    side         VARCHAR(125),

    PRIMARY KEY (bid_list_id)
);

CREATE TABLE Trade
(
    trade_id      tinyint(4)  NOT NULL AUTO_INCREMENT,
    account      VARCHAR(30) NOT NULL,
    type         VARCHAR(30) NOT NULL,
    buy_quantity  DOUBLE,
    sell_quantity DOUBLE,
    buy_price     DOUBLE,
    sell_price    DOUBLE,
    trade_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    security     VARCHAR(125),
    status       VARCHAR(10),
    trader       VARCHAR(125),
    benchmark    VARCHAR(125),
    book         VARCHAR(125),
    creation_name VARCHAR(125),
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    revision_name VARCHAR(125),
    revision_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deal_name     VARCHAR(125),
    deal_type     VARCHAR(125),
    source_list_id VARCHAR(125),
    side         VARCHAR(125),

    PRIMARY KEY (trade_id)
);

CREATE TABLE CurvePoint
(
    id           tinyint(4) NOT NULL AUTO_INCREMENT,
    curve_id      tinyint,
    as_of_date     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    term         DOUBLE,
    value        DOUBLE,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id)
);

CREATE TABLE Rating
(
    Id           tinyint(4) NOT NULL AUTO_INCREMENT,
    moodys_rating VARCHAR(125),
    sand_p_rating  VARCHAR(125),
    fitch_rating  VARCHAR(125),
    order_number  tinyint,

    PRIMARY KEY (Id)
);

CREATE TABLE RuleName
(
    Id          tinyint(4) NOT NULL AUTO_INCREMENT,
    name        VARCHAR(125),
    description VARCHAR(125),
    json        VARCHAR(125),
    template    VARCHAR(512),
    sql_str      VARCHAR(125),
    sql_part     VARCHAR(125),

    PRIMARY KEY (Id)
);

CREATE TABLE Users
(
    Id       tinyint(4) NOT NULL AUTO_INCREMENT,
    username VARCHAR(125),
    password VARCHAR(125),
    fullname VARCHAR(125),
    role     VARCHAR(125),
    is_sso   boolean DEFAULT 0,

    PRIMARY KEY (Id)
);


INSERT INTO users (fullname, password, role, username)
VALUES ('John', '$2a$10$rgVyJt18/S/QFuDoL1oqoOOYPjLvyN2eLJDurWgOzeD/khrqLvjAK', 'USER', 'John'),
       ('Henry', '$2a$10$rgVyJt18/S/QFuDoL1oqoOOYPjLvyN2eLJDurWgOzeD/khrqLvjAK', 'USER', 'Henry'),
       ('Amelie', '$2a$10$rgVyJt18/S/QFuDoL1oqoOOYPjLvyN2eLJDurWgOzeD/khrqLvjAK', 'USER', 'Amelie'),
       ('Loïc', '$2a$10$rgVyJt18/S/QFuDoL1oqoOOYPjLvyN2eLJDurWgOzeD/khrqLvjAK', 'ADMIN', 'loutix');

#
# insert into Users(fullname, username, password, role) values("Administrator", "admin", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "ADMIN")
# insert into Users(fullname, username, password, role) values("User", "user", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "USER")