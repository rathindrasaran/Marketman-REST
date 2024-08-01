CREATE TABLE campaigns (
    campaign_id INT AUTO_INCREMENT PRIMARY KEY,
    caption VARCHAR(255),
    `desc` TEXT,
    `type` VARCHAR(50)
);

CREATE TABLE lists (
    list_id INT AUTO_INCREMENT PRIMARY KEY,
    caption VARCHAR(255),
    `desc` TEXT,
    campaign_id INT,
    FOREIGN KEY (campaign_id) REFERENCES campaigns(campaign_id)
);

CREATE TABLE contacts (
    contact_id INT AUTO_INCREMENT PRIMARY KEY,
    list_id INT,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    zipcode VARCHAR(20),
    company VARCHAR(255),
    `desc` TEXT,
    FOREIGN KEY (list_id) REFERENCES lists(list_id)
);

CREATE TABLE templates (
    template_id INT AUTO_INCREMENT PRIMARY KEY,
    campaign_id INT,
    caption VARCHAR(255),
    `desc` TEXT,
    `type` VARCHAR(50),
    content TEXT,
    FOREIGN KEY (campaign_id) REFERENCES campaigns(campaign_id)
);

CREATE TABLE attempts (
    attempt_id INT AUTO_INCREMENT PRIMARY KEY,
    contact_id INT,
    campaign_id INT,
    email_text VARCHAR(255),
    email_body TEXT,
    call_notes TEXT,
    sms_text VARCHAR(255),
    call_recording BLOB,
    attempt_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    template_id INT,
    FOREIGN KEY (contact_id) REFERENCES contacts(contact_id),
    FOREIGN KEY (campaign_id) REFERENCES campaigns(campaign_id),
    FOREIGN KEY (template_id) REFERENCES templates(template_id)
);

CREATE TABLE replies (
    reply_id INT AUTO_INCREMENT PRIMARY KEY,
    attempt_id INT,
    contact_id INT,
    campaign_id INT,
    email_text VARCHAR(255),
    email_body TEXT,
    call_notes TEXT,
    sms_text VARCHAR(255),
    call_recording BLOB,
    reply_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (attempt_id) REFERENCES attempts(attempt_id),
    FOREIGN KEY (contact_id) REFERENCES contacts(contact_id),
    FOREIGN KEY (campaign_id) REFERENCES campaigns(campaign_id)
);

CREATE TABLE reminders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    text VARCHAR(255),
    `time` DATETIME,
    `type` VARCHAR(50),
    idoftype INT
);
