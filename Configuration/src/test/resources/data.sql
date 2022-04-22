
BEGIN;
insert into CONFIGURATION
values (1, 'Test file', 'test');

insert into CONFIGURATION_TAG
values (1, '2022-04-20', '2022-04-20',1);
insert into CONFIGURATION_TAG
values (2, '2022-04-20', '2022-04-20', 1);

insert into CONFIGURATION_VARIABLE
values (1, '2022-04-20', 'volume', '2022-04-20', 100, 1, 'blabal', 5, 1);
insert into CONFIGURATION_VARIABLE
values (2, '2022-04-20', 'volume', '2022-04-20', 100, 1, 'blabal', 5, 1);




insert into CONFIGURATION
values (2, 'Test file', 'test');

insert into CONFIGURATION_TAG
values (3, '2022-04-20', '2022-04-20',2);
insert into CONFIGURATION_TAG
values (4, '2022-04-20', '2022-04-20', 2);

insert into CONFIGURATION_VARIABLE
values (3, '2022-04-20', 'volume', '2022-04-20', 100, 1, 'blabal', 5, 2);
insert into CONFIGURATION_VARIABLE
values (4, '2022-04-20', 'volume', '2022-04-20', 100, 1, 'blabal', 5, 2);

COMMIT