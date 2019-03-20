package vif.online.chungkhoan.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vif.online.chungkhoan.dao.UserDao;
import vif.online.chungkhoan.entities.User;

@Transactional
@Repository
public class UserDaoImpl implements UserDao{
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		String hql = "FROM User as u WHERE u.isDeleted = 0 ORDER BY u.username asc";
		return (List<User>) entityManager.createQuery(hql).getResultList();
	}
	 

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return entityManager.find(User.class, id);
	}

	@Override
	public User getUserByUserName(String user_name) {
		// TODO Auto-generated method stub
		String hql = "FROM User as u WHERE u.username = :username";
		@SuppressWarnings("unchecked")
		List<User> lstResult = entityManager.createQuery(hql).setParameter("username", user_name).getResultList();
		if (lstResult != null && lstResult.size() > 0) {
			return lstResult.get(0);
		}
		return null;
	}

	@Override
	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		user.setPassword(passwordEncoder.encode(user.returnPassword()));
		entityManager.persist(user);
		return true;
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
//		entityManager.flush();
		User mUser = entityManager.find(User.class, user.getId());
		user.setPassword(mUser.returnPassword());
		entityManager.merge(user);
	}

	@Override
	public void deleteUserByUsername(String user_name) {
		// TODO Auto-generated method stub
//		entityManager.remove(getUserByUserName(user_name));
		User mUser = getUserByUserName(user_name);
		mUser.setIsDeleted(1);
		entityManager.merge(mUser);
	}
	
	@Override
	public void deleteUserById(Integer id) {
		// TODO Auto-generated method stub
		User mUser = entityManager.find(User.class, id);
		mUser.setIsDeleted(1);
		entityManager.merge(mUser);
	}

	@Override
	public boolean userExists(User user) {
		// TODO Auto-generated method stub
		String hql = "FROM User as u WHERE u.isDeleted = 0 AND u.username = :username";
		return entityManager.createQuery(hql).setParameter("username", user.getUsername()).getResultList().size() >0?true:false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean authenUser(String username, String password) {
		// TODO Auto-generated method stub
		String hql = "FROM User as u WHERE u.username = :username AND u.password = :password AND u.activeFlg = 1 AND u.isDeleted = 0";
		List<User> lstUsers = (List<User>) entityManager.createQuery(hql).setParameter("username", username).setParameter("password", password).getResultList();
		if(lstUsers != null && lstUsers.size()>0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> SearchUserByCondition(int page, int pageSize, String columnSortName, Boolean asc, String username,
			Integer activeFlg, String email, String role) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<User> from = criteriaQuery.from(User.class);
		
		CriteriaQuery<Object> select = criteriaQuery.select(from);
		
		select.where(criteriaBuilder.equal(from.get("isDeleted"), 0));
		
		if(username != null && !username.equals("")) {
			select.where(criteriaBuilder.equal(from.get("username"), username));
		}
		
		if(activeFlg != null) {
			select.where(criteriaBuilder.equal(from.get("activeFlg"), activeFlg));
		}
		
		if(email != null && !email.equals("")) {
			select.where(criteriaBuilder.equal(from.get("email"), email));
		}
		
		if(role != null && !role.equals("")) {
			select.where(criteriaBuilder.equal(from.get("role"), role));
		}
		
		if(columnSortName != null && !columnSortName.equals("")) {
			if(asc== null || asc) {
				select.orderBy(criteriaBuilder.asc(from.get(columnSortName)));
			}else {
				select.orderBy(criteriaBuilder.desc(from.get(columnSortName)));
			}
		}
		
		Query query = entityManager.createQuery(criteriaQuery);
		if (page >= 0 && pageSize >= 0) {
			query.setFirstResult((page - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		List<User> lstResult = query.getResultList();

		return lstResult;
	}
}
