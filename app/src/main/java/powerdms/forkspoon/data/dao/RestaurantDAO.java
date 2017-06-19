package powerdms.forkspoon.data.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;
import java.util.ArrayList;

import powerdms.forkspoon.data.AppDatabaseHelper;
import powerdms.forkspoon.helper.log.LogManager;
import powerdms.forkspoon.model.restaurant.search.ZomatoRestaurant;


public class RestaurantDAO extends AbstractDAO<ZomatoRestaurant, String> implements
        IOperationDAO<ZomatoRestaurant> {

    private AppDatabaseHelper helper;

    public RestaurantDAO(AppDatabaseHelper helper) {
        this.helper = helper;
    }

    public AppDatabaseHelper getHelper() {
        return helper;
    }

    @Override
    public Class<ZomatoRestaurant> getEntityClass() {
        return ZomatoRestaurant.class;
    }

    @Override
    public Dao<ZomatoRestaurant, String> getDao() {
        try {
            return DaoManager.createDao(getHelper().getConnectionSource(), getEntityClass());
        } catch (Exception localException) {
            LogManager.getInstance().error(getClass().getCanonicalName(), localException.getMessage());
        }
        return null;
    }

    public void Insert(ZomatoRestaurant l, int operation) {
        try {
            switch (operation) {
                case IOperationDAO.OPERATION_INSERT:
                    getDao().create(l);
                    break;
                case IOperationDAO.OPERATION_INSERT_OR_UPDATE:
                    getDao().createOrUpdate(l);
                    break;
                case IOperationDAO.OPERATION_INSERT_IF_NOT_EXISTS:
                    getDao().createIfNotExists(l);
                    break;
            }
        } catch (SQLException e) {
            LogManager.getInstance().error(getClass().getCanonicalName(), e.getMessage());
        }
    }

    public ZomatoRestaurant Get(ZomatoRestaurant object) {
        ZomatoRestaurant restaurant = null;
        try {
            restaurant = getDao().queryForSameId(object);
        } catch (SQLException e) {
            LogManager.getInstance().error(getClass().getCanonicalName(), e.getMessage());
        }
        return restaurant;
    }

    public ArrayList<ZomatoRestaurant> GetAll() {
        ArrayList<ZomatoRestaurant> lists = null;
        try {
            lists = (ArrayList<ZomatoRestaurant>) getDao().queryBuilder().query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lists;
    }

    public void Delete(ZomatoRestaurant restaurant) {
        try {
            getDao().delete(restaurant);
        } catch (SQLException e) {
            LogManager.getInstance().error(getClass().getCanonicalName(), e.getMessage());
        }
    }

    public void Update(ZomatoRestaurant restaurant) {
        try {
            getDao().update(restaurant);
        } catch (SQLException e) {
            LogManager.getInstance().error(getClass().getCanonicalName(), e.getMessage());
        }
    }



}
