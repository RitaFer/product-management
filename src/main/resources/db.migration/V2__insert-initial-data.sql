INSERT INTO users (id, is_active, name, username, email, password, role, created_at, updated_at)
VALUES
    (UUID(), true, 'Admin User', 'admin', 'rialf.ferreira@gmail.com', '$2a$10$CwTycUXWue0Thq9StjUM0uJ8KZ2SJMC9WjTSK5znPi3L.tGnWl.D.', 'ADMIN', NOW(), NOW()),
    (UUID(), true, 'Stockist User', 'stockist', 'iwjkwoods@gmail.com', '$2a$10$Ei4U4XGy8vGaJx7l9tGhSuSfltiEXFBNzOwHFLM8wlGEsK/5evLOy', 'STOCKIST', NOW(), NOW());

INSERT INTO categories (id, is_active, name, active, type)
VALUES
    (UUID(), true, 'Category Normal Active', 'NORMAL ACTIVE', 'NORMAL'),
    (UUID(), false, 'Category Normal Inactive', 'NORMAL INACTIVE', 'NORMAL'),
    (UUID(), true, 'Category Especial Active', 'ESPECIAL ACTIVE', 'ESPECIAL'),
    (UUID(), false, 'Category Especial Inactive', 'ESPECIAL INACTIVE', 'ESPECIAL'),
    (UUID(), true, 'Category Custom Active', 'CUSTOM ACTIVE', 'CUSTOM'),
    (UUID(), false, 'Category Custom Inactive', 'CUSTOM INACTIVE', 'CUSTOM');

INSERT INTO display_rules (id, is_active, role)
VALUES
    (UUID(), false, 'STOCKIST'),
    (UUID(), false, 'STOCKIST'),
    (UUID(), true, 'STOCKIST');

SET @display_rule_id = (SELECT id FROM display_rules WHERE is_active = true);

SET @cat_normal_active = (SELECT id FROM categories WHERE name = 'Category Normal Active');
SET @cat_especial_active = (SELECT id FROM categories WHERE name = 'Category Especial Active');
SET @cat_custom_active = (SELECT id FROM categories WHERE name = 'Category Custom Active');

SET @admin_user = (SELECT id FROM users WHERE username = 'admin');

INSERT INTO products (id, is_active, name, active, sku, category_id, cost_value, icms, sale_value, quantity_in_stock, created_by, created_at, updated_by, updated_at)
VALUES
    (UUID(), true, 'Normal Product 1', 'ACTIVE', 'NP1', @cat_normal_active, 10.50, 18.0, 15.75, 100, @admin_user, NOW(), @admin_user, NOW()),
    (UUID(), true, 'Normal Product 2', 'ACTIVE', 'NP2', @cat_normal_active, 12.50, 20.0, 18.00, 150, @admin_user, NOW(), @admin_user, NOW()),
    (UUID(), true, 'Normal Product 3', 'ACTIVE', 'NP3', @cat_normal_active, 14.00, 22.0, 20.00, 200, @admin_user, NOW(), @admin_user, NOW()),
    (UUID(), false, 'Normal Product 4', 'INACTIVE', 'NP4', @cat_normal_active, 10.50, 18.0, 15.75, 100, @admin_user, NOW(), @admin_user, NOW()),
    (UUID(), false, 'Normal Product 5', 'INACTIVE', 'NP5', @cat_normal_active, 12.50, 20.0, 18.00, 150, @admin_user, NOW(), @admin_user, NOW()),
    (UUID(), false, 'Normal Product 6', 'INACTIVE', 'NP6', @cat_normal_active, 14.00, 22.0, 20.00, 200, @admin_user, NOW(), @admin_user, NOW());

INSERT INTO products (id, is_active, name, active, sku, category_id, cost_value, icms, sale_value, quantity_in_stock, created_by, created_at, updated_by, updated_at)
VALUES
    (UUID(), true, 'Especial Product 1', 'ACTIVE', 'EP1', @cat_especial_active, 20.00, 25.0, 30.00, 80, @admin_user, NOW(), @admin_user, NOW()),
    (UUID(), true, 'Especial Product 2', 'ACTIVE', 'EP2', @cat_especial_active, 22.50, 27.0, 35.00, 90, @admin_user, NOW(), @admin_user, NOW()),
    (UUID(), true, 'Especial Product 3', 'ACTIVE', 'EP3', @cat_especial_active, 25.00, 30.0, 40.00, 100, @admin_user, NOW(), @admin_user, NOW()),
    (UUID(), false, 'Especial Product 4', 'INACTIVE', 'EP4', @cat_especial_active, 20.00, 25.0, 30.00, 80, @admin_user, NOW(), @admin_user, NOW()),
    (UUID(), false, 'Especial Product 5', 'INACTIVE', 'EP5', @cat_especial_active, 22.50, 27.0, 35.00, 90, @admin_user, NOW(), @admin_user, NOW()),
    (UUID(), false, 'Especial Product 6', 'INACTIVE', 'EP6', @cat_especial_active, 25.00, 30.0, 40.00, 100, @admin_user, NOW(), @admin_user, NOW());

INSERT INTO products (id, is_active, name, active, sku, category_id, cost_value, icms, sale_value, quantity_in_stock, created_by, created_at, updated_by, updated_at)
VALUES
    (UUID(), true, 'Custom Product 1', 'ACTIVE', 'CP1', @cat_custom_active, 15.00, 20.0, 25.00, 120, @admin_user, NOW(), @admin_user, NOW()),
    (UUID(), true, 'Custom Product 2', 'ACTIVE', 'CP2', @cat_custom_active, 16.50, 22.0, 28.00, 140, @admin_user, NOW(), @admin_user, NOW()),
    (UUID(), true, 'Custom Product 3', 'ACTIVE', 'CP3', @cat_custom_active, 18.00, 24.0, 30.00, 160, @admin_user, NOW(), @admin_user, NOW()),
    (UUID(), false, 'Custom Product 4', 'INACTIVE', 'CP4', @cat_custom_active, 15.00, 20.0, 25.00, 120, @admin_user, NOW(), @admin_user, NOW()),
    (UUID(), false, 'Custom Product 5', 'INACTIVE', 'CP5', @cat_custom_active, 16.50, 22.0, 28.00, 140, @admin_user, NOW(), @admin_user, NOW()),
    (UUID(), false, 'Custom Product 6', 'INACTIVE', 'CP6', @cat_custom_active, 18.00, 24.0, 30.00, 160, @admin_user, NOW(), @admin_user, NOW());

INSERT INTO audit_logs (id, action, entity_name, entity_id, field, old_value, new_value, modified_by, modified_date)
SELECT
    UUID(),
    'CREATE',
    'ProductEntity',
    p.id,
    NULL,
    NULL,
    CONCAT('Created Product: ', p.name),
    @admin_user,
    NOW()
FROM products p;

INSERT INTO hidden_fields (display_rule_id, field_name)
VALUES
    (@display_rule_id, 'name');