CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    is_active BOOLEAN NOT NULL,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'STOCKIST') NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE categories (
    id VARCHAR(36) PRIMARY KEY,
    is_active BOOLEAN NOT NULL,
    name VARCHAR(255) NOT NULL,
    active VARCHAR(255) NOT NULL,
    type ENUM('NORMAL', 'ESPECIAL', 'CUSTOM') NOT NULL
);

CREATE TABLE products (
    id VARCHAR(36) PRIMARY KEY,
    is_active BOOLEAN NOT NULL,
    name VARCHAR(255) NOT NULL,
    active VARCHAR(255) NOT NULL,
    sku VARCHAR(255) NOT NULL,
    category_id VARCHAR(36) NOT NULL,
    cost_value DECIMAL(10, 2) NOT NULL,
    icms DOUBLE NOT NULL,
    sale_value DECIMAL(10, 2) NOT NULL,
    quantity_in_stock BIGINT NOT NULL,
    created_by VARCHAR(36) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_by VARCHAR(36) NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id)
);

CREATE TABLE audit_logs (
    id VARCHAR(36) PRIMARY KEY,
    action ENUM('CREATE', 'UPDATE', 'DELETE') NOT NULL,
    entity_name VARCHAR(255) NOT NULL,
    entity_id VARCHAR(36) NOT NULL,
    field VARCHAR(255),
    old_value VARCHAR(2000),
    new_value VARCHAR(2000),
    modified_by VARCHAR(36) NOT NULL,
    modified_date TIMESTAMP NOT NULL,
    FOREIGN KEY (modified_by) REFERENCES users(id)
);

CREATE TABLE display_rules (
    id VARCHAR(36) PRIMARY KEY,
    is_active BOOLEAN NOT NULL,
    role VARCHAR(255) NOT NULL
);

CREATE TABLE hidden_fields (
    display_rule_id VARCHAR(36) NOT NULL,
    field_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (display_rule_id, field_name),
    CONSTRAINT fk_display_rule FOREIGN KEY (display_rule_id) REFERENCES display_rules (id) ON DELETE CASCADE
);

CREATE TABLE tokens (
    id VARCHAR(36) PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    user_id VARCHAR(36) NOT NULL,
    token_used BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expired_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);
