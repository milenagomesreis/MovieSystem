package com.movies.controller;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.movies.persistence.HibernateUtil;

public class SessionController {
	
	public Session getSession(){
		Session session = null;
		try{
			SessionFactory factory = HibernateUtil.getSessionFactory();
			session = factory.openSession();
		}catch(HibernateException e){
			e.printStackTrace();
		}
		return session;
	}

	public void closeSession(Session session) {
		try{
			if (session.isOpen()) {
				session.close();
				if (!session.isOpen()) {
					System.out.println("Connection closed");
				}
			}
		}catch(HibernateException e){
			e.printStackTrace();
		}
		

	}
}
