insert into clinic(name, description, web_site, country, clinic_city, post_code, address, telephone_number)
values ('Pets clinic1', 'Clinic for small and big pets', 'https://clinic-example.de', 'Germany', 'Berlin', '01010', 'Hauptstrasse 7', '+49 172 111 11 111');

insert into clinic(name, description, web_site, country, clinic_city, post_code, address, telephone_number)
values ('Pets clinic2', 'Clinic for small and big pets', 'https://clinic-example.de', 'Germany', 'Berlin', '01010', 'Prager Straße 7', '+49 172 111 11 111');

insert into kennel(name, description, web_site, country, kennel_city, post_code, address, telephone_number)
values ('Pets kennel1', 'Kennel for small and big pets', 'https://kennel-example.de', 'Germany', 'Berlin', '01010', 'Luther Straße 17', '+49 172 777 11 111');

insert into kennel(name, description, web_site, country, kennel_city, post_code, address, telephone_number)
values ('Pets kennel2', 'Kennel for small and big pets', 'https://kennel-example.de', 'Germany', 'Berlin', '01010', 'Biesnitzer Straße 57', '+49 172 444 11 111');

insert into account(email, hash_password, role, state, user_name)
values ('user@mail.com', 'Qwerty007!', 'DOGLOVER', 'CONFIRMED', 'User');

insert into account(email, hash_password, role, state, user_name)
values ('user1@mail.com', 'Qwerty007!', 'DOGLOVER', 'CONFIRMED', 'Max');

insert into dog_sitters(id, first_name, last_name, email, role, user_name, zip, dog_size, city)
values (1,'Sitter1', 'Sitter1', 'simple@mail.com', 'DOGSITTER', 'Sitter1', '01010', 'A_MINI', 'Berlin');

insert into dog_sitters(id, first_name, last_name, email, role, user_name, zip, dog_size, city)
values (2, 'Sitter2', 'Sitter2', 'simple2@mail.com', 'DOGSITTER', 'Sitter2', '01010', 'A_MINI', 'Berlin');

insert into dog_lovers(id, first_name, last_name, email, role, user_name, zip, city)
values (1,'DogLover1', 'DogLover1', 'user@mail.com', 'DOGLOVER', 'User', '01010', 'Berlin');

insert into dog_lovers_dog_sitters(dog_sitter_id, dog_lover_id)
values (1, 1);