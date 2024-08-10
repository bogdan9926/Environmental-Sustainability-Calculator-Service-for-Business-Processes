CREATE TABLE energy_co2_emission_factor (
    id SERIAL PRIMARY KEY,
    country VARCHAR(255),
    unit VARCHAR(255),
    factor NUMERIC
);
INSERT INTO energy_co2_emission_factor (country, unit, factor) VALUES
     ('Romania', 'KWh', 0.2895),
     ('Netherlands', 'KWh', 0.2029);

CREATE TABLE fuel_emission_factor (
    fuel_type VARCHAR(255) PRIMARY KEY ,
    unit VARCHAR(255),
    factor NUMERIC
);
INSERT INTO fuel_emission_factor (fuel_type, unit, factor) VALUES
     ('Natural gas', 'm3', 2.02135),
     ('Aviation turbine fuel', 'l', 2.54514),
     ('Diesel', 'l', 2.70553),
     ('Coal', 't', 2403.84),
     ('Bioethanol', 'l', 0.00901),
     ('Biodiesel ME', 'l', 0.16751),
     ('Wood pellets', 't', 72.61754),
     ('Grass/straw', 't', 49.23656),
     ('Biogas', 't', 1.21518),
     ('Energy_Romania','kwh',0.2895),
     ('Energy_Netherlands', 'KWh', 0.2029);

