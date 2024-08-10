CREATE TABLE process (
    id SERIAL PRIMARY KEY,
    platform VARCHAR(255),
    process_name VARCHAR(255),
    process_key VARCHAR(255),
    status VARCHAR(255),
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    diagram_xml TEXT
);
CREATE TABLE task (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    process_id int not null,
    FOREIGN KEY (process_id) REFERENCES process(id)
);

CREATE TABLE process_annotation (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  frequency_per_month INTEGER
);
CREATE TABLE task_annotation (
     name VARCHAR(255),
     duration VARCHAR(255),
     time_unit VARCHAR(255),
     process_id INTEGER,
     PRIMARY KEY (name, process_id),
     FOREIGN KEY (process_id) REFERENCES process_annotation(id)
);

CREATE TABLE resource_annotation (
  name VARCHAR(255) ,
  fuel_per_use VARCHAR(255),
  type VARCHAR(255),
  fuel_type VARCHAR(255),
  fuel_unit VARCHAR(255),
  time_unit VARCHAR(255),
  process_id INTEGER,
  PRIMARY KEY (name, process_id),
  FOREIGN KEY (process_id) REFERENCES process_annotation(id)

);

CREATE TABLE resource_usage (
    id SERIAL PRIMARY KEY,
    process_id INTEGER NOT NULL,
    task_name VARCHAR(255) NOT NULL,
    resource_name VARCHAR(255),
    time_used VARCHAR(255),
    unit VARCHAR(255),
    FOREIGN KEY (process_id, task_name) REFERENCES task_annotation(process_id, name)
);


CREATE TABLE intermediate_process_consumption (
    id SERIAL PRIMARY KEY,
    process_id int not null,
    task VARCHAR(255),
    unit VARCHAR(255),
    value DOUBLE PRECISION,
    fuel_type VARCHAR(255),
    FOREIGN KEY (process_id) REFERENCES process(id)
);

CREATE TABLE intermediate_co2_emission (
  id SERIAL PRIMARY KEY,
  process_id int not null,
  task VARCHAR(255) not null,
  value DOUBLE PRECISION,
  FOREIGN KEY (process_id) REFERENCES process(id)
);
