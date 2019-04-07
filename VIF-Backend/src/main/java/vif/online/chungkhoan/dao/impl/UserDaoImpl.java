package vif.online.chungkhoan.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vif.online.chungkhoan.dao.UserDao;
import vif.online.chungkhoan.entities.User;
import vif.online.chungkhoan.helper.VifApiHelper;
import vif.online.chungkhoan.helper.VifMailHelper;

@Transactional
@Repository
public class UserDaoImpl implements UserDao{
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private VifApiHelper apiHelper;
	
	@Autowired
	private VifMailHelper emailHepler;

	
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
		String hql = "FROM User as u WHERE u.username = :username AND u.isDeleted=0";
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
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		predicates.add(criteriaBuilder.equal(from.get("isDeleted"), 0));
		
		if(username != null && !username.equals("")) {
			predicates.add(criteriaBuilder.equal(from.get("username"), username));
		}
		
		if(activeFlg != null) {
			predicates.add(criteriaBuilder.equal(from.get("activeFlg"), activeFlg));
		}
		
		if(email != null && !email.equals("")) {
			predicates.add(criteriaBuilder.equal(from.get("email"), email));
		}
		
		if(role != null && !role.equals("")) {
			predicates.add(criteriaBuilder.equal(from.get("role"), role));
		}
		
		select.select(from).where(predicates.toArray(new Predicate[]{}));
		
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


	@SuppressWarnings("unchecked")
	@Override
	public int getRowCount(String username, Integer activeFlg, String email, String role) {
		// TODO Auto-generated method stub
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<User> from = criteriaQuery.from(User.class);
		
		CriteriaQuery<Object> select = criteriaQuery.select(from);
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		predicates.add(criteriaBuilder.equal(from.get("isDeleted"), 0));
		
		if(username != null && !username.equals("")) {
			predicates.add(criteriaBuilder.equal(from.get("username"), username));
		}
		
		if(activeFlg != null) {
			predicates.add(criteriaBuilder.equal(from.get("activeFlg"), activeFlg));
		}
		
		if(email != null && !email.equals("")) {
			predicates.add(criteriaBuilder.equal(from.get("email"), email));
		}
		
		if(role != null && !role.equals("")) {
			predicates.add(criteriaBuilder.equal(from.get("role"), role));
		}
		
		select.select(from).where(predicates.toArray(new Predicate[]{}));
		
		Query query = entityManager.createQuery(select);

		List<User> lstResult = query.getResultList();
		return lstResult.size();
	}

	@Override
	public boolean prepareResetPassword(String username) {
		User user = getUserByUserName(username);
		if(user!=null) {
			String tokenReset = apiHelper.generateString(20);
			String mLink = "http://localhost:4200/#/change-pass?username="+username+"&token="+tokenReset;
			String content = "You're receiving this e-mail because you requested a password reset for your Postmates account. Please <a href='" + mLink + "'>click here</a> to choose a new password.";
			try {
				emailHepler.sendMailWithHTMLContent(user.getEmail(), "Reset password", content);
				user.setTokenReset(tokenReset);
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.DATE, 1);
				user.setTokenResetExpried(c.getTime());
				entityManager.merge(user);
				return true;
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}


	@Override
	public boolean resetPassword(String username, String token, String newPass) {
		User user = getUserByUserName(username);
		if(user!=null) {
			// check user, token, time expired
			if(user.getTokenReset()!=null && user.getTokenReset().equals(token)) {
				if(user.getTokenResetExpried()!=null && user.getTokenResetExpried().after(new Date())){
					// set new pass
					user.setPassword(passwordEncoder.encode(newPass));
					user.setTokenResetExpried(new Date());
					entityManager.merge(user);
					// send email inform
					emailHepler.sendMailWithSimpleText(user.getEmail(), "Reset password Successfully", "You had change new password successfully!");
					return true;
				}
			}
		}
		return false;
	}
}
