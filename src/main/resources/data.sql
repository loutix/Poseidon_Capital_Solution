-- Supprimer les tables si elles existent
DROP TABLE IF EXISTS BidList;
DROP TABLE IF EXISTS Trade;
DROP TABLE IF EXISTS CurvePoint;
DROP TABLE IF EXISTS Rating;
DROP TABLE IF EXISTS RuleName;
DROP TABLE IF EXISTS Users;

-- Créer la table BidList
CREATE TABLE BidList
(
    bid_list_id    SERIAL PRIMARY KEY,
    account        VARCHAR(30) NOT NULL,
    type           VARCHAR(30) NOT NULL,
    bid_quantity   DOUBLE PRECISION NOT NULL,
    ask_quantity   DOUBLE PRECISION,
    bid            DOUBLE PRECISION,
    ask            DOUBLE PRECISION,
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
    side           VARCHAR(125)
);

-- Créer la table Trade
CREATE TABLE Trade
(
    trade_id       SERIAL PRIMARY KEY,
    account        VARCHAR(30) NOT NULL,
    type           VARCHAR(30) NOT NULL,
    buy_quantity   DOUBLE PRECISION NOT NULL,
    sell_quantity  DOUBLE PRECISION,
    buy_price      DOUBLE PRECISION,
    sell_price     DOUBLE PRECISION,
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
    side           VARCHAR(125)
);

-- Créer la table CurvePoint
CREATE TABLE CurvePoint
(
    id            SERIAL PRIMARY KEY,
    curve_id      SMALLINT,
    as_of_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    term          DOUBLE PRECISION NOT NULL,
    value         DOUBLE PRECISION NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Créer la table Rating
CREATE TABLE Rating
(
    id            SERIAL PRIMARY KEY,
    moodys_rating VARCHAR(125) NOT NULL,
    sand_p_rating VARCHAR(125) NOT NULL,
    fitch_rating  VARCHAR(125) NOT NULL,
    order_number  SMALLINT NOT NULL
);

-- Créer la table RuleName
CREATE TABLE RuleName
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(125) NOT NULL,
    description VARCHAR(125) NOT NULL,
    json        VARCHAR(125) NOT NULL,
    template    VARCHAR(512) NOT NULL,
    sql_str     VARCHAR(125) NOT NULL,
    sql_part    VARCHAR(125) NOT NULL
);

-- Créer la table Users
CREATE TABLE Users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(125) NOT NULL UNIQUE,
    password VARCHAR(125),
    fullname VARCHAR(125) NOT NULL,
    role     VARCHAR(125) NOT NULL,
    is_sso   BOOLEAN DEFAULT FALSE
);

-- Insérer des données dans la table Users
INSERT INTO Users (fullname, password, role, username)
VALUES 
    ('John', '$2a$10$rgVyJt18/S/QFuDoL1oqoOOYPjLvyN2eLJDurWgOzeD/khrqLvjAK', 'USER', 'John'),
    ('Henry', '$2a$10$rgVyJt18/S/QFuDoL1oqoOOYPjLvyN2eLJDurWgOzeD/khrqLvjAK', 'USER', 'Henry'),
    ('Amelie', '$2a$10$rgVyJt18/S/QFuDoL1oqoOOYPjLvyN2eLJDurWgOzeD/khrqLvjAK', 'USER', 'Amelie'),
    ('loic', '$2a$10$rgVyJt18/S/QFuDoL1oqoOOYPjLvyN2eLJDurWgOzeD/khrqLvjAK', 'ADMIN', 'loic');
