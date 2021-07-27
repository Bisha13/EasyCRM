-- Нужно убрать исполнителя из заказа.
-- поменять длину колонки с телефоном


ALTER TABLE devicestable ADD COLUMN device_name VARCHAR(100);
ALTER TABLE devicestable MODIFY vendor varchar(100);
ALTER TABLE devicestable MODIFY model varchar(100);
ALTER TABLE devicestable MODIFY serial_number INT(11);
ALTER TABLE devicestable MODIFY device_type INT(11);

ALTER TABLE service_table ADD COLUMN quantity INT(3) default 1;
ALTER TABLE service_table ADD COLUMN is_custom bool;
ALTER TABLE service_table modify description mediumtext null;
ALTER TABLE service_table MODIFY executor_id int null;
ALTER TABLE userstable modify procent int(3) null;
ALTER TABLE `service_table` ADD COLUMN `item_id` int(11) default 169;
ALTER TABLE `service_table` MODIFY `executor_money` DOUBLE(9,2);
ALTER TABLE `service_table` MODIFY `profit` DOUBLE(9,2);
ALTER TABLE `service_table` MODIFY `price` DOUBLE(9,2);
ALTER TABLE `item` MODIFY `price` DOUBLE(9,2);
ALTER TABLE `orderstable` MODIFY `full_price` DOUBLE(9,2);
ALTER TABLE `orderstable` MODIFY `work_price` DOUBLE(9,2);
ALTER TABLE `orderstable` MODIFY `parts_price` DOUBLE(9,2);



-- Чтобы можно было удалять клиентов
ALTER TABLE `orderstable` MODIFY `client_id` int(11);

-- Убрать заказы с несуществующими клиентами
delete from `orderstable` where `client_id` IN (SELECT distinct `client_id` FROM `orderstable` WHERE `client_id` NOT IN (SELECT `id` FROM `clientstable`));

ALTER TABLE `orderstable`
    ADD CONSTRAINT `FK_orders_clients`
        FOREIGN KEY (`client_id`)
            REFERENCES `clientstable`(`id`) on delete set null;





-- Добавляем foreign keys между order и device
-- До этого нужно проверить, что все id актуальны.
-- Нужно чтобы можно было удалять велосипеды

-- Сначала убить заказы с несуществующими велосипедами
DELETE FROM `orderstable` WHERE `bike_id` IN (SELECT DISTINCT `bike_id` FROM `orderstable` WHERE `bike_id` NOT IN (SELECT `device_id` FROM `devicestable`));

ALTER TABLE `orderstable`
    ADD CONSTRAINT `FK_orders_devices`
        FOREIGN KEY (`bike_id`)
            REFERENCES `devicestable`(`device_id`) on delete set null;







-- Таблица со статусами и наполнение
CREATE TABLE IF NOT EXISTS `status`
(
    `statusId` int auto_increment primary key,
    `name` VARCHAR(100) not null
    );
INSERT IGNORE INTO `status` (`statusid`, `name`) VALUES
(1, 'Принят'),
(2, 'Ожидает доставки запчастей'),
(3, 'Ожидает заказа запчастей'),
(4, 'В работе'),
(5, 'Ожидает проверки'),
(6, 'Вел у клиента'),
(7, 'Позвонить клиенту'),
(8, 'Ожидает ремонта Глеба'),
(9, 'Ожидает клиента'),
(10, 'Ожидает выдачи клиенту'),
(11, 'Ожидает доставки клиенту'),
(12, 'Ожидает курьера'),
(13, 'Неизвестный статус'),
(14, 'Закрыт'),
(15, 'Отменен');
UPDATE `orderstable` SET `execute_status` = '13' WHERE `execute_status` = '0';
-- Foreign key
ALTER TABLE `orderstable`
    ADD CONSTRAINT `FK_orders_status` FOREIGN KEY (`execute_status`) REFERENCES `status` (`statusId`) ON DELETE SET NULL;
ALTER TABLE `orderstable`
    CHANGE COLUMN `execute_status` `execute_status` INT(11) NULL DEFAULT '1';

-- Меняем длину телефона
ALTER TABLE `clientstable` MODIFY `phone_number` varchar(20);
ALTER TABLE `clientstable` MODIFY `phone_number2` varchar(20);
ALTER TABLE `clientstable` MODIFY `phone_number3` varchar(20);


-- Убираем велосипеды без клиентов
select device_id from devicestable where owner not in (select id from clientstable);

delete from devicestable where owner not in (select id from clientstable);



create table stock
(
    part_id int auto_increment primary key,
    article_number varchar(100) null,
    name varchar(255) null,
    purchase_price double(9, 2) null,
 extra int          null,
 price double(9, 2) null
);


create table parts
(
    part_id int auto_increment        primary key,
    name varchar(255),
    purchase_price double(9, 2),
 price double(9, 2),
 order_id int,
 qty int,
 stock_id int,
 is_stock bool,
 constraint parts_orders__fk
        foreign key (order_id) references orderstable (order_id),
 constraint parts_stock__fk
            foreign key (stock_id) references stock (part_id)
);



INSERT INTO stock (article_number, name, purchase_price, extra,
                   price)
VALUES ('5-524721',
        'Покрышка 26"x2, 10 (54-559) K901F высокий KOYOTE KENDA', 717,
        null, 1200);

INSERT INTO stock (article_number, name, purchase_price, extra,
                   price)
VALUES ('DHB01007',
        'Камера велосипедная DURO 26x1, 75/2, 125, бутил, автониппель A/V', 184,
        null, 350);

INSERT INTO stock (article_number, name, purchase_price, extra,
                   price)
VALUES ('KL-08A',
        'KENLI Каретка-картридж MTB BC 1.37"X24T L/R 68/113мм стальные чашки, стальной корпус, 2 промподшипникаб в торг.уп',
        261, null, 900);

INSERT INTO stock (article_number, name, purchase_price, extra,
                   price)
VALUES ('KL-08A',
        'KENLI Каретка-картридж MTB BC 1.37"X24T L/R 68/116мм стальные чашки',
        261, null, 900);

INSERT INTO stock (article_number, name, purchase_price, extra,
                   price)
VALUES ('KL-08A',
        'KENLI Каретка-картридж MTB BC 1.37"X24T L/R 68/122.5мм стальные чашки, стальной корпус, 2 промподшипникаб в торг.уп',
        278, null, 900);

INSERT INTO stock (article_number, name, purchase_price, extra,
                   price)
VALUES ('Wellgo B-107P',
        'Педали велосипедные, полупрозрачный крепкий пластик Cr--Mo ось синие',
        663, null, 1200);

INSERT INTO stock (article_number, name, purchase_price, extra,
                   price)
VALUES ('Wellgo LU-V205', 'Педали велосипедные, пластик, стальная ось, черные',
        350, null, 700);

INSERT INTO stock (article_number, name, purchase_price, extra,
                   price)
VALUES ('ARDTY21BGSDL',
        'Переключатель велосипедный Shimano Tourney задний 6 скоростей черный',
        517, null, 1000);

INSERT INTO stock (article_number, name, purchase_price, extra,
                   price)
VALUES ('00-011073',
        'Покрышка велосипедная HORST, 26x2. 10 (54-559), MTB P1197(D)-01 средний, H.R.T, черный',
        445, null, 900);

INSERT INTO stock (article_number, name, purchase_price, extra,
                   price)
VALUES ('AMFTZ5007428CP',
        'Трещотка Shimano MF-TZ500, на 7 скоростей, зведы 14-28', 687, null,
        1100);
INSERT INTO stock (article_number, name, purchase_price, extra,
                   price)
VALUES (null, null, 0, 0, 0);
UPDATE stock s SET s.part_id = 0  WHERE s.part_id = LAST_INSERT_ID();


INSERT INTO item (name, description, price, category_id, priority)
VALUES (null, null, 0, 1, -100);

UPDATE item t SET t.id = 0  WHERE t.id = LAST_INSERT_ID();


------------------------------------------------
alter table status add column colour varchar(50);