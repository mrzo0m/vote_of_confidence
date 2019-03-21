CREATE KEYSPACE IF NOT EXISTS voc_test_keyspace
    WITH REPLICATION = {
        'class': 'SimpleStrategy',
        'replication_factor': 1
    };

USE voc_test_keyspace;

CREATE TABLE claim_by_company (
    company_name text,
    claim_id uuid,
    claiminfo text,
    PRIMARY KEY (claim_id)
    );

CREATE INDEX on claim_by_company(company_name);

-- Sample Data 10 Cars
INSERT INTO claim_by_company (company_name, claim_id, claiminfo)
VALUES ('BMW', 8557f426-1cc9-41bf-a581-fadb101cd289,  'BMW 335D');
