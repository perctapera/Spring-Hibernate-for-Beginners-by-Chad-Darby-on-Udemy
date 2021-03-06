package my.project.hibernate.demo;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import my.project.hibernate.demo.entity.Course;
import my.project.hibernate.demo.entity.Instructor;
import my.project.hibernate.demo.entity.InstructorDetail;
import my.project.hibernate.demo.entity.Review;
import my.project.hibernate.demo.entity.Student;

public class AddCoursesForGivenStudentDemo {

	public static void main(String[] args) {
		// create session factory
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.addAnnotatedClass(Course.class)
				.addAnnotatedClass(Review.class)
				.addAnnotatedClass(Student.class)
				.buildSessionFactory();

		// create session
		Session session = factory.getCurrentSession();
		
		try {
			// find the student, add some courses, join with student and save to database's tables using transaction
			Scanner scanner = new Scanner(System.in);
			System.out.print("Give the student id: ");
			int studentId = scanner.nextInt();
			scanner.close();
			session.beginTransaction();
			Student student = session.get(Student.class, studentId);
			if (student!=null) {
				Course course1 = new Course("Trzeci kurs dla wybranego studenta");
				Course course2 = new Course("Czwarty jeden kurs dla wybranego studenta");
				student.addCourse(course1);
				student.addCourse(course2);
				session.save(course1);
				session.save(course2);
			}
			else {
				System.out.println("Student with id " + studentId + " not found.");
			}
			session.getTransaction().commit();
		}
		finally {
			session.close();
			factory.close();
		}

	}

}
