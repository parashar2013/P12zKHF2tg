CREATE TABLE Appointment (
    health_card CHAR(15) NOT NULL,
    doctor_id INT NOT NULL,
    date_and_time DATETIME NOT NULL,
    primary key (date_and_time, health_card),
    foreign key (health_card)
        references Patient (health_card),
    foreign key (doctor_id)
        references Employee (id)
) ENGINE = MyISAM;

CREATE TABLE Patient (
    name VARCHAR(50) NOT NULL,
    address TEXT NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    health_card CHAR(15) NOT NULL,
    sin_number INT NOT NULL UNIQUE,
    number_of_visits INT NOT NULL,
    default_doctor_id INT NOT NULL,
    current_health TEXT NOT NULL,
    password VARCHAR(64) NOT NULL,
    primary key (health_card),
    foreign key (default_doctor_id)
        references Employee (id)
    ) ENGINE = MyISAM;

CREATE TABLE Doc_Patient (
    doctor_id INT NOT NULL,
    health_card CHAR(15) NOT NULL,
    foreign key (doctor_id)
        references Employee (id),
    foreign key (patient)
        references Patient (health_card)
) ENGINE = MyISAM;

CREATE TABLE Visit (
    duration INT NOT NULL,
    health_card CHAR(15) NOT NULL,
    doctor_id INT NOT NULL,
    diagnosis TEXT NOT NULL,
    prescriptions TEXT NOT NULL,
    treatment DATETIME NOT NULL,
    comments TEXT NOT NULL,
    date_and_time DATETIME NOT NULL,
    last_modified DATETIME NOT NULL,
    primary key (date_and_time, health_card),
    foreign key (health_card)
        references Appointment (health_card),
    foreign key (doctor_id)
        references Appointment (doctor_id),
    foreign key (date_and_time)
        references Appointment (date_and_time)
) ENGINE = MyISAM;

CREATE TABLE Employee (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL,
    password VARCHAR(64) NOT NULL,
    primary key (id)
) ENGINE = MyISAM;

CREATE TABLE Doc_Staff(
    doctor_id INT NOT NULL,
    staff_id INT NOT NULL,
    foreign key (doctor_id)
        references Employee (id),
    foreign key (staff_id)
        references Employee (id)
) ENGINE = MyISAM;