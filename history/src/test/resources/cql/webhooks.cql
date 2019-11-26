CREATE KEYSPACE IF NOT EXISTS voc_test_keyspace
    WITH REPLICATION = {
        'class': 'SimpleStrategy',
        'replication_factor': 1
    };

USE voc_test_keyspace;

CREATE TABLE calendly_invitee_stream (
    assigned_to text,
    day text,
    bucket int,
    ts timeuuid,
    payload text,
    primary key((assigned_to, day, bucket), ts)
) WITH CLUSTERING ORDER BY (ts DESC)
         AND COMPACTION = {'class': 'TimeWindowCompactionStrategy',
                       'compaction_window_unit': 'DAYS',
                       'compaction_window_size': 1};
CREATE INDEX on calendly_invitee_stream(assigned_to);

insert into calendly_invitee_stream (assigned_to, day, bucket, ts, payload)
    VALUES ('Jane Sample Data', '2018-03-14', 0, now(), 'hi');