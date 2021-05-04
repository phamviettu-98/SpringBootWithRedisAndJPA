package com.example.demowithredis.repository;

import com.example.demowithredis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class UserDAOImpl implements UserDAO{

    @Autowired
    private  UserReposity userReposity;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean saveUser(User user) {
        try {
            userReposity.save(user);
            redisTemplate.opsForHash().put("users",user.getId().toString(), user);

            redisTemplate.expire("users",1, TimeUnit.MINUTES);
            return true;
        }catch (Exception e){
            return false;
        }


    }

    @Override
    public Optional<User> findUserById(Long id) {
        Optional<User> user;
        user = (Optional<User>) redisTemplate.opsForHash().get("users", id.toString());
        if ( user == null){
            user = userReposity.findById(id);
            redisTemplate.opsForHash().put("users",id.toString(), user);
        }

        return user;
    }

    @Override
    public Boolean updateUser(Long id, User user) {
        try {
            redisTemplate.opsForHash().put("users", id.toString(), user);
            userReposity.save(user);
            return  true;
        }catch (Exception e){
            return false;
        }


    }

    @Override
    public List<User> getAllUser() {
        List<User> users;
        users = redisTemplate.opsForHash().values("users");
        if ( users.isEmpty()){
            users = userReposity.findAll();
            for(User user: users){
                redisTemplate.opsForHash().put("users",user.getId().toString(), user);
            }
        }
        return users;
    }

    @Override
    public boolean deleteUser(Long id, User user) {
        try {
            user.setStatus(0);
            redisTemplate.opsForHash().put("users", id.toString(), user);
            userReposity.save(user);

            return true;
        }catch (Exception e){
            return false;
        }

    }
    private void CloseConnect(StringRedisTemplate stringRedisTemplate){
        try {
            JedisConnectionFactory jedisConnectionFactory = (JedisConnectionFactory) stringRedisTemplate.getConnectionFactory();
            jedisConnectionFactory.getConnection().close();
            jedisConnectionFactory.destroy();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void closeClient(StringRedisTemplate stringRedisTemplate){
        try {
            if ( null != stringRedisTemplate.getClientList()){
                stringRedisTemplate.getClientList().remove(0);
                stringRedisTemplate.getClientList().remove(1);
                stringRedisTemplate.getClientList().forEach(redisClientInfo -> {
                    String address = redisClientInfo.getAddressPort();
                    if ( address != null){
                        String [] adressList = address.split(":");
                        stringRedisTemplate.killClient(adressList[0], Integer.parseInt(adressList[1]));

                    }
                });
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
