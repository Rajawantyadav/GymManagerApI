
CREATE Database practice;
-- practice.gym_member definition

CREATE TABLE `gym_member` (
  `member_id` int NOT NULL AUTO_INCREMENT,
  `member_name` varchar(22) DEFAULT NULL,
  `email` varchar(22) DEFAULT NULL,
  `mobile` varchar(22) DEFAULT NULL,
  `dob` varchar(22) DEFAULT NULL,
  `gender` varchar(22) DEFAULT NULL,
  `weight` varchar(10) DEFAULT NULL,
  `plan_id` varchar(22) DEFAULT NULL,
  `batch_id` varchar(22) DEFAULT NULL,
  `member_active` varchar(5) DEFAULT NULL,
  `member_joining_date` varchar(22) DEFAULT NULL,
  `member_exit_date` varchar(22) DEFAULT NULL,
  `plan_taken_date` varchar(22) DEFAULT NULL,
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- practice.gym_member_attendance definition

CREATE TABLE `gym_member_attendance` (
  `attendance_id` int NOT NULL AUTO_INCREMENT,
  `member_id` varchar(22) DEFAULT NULL,
  `member_name` varchar(22) DEFAULT NULL,
  `attendance` varchar(22) DEFAULT NULL,
  `punch_date` varchar(20) DEFAULT NULL,
  `punch_in_datetime` varchar(30) DEFAULT NULL,
  `punch_out_datetime` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`attendance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- practice.gym_plan definition

CREATE TABLE `gym_plan` (
  `plan_id` int NOT NULL AUTO_INCREMENT,
  `plan_name` varchar(22) DEFAULT NULL,
  `plan_fees` varchar(22) DEFAULT NULL,
  `plan_duration` varchar(22) DEFAULT NULL,
  `plan_desc` varchar(50) DEFAULT NULL,
  `plan_active` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`plan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- practice.gym_batch definition

CREATE TABLE `gym_batch` (
  `batch_id` int NOT NULL AUTO_INCREMENT,
  `batch_name` varchar(22) DEFAULT NULL,
  `batch_limit` varchar(22) DEFAULT NULL,
  `batch_start_time` varchar(22) DEFAULT NULL,
  `batch_end_time` varchar(50) DEFAULT NULL,
  `batch_active` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`batch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;