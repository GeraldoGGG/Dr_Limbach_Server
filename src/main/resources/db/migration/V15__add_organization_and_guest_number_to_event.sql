-- V2__add_organization_and_guest_number_to_event.sql

-- Add new columns to the event table
ALTER TABLE event
    ADD COLUMN organization VARCHAR(255);

ALTER TABLE event
    ADD COLUMN guest_number INTEGER;
