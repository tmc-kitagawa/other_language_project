TRUNCATE TABLE users CASCADE;
INSERT INTO users VALUES (1, 'masahiro', '{bcrypt}$2a$10$GlcYhrYLoY4VgyTIQICzj.FaYC2.F2rzTTzYi4g4xh.oRanKJqzLa', true);
INSERT INTO users VALUES (2, 'mac', '{bcrypt}$2a$10$GlcYhrYLoY4VgyTIQICzj.FaYC2.F2rzTTzYi4g4xh.oRanKJqzLb', true);
INSERT INTO authorities VALUES (1, 'masahiro' );
INSERT INTO authorities VALUES (2, 'mac');