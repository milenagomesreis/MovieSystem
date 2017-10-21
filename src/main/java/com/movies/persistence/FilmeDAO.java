package com.movies.persistence;

import java.util.ArrayList;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.movies.controller.SessionController;
import com.movies.model.Filme;

public class FilmeDAO {
	static SessionController sc;
	
	public FilmeDAO(){
		sc = new SessionController();
	}
	
	public Filme incluir(Filme filme){
		Session sessao = null;
		Transaction tr = null;
		try{
			sessao = sc.getSession();
			tr = sessao.beginTransaction();
			sessao.save(filme);
			tr.commit();
		}catch(RuntimeException e){
			e.printStackTrace();
			if (tr != null){
				tr.rollback();
			}
		}finally {
			sc.closeSession(sessao);
		}
		return filme;
		
	}
	
	public boolean alterar(Filme filme){
		boolean alterado = false;
		Session sessao = null;
		Transaction tr = null;
		try{
			sessao = sc.getSession();
			tr = sessao.beginTransaction();
			sessao.merge(filme);
			tr.commit();
			alterado = true;
		}catch(RuntimeException e){
			if (tr != null){
				tr.rollback();
			}
			alterado = false;
		}finally{
			sc.closeSession(sessao);
		}
		return alterado;
	}
	
	public boolean excluir(int id){
		boolean deletado = false;
		Session sessao = null;
		Transaction tr = null;
		try{
			sessao = sc.getSession();
			tr = sessao.beginTransaction();
			Filme c = (Filme) sessao.load(Filme.class, id);
			sessao.delete(c);
			tr.commit();
			deletado = true;
		}catch(RuntimeException e){
			if (tr != null){
				tr.rollback();
			}
			deletado = false;
		}finally{
			sc.closeSession(sessao);
		}
		return deletado;
		
	}
	
	public Filme getFilmePorId(int id){
		Filme c = null;
		Session sessao = null;
		try{
			sessao = sc.getSession();
			CriteriaBuilder builder = sessao.getCriteriaBuilder();
			CriteriaQuery<Filme> criteria = builder.createQuery(Filme.class);
			Root<Filme> from = criteria.from (Filme.class);
			TypedQuery<Filme> tq = sessao.createQuery(criteria.select(from).where(builder.equal(from.get("id"), id)));
			
			c = tq.getSingleResult();
			
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		
		return c;
	}
	
	public boolean hasFilme(Filme filme){
		Session sessao = null;
		Transaction tr = null;
		try{
			sessao = sc.getSession();
			tr = sessao.beginTransaction();
			Filme filme1 = (Filme) sessao.load(Filme.class, filme.getId());
			if(filme1.equals(filme)){
				return true;
			}
		}catch(RuntimeException e){
			e.printStackTrace();
		}finally{
			sc.closeSession(sessao);
		}
		return false;
	}
	
	public ArrayList<Filme> listarFilmes(){
		ArrayList<Filme> filmes = null;
		Session sessao = null;
		
		try{
			sessao = sc.getSession();
			CriteriaBuilder builder = sessao.getCriteriaBuilder();
			CriteriaQuery<Filme> criteria = builder.createQuery(Filme.class);
			criteria.from(Filme.class);
			filmes = (ArrayList<Filme>) sessao.createQuery(criteria).getResultList();
			
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		
		return filmes;
	}
}
