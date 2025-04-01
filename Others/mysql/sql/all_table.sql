-- 用户表(users)
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    salted_password VARCHAR(255) NOT NULL,
    role ENUM('管理员', '游客') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


-- 网站表(websites)
CREATE TABLE websites (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    domain VARCHAR(255) NOT NULL COMMENT '域名',
    subdomain VARCHAR(255) COMMENT '子域名',
    full_url TEXT NOT NULL COMMENT '完整URL',
    port INT COMMENT '端口',
    title VARCHAR(255) COMMENT '网站标题',
    ip VARCHAR(45) COMMENT '服务器IP',
    is_malicious BOOLEAN DEFAULT FALSE COMMENT '是否是恶意站点',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ICP备案主体表(icp_entities)
CREATE TABLE icp_entities (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    icp_code VARCHAR(50) NOT NULL UNIQUE COMMENT 'ICP备案/许可证号',
    approval_date DATE COMMENT '审核通过日期',
    entity_name VARCHAR(255) NOT NULL COMMENT '主办单位名称',
    entity_type VARCHAR(50) NOT NULL COMMENT '主办单位性质',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ICP备案服务表(icp_services)
CREATE TABLE icp_services (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    entity_id BIGINT UNSIGNED NOT NULL COMMENT '关联的主体ID',
    service_code VARCHAR(50) NOT NULL COMMENT '服务备案号',
    website_id BIGINT UNSIGNED NOT NULL COMMENT '关联的网站ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (entity_id) REFERENCES icp_entities(id),
    FOREIGN KEY (website_id) REFERENCES websites(id)
);

-- 企业信息表(enterprises)
CREATE TABLE enterprises (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    social_credit_code VARCHAR(30) UNIQUE COMMENT '统一社会信用代码',
    company_name VARCHAR(255) NOT NULL COMMENT '企业名称',
    legal_representative VARCHAR(50) COMMENT '法定代表人',
    establishment_date DATE COMMENT '成立日期',
    approval_date DATE COMMENT '核准日期',
    registration_status VARCHAR(20) COMMENT '登记状态',
    registration_number VARCHAR(50) COMMENT '注册号',
    company_type VARCHAR(50) COMMENT '类型',
    registered_capital DECIMAL(15,2) COMMENT '注册资本',
    registration_authority VARCHAR(100) COMMENT '登记机关',
    address TEXT COMMENT '住所',
    business_scope TEXT COMMENT '经营范围',
    business_term_start DATE COMMENT '营业期限开始',
    business_term_end DATE COMMENT '营业期限结束',
    entity_id BIGINT UNSIGNED COMMENT '关联的ICP主体ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (entity_id) REFERENCES icp_entities(id)
);