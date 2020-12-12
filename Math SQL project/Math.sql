
#Team sqlinjectors
#Sho Masuda, Chandula Kodituwakku, Samuel Tsokwa, Tony Chan, Andrew Yung
#2019/11/23



DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Student; 
DROP TABLE IF EXISTS Teacher;
DROP TABLE IF EXISTS Math_levels;
DROP TABLE IF EXISTS Worksheets;
DROP TABLE IF EXISTS Worksheettemplate;
DROP TABLE IF EXISTS TeacherAvailability; 
DROP TABLE IF EXISTS Online_cource; 
DROP TABLE IF EXISTS Math_Category;


CREATE TABLE Credentials(Username VARCHAR(255) PRIMARY KEY, Hashed_PWD VARCHAR(255));
CREATE TABLE Math_levels(Level INT NOT NULL PRIMARY KEY);
CREATE TABLE Math_Category(Category Text PRIMARY KEY);

CREATE TABLE Users(
	User_ID VARCHAR(255) PRIMARY KEY,
	Username VARCHAR(255) REFERENCES Credentials(Username),
	Age INT NOT NULL,
	Skype_ID TEXT, 
	Preferred_Language TEXT, 
	Contact_info TEXT,
	Category VARCHAR (255),
	Address TEXT
 );


CREATE TABLE Student(
    Student_ID VARCHAR(255) PRIMARY KEY NOT NULL ,
    User_ID VARCHAR(255) REFERENCES Users(User_ID),
	Starting_Level INT
 );

CREATE TABLE Teacher(
    Teacher_ID VARCHAR(255) PRIMARY KEY NOT NULL ,
    User_ID VARCHAR(255) REFERENCES Users(User_ID)
 );

CREATE TABLE Worksheet_template(
    Worksheet_ID VARCHAR(255) PRIMARY KEY,
	Worksheet_temp Text
 );


CREATE TABLE Online_course(
    Course_ID VARCHAR(255) PRIMARY KEY ,
    Math_levels INT REFERENCES Math_levels(level),
	Worksheet_ID VARCHAR(255) REFERENCES Worksheet_template(Worksheet_ID),
	Math_Category VARCHAR(255) REFERENCES Math_Category(Category),
	Course_link Text
 );

CREATE TABLE Worksheets(
	Worksheet_ID VARCHAR(255) REFERENCES Worksheet_template(Worksheet_ID),
	Worksheet_submission_ID VARCHAR(255) PRIMARY KEY,
	Student_ID VARCHAR(255) REFERENCES Student(Student_ID),
	Teacher_ID VARCHAR(255) REFERENCES Teacher(Teacher_ID),
    Grade DECIMAL,
    Course_ID VARCHAR(255) REFERENCES Online_Course(Course_ID),
    Date_submitted DATE NOT NULL,
	Date_marked DATE NOT NULL,
	Feedback TEXT
 );


CREATE TABLE  Teacher_availability(
    Teacher_Availability_ID VARCHAR(255) PRIMARY KEY ,
    Teacher_ID VARCHAR(255) REFERENCES Teacher(Teacher_ID),
	Day TIMESTAMP NOT NULL,
	Time_start TIME NOT NULL,
	Time_end TIME NOT NULL
 );



INSERT INTO Credentials (Username, Hashed_PWD)
VALUES ('John_D', '56786'),
	   ('DAVE_S','678987'),
	   ('Howard_G', '76578'),
	   ('Show_H', '091878'), 
	   ('AL_Y', '098679');
  

INSERT INTO Users(User_ID, Username , Age , Skype_ID, Preferred_Language,Contact_info,Category,Address)
VALUES('ytuyt','John_D',67,'ghjhj','Chinese','878789696','Logic','China'),
	  ('t7987u93','DAVE_S',14,'iu899u','Greek','989823809','Algebra','Russia'),
	  ('8787hfnbw','Howard_G',35,'k0k0j0h','English','989789234','Probability','England'),
	  ('9879ufhe','Show_H',88,'89890u','Japanese','03533773650','Proofs','Japan'),
	  ('hsohwa','AL_Y',23,'hshss','Korean','9393020202','Proofs','Korea' );

INSERT INTO Student(Student_ID, User_ID,Starting_Level)
VALUES('S878907','t7987u93',1),
	   ('S890898','hsohwa', 3);

INSERT INTO Teacher(Teacher_ID, User_ID)
VALUES('T456767','ytuyt'),
	  ('T676982','9879ufhe'),
	  ('T788887','8787hfnbw');

INSERT INTO Worksheet_template(Worksheet_ID,Worksheet_temp)
VALUES('W090009','www.link1.com'),
	  ('W676656','www.link2.com'),
	  ('W125473','www.link3.com'),
	  ('W463517','www.link4.com'),
	  ('W989482','www.link5.com'),
	  ('W546454','www.link6.com');

INSERT INTO Worksheets(Worksheet_ID,Worksheet_submission_ID,Student_ID,Teacher_ID,Grade,Course_ID,Date_submitted,Date_marked,Feedback)
VALUES('W090009','WS9878898','S878907','T456767',34.9,'C988803',TO_DATE('2019-09-30','YYYY-MM-DD'),TO_DATE('2019-10-22','YYYY-MM-DD'),'Work on Section 4'),
	  ('W463517','WS9876728','S878907','T676982',23.6,'C989453',TO_DATE('2019-09-02','YYYY-MM-DD'),TO_DATE('2019-10-21','YYYY-MM-DD'),'Work on Section 8'),
	  ('W546454','WS4324564','S890898','T788887',89.0,'C989453',TO_DATE('2019-09-12','YYYY-MM-DD'),TO_DATE('2019-10-22','YYYY-MM-DD'),'Work on Section 7'),
	  ('W090009','WS6161661','S890898','T788887',50.0,'C989803',TO_DATE('2019-09-18','YYYY-MM-DD'),TO_DATE('2019-10-24','YYYY-MM-DD'),'Work on Section 7'),
	  ('W546454','WS9001001','S890898','T788887',67.7,'C987373',TO_DATE('2019-09-10','YYYY-MM-DD'),TO_DATE('2019-10-23','YYYY-MM-DD'),'Work on Section 6'),
	  ('W546454','WS1919196','S890898','T676982',77.9,'C989453',TO_DATE('2019-09-29','YYYY-MM-DD'),TO_DATE('2019-10-22','YYYY-MM-DD'),'Work on Section 5'),
	  ('W125473','WS1735362','S878907','T456767',78.9,'C987373',TO_DATE('2019-09-04','YYYY-MM-DD'),TO_DATE('2019-10-21','YYYY-MM-DD'),'Work on Section 4'),
	  ('W125473','WS0193829','S878907','T788887',56.5,'C989803',TO_DATE('2019-09-04','YYYY-MM-DD'),TO_DATE('2019-10-18','YYYY-MM-DD'),'Work on Section 3'),
	  ('W125473','WS1424355','S890898','T456767',87.6,'C002803',TO_DATE('2019-09-12','YYYY-MM-DD'),TO_DATE('2019-10-12','YYYY-MM-DD'),'Work on Section 1'),
	  ('W463517','WS9187657','S890898','T788887',14.8,'C002803',TO_DATE('2019-09-09','YYYY-MM-DD'),TO_DATE('2019-10-01','YYYY-MM-DD'),'Work on Section 3');

INSERT INTO Math_levels(Level)
VALUES(1),
(2),
(3),
(4),
(5),
(6),
(7),
(8),
(9),
(10);

INSERT INTO Teacher_availability(Teacher_Availability_ID, Teacher_ID, Day, Time_start, Time_end)
Values('Time4545','T788887','12-06-18' , '12:00' , '14:00'),
      ('Time8785','T456767','08-08-18' , '17:00' , '21:30'),
      ('Time1237','T676982','11-04-18' , '13:00' , '16:30'),
      ('Time0956','T788887','11-23-18' , '11:00' , '14:00'),
      ('Time1242','T456767','12-09-18', '10:00' , '14:00');


INSERT INTO Math_Category(Category)
Values ('Proofs'),
	   ('Probability'),
	   ('Algebra'),
	   ('Logic'),
	   ('Basic Arithmetic'),
	   ('Geometry'),
	   ('Combination'),
	   ('Number Theory');

INSERT INTO Online_Course(Course_ID,Math_levels,Worksheet_ID,Math_Category,Course_link)
VALUES('C988803',1,'W676656','Proofs','www.Course_link1.com'),
('C989803',1,'W676656','Proofs','www.Course_link1.com'),
('C989453',2,'W676656','Proofs','www.Course_link2.com'),
('C987373',3,'W676656','Proofs','www.Course_link3.com'),
('C002803',4,'W676656','Proofs','www.Course_link4.com'),
('C963703',5,'W676656','Proofs','www.Course_link5.com'),
('C989049',6,'W676656','Proofs','www.Course_link6.com'),
('C263654',7,'W676656','Proofs','www.Course_link7.com');


(Select Airlines 
from [I don't know where]
where route = LAX to SFO)

Union

(Select Airlines 
from [I don't know the schema]
where route = LAX to SFO)


	 
