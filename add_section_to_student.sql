ALTER TABLE student ADD COLUMN section_id BIGINT;
ALTER TABLE student ADD CONSTRAINT fk_student_section FOREIGN KEY (section_id) REFERENCES section(id); 