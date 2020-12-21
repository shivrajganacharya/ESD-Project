package com.example.esdprojectdemo8;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Path("student")
public class StudentController {

    private String final_email;

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(Student student)
    {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Student s1 = null;
        String ret;
        s1 = entityManager.find(Student.class, student.getEmail());
        System.out.println("----------> received value of password: " + student.getPassword());
        System.out.println("----------> database value of password: " + s1.getPassword());
        entityManager.getTransaction().commit();
        entityManagerFactory.close();
        if(s1.getPassword().equals(student.getPassword()))
        {
            final_email = s1.getEmail();
            return Response.ok().build();
        }
        else
        {
            return Response.status(203).build();
        }
    }

    @POST
    @Path("display")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Student dashboard(Student student)
    {
        System.out.println("----------> In dashboard function");
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        System.out.println("----------> final_email: " + student.getEmail());
        Student s1 = entityManager.find(Student.class, student.getEmail());
        return s1;
    }


    @POST
    @Path("courses")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<StudentCourses> getCourses(Student student)
    {
        System.out.println("----------> In getCourses()");
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Student s1 = entityManager.find(Student.class, student.getEmail());

        Query query = entityManager.createQuery("SELECT c.student_id, c.course, c.grade FROM StudentCourses c WHERE c.student_id = :student_id");
        query.setParameter("student_id", s1.getRoll_number());
        List<StudentCourses> listCourses = query.getResultList();
        System.out.println("----------> s1.getRoll_number(): " + s1.getRoll_number());
        System.out.println("----------> List size: " + listCourses.size());

        /*if(listCources == null)
        {
            System.out.println("----------> No Cources");
        }
        else
        {
            System.out.println("----------> Yes Cources");
            System.out.println("----------> listCources length" + listCources.size());
            for(StudentCourses sc: listCources)
            {
                System.out.println("----------> In for loop");
                System.out.println("----------> sc.getStudent_id(): " + sc.getStudent_id());
                System.out.println("----------> student.getStudent_id(): " + student.getStudent_id());
                if(sc.getStudent_id() == s1.getRoll_number())
                {
                    System.out.println("----------> In for loop, in if");
                    System.out.println("----------> Course: " + sc.getCourse());
                    courses.add(sc.getCourse());
                }
            }
        }*/
        entityManager.getTransaction().commit();
        entityManagerFactory.close();
        return listCourses;
    }

    @POST
    @Path("change")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String changePassword(Student student)
    {
        Random r = new Random();
        int low = 1000;
        int high = 9999;
        int result = r.nextInt(high-low) + low;

        String newPassword = Integer.toString(result);

        try {
            JavaMailUtil.sendMail(student.getEmail(), newPassword);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Student s1 = null;
        s1 = entityManager.find(Student.class, student.getEmail());
        System.out.println(s1.getFirst_name());
        s1.setPassword(newPassword);
        entityManager.persist(s1);
        entityManager.getTransaction().commit();
        entityManagerFactory.close();

        return newPassword;
    }

}
