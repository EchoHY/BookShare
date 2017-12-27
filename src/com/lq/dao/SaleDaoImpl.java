package com.lq.dao;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.lq.entity.Sale;
@Repository
public class SaleDaoImpl implements SaleDao{
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public boolean updateSale(int bookid, long start_time, int sureornot) {
		// TODO Auto-generated method stub
		String hql = "update Sale u set u.start_time=?,u.sureornot=?where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setLong(0, start_time);
		query.setInteger(1, sureornot);
		query.setInteger(2, bookid);
		return (query.executeUpdate()>0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sale> getSalewithoutConfirm(List<Integer> books) {
		// TODO Auto-generated method stub
		String hql = "FROM Sale u WHERE u.sureornot = 0 and u.id in (:alist)";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameterList("alist", books);
		return query.list();
	}

	@Override
	public Sale getOneSale(int bookid) {
		// TODO Auto-generated method stub
		String hql = "FROM Sale u Where u.id=? ";
		// 根据需要显示的信息不同  将*替换成不同属性，还是封装成一个类CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, bookid);
		return (Sale) query.uniqueResult();
	}
}