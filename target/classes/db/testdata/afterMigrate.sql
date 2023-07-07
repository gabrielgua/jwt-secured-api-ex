set foreign_key_checks = 0;

delete from user;

set foreign_key_checks = 1;

alter table user auto_increment = 1;

insert into user(id, name, email, role, password) values
(1, "Admin", "admin@email.com", "ADMIN", "$2a$12$C6kg3E0aagq5gbuF178cg.LCnmoFhbSvgwRQSDl6y9YGMJdSavlNS"),
(2, "User", "user@email.com", "USER", "$2a$10$x72nTlBMIDFYCfCUOUQD5uasnTIrlFXtqu88nFUysehCdqTou/i0K");
