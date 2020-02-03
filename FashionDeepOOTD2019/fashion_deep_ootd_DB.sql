create database closet;

use closet;

create table member( id varchar(15) PRIMARY KEY
			, password varchar(30) not null
			, nickname varchar(15) not null);
       

create table fashionSet( id varchar(15)
			, set_name varchar(15)
			, outerC varchar(100) 
			, upperC varchar(100) 
			, lowerC varchar(100) 
			, cap varchar(100) 
			, bag varchar(100) 
			, shoes varchar(100)
			, accessory1 varchar(100)
			, accessory2 varchar(100)
			, accessory3 varchar(100)
			, PRIMARY KEY(id, set_name));       


create table clothes( id varchar(15)
			, dress_number varchar(100)
			, category varchar(15) 
			, low_category varchar(30) 
			, color varchar(15)    
			, pattern varchar(15)
			, lengthC varchar(15)
			, PRIMARY KEY(id, dress_number));             
       
insert into member values('test', 'test1234', 'TEST');
insert into member values('yumi', 'yumi1234', 'юЁ');       
insert into fashionSet values("test" , "дЁаж╬С1" , "null" , "/data/user/0/com.example.ootd/files/upper/short_T/short_T_83.jpg", "/data/user/0/com.example.ootd/files/lower/long_pants/long_pants.jpg", "null", "null", "null", "null", "null", "null");

insert into clothes values('test', 'hood_T_1', 'upper', 'hood_T', 'white', 'no', 'long');
insert into clothes values('test', 'hood_T_2', 'upper', 'hood_T', 'black', 'no', 'long');
insert into clothes values('test', 'hood_T_3', 'upper', 'hood_T', 'brown', 'no', 'long');
insert into clothes values('test', 'hood_T_4', 'upper', 'hood_T', 'gray', 'no', 'long');
insert into clothes values('test', 'hood_T_5', 'upper', 'hood_T', 'purple', 'no', 'long');
insert into clothes values('test', 'hood_T_6', 'upper', 'hood_T', 'green', 'no', 'long');

insert into clothes values('test', 'long_T_1', 'upper', 'long_T', 'white', 'no', 'long');
insert into clothes values('test', 'long_T_2', 'upper', 'long_T', 'blue', 'no', 'long');
insert into clothes values('test', 'long_T_3', 'upper', 'long_T', 'fluorescent', 'no', 'long');
insert into clothes values('test', 'long_T_4', 'upper', 'long_T', 'red', 'no', 'long');
insert into clothes values('test', 'long_T_5', 'upper', 'long_T', 'black', 'no', 'long');
insert into clothes values('test', 'long_T_6', 'upper', 'long_T', 'black', 'no', 'long');

insert into clothes values('test', 'shirt_1', 'upper', 'shirt', 'yellow', 'check', 'long');
insert into clothes values('test', 'shirt_2', 'upper', 'shirt', 'yellow', 'check', 'long');
insert into clothes values('test', 'shirt_3', 'upper', 'shirt', 'gray', 'no', 'short');
insert into clothes values('test', 'shirt_4', 'upper', 'shirt', 'white', 'no', 'long');
insert into clothes values('test', 'shirt_5', 'upper', 'shirt', 'beige', 'no', 'long');
insert into clothes values('test', 'shirt_6', 'upper', 'shirt', 'blue', 'check', 'long');

insert into clothes values('test', 'short_T_9', 'upper', 'short_T', 'blue', 'no', 'short');
insert into clothes values('test', 'short_T_83', 'upper', 'short_T', 'orange', 'no', 'short');
insert into clothes values('test', 'short_T_123', 'upper', 'short_T', 'white', 'no', 'short');
insert into clothes values('test', 'short_T_158', 'upper', 'short_T', 'red', 'no', 'short');
insert into clothes values('test', 'short_T_5088447987087601774', 'upper', 'short_T', 'white', 'no', 'short');

insert into clothes values('test', 'sleeveless_1', 'upper', 'sleeveless', 'white', 'no', 'short');
insert into clothes values('test', 'sleeveless_3', 'upper', 'sleeveless', 'green', 'no', 'short');
insert into clothes values('test', 'sleeveless_4', 'upper', 'sleeveless', 'white', 'no', 'short');
insert into clothes values('test', 'sleeveless_5', 'upper', 'sleeveless', 'black', 'no', 'short');
insert into clothes values('test', 'sleeveless_6', 'upper', 'sleeveless', 'black', 'no', 'short');
insert into clothes values('test', 'sleeveless_111', 'upper', 'sleeveless', 'white', 'no', 'short');
insert into clothes values('test', 'sleeveless_174', 'upper', 'sleeveless', 'brown', 'stripe', 'short');

insert into clothes values('test', 'vest_1', 'upper', 'vest', 'brown', 'no', 'short');
insert into clothes values('test', 'vest_2', 'upper', 'vest', 'pink', 'no', 'short');
insert into clothes values('test', 'vest_3', 'upper', 'vest', 'black', 'no', 'short');
insert into clothes values('test', 'vest_4', 'upper', 'vest', 'beige', 'no', 'short');
insert into clothes values('test', 'vest_5', 'upper', 'vest', 'pink', 'no', 'short');
insert into clothes values('test', 'vest_6', 'upper', 'vest', 'green', 'no', 'short');

insert into clothes values('test', 'long_pants', 'lower', 'long_pants', 'beige', 'no', 'long');
insert into clothes values('test', 'long_pants_012', 'lower', 'long_pants', 'light_blue', 'no', 'long');
insert into clothes values('test', 'long_pants_123', 'lower', 'long_pants', 'black', 'no', 'long');
insert into clothes values('test', 'long_pants_456', 'lower', 'long_pants', 'blue', 'no', 'long');
insert into clothes values('test', 'long_pants_789', 'lower', 'long_pants', 'blue', 'no', 'long');

insert into clothes values('test', 'long_pants_61', 'lower', 'long_pants', 'light_blue', 'no', 'long');
insert into clothes values('test', 'long_pants_112', 'lower', 'long_pants', 'beige', 'no', 'long');
insert into clothes values('test', 'long_pants_1', 'lower', 'long_pants', 'red', 'check', 'long');
insert into clothes values('test', 'long_pants_2', 'lower', 'long_pants', 'black', 'check', 'long');

insert into clothes values('test', 'long_skirt_1', 'lower', 'long_skirt', 'brown', 'check', 'long');
insert into clothes values('test', 'long_skirt_2', 'lower', 'long_skirt', 'beige', 'no', 'long');
insert into clothes values('test', 'long_skirt_3', 'lower', 'long_skirt', 'black', 'no', 'long');
insert into clothes values('test', 'long_skirt_4', 'lower', 'long_skirt', 'brown', 'no', 'long');

insert into clothes values('test', 'long_skirt_290', 'lower', 'long_skirt', 'blue', 'no', 'long');
insert into clothes values('test', 'long_skirt_358', 'lower', 'long_skirt', 'red', 'no', 'long');
insert into clothes values('test', 'long_skirt_654', 'lower', 'long_skirt', 'black', 'check', 'long');

insert into clothes values('test', 'mini_skirt_1', 'lower', 'mini_skirt', 'yellow', 'check', 'short');
insert into clothes values('test', 'mini_skirt_2', 'lower', 'mini_skirt', 'black', 'check', 'short');
insert into clothes values('test', 'mini_skirt_3', 'lower', 'mini_skirt', 'gray', 'no', 'short');
insert into clothes values('test', 'mini_skirt_4', 'lower', 'mini_skirt', 'black', 'no', 'short');

insert into clothes values('test', 'short_pants_1', 'lower', 'short_pants', 'black', 'no', 'short');
insert into clothes values('test', 'short_pants_2', 'lower', 'short_pants', 'black', 'no', 'short');
insert into clothes values('test', 'short_pants_3', 'lower', 'short_pants', 'black', 'no', 'short');
insert into clothes values('test', 'short_pants_4', 'lower', 'short_pants', 'blue', 'no', 'short');

insert into clothes values('test', 'short_pants_26', 'lower', 'short_pants', 'brown', 'no', 'short');
insert into clothes values('test', 'short_pants_85', 'lower', 'short_pants', 'light_blue', 'no', 'short');

insert into clothes values('test', 'cardigan_1', 'outer', 'cardigan', 'black', 'no', 'long');
insert into clothes values('test', 'cardigan_2', 'outer', 'cardigan', 'red', 'no', 'long');
insert into clothes values('test', 'cardigan_3', 'outer', 'cardigan', 'black', 'no', 'long');
insert into clothes values('test', 'cardigan_4', 'outer', 'cardigan', 'gray', 'no', 'long');

insert into clothes values('test', 'coat_1', 'outer', 'coat', 'black', 'no', 'long');
insert into clothes values('test', 'coat_2', 'outer', 'coat', 'gray', 'check', 'long');
insert into clothes values('test', 'coat_3', 'outer', 'coat', 'brown', 'no', 'long');
insert into clothes values('test', 'coat_4', 'outer', 'coat', 'gray', 'check', 'long');
insert into clothes values('test', 'coat_5', 'outer', 'coat', 'beige', 'no', 'long');

insert into clothes values('test', 'hood_zipup_1', 'outer', 'hood_zipup', 'white', 'no', 'long');
insert into clothes values('test', 'hood_zipup_2', 'outer', 'hood_zipup', 'gray', 'no', 'long');
insert into clothes values('test', 'hood_zipup_3', 'outer', 'hood_zipup', 'black', 'no', 'long');
insert into clothes values('test', 'hood_zipup_4', 'outer', 'hood_zipup', 'black', 'no', 'long');

insert into clothes values('test', 'jacket_1', 'outer', 'jacket', 'gray', 'no', 'long');
insert into clothes values('test', 'jacket_2', 'outer', 'jacket', 'gray', 'check', 'long');
insert into clothes values('test', 'jacket_3', 'outer', 'jacket', 'black', 'no', 'long');
insert into clothes values('test', 'jacket_4', 'outer', 'jacket', 'black', 'no', 'long');

insert into clothes values('test', 'jumper_1', 'outer', 'jumper', 'blue', 'no', 'long');
insert into clothes values('test', 'jumper_2', 'outer', 'jumper', 'white', 'no', 'long');
insert into clothes values('test', 'jumper_3', 'outer', 'jumper', 'black', 'no', 'long');
insert into clothes values('test', 'jumper_4', 'outer', 'jumper', 'black', 'no', 'long');

insert into clothes values('test', 'padding_1', 'outer', 'padding', 'black', 'no', 'long');
insert into clothes values('test', 'padding_2', 'outer', 'padding', 'white', 'no', 'long');
insert into clothes values('test', 'padding_3', 'outer', 'padding', 'black', 'no', 'long');
insert into clothes values('test', 'padding_4', 'outer', 'padding', 'black', 'no', 'long');

insert into clothes values('test', 'bag_123', 'etc', 'bag', 'white', 'no', 'no');
insert into clothes values('test', 'cap_123', 'etc', 'cap', 'black', 'no', 'no');
insert into clothes values('test', 'shoes_123', 'etc', 'shoes', 'white', 'no', 'no');
insert into clothes values('test', 'accessory_123', 'etc', 'accessory', 'no', 'no', 'no');
insert into clothes values('test', 'accessory_234', 'etc', 'accessory', 'no', 'no', 'no');
insert into clothes values('test', 'accessory_345', 'etc', 'accessory', 'no', 'no', 'no');

insert into clothes values('test', 'accessory1', 'etc', 'accessory', 'no', 'no', 'no');
insert into clothes values('test', 'earring1', 'etc', 'accessory', 'no', 'no', 'no');
insert into clothes values('test', 'earring2', 'etc', 'accessory', 'no', 'no', 'no');
insert into clothes values('test', 'watch1', 'etc', 'accessory', 'no', 'no', 'no');
insert into clothes values('test', 'watch2', 'etc', 'accessory', 'no', 'no', 'no');
insert into clothes values('test', 'bag1', 'etc', 'bag', 'brown', 'no', 'no');
insert into clothes values('test', 'bag2', 'etc', 'bag', 'brown', 'no', 'no');
insert into clothes values('test', 'bag3', 'etc', 'bag', 'yellow', 'no', 'no');
insert into clothes values('test', 'bag4', 'etc', 'bag', 'gray', 'no', 'no');
insert into clothes values('test', 'cap1', 'etc', 'cap', 'beige', 'no', 'no');
insert into clothes values('test', 'cap2', 'etc', 'cap', 'blue', 'no', 'no');
insert into clothes values('test', 'cap3', 'etc', 'cap', 'black', 'no', 'no');
insert into clothes values('test', 'cap4', 'etc', 'cap', 'yellow', 'no', 'no');
insert into clothes values('test', 'cap5', 'etc', 'cap', 'white', 'no', 'no');
insert into clothes values('test', 'shoes1', 'etc', 'shoes', 'white', 'no', 'no');
insert into clothes values('test', 'shoes2', 'etc', 'shoes', 'white', 'no', 'no');
insert into clothes values('test', 'shoes3', 'etc', 'shoes', 'gray', 'no', 'no');
insert into clothes values('test', 'shoes4', 'etc', 'shoes', 'black', 'no', 'no');
insert into clothes values('test', 'shoes5', 'etc', 'shoes', 'black', 'no', 'no');
insert into clothes values('test', 'shoes6', 'etc', 'shoes', 'red', 'no', 'no');


select * from member;
select * from fashionSet;
select * from clothes;