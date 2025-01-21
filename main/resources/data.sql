INSERT INTO users (username, password, role, avatar) VALUES
                                                         ('admin', 'Abc@12345', 'ADMIN', NULL),
                                                         ('employee', 'Employee@123', 'EMPLOYEE', NULL);

INSERT INTO salaries (user_id, base_salary, performance_bonus, full_attendance_bonus, payment_date) VALUES
    (2, 3000, 500, 200, '2023-12-31');

INSERT INTO attendances (user_id, date, status) VALUES
    (2, '2023-12-01', 'PRESENT');

INSERT INTO leave_requests (user_id, leave_type, start_date, end_date, status) VALUES
    (2, 'SICK', '2023-12-10', '2023-12-12', 'pending');

INSERT INTO trainings (title, description, start_date, end_date, status) VALUES
    ('Java Training', 'Learn advanced Java programming.', '2023-12-15', '2023-12-20', 'scheduled');
