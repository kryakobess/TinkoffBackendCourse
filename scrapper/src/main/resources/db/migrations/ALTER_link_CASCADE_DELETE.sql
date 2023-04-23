ALTER TABLE link_subscription
    DROP CONSTRAINT link_subscription_tguserid_fkey;

ALTER TABLE link_subscription
    ADD CONSTRAINT link_subscription_tguserid_fkey_Cascade
    FOREIGN KEY (tguserid) REFERENCES tg_user(id) ON DELETE CASCADE;