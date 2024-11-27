ALTER TABLE user_address
ADD CONSTRAINT unique_account_user_address_mapping UNIQUE (account_user_id);

ALTER TABLE user_role
ADD CONSTRAINT unique_auth_user_role_mapping UNIQUE (user_auth_id);