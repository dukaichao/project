package cn.dkc.travel.service.impl;


import cn.dkc.travel.dao.FavoriteDao;
import cn.dkc.travel.dao.impl.FavoriteDaoImpl;
import cn.dkc.travel.domain.Favorite;
import cn.dkc.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {

        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);

        return favorite != null;//如果对象有值，则为true，反之，则为false
    }

    @Override
    public void add(String rid, int uid) {

        favoriteDao.add(Integer.parseInt(rid),uid);
    }
}
