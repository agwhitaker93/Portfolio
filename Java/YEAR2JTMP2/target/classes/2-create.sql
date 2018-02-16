CREATE TABLE `student`	(
	`student_ID`				VARCHAR(6),
    `student_name`				VARCHAR(30),
    `degree_scheme`				VARCHAR(50),
    
	PRIMARY KEY					(`student_ID`)
) ENGINE = InnoDB;

CREATE TABLE `staff`		(
	`staff_ID`					VARCHAR(6),
	`staff_name`				VARCHAR(50),
    `staff_grade`				VARCHAR(30),
    
    PRIMARY KEY					(`staff_ID`)
) ENGINE = InnoDB;

CREATE TABLE `module` (
	`module_ID`					VARCHAR(6),
    `module_name`				VARCHAR(50),
    `credits`					INT,
    
    PRIMARY KEY					(`module_ID`)
) ENGINE = InnoDB;

CREATE TABLE `registered` (
	`student_ID`				VARCHAR(6),
    `module_ID`					VARCHAR(6),
    
    PRIMARY KEY					(`student_ID`, `module_ID`),
    
    INDEX						`fk_registered_student_idx`	(`student_ID` ASC),
    CONSTRAINT					`fk_registered_student`
		FOREIGN KEY				(`student_ID`)
        REFERENCES				`student`	(`student_ID`)
        ON DELETE CASCADE,
        
	INDEX						`fk_registered_module_idx`	(`module_ID` ASC),
    CONSTRAINT					`fk_registered_module`
		FOREIGN KEY				(`module_ID`)
        REFERENCES				`module`	(`module_ID`)
        ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE `teaches` (
	`staff_ID`					VARCHAR(6),
    `module_ID`					VARCHAR(6),
    
    PRIMARY KEY					(`staff_ID`, `module_ID`),
    
    INDEX						`fk_teaches_staff_idx`	(`staff_ID` ASC),
    CONSTRAINT					`fk_teaches_staff`
		FOREIGN KEY				(`staff_ID`)
        REFERENCES				`staff`	(`staff_ID`),
        
	INDEX						`fk_teaches_module_idx`	(`module_ID` ASC),
    CONSTRAINT					`fk_teaches_module`
		FOREIGN KEY				(`module_ID`)
        REFERENCES				`module`	(`module_ID`)
) ENGINE = InnoDB;
