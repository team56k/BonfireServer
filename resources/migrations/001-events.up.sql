create table event (
  id bigserial PRIMARY KEY,
  creator_id varchar(128),
  created_at TIMESTAMP default CURRENT_TIMESTAMP,
  updated_at TIMESTAMP default CURRENT_TIMESTAMP
);
