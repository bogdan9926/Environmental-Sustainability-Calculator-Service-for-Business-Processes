CREATE TABLE frontend_data (
    id SERIAL PRIMARY KEY,
    process_name VARCHAR(255),
    process_instance VARCHAR(255),
    task_name VARCHAR(255),
    co2_emission_value VARCHAR(255),
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    diagram_xml TEXT
);
CREATE TABLE annotation_json(
  process_name VARCHAR(255) PRIMARY KEY,
  data TEXT
);