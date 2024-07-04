DROP TABLE IF EXISTS BidList;
DROP TABLE IF EXISTS Trade;
DROP TABLE IF EXISTS CurvePoint;
DROP TABLE IF EXISTS Rating;
DROP TABLE IF EXISTS RuleName;
DROP TABLE IF EXISTS Users;



CREATE TABLE BidList
(
    bid_list_id    tinyint(4)  NOT NULL AUTO_INCREMENT,
    account        VARCHAR(30) NOT NULL,
    type           VARCHAR(30) NOT NULL,
    bid_quantity   DOUBLE NOT NULL,
    ask_quantity   DOUBLE,
    bid            DOUBLE,
    ask            DOUBLE,
    benchmark      VARCHAR(125),
    bid_list_date  TIMESTAMP,
    commentary     VARCHAR(125),
    security       VARCHAR(125),
    status         VARCHAR(10),
    trader         VARCHAR(125),
    book           VARCHAR(125),
    creation_name  VARCHAR(125),
    creation_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    revision_name  VARCHAR(125),
    revision_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deal_name      VARCHAR(125),
    deal_type      VARCHAR(125),
    source_list_id VARCHAR(125),
    side           VARCHAR(125),

    PRIMARY KEY (bid_list_id)
);

CREATE TABLE Trade
(
    trade_id       tinyint(4)  NOT NULL AUTO_INCREMENT,
    account        VARCHAR(30) NOT NULL,
    type           VARCHAR(30) NOT NULL,
    buy_quantity   DOUBLE NOT NULL,
    sell_quantity  DOUBLE,
    buy_price      DOUBLE,
    sell_price     DOUBLE,
    trade_date     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    security       VARCHAR(125),
    status         VARCHAR(10),
    trader         VARCHAR(125),
    benchmark      VARCHAR(125),
    book           VARCHAR(125),
    creation_name  VARCHAR(125),
    creation_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    revision_name  VARCHAR(125),
    revision_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deal_name      VARCHAR(125),
    deal_type      VARCHAR(125),
    source_list_id VARCHAR(125),
    side           VARCHAR(125),

    PRIMARY KEY (trade_id)
);

CREATE TABLE CurvePoint
(
    id            tinyint(4) NOT NULL AUTO_INCREMENT,
    curve_id      tinyint,
    as_of_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    term          DOUBLE NOT NULL,
    value         DOUBLE NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id)
);

CREATE TABLE Rating
(
    Id            tinyint(4) NOT NULL AUTO_INCREMENT,
    moodys_rating VARCHAR(125) NOT NULL,
    sand_p_rating VARCHAR(125) NOT NULL,
    fitch_rating  VARCHAR(125) NOT NULL,
    order_number  tinyint NOT NULL,

    PRIMARY KEY (Id)
);

CREATE TABLE RuleName
(
    Id          tinyint(4) NOT NULL AUTO_INCREMENT,
    name        VARCHAR(125) NOT NULL,
    description VARCHAR(125) NOT NULL,
    json        VARCHAR(125) NOT NULL,
    template    VARCHAR(512) NOT NULL,
    sql_str     VARCHAR(125) NOT NULL,
    sql_part    VARCHAR(125) NOT NULL,

    PRIMARY KEY (Id)
);

CREATE TABLE Users
(
    Id       tinyint(4) NOT NULL AUTO_INCREMENT,
    username VARCHAR(125) NOT NULL UNIQUE,
    password VARCHAR(125),
    fullname VARCHAR(125) NOT NULL,
    role     VARCHAR(125) NOT NULL,
    is_sso   boolean DEFAULT 0,

    PRIMARY KEY (Id)
);


INSERT INTO users (fullname, password, role, username)
VALUES ('John', '$2a$10$rgVyJt18/S/QFuDoL1oqoOOYPjLvyN2eLJDurWgOzeD/khrqLvjAK', 'USER', 'John'),
       ('Henry', '$2a$10$rgVyJt18/S/QFuDoL1oqoOOYPjLvyN2eLJDurWgOzeD/khrqLvjAK', 'USER', 'Henry'),
       ('Amelie', '$2a$10$rgVyJt18/S/QFuDoL1oqoOOYPjLvyN2eLJDurWgOzeD/khrqLvjAK', 'USER', 'Amelie'),
       ('loutix', '$2a$10$rgVyJt18/S/QFuDoL1oqoOOYPjLvyN2eLJDurWgOzeD/khrqLvjAK', 'ADMIN', 'loic');