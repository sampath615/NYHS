-- The eHARS tables NYEHMS reads (H2) for EharsPersistenceUnitTest.

CREATE TABLE country_code (country_cd VARCHAR(10), descr VARCHAR(100));
CREATE TABLE lookup_code (lookup_type VARCHAR(30), lookup_cd VARCHAR(10), lookup_name VARCHAR(100), lookup_descr VARCHAR(100));
CREATE TABLE facility_code (facility_uid VARCHAR(20), name1 VARCHAR(100), street_address1 VARCHAR(100), city_name VARCHAR(50), county_fips VARCHAR(5),
	state_cd VARCHAR(2), zip_cd VARCHAR(10), phone VARCHAR(20), country_cd VARCHAR(10), facility_type_cd VARCHAR(10));

INSERT INTO country_code VALUES ('US', 'United States');
INSERT INTO country_code VALUES ('CA', 'Canada');

INSERT INTO lookup_code VALUES ('OCCUPATION', '1', 'Nurse', 'd1');
INSERT INTO lookup_code VALUES ('OCCUPATION', '2', 'Clerk', 'd2');
INSERT INTO lookup_code VALUES ('FACILITY_TYPE', 'H', 'Hospital', 'Hospital description');
INSERT INTO lookup_code VALUES ('OTHER', 'Z', 'Not a facility type', 'x');

INSERT INTO facility_code VALUES ('F1', 'Albany Med', '43 New Scotland', 'Albany', '001', 'NY', '12208', '5185551234', 'US', 'H');
INSERT INTO facility_code VALUES ('F2', 'Bellevue', '462 First Ave', 'New York', '061', 'NY', '10016', '2125551234', 'US', 'H');
INSERT INTO facility_code VALUES ('F3', 'Other', '1 Main', 'Troy', '083', 'NY', '12180', NULL, 'US', 'Z');
